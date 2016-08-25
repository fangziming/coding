/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.zxy.entity.Chapter;
import com.thinkgem.jeesite.modules.zxy.entity.LiveCourse;
import com.thinkgem.jeesite.modules.zxy.utils.GenseeUtils;
import com.thinkgem.jeesite.modules.zxy.dao.LiveCourseDao;

/**
 * 直播管理Service
 * @author sheungxin
 * @version 2016-06-12
 */
@Service
@Transactional(readOnly = true)
public class LiveCourseService extends CrudService<LiveCourseDao, LiveCourse> {
	@Autowired
	private ChapterService chapterService;
	
	public LiveCourse get(String id) {
		return super.get(id);
	}
	
	public List<LiveCourse> findList(LiveCourse liveCourse) {
		return super.findList(liveCourse);
	}
	
	public Page<LiveCourse> findPage(Page<LiveCourse> page, LiveCourse liveCourse) {
		return super.findPage(page, liveCourse);
	}
	
	@Transactional(readOnly = false)
	public void save(LiveCourse liveCourse) {
		//创建直播
		if(liveCourse.getIsNewRecord()){
			Map<String, Object> result=GenseeUtils.createTrainingRoom(liveCourse.getName(),liveCourse.getStartdate().getTime());
			if(result!=null){
				liveCourse.setCode((String)result.get("code"));
				liveCourse.setMessage((String)result.get("message"));
				if("0".equals((String)result.get("code"))){
					liveCourse.setSdkId((String)result.get("id"));
					liveCourse.setNumber((String)result.get("number"));
					liveCourse.setAssistanttoken((String)result.get("assistantToken"));
					liveCourse.setStudenttoken((String)result.get("studentToken"));
					liveCourse.setTeachertoken((String)result.get("teacherToken"));
					liveCourse.setStudentclienttoken((String)result.get("studentClientToken"));
					liveCourse.setWebjoin((Boolean)result.get("webJoin")==true?"1":"0");
					liveCourse.setClientjoin((Boolean)result.get("clientJoin")==true?"1":"0");
					if(result.get("invalidDate")!=null){
						liveCourse.setInvaliddate(new Date(Integer.parseInt((String)result.get("invalidDate"))));
					}
					liveCourse.setTeacherjoinurl((String)result.get("teacherJoinUrl"));
					liveCourse.setStudentjoinurl((String)result.get("studentJoinUrl"));
				}
			}else{
				liveCourse.setCode("-2");
				liveCourse.setMessage("请求展视互动失败");
			}
		}else{//修改直播信息
			Map<String, Object> result=GenseeUtils.modifyTrainingRoom(liveCourse.getSdkId(),liveCourse.getName(),liveCourse.getStartdate().getTime());
			if(result!=null){
				if(!"0".equals((String)result.get("code"))){
					liveCourse.setCode((String)result.get("code"));
					liveCourse.setMessage((String)result.get("message"));
				}
			}else{
				liveCourse.setCode("-2");
				liveCourse.setMessage("请求展视互动失败");
			}
		}
		//请求展示互动成功，更新本地直播信息
		if("0".equals(liveCourse.getCode())){
			super.save(liveCourse);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(LiveCourse liveCourse) {
		super.delete(liveCourse);
	}
	
	@Transactional(readOnly = false)
	public int deleteAndCheck(LiveCourse liveCourse) {
		int flag=0;
		List<Chapter> chapterList=chapterService.getByObjId("2", liveCourse.getId());
		if(chapterList!=null&&chapterList.size()>0){
			flag=1;
		}else{
			if(liveCourse.getStartdate().getTime()<System.currentTimeMillis()){
				delete(liveCourse);
				GenseeUtils.deleteTrainingRoom(liveCourse.getSdkId());
			}else{
				flag=2;
			}
		}
		return flag;
	}
}