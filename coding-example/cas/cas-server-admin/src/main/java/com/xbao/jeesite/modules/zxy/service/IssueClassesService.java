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
import com.thinkgem.jeesite.modules.zxy.entity.IssueClasses;
import com.thinkgem.jeesite.modules.zxy.dao.IssueClassesDao;

/**
 * 班级管理Service
 * @author sheungxin
 * @version 2016-06-21
 */
@Service
@Transactional(readOnly = true)
public class IssueClassesService extends CrudService<IssueClassesDao, IssueClasses> {
	@Autowired
	private IssueClassesTeacherService issueClassesTeacherService;
	@Autowired
	private IssueTeacherService issueTeacherService;
	
	public IssueClasses get(String id) {
		IssueClasses issueClasses=super.get(id);
		issueClasses.setTeacherList(issueClassesTeacherService.findList(id));
		issueClasses.setTeacherStr(issueClasses.toFdTeachers());
		return issueClasses;
	}
	
	public List<IssueClasses> findList(IssueClasses issueClasses) {
		return super.findList(issueClasses);
	}
	
	public Page<IssueClasses> findPage(Page<IssueClasses> page, IssueClasses issueClasses) {
		return super.findPage(page, issueClasses);
	}
	
	@Transactional(readOnly = false)
	public void save(IssueClasses issueClasses) {
		super.save(issueClasses);
		issueClassesTeacherService.save(issueClasses.getId(), issueClasses.getTeacherStr());
		issueTeacherService.update(issueClasses.getTeacherStr(), issueClasses.getIssueId());
	}
	
	@Transactional(readOnly = false)
	public int saveAndCheck(IssueClasses issueClasses) {
		int flag=0;
		if(dao.checkExist(issueClasses)==null){
			save(issueClasses);
		}else{
			flag=1;
		}
		return flag;
	}
	
	@Transactional(readOnly = false)
	public void delete(IssueClasses issueClasses) {
		super.delete(issueClasses);
	}
	
}