/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 课程讲师Entity
 * @author sheungxin
 * @version 2016-06-17
 */
public class CourseTeachers extends DataEntity<CourseTeachers> {
	
	private static final long serialVersionUID = 1L;
	private String courseId;		// 课程编号
	private Teachers teacher;		// 老师编号
	
	public CourseTeachers() {
		super();
	}

	public CourseTeachers(String id){
		super(id);
	}

	@Length(min=1, max=64, message="课程编号长度必须介于 1 和 64 之间")
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	@NotNull(message="老师编号不能为空")
	public Teachers getTeacher() {
		return teacher;
	}

	public void setTeacher(Teachers teacher) {
		this.teacher = teacher;
	}
	
}