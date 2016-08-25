/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 作业管理Entity
 * @author sheungxin
 * @version 2016-06-06
 */
public class Homework extends DataEntity<Homework> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 作业标题
	private String attachment;		// 作业附件
	private ResourseType resourseType;	//资源分类
	
	public Homework() {
		super();
	}

	public Homework(String id){
		super(id);
	}

	public ResourseType getResourseType() {
		return resourseType;
	}

	public void setResourseType(ResourseType resourseType) {
		this.resourseType = resourseType;
	}
	
	@Length(min=1, max=64, message="作业标题长度必须介于 1 和 64 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="作业附件长度必须介于 0 和 255 之间")
	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
}