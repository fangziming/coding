/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户期次Entity
 * @author sheungxin
 * @version 2016-07-26
 */
public class UserIssue extends DataEntity<UserIssue> {
	
	private static final long serialVersionUID = 1L;
	
	public UserIssue() {
		super();
	}

	public UserIssue(String id){
		super(id);
	}

}