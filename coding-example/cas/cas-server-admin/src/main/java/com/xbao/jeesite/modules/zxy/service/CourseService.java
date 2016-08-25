/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.zxy.entity.Chapter;
import com.thinkgem.jeesite.modules.zxy.entity.Course;
import com.thinkgem.jeesite.modules.zxy.entity.Unit;
import com.thinkgem.jeesite.modules.zxy.utils.ZxyUtils;
import com.thinkgem.jeesite.modules.zxy.dao.CourseDao;

/**
 * 课程管理Service
 * @author sheungxin
 * @version 2016-05-29
 */
@Service
@Transactional(readOnly = true)
public class CourseService extends CrudService<CourseDao, Course> {

	@Autowired
	private UnitService unitService;
	@Autowired
	private ChapterService chapterService;
	@Autowired
	private CourseTeacherService courseTeacherService;
	
	public Course get(String id) {
		Course course=super.get(id);
		course.setTeachersList(courseTeacherService.findListByCourseId(id));
		course.setTeachers(course.toTeachers());
		return course;
	}
	
	public List<Course> findList(Course course) {
		return super.findList(course);
	}
	
	public Page<Course> findPage(Page<Course> page, Course course) {
		return super.findPage(page, course);
	}
	
	@Transactional(readOnly = false)
	public void save(Course course) {
		if(!course.getIsNewRecord()){
			course.setLessoncnt(null);
			course.setLessontime(null);
			course.setLivecourseCnt(null);
			course.setHomeworkCnt(null);
		}
		super.save(course);
		courseTeacherService.save(course.getTeachers(), course.getId());
		ZxyUtils.removeCache("courseList");
	}
	
	@Transactional(readOnly = false)
	public int saveAndCheck(Course course) {
		int flag=0;
		if(course.getIsNewRecord()){//add
			List<Course> courseList=super.dao.getByName(course);
			if(courseList!=null&&courseList.size()>0){
				flag=1;
			}else{
				course.setVersion("1.0");
			}
		}else{//modified
			Course obj=get(course.getId());
			if(!obj.getName().equals(course.getName())){
				List<Course> courseList=super.dao.getByName(course);
				if(courseList!=null&&courseList.size()>0){
					flag=1;
				}
			}
		}
		if(flag==0){
			save(course);
		}
		return flag;
	}
	
	@Transactional(readOnly = false)
	public void delete(Course course) {
		super.delete(course);
		ZxyUtils.removeCache("courseList");
	}
	
	@Transactional(readOnly = false)
	public int deleteAndCheck(Course course) {
		int flag=0;
		Unit unit=new Unit();
		unit.setCourseId(course.getId());
		List<Unit> unitList=unitService.findList(unit);
		if(unitList!=null&&unitList.size()>0){
			flag=1;
		}else{
			delete(course);
		}
		return flag;
	}
	
	@Transactional(readOnly = false)
	public void copy(Course course) {
		if(course!=null){
			//获取原有章节列表
			Unit unit=new Unit();
			unit.setCourseId(course.getId());
			List<Unit> unitList=unitService.findList(unit);
			//获取原有小节列表
			Chapter chapter=new Chapter();
			chapter.setCourseId(course.getId());
			List<Chapter> chapterList=chapterService.findList(chapter);
			//复制课程
			course.setId(null);
			course.setVersion((Float.parseFloat(course.getVersion())+0.1f)+"");
			save(course);
			ZxyUtils.removeCache("courseList");
			//复制章节
			Map<String, String> unitMap=new HashMap<String, String>();//新旧章节编号
			String oldKey=null;
			for(Unit obj:unitList){
				oldKey=obj.getId();
				obj.setId(null);
				obj.setCourseId(course.getId());
				unitService.save(obj);
				unitMap.put(oldKey, obj.getId());
			}
			//复制小节
			for(Chapter obj:chapterList){
				obj.setId(null);
				obj.setCourseId(course.getId());
				obj.setUnitId(unitMap.get(obj.getUnitId()));
				chapterService.save(obj);
			}
		}
	}
	
	public List<Map<String, Object>> treeData(Course course){
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String, Object> courseMap = Maps.newHashMap();
		courseMap.put("id", course.getId());
		courseMap.put("pId", "0");
		courseMap.put("name", course.getName()+" V"+course.getVersion()+"版");
		courseMap.put("isParent", true);
		courseMap.put("level", 0);
		
		Unit unit=new Unit();
		unit.setCourseId(course.getId());
		List<Unit> unitList =unitService.findList(unit);
		courseMap.put("sort", unitList.size()+1);
		mapList.add(courseMap);
		
		for(Unit ut:unitList){
			Map<String, Object> utMap = Maps.newHashMap();
			utMap.put("id", ut.getId());
			utMap.put("pId", course.getId());
			utMap.put("name", ut.getName());
			utMap.put("isParent", true);
			utMap.put("cid", course.getId());
			utMap.put("level", 1);
			
			
			Chapter chapter=new Chapter();
			chapter.setUnitId(ut.getId());
			List<Chapter> chapterList =chapterService.findList(chapter);
			utMap.put("sort", chapterList.size()+1);
			mapList.add(utMap);
			
			for(Chapter ct:chapterList){
				Map<String, Object> ctMap = Maps.newHashMap();
				ctMap.put("id", ct.getId());
				ctMap.put("pId", utMap.get("id"));
				ctMap.put("name", ct.getName());
				ctMap.put("isParent", false);
				ctMap.put("cid", ut.getCourseId());
				ctMap.put("uid", ut.getId());
				ctMap.put("level", 2);
				ctMap.put("sort", ct.getSort());
				mapList.add(ctMap);
			}
			
		}
		return mapList;
	}
}