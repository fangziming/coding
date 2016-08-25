/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 班级管理Entity
 * @author sheungxin
 * @version 2016-06-21
 */
public class IssueClasses extends DataEntity<IssueClasses> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 班级名称
	private String issueId;		// 所属期次
	private Date startdate;		// 开班时间
	private Date enddate;		// 结班时间
	private String studentnum;		// 预期班级人数
	private String presentstunum;		// 当前班级人数
	private String stuRate;		// 当前进度
	private List<IssueClassesTeacher> teacherList; //辅导老师
	private String teacherStr;//辅导老师，逗号隔开

	public IssueClasses() {
		super();
	}

	public IssueClasses(String id){
		super(id);
	}
	
	public List<IssueClassesTeacher> getTeacherList() {
		return teacherList;
	}

	public void setTeacherList(List<IssueClassesTeacher> teacherList) {
		this.teacherList = teacherList;
	}

	public String getTeacherStr() {
		return teacherStr;
	}

	public void setTeacherStr(String teacherStr) {
		this.teacherStr = teacherStr;
	}

	@Length(min=1, max=64, message="班级名称长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="所属期次长度必须介于 1 和 64 之间")
	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开班时间不能为空")
	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结班时间不能为空")
	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	@Length(min=1, max=5, message="预期班级人数长度必须介于 1 和 5 之间")
	public String getStudentnum() {
		return studentnum;
	}

	public void setStudentnum(String studentnum) {
		this.studentnum = studentnum;
	}
	
	@Length(min=1, max=5, message="当前班级人数长度必须介于 1 和 5 之间")
	public String getPresentstunum() {
		return presentstunum;
	}

	public void setPresentstunum(String presentstunum) {
		this.presentstunum = presentstunum;
	}
	
	public String getStuRate() {
		return stuRate;
	}

	public void setStuRate(String stuRate) {
		this.stuRate = stuRate;
	}
	
	public String toFdTeachers(){
		if(teacherList!=null){
			int i=0;
			StringBuilder builder=new StringBuilder();
			for(IssueClassesTeacher t:teacherList){
				builder.append(t.getTeacher().getId());
				if(i<teacherList.size()-1){
					builder.append(",");
				}
			}
			teacherStr=builder.toString();
		}
		return teacherStr;
	}
}