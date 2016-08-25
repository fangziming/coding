/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.zxy.entity.UserIssue;
import com.thinkgem.jeesite.modules.zxy.dao.UserIssueDao;

/**
 * 用户期次Service
 * @author sheungxin
 * @version 2016-07-26
 */
@Service
@Transactional(readOnly = true)
public class UserIssueService extends CrudService<UserIssueDao, UserIssue> {

	public UserIssue get(String id) {
		return super.get(id);
	}
	
	public List<UserIssue> findList(UserIssue userIssue) {
		return super.findList(userIssue);
	}
	
	public Page<UserIssue> findPage(Page<UserIssue> page, UserIssue userIssue) {
		return super.findPage(page, userIssue);
	}
	
	@Transactional(readOnly = false)
	public void save(UserIssue userIssue) {
		super.save(userIssue);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserIssue userIssue) {
		super.delete(userIssue);
	}
	
}