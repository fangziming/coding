/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 关卡管理Entity
 * @author sheungxin
 * @version 2016-06-15
 */
public class Locks extends DataEntity<Locks> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 关卡名称
	private String majorId;		// 所属专业
	private Course course;		// 所属课程
	private String position;		// 关卡位置
	private String conditions;		// 过关条件
	private String isreward;		// 是否设有奖学金
	private String quizId;		// 测验编号
	private String passcnt;		// 通关人数
	private Date startdate;		// 最早开启时间
	private String topspeedDays;		// 极速闯关天数
	private String topspeedScholarship;		// 极速闯关奖学金
	
	public Locks() {
		super();
	}

	public Locks(String id){
		super(id);
	}

	@Length(min=1, max=64, message="关卡名称长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="所属专业长度必须介于 1 和 64 之间")
	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}
	
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	@Length(min=1, max=3, message="期次号长度必须介于 1 和 3 之间")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Length(min=1, max=255, message="过关条件长度必须介于 1 和 255 之间")
	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	
	@Length(min=1, max=1, message="是否设有奖学金长度必须介于 1 和 1 之间")
	public String getIsreward() {
		return isreward;
	}

	public void setIsreward(String isreward) {
		this.isreward = isreward;
	}
	
	@Length(min=0, max=64, message="测验编号长度必须介于 0 和 64 之间")
	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}
	
	@Length(min=1, max=8, message="通关人数长度必须介于 1 和 8 之间")
	public String getPasscnt() {
		return passcnt;
	}

	public void setPasscnt(String passcnt) {
		this.passcnt = passcnt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="最早开启时间不能为空")
	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	@Length(min=0, max=2, message="极速闯关天数长度必须介于 0 和 2 之间")
	public String getTopspeedDays() {
		return topspeedDays;
	}

	public void setTopspeedDays(String topspeedDays) {
		this.topspeedDays = topspeedDays;
	}
	
	public String getTopspeedScholarship() {
		return topspeedScholarship;
	}

	public void setTopspeedScholarship(String topspeedScholarship) {
		this.topspeedScholarship = topspeedScholarship;
	}
	
}