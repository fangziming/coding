/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 期次讲师Entity
 * @author sheungxin
 * @version 2016-06-17
 */
public class IssueTeachers extends DataEntity<IssueTeachers> {
	
	private static final long serialVersionUID = 1L;
	private String issueId;		// 期次编号
	private Teachers teacher;		// 老师编号
	
	public IssueTeachers() {
		super();
	}

	public IssueTeachers(String id){
		super(id);
	}

	@Length(min=1, max=64, message="期次编号长度必须介于 1 和 64 之间")
	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	
	@NotNull(message="老师编号不能为空")
	public Teachers getTeacher() {
		return teacher;
	}

	public void setTeacher(Teachers teacher) {
		this.teacher = teacher;
	}
	
}