/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.zxy.entity.Locks;
import com.thinkgem.jeesite.modules.zxy.entity.Major;
import com.thinkgem.jeesite.modules.zxy.utils.ZxyUtils;
import com.thinkgem.jeesite.modules.zxy.dao.MajorDao;

/**
 * 专业管理Service
 * @author sheungxin
 * @version 2016-05-29
 */
@Service
@Transactional(readOnly = true)
public class MajorService extends CrudService<MajorDao, Major> {

	@Autowired
	private LocksService locksService;
	
	public Major get(String id) {
		return super.get(id);
	}
	
	public List<Major> findList(Major major) {
		return super.findList(major);
	}
	
	public Page<Major> findPage(Page<Major> page, Major major) {
		return super.findPage(page, major);
	}
	
	@Transactional(readOnly = false)
	public void save(Major major) {
		super.save(major);
		ZxyUtils.removeCache("majorList");
	}
	
	@Transactional(readOnly = false)
	public int saveAndCheck(Major major) {
		int flag=0;
		if(major.getIsNewRecord()){//add
			List<Major> majorList=super.dao.getByName(major);
			if(majorList!=null&&majorList.size()>0){
				flag=1;
			}else{
				major.setVersion("1.0");
			}
		}else{//modified
			Major obj=get(major.getId());
			if(!obj.getName().equals(major.getName())){
				List<Major> majorList=super.dao.getByName(major);
				if(majorList!=null&&majorList.size()>0){
					flag=1;
				}
			}
		}
		if(flag==0){
			save(major);
		}
		return flag;
	}
	
	@Transactional(readOnly = false)
	public void delete(Major major) {
		super.delete(major);
		ZxyUtils.removeCache("majorList");
	}
	
	@Transactional(readOnly = false)
	public void copy(Major major) {
		if(major!=null){
			//获取原专业已有关卡信息
			Locks lock=new Locks();
			lock.setMajorId(major.getId());
			List<Locks> lockList=locksService.findList(lock);
			//复制专业
			major.setId(null);
			major.setVersion((Float.parseFloat(major.getVersion())+0.1f)+"");
			save(major);
			ZxyUtils.removeCache("majorList");
			//复制关卡
			for(Locks lk:lockList){
				lk.setId(null);
				lk.setMajorId(major.getId());
				locksService.save(lk);
			}
		}
	}
}