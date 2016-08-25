/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 专业管理Entity
 * @author sheungxin
 * @version 2016-05-29
 */
public class Major extends DataEntity<Major> {
	
	private static final long serialVersionUID = 1L;
	private String college;		// 所属学院
	private String name;		// 专业名称
	private String logo;		// 专业Logo
	private String url;		// 详情页
	private String lockCnt;		// 关卡数
	private String lessonCnt;		// 课时数
	private String livecourseCnt;		// 专业直播课程数
	private String homeworkCnt;		// 作业数
	private String kind;		// 专业类型
	private String status;		// 专业状态
	private String version;	//专业版本

	public Major() {
		super();
	}

	public Major(String id){
		super(id);
	}

	@Length(min=1, max=1, message="所属学院长度必须介于 1 和 1 之间")
	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}
	
	@Length(min=1, max=64, message="专业名称长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="专业Logo长度必须介于 0 和 255 之间")
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	@Length(min=0, max=255, message="详情页长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=1, max=3, message="关卡数长度必须介于 1 和 3 之间")
	public String getLockCnt() {
		return lockCnt;
	}

	public void setLockCnt(String lockCnt) {
		this.lockCnt = lockCnt;
	}
	
	@Length(min=1, max=4, message="课时数长度必须介于 1 和 4 之间")
	public String getLessonCnt() {
		return lessonCnt;
	}

	public void setLessonCnt(String lessonCnt) {
		this.lessonCnt = lessonCnt;
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
	
	@Length(min=1, max=1, message="专业类型长度必须介于 1 和 1 之间")
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	
	@Length(min=1, max=1, message="专业状态长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}