/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 资源分类Entity
 * @author sheungxin
 * @version 2016-07-14
 */
public class ResourseType extends DataEntity<ResourseType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 分类名称
	private String pid;		// 直属上级分类编号
	private String pname;	// 直属上级分类名称
	private String pids;		// 级联上级分类编号
	private String type;		// 资源类型
	private Integer sort;	//排序
	
	public ResourseType() {
		super();
	}

	public ResourseType(String id){
		super(id);
	}
	
	@Max(value=999)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
	
	@Length(min=1, max=64, message="分类名称长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="直属上级分类编号长度必须介于 1 和 64 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Length(min=0, max=512, message="级联上级分类编号长度必须介于 1 和 512 之间")
	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}
	
	@Length(min=0, max=1, message="资源类型长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}