/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.zxy.entity.WclassStudents;
import com.thinkgem.jeesite.modules.zxy.dao.WclassStudentsDao;

/**
 * 班级学生管理Service
 * @author sheungxin
 * @version 2016-08-02
 */
@Service
@Transactional(readOnly = true)
public class WclassStudentsService extends CrudService<WclassStudentsDao, WclassStudents> {

	public WclassStudents get(String id) {
		return super.get(id);
	}
	
	public List<WclassStudents> findList(WclassStudents wclassStudents) {
		return super.findList(wclassStudents);
	}
	
	public Page<WclassStudents> findPage(Page<WclassStudents> page, WclassStudents wclassStudents) {
		return super.findPage(page, wclassStudents);
	}
	
	@Transactional(readOnly = false)
	public void save(WclassStudents wclassStudents) {
		super.save(wclassStudents);
	}
	
	@Transactional(readOnly = false)
	public void delete(WclassStudents wclassStudents) {
		super.delete(wclassStudents);
	}
	
}