/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 期次关卡Entity
 * @author sheungxin
 * @version 2016-06-17
 */
public class IssueLock extends DataEntity<IssueLock> {
	
	private static final long serialVersionUID = 1L;
	private String issueId;		// 期次编号
	private String lockType;		// 关卡类型
	private String title;		// 关卡标题
	private String num;		// 关卡数

	public IssueLock() {
		super();
	}

	public IssueLock(String id){
		super(id);
	}

	@Length(min=1, max=64, message="期次编号长度必须介于 1 和 64 之间")
	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	
	@Length(min=1, max=1, message="关卡类型长度必须介于 1 和 1 之间")
	public String getLockType() {
		return lockType;
	}

	public void setLockType(String lockType) {
		this.lockType = lockType;
	}
	
	@Length(min=1, max=64, message="关卡标题长度必须介于 1 和 64 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=1, max=64, message="关卡标题长度必须介于 1 和3 之间")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
}