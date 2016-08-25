/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 视频管理Entity
 * @author sheungxin
 * @version 2016-06-06
 */
public class Vedio extends DataEntity<Vedio> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 视频名称
	private String duration;		// 视频时长
	private String url;		// 视频地址
	private String status;		// 视频状态
	
	private ResourseType resourseType;	//资源分类
	
	private String code;
	private String tag;
	private String catalog;
	
	public ResourseType getResourseType() {
		return resourseType;
	}

	public void setResourseType(ResourseType resourseType) {
		this.resourseType = resourseType;
	}
	
	public Vedio() {
		super();
	}

	public Vedio(String id){
		super(id);
	}

	@Length(min=1, max=64, message="视频名称长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=10, message="视频时长长度必须介于 0 和 10 之间")
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	@Length(min=0, max=225, message="视频地址长度必须介于 0 和 225 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=1, max=99, message="视频状态长度必须介于 1 和 99 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	
	@Override
	public String toString() {
		return "[catalog:"+this.getCatalog()+",code:"+this.getCode()+",duration:"+this.getDuration()+",status:"+this.getStatus()+",url:"+this.getUrl()+"]";
	}
	
}