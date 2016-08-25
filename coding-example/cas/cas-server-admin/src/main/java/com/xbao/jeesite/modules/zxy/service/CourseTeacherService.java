package com.thinkgem.jeesite.modules.zxy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.zxy.dao.CourseTeachersDao;
import com.thinkgem.jeesite.modules.zxy.entity.CourseTeachers;
import com.thinkgem.jeesite.modules.zxy.entity.Teachers;

/**
 * 课程老师管理Service
 * @author sheungxin
 * @version 2016-05-29
 */
@Service
@Transactional(readOnly = true)
public class CourseTeacherService extends CrudService<CourseTeachersDao, CourseTeachers>{

	public CourseTeachers get(String id) {
		return super.get(id);
	}
	
	/**
	 * 获取期次老师list
	 * @param issueId
	 * @return
	 */
	public List<CourseTeachers> findListByCourseId(String courseId) {
		CourseTeachers bean=new CourseTeachers();
		bean.setCourseId(courseId);
		return super.findList(bean);
	}
	
	@Transactional(readOnly = false)
	public void save(CourseTeachers bean) {
		super.save(bean);
	}
	
	/**
	 * 保存课程老师关系
	 * @param fdTeachers 多个逗号隔开
	 * @param issueId
	 */
	@Transactional(readOnly = false)
	public void save(String teachers,String courseId) {
		if(StringUtils.isNoneBlank(teachers)){
			CourseTeachers condition=new CourseTeachers();
			condition.setCourseId(courseId);
			delete(condition);
			for(String tid:teachers.split(",")){
				CourseTeachers bean=new CourseTeachers();
				bean.setCourseId(courseId);
				bean.setTeacher(new Teachers(tid));
				save(bean);
			}
		}
	}
	
	/**
	 * 增量更新课程老师，
	 * @param teachers 课程老师，多个逗号隔开
	 * @param courseId 课程编号
	 */
	@Transactional(readOnly = false)
	public void update(String teachers,String courseId) {
		if(StringUtils.isNoneBlank(teachers)){
			for(String tid:teachers.split(",")){
				CourseTeachers condition=new CourseTeachers();
				condition.setCourseId(courseId);
				condition.setTeacher(new Teachers(tid));
				if(super.get(condition)==null){
					CourseTeachers bean=new CourseTeachers();
					bean.setCourseId(courseId);
					bean.setTeacher(new Teachers(tid));
					save(bean);
				}
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(CourseTeachers bean) {
		super.delete(bean);
	}
}
