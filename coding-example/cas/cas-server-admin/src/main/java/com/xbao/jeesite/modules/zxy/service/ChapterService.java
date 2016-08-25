/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.zxy.entity.Chapter;
import com.thinkgem.jeesite.modules.zxy.entity.Course;
import com.thinkgem.jeesite.modules.zxy.dao.ChapterDao;
import com.thinkgem.jeesite.modules.zxy.dao.CourseDao;

/**
 * 小节管理Service
 * @author sheungxin
 * @version 2016-05-29
 */
@Service
@Transactional(readOnly = true)
public class ChapterService extends CrudService<ChapterDao, Chapter> {
	@Autowired
	private CourseDao courseDao;
	
	public Chapter get(String id) {
		return super.get(id);
	}
	
	public List<Chapter> findList(Chapter chapter) {
		return super.findList(chapter);
	}
	
	public Page<Chapter> findPage(Page<Chapter> page, Chapter chapter) {
		return super.findPage(page, chapter);
	}
	
	@Transactional(readOnly = false)
	public void save(Chapter chapter) {
		Course course=new Course(chapter.getCourseId());
		if(chapter.getType().equals("0")){
			course.setLessoncnt("1");
		}else if(chapter.getType().equals("1")){
			course.setHomeworkCnt("1");
		}else{
			course.setLivecourseCnt("1");
		}
		if(!chapter.getIsNewRecord()){
			Chapter oldChapter=get(chapter.getId());
			if(!chapter.getType().equals(oldChapter.getType())){
				if(oldChapter.getType().equals("0")){
					course.setLessoncnt("-1");
				}else if(oldChapter.getType().equals("1")){
					course.setHomeworkCnt("-1");
				}else{
					course.setLivecourseCnt("-1");
				}
			}
		}
		
		course.preUpdate();
		courseDao.update(course);
		super.save(chapter);
	}
	
	@Transactional(readOnly = false)
	public int saveAndCheck(Chapter chapter) {
		int flag=0;
		if(super.dao.getByName(chapter)!=null){
			flag=1;
		}else{
			save(chapter);
		}
		return flag;
	}
	
	@Transactional(readOnly = false)
	public void delete(Chapter chapter) {
		super.delete(chapter);
		Course course=new Course(chapter.getCourseId());
		if(chapter.getType().equals("0")){
			course.setLessoncnt("-1");
		}else if(chapter.getType().equals("1")){
			course.setHomeworkCnt("-1");
		}else{
			course.setLivecourseCnt("-1");
		}
		course.preUpdate();
		courseDao.update(course);
	}
	
	public List<Chapter> getByObjId(String type,String objid){
		Chapter chapter=new Chapter();
		chapter.setType(type);
		chapter.setObjid(objid);
		return super.dao.getByObjId(chapter);
	}
}