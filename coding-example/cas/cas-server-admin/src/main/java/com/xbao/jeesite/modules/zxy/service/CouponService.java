/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.zxy.entity.Coupon;
import com.thinkgem.jeesite.modules.zxy.utils.NumberConfusion;
import com.thinkgem.jeesite.modules.zxy.dao.CouponDao;

/**
 * 优惠券管理Service
 * @author sheungxin
 * @version 2016-06-14
 */
@Service
@Transactional(readOnly = true)
public class CouponService extends CrudService<CouponDao, Coupon> {

	public Coupon get(String id) {
		return super.get(id);
	}
	
	public List<Coupon> findList(Coupon coupon) {
		return super.findList(coupon);
	}
	
	public Page<Coupon> findPage(Page<Coupon> page, Coupon coupon) {
		return super.findPage(page, coupon);
	}
	
	@Transactional(readOnly = false)
	public void save(Coupon coupon) {
		if(coupon.getIsNewRecord()){
			//查询当天某类优惠券已创建数量
			Coupon condition=new Coupon();
			condition.setType(coupon.getType());
			condition.setCreateDate(DateUtils.parseDate(DateUtils.getDate("yyyy-MM-dd")));
			int total=super.dao.findTodayTotal(condition);
			Integer batch=coupon.getBatch()==null?1:coupon.getBatch();
			for(int i=0;i<batch;i++){
				coupon.setCode(generateCode(Integer.parseInt(coupon.getPrice()), total+i+1, coupon.getChannel()));
				coupon.setStatus("0");
				coupon.preInsert();
				dao.insert(coupon);
				coupon.setId(null);
			}
		}else{
			super.save(coupon);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Coupon coupon) {
		super.delete(coupon);
	}

	@Transactional(readOnly = false)
	public void updateExpired(){
		Coupon coupon=new Coupon();
		coupon.setStatus("2");
		coupon.setExpiredtime(new Date());
		coupon.preUpdate();
		dao.updateExpired(coupon);
	}
	
	private String generateCode(Integer coupon_price, Integer index, String _channel){
        if(null == coupon_price)
            coupon_price = 0;
        if(null == index)
            index = 0;
        if(null == _channel)
            _channel = "W";
        
        String price_str = String.format("%04d", coupon_price);
        String today = DateUtils.getDate("yyyyMMdd").substring(2, 8);
        // 4位顺序数
        String suffixNum = String.format("%04d", index);
        // 取6位日期（yyMMdd），4位顺序数字前后各3位
        StringBuilder str_org_sbu = new StringBuilder();
        str_org_sbu.append(today.substring(0, 3));
        str_org_sbu.append(suffixNum);
        str_org_sbu.append(today.substring(3));
        String str_org = str_org_sbu.toString();
        // 数字混淆
        String str_numb = NumberConfusion.encode(Integer.parseInt(str_org));
        str_numb = price_str.concat(str_numb);
        // 添加渠道号，默认W
        String result = _channel.concat(str_numb);
        
        if(result.length() > 25)
            result = result.substring(0, 25);
        return result;
    }
}