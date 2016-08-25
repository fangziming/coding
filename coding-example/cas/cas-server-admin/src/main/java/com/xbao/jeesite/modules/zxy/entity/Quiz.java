/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 测验Entity
 * @author sheungxin
 * @version 2016-06-06
 */
public class Quiz extends DataEntity<Quiz> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 测验名称
	private String attachment;		// 测验附件
	private ResourseType resourseType;	//资源分类
	
	public Quiz() {
		super();
	}

	public Quiz(String id){
		super(id);
	}

	public ResourseType getResourseType() {
		return resourseType;
	}

	public void setResourseType(ResourseType resourseType) {
		this.resourseType = resourseType;
	}
	
	@Length(min=1, max=64, message="测验名称长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="测验附件长度必须介于 0 和 255 之间")
	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
}