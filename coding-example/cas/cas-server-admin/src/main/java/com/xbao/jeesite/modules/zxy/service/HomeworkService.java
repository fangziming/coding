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
import com.thinkgem.jeesite.modules.zxy.entity.Homework;
import com.thinkgem.jeesite.modules.zxy.dao.HomeworkDao;

/**
 * 作业管理Service
 * @author sheungxin
 * @version 2016-06-06
 */
@Service
@Transactional(readOnly = true)
public class HomeworkService extends CrudService<HomeworkDao, Homework> {
	@Autowired
	private ChapterService chapterService;
	
	public Homework get(String id) {
		return super.get(id);
	}
	
	public List<Homework> findList(Homework homework) {
		return super.findList(homework);
	}
	
	public Page<Homework> findPage(Page<Homework> page, Homework homework) {
		return super.findPage(page, homework);
	}
	
	@Transactional(readOnly = false)
	public void save(Homework homework) {
		super.save(homework);
	}
	
	@Transactional(readOnly = false)
	public void delete(Homework homework) {
		super.delete(homework);
	}
	
	@Transactional(readOnly = false)
	public int deleteAndCheck(Homework homework) {
		int flag=0;
		List<Chapter> chapterList=chapterService.getByObjId("1", homework.getId());
		if(chapterList!=null&&chapterList.size()>0){
			flag=1;
		}else{
			delete(homework);
		}
		return flag;
	}
	
	@Transactional(readOnly = false)
	public int saveAndCheck(Homework homework) {
		int flag=0;
		if(super.dao.getByName(homework)!=null){
			flag=1;
		}else{
			save(homework);
		}
		return flag;
	}
}