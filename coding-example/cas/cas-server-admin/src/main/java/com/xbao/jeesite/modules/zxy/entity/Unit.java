/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 章节管理Entity
 * @author sheungxin
 * @version 2016-05-29
 */
public class Unit extends DataEntity<Unit> {
	
	private static final long serialVersionUID = 1L;
	private String courseId;		// 所属课程
	private String name;		// 章节名称
	private String sort;		// 排序
	
	public Unit() {
		super();
	}

	public Unit(String id){
		super(id);
	}

	@Length(min=1, max=64, message="所属课程长度必须介于 1 和 64 之间")
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	@Length(min=1, max=64, message="章节名称长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=3, message="排序长度必须介于 1 和 3 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}