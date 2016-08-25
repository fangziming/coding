/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 期次班级辅导老师Entity
 * @author sheungxin
 * @version 2016-06-21
 */
public class IssueClassesTeacher extends DataEntity<IssueClassesTeacher> {
	
	private static final long serialVersionUID = 1L;
	private String classId;		// 班级编号
	private Teachers teacher;		// 辅导老师编号
	
	public IssueClassesTeacher() {
		super();
	}

	public IssueClassesTeacher(String id){
		super(id);
	}
	
	@NotNull(message="辅导老师编号不能为空")
	public Teachers getTeacher() {
		return teacher;
	}

	public void setTeacher(Teachers teacher) {
		this.teacher = teacher;
	}

	@Length(min=1, max=64, message="班级编号长度必须介于 1 和 64 之间")
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
}