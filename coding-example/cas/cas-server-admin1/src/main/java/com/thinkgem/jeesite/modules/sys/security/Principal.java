package com.thinkgem.jeesite.modules.sys.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

public class Principal implements Serializable{

	private static final long serialVersionUID = -5657442386281467288L;
	private String id; // 编号
	private String loginName; // 登录名
	private String name; // 姓名
	private boolean mobileLogin; // 是否手机登录
	private Map<String, Object> extendMap;

	public Principal(User user, boolean mobileLogin) {
		this.id = user.getId();
		this.loginName = user.getLoginName();
		this.name = user.getName();
		this.mobileLogin = mobileLogin;
	}

	public Principal(String userId,Map<String, Object> attributes){
		this.id=(String)attributes.get("id");
		this.loginName=userId;
		this.name=(String)attributes.get("name");
	}
	
	public String getId() {
		return id;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getName() {
		return name;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}

	@JsonIgnore
	public Map<String, Object> getExtendMap() {
		if (extendMap==null){
			extendMap = new HashMap<String, Object>();
		}
		return extendMap;
	}

	/**
	 * 获取SESSIONID
	 */
	public String getSessionid() {
		try{
			return (String) UserUtils.getSession().getId();
		}catch (Exception e) {
			return "";
		}
	}
	
	@Override
	public String toString() {
		return id;
	}
}
