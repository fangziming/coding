/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 班级学生管理Entity
 * @author sheungxin
 * @version 2016-08-02
 */
public class WclassStudents extends DataEntity<WclassStudents> {
	
	private static final long serialVersionUID = 1L;
	private IssueClasses wclass;		// 班级信息
	private UserInfo student;		// 学生信息
	private String learningtime;		// 学习时长
	private String level;		// 综合级别
	
	public WclassStudents() {
		super();
	}

	public WclassStudents(String id){
		super(id);
	}
	
	public IssueClasses getWclass() {
		return wclass;
	}

	public void setWclass(IssueClasses wclass) {
		this.wclass = wclass;
	}

	public UserInfo getStudent() {
		return student;
	}

	public void setStudent(UserInfo student) {
		this.student = student;
	}

	@Length(min=1, max=11, message="学习时长长度必须介于 1 和 11 之间")
	public String getLearningtime() {
		return learningtime;
	}

	public void setLearningtime(String learningtime) {
		this.learningtime = learningtime;
	}
	
	@Length(min=0, max=1, message="综合级别长度必须介于 0 和 1 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
}