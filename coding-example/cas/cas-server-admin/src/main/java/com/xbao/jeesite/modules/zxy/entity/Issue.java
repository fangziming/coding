/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 期次管理Entity
 * @author sheungxin
 * @version 2016-06-16
 */
public class Issue extends DataEntity<Issue> {
	
	private static final long serialVersionUID = 1L;
	private String majorId;		// 所属专业
	private String name;		// 期次名称
	private String num;		// 期次号
	private String url;		// 详情页
	private String picUrl;		// 本期封面
	private String price;		// 本期专业售价
	private String locknumdownpayment;		// 购买部分关卡数
	private String downpayment;		// 本期专业首付
	private String sellcntprepared;		// 预售数量
	private String soldcnt;		// 已售数量
	private String status;		// 状态
	private Date startdate;		// 开班时间
	private Date enddate;		// 结束时间
	private String qq;		// 本期QQ群号
	private List<IssueLock> issueLockList;//期次关卡
	private String fdTeachers;//辅导老师
	private List<IssueTeachers>  issueTeachersList;//期次辅导老师
	
	public String getFdTeachers() {
		return fdTeachers;
	}

	public void setFdTeachers(String fdTeachers) {
		this.fdTeachers = fdTeachers;
	}
	
	public List<IssueLock> getIssueLockList() {
		return issueLockList;
	}

	public void setIssueLockList(List<IssueLock> issueLockList) {
		this.issueLockList = issueLockList;
	}

	public List<IssueTeachers> getIssueTeachersList() {
		return issueTeachersList;
	}

	public void setIssueTeachersList(List<IssueTeachers> issueTeachersList) {
		this.issueTeachersList = issueTeachersList;
	}

	public Issue() {
		super();
	}

	public Issue(String id){
		super(id);
	}

	@Length(min=1, max=64, message="所属专业长度必须介于 1 和 64 之间")
	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}
	
	@Length(min=1, max=64, message="期次名称长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=3, message="期次号长度必须介于 1 和 3 之间")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@Length(min=0, max=255, message="详情页长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=255, message="本期封面长度必须介于 0 和 255 之间")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@Length(min=1, max=4, message="购买部分关卡数长度必须介于 1 和 4 之间")
	public String getLocknumdownpayment() {
		return locknumdownpayment;
	}

	public void setLocknumdownpayment(String locknumdownpayment) {
		this.locknumdownpayment = locknumdownpayment;
	}
	
	public String getDownpayment() {
		return downpayment;
	}

	public void setDownpayment(String downpayment) {
		this.downpayment = downpayment;
	}
	
	@Length(min=1, max=6, message="预售数量长度必须介于 1 和 6 之间")
	public String getSellcntprepared() {
		return sellcntprepared;
	}

	public void setSellcntprepared(String sellcntprepared) {
		this.sellcntprepared = sellcntprepared;
	}
	
	@Length(min=1, max=6, message="已售数量长度必须介于 1 和 6 之间")
	public String getSoldcnt() {
		return soldcnt;
	}

	public void setSoldcnt(String soldcnt) {
		this.soldcnt = soldcnt;
	}
	
	@Length(min=1, max=1, message="状态长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开班时间不能为空")
	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	@Length(min=0, max=16, message="本期QQ群号长度必须介于 0 和 16 之间")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	public String toFdTeachers(){
		if(issueTeachersList!=null){
			int i=0;
			StringBuilder builder=new StringBuilder();
			for(IssueTeachers t:issueTeachersList){
				builder.append(t.getTeacher().getId());
				if(i<issueTeachersList.size()-1){
					builder.append(",");
				}
			}
			fdTeachers=builder.toString();
		}
		return fdTeachers;
	}
}