/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 课程管理Entity
 * @author sheungxin
 * @version 2016-05-29
 */
public class Course extends DataEntity<Course> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 课程名称
	private String lessoncnt;		// 课时数
	private String livecourseCnt;		// 专业直播课程数
	private String homeworkCnt;		// 作业数
	private String lessontime;		// 课时时间
	private String teachers;//课程老师
	private List<CourseTeachers>  teachersList;//课程老师
	private String version;
	
	public Course() {
		super();
	}

	public Course(String id){
		super(id);
	}

	public String getTeachers() {
		return teachers;
	}

	public void setTeachers(String teachers) {
		this.teachers = teachers;
	}

	public List<CourseTeachers> getTeachersList() {
		return teachersList;
	}

	public void setTeachersList(List<CourseTeachers> teachersList) {
		this.teachersList = teachersList;
	}
	
	@Length(min=1, max=63, message="课程名称长度必须介于 1 和 63 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=3, message="课时数长度必须介于 1 和 3 之间")
	public String getLessoncnt() {
		return lessoncnt;
	}

	public void setLessoncnt(String lessoncnt) {
		this.lessoncnt = lessoncnt;
	}
	
	@Length(min=1, max=4, message="课时时间长度必须介于 1 和 4 之间")
	public String getLessontime() {
		return lessontime;
	}

	public void setLessontime(String lessontime) {
		this.lessontime = lessontime;
	}
	
	@Length(min=1, max=3, message="专业直播课程数长度必须介于 1 和 3 之间")
	public String getLivecourseCnt() {
		return livecourseCnt;
	}

	public void setLivecourseCnt(String livecourseCnt) {
		this.livecourseCnt = livecourseCnt;
	}
	
	@Length(min=1, max=3, message="作业数长度必须介于 1 和 3 之间")
	public String getHomeworkCnt() {
		return homeworkCnt;
	}

	public void setHomeworkCnt(String homeworkCnt) {
		this.homeworkCnt = homeworkCnt;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String toTeachers(){
		if(teachersList!=null){
			int i=0;
			StringBuilder builder=new StringBuilder();
			for(CourseTeachers t:teachersList){
				builder.append(t.getTeacher().getId());
				if(i<teachersList.size()-1){
					builder.append(",");
				}
			}
			teachers=builder.toString();
		}
		return teachers;
	}
}