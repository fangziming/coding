/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 直播管理Entity
 * @author sheungxin
 * @version 2016-06-12
 */
public class LiveCourse extends DataEntity<LiveCourse> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 直播课程名称
	private String sdkId;		// 直播课堂编号
	private String lciKind;		// 直播课程类型
	private String majorId;		// 所属专业
	private String company;		// 主讲企业
	private String lecturer;		// 主讲人
	private String viewcnt;		// 观看人数
	private String vedioUrl;		// 直播课录播文件
	private String videosrtUrl;		// 直播课录播字幕
	private String videopicUrl;		// 直播课录播封面
	private String videobigpicUrl;		// 直播课轮播封面
	private String number;		// 课堂编号
	private String assistanttoken;		// 助教口令
	private String studenttoken;		// 学生口令
	private String teachertoken;		// 老师口令
	private String studentclienttoken;		// 学生客户端口令
	private Date startdate;		// 预期开始时间
	private String webjoin;		// 是否允许web端加入
	private String clientjoin;		// 是否允许客户端加入
	private Date invaliddate;		// 失效时间
	private String teacherjoinurl;		// 讲师/助教加入地址
	private String studentjoinurl;		// 学员加入地址
	private String code;		// code
	private String message;		// 结果说明
	private String introduce;		// 课程介绍
	private String lecturerpicUrl;		// 讲师头像
	private String lecturerIntroduce;		// 讲师介绍
	
	public LiveCourse() {
		super();
	}

	public LiveCourse(String id){
		super(id);
	}

	@Length(min=1, max=64, message="直播课程名称长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=16, message="直播课堂编号长度必须介于 0 和 16 之间")
	public String getSdkId() {
		return sdkId;
	}

	public void setSdkId(String sdkId) {
		this.sdkId = sdkId;
	}
	
	@Length(min=1, max=1, message="直播课程类型长度必须介于 1 和 1 之间")
	public String getLciKind() {
		return lciKind;
	}

	public void setLciKind(String lciKind) {
		this.lciKind = lciKind;
	}
	
	@Length(min=1, max=64, message="所属专业长度必须介于 1 和 64 之间")
	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}
	
	@Length(min=1, max=64, message="主讲企业长度必须介于 1 和 64 之间")
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@Length(min=0, max=64, message="主讲人长度必须介于 0 和 64 之间")
	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}
	
	@Length(min=1, max=11, message="观看人数长度必须介于 1 和 11 之间")
	public String getViewcnt() {
		return viewcnt;
	}

	public void setViewcnt(String viewcnt) {
		this.viewcnt = viewcnt;
	}
	
	@Length(min=0, max=255, message="直播课录播文件长度必须介于 0 和 255 之间")
	public String getVedioUrl() {
		return vedioUrl;
	}

	public void setVedioUrl(String vedioUrl) {
		this.vedioUrl = vedioUrl;
	}
	
	@Length(min=0, max=255, message="直播课录播字幕长度必须介于 0 和 255 之间")
	public String getVideosrtUrl() {
		return videosrtUrl;
	}

	public void setVideosrtUrl(String videosrtUrl) {
		this.videosrtUrl = videosrtUrl;
	}
	
	@Length(min=0, max=255, message="直播课录播封面长度必须介于 0 和 255 之间")
	public String getVideopicUrl() {
		return videopicUrl;
	}

	public void setVideopicUrl(String videopicUrl) {
		this.videopicUrl = videopicUrl;
	}
	
	@Length(min=0, max=255, message="直播课轮播封面长度必须介于 0 和 255 之间")
	public String getVideobigpicUrl() {
		return videobigpicUrl;
	}

	public void setVideobigpicUrl(String videobigpicUrl) {
		this.videobigpicUrl = videobigpicUrl;
	}
	
	@Length(min=0, max=16, message="课堂编号长度必须介于 0 和 16 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=0, max=16, message="助教口令长度必须介于 0 和 16 之间")
	public String getAssistanttoken() {
		return assistanttoken;
	}

	public void setAssistanttoken(String assistanttoken) {
		this.assistanttoken = assistanttoken;
	}
	
	@Length(min=0, max=16, message="学生口令长度必须介于 0 和 16 之间")
	public String getStudenttoken() {
		return studenttoken;
	}

	public void setStudenttoken(String studenttoken) {
		this.studenttoken = studenttoken;
	}
	
	@Length(min=0, max=16, message="老师口令长度必须介于 0 和 16 之间")
	public String getTeachertoken() {
		return teachertoken;
	}

	public void setTeachertoken(String teachertoken) {
		this.teachertoken = teachertoken;
	}
	
	@Length(min=0, max=16, message="学生客户端口令长度必须介于 0 和 16 之间")
	public String getStudentclienttoken() {
		return studentclienttoken;
	}

	public void setStudentclienttoken(String studentclienttoken) {
		this.studentclienttoken = studentclienttoken;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="预期开始时间不能为空")
	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	@Length(min=0, max=1, message="是否允许web端加入长度必须介于 0 和 1 之间")
	public String getWebjoin() {
		return webjoin;
	}

	public void setWebjoin(String webjoin) {
		this.webjoin = webjoin;
	}
	
	@Length(min=0, max=1, message="是否允许客户端加入长度必须介于 0 和 1 之间")
	public String getClientjoin() {
		return clientjoin;
	}

	public void setClientjoin(String clientjoin) {
		this.clientjoin = clientjoin;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInvaliddate() {
		return invaliddate;
	}

	public void setInvaliddate(Date invaliddate) {
		this.invaliddate = invaliddate;
	}
	
	@Length(min=0, max=255, message="讲师/助教加入地址长度必须介于 0 和 255 之间")
	public String getTeacherjoinurl() {
		return teacherjoinurl;
	}

	public void setTeacherjoinurl(String teacherjoinurl) {
		this.teacherjoinurl = teacherjoinurl;
	}
	
	@Length(min=0, max=255, message="学员加入地址长度必须介于 0 和 255 之间")
	public String getStudentjoinurl() {
		return studentjoinurl;
	}

	public void setStudentjoinurl(String studentjoinurl) {
		this.studentjoinurl = studentjoinurl;
	}
	
	@Length(min=0, max=1, message="code长度必须介于 0 和 1 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=255, message="结果说明长度必须介于 0 和 255 之间")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Length(min=1, max=512, message="课程介绍长度必须介于 1 和 512 之间")
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	@Length(min=1, max=255, message="讲师头像长度必须介于 1 和 255 之间")
	public String getLecturerpicUrl() {
		return lecturerpicUrl;
	}

	public void setLecturerpicUrl(String lecturerpicUrl) {
		this.lecturerpicUrl = lecturerpicUrl;
	}
	
	@Length(min=1, max=512, message="讲师介绍长度必须介于 1 和 512 之间")
	public String getLecturerIntroduce() {
		return lecturerIntroduce;
	}

	public void setLecturerIntroduce(String lecturerIntroduce) {
		this.lecturerIntroduce = lecturerIntroduce;
	}
	
}