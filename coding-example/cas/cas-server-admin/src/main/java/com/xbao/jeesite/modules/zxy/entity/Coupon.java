/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 优惠券管理Entity
 * @author sheungxin
 * @version 2016-06-14
 */
public class Coupon extends DataEntity<Coupon> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 兑换码
	private String price;		// 价格
	private Date expiredtime;		// 到期时间
	private Date usedtime;		// 使用时间
	private String status;		// 状态
	private String type;		// 优惠券类型
	private String channel;		//渠道
	private Integer batch;	//批量生成数量

	public Coupon() {
		super();
	}

	public Coupon(String id){
		super(id);
	}

	@Length(min=1, max=1, message="状态长度必须介于 1 和 1 之间")
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@Max(value=9999)
	public Integer getBatch() {
		return batch;
	}

	public void setBatch(Integer batch) {
		this.batch = batch;
	}
	
	@Length(min=0, max=25, message="兑换码长度必须介于 0 和 25 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="到期时间不能为空")
	public Date getExpiredtime() {
		return expiredtime;
	}

	public void setExpiredtime(Date expiredtime) {
		this.expiredtime = expiredtime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUsedtime() {
		return usedtime;
	}

	public void setUsedtime(Date usedtime) {
		this.usedtime = usedtime;
	}
	
	@Length(min=1, max=1, message="状态长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=1, max=1, message="优惠券类型长度必须介于 1 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}