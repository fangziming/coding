/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 老师管理Entity
 * @author sheungxin
 * @version 2016-06-15
 */
public class Teachers extends DataEntity<Teachers> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户信息
	private String name;		// 姓名
	private String title;	//头衔
	private String photo;		// 头像
	private String type;		// 类型

	public Teachers() {
		super();
	}

	public Teachers(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=64, message="姓名长度必须介于 1 和 16 之间")
	public String getName() {
		return name;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=64, message="头衔长度必须介于 0 和 64 之间")
	public String getTitle() {
		return title;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="头像长度必须介于 0 和 255 之间")
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@Length(min=1, max=1, message="类型长度必须介于 1 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}