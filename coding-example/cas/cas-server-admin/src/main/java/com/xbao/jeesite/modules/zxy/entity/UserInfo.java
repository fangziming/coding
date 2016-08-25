/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 学生管理Entity
 * @author sheungxin
 * @version 2016-06-14
 */
public class UserInfo extends DataEntity<UserInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 真实姓名
	private String nickname;		// 昵称
	private String sex;		// 性别
	private String photo;		// 头像
	private String identityNumber;		// 身份证号
	private String eduMajor;		// 专业
	private String highEducation;		// 最高学历
	private Date birthday;		// 出生年月
	private String tel;		// 联系电话
	private String telchecked;		// 手机校验
	private String email;		// 邮箱
	private String emailchecked;		// 邮箱校验
	private String qq;		// QQ
	private String area;		// 所在地区
	private String address;		// 详址
	private String currentstatus;		// 目前状态
	private String ismarried;		// 是否结婚
	private String unit;		// 目前学校或单位
	private String university;		// 毕业院校
	private Date startWorkyear;		// 开始工作时间
	private String intention;		// 求职意向
	private String blogUrl;		// 技术博客地址
	private String constellation;		// 星座
	
	public UserInfo() {
		super();
	}

	public UserInfo(String id){
		super(id);
	}

	@Length(min=0, max=16, message="真实姓名长度必须介于 0 和 16 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="昵称长度必须介于 1 和 64 之间")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="头像长度必须介于 0 和 255 之间")
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@Length(min=0, max=32, message="身份证号长度必须介于 0 和 32 之间")
	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}
	
	@Length(min=0, max=1, message="专业长度必须介于 0 和 1 之间")
	public String getEduMajor() {
		return eduMajor;
	}

	public void setEduMajor(String eduMajor) {
		this.eduMajor = eduMajor;
	}
	
	@Length(min=0, max=1, message="最高学历长度必须介于 0 和 1 之间")
	public String getHighEducation() {
		return highEducation;
	}

	public void setHighEducation(String highEducation) {
		this.highEducation = highEducation;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Length(min=0, max=32, message="联系电话长度必须介于 0 和 32 之间")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Length(min=0, max=1, message="手机校验长度必须介于 0 和 1 之间")
	public String getTelchecked() {
		return telchecked;
	}

	public void setTelchecked(String telchecked) {
		this.telchecked = telchecked;
	}
	
	@Length(min=0, max=32, message="邮箱长度必须介于 0 和 32 之间")
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=1, message="邮箱校验长度必须介于 0 和 1 之间")
	public String getEmailchecked() {
		return emailchecked;
	}

	public void setEmailchecked(String emailchecked) {
		this.emailchecked = emailchecked;
	}
	
	@Length(min=0, max=16, message="QQ长度必须介于 0 和 16 之间")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@Length(min=0, max=1, message="所在地区长度必须介于 0 和 1 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Length(min=0, max=255, message="详址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=1, message="目前状态长度必须介于 0 和 1 之间")
	public String getCurrentstatus() {
		return currentstatus;
	}

	public void setCurrentstatus(String currentstatus) {
		this.currentstatus = currentstatus;
	}
	
	@Length(min=0, max=1, message="是否结婚长度必须介于 0 和 1 之间")
	public String getIsmarried() {
		return ismarried;
	}

	public void setIsmarried(String ismarried) {
		this.ismarried = ismarried;
	}
	
	@Length(min=0, max=64, message="目前学校或单位长度必须介于 0 和 64 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=64, message="毕业院校长度必须介于 0 和 64 之间")
	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartWorkyear() {
		return startWorkyear;
	}

	public void setStartWorkyear(Date startWorkyear) {
		this.startWorkyear = startWorkyear;
	}
	
	@Length(min=0, max=255, message="求职意向长度必须介于 0 和 255 之间")
	public String getIntention() {
		return intention;
	}

	public void setIntention(String intention) {
		this.intention = intention;
	}
	
	@Length(min=0, max=255, message="技术博客地址长度必须介于 0 和 255 之间")
	public String getBlogUrl() {
		return blogUrl;
	}

	public void setBlogUrl(String blogUrl) {
		this.blogUrl = blogUrl;
	}
	
	@Length(min=0, max=1, message="星座长度必须介于 0 和 1 之间")
	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	
}