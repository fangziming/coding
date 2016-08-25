/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.zxy.entity.Locks;
import com.thinkgem.jeesite.modules.zxy.dao.LocksDao;

/**
 * 关卡管理Service
 * @author sheungxin
 * @version 2016-06-15
 */
@Service
@Transactional(readOnly = true)
public class LocksService extends CrudService<LocksDao, Locks> {

	public Locks get(String id) {
		return super.get(id);
	}
	
	public List<Locks> findList(Locks locks) {
		return super.findList(locks);
	}
	
	public Page<Locks> findPage(Page<Locks> page, Locks locks) {
		return super.findPage(page, locks);
	}
	
	@Transactional(readOnly = false)
	public void save(Locks locks) {
		if(StringUtils.isBlank(locks.getTopspeedDays())){
			locks.setTopspeedDays(null);
		}
		if(StringUtils.isBlank(locks.getTopspeedScholarship())){
			locks.setTopspeedScholarship(null);
		}
		super.save(locks);
	}
	
	@Transactional(readOnly = false)
	public void delete(Locks locks) {
		super.delete(locks);
	}
	
	@Transactional(readOnly = false)
	public int saveAndCheck(Locks locks) {
		int flag=0;
		Locks lock=dao.checkExist(locks);
		if(lock==null){
			save(locks);
		}else{
			flag=1;
		}
		return flag;
	}

}