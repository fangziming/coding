/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 小节管理Entity
 * @author sheungxin
 * @version 2016-05-29
 */
public class Chapter extends DataEntity<Chapter> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 小节名称
	private String type;		// 小节形式
	private String objid;		// 形式对象
	private String sort;		// 排序
	private String unitId;		// 章节编号
	private String courseId;		// 课程编号
	
	public Chapter() {
		super();
	}

	public Chapter(String id){
		super(id);
	}

	@Length(min=1, max=64, message="小节名称长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=1, message="小节形式长度必须介于 1 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=1, max=64, message="形式对象长度必须介于 1 和 64 之间")
	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}
	
	@Length(min=1, max=3, message="排序长度必须介于 1 和 3 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Length(min=1, max=64, message="章节编号长度必须介于 1 和 64 之间")
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	@Length(min=1, max=64, message="课程编号长度必须介于 1 和 64 之间")
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
}