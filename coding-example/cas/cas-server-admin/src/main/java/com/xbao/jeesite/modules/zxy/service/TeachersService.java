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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.zxy.entity.Teachers;
import com.thinkgem.jeesite.modules.zxy.dao.TeachersDao;

/**
 * 老师管理Service
 * @author sheungxin
 * @version 2016-06-15
 */
@Service
@Transactional(readOnly = true)
public class TeachersService extends CrudService<TeachersDao, Teachers> {
	@Autowired
	private SystemService systemService;
	
	public Teachers get(String id) {
		return super.get(id);
	}
	
	public List<Teachers> findList(Teachers teachers) {
		return super.findList(teachers);
	}
	
	public Page<Teachers> findPage(Page<Teachers> page, Teachers teachers) {
		return super.findPage(page, teachers);
	}
	
	@Transactional(readOnly = false)
	public void save(Teachers teachers) {
		if(StringUtils.isNoneBlank(teachers.getUser().getMobile())){
			systemService.saveUser(teachers);
		}
		super.save(teachers);
	}
	
	@Transactional(readOnly = false)
	public void delete(Teachers teachers) {
		super.delete(teachers);
	}
	
	/**
	 * 获取老师列表
	 * @param issueId 期次ID
	 * @param type 0：金牌讲师  	1：辅导老师
	 * @return
	 */
	public List<Teachers> findList(String type) {
		Teachers teachers=new Teachers();
		teachers.setType(type);
		return super.findList(teachers);
	}
}