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
import com.thinkgem.jeesite.modules.zxy.entity.Locks;
import com.thinkgem.jeesite.modules.zxy.entity.Quiz;
import com.thinkgem.jeesite.modules.zxy.utils.ZxyUtils;
import com.thinkgem.jeesite.modules.zxy.dao.QuizDao;

/**
 * 测验Service
 * @author sheungxin
 * @version 2016-06-06
 */
@Service
@Transactional(readOnly = true)
public class QuizService extends CrudService<QuizDao, Quiz> {
	@Autowired
	private LocksService locksService;

	public Quiz get(String id) {
		return super.get(id);
	}
	
	public List<Quiz> findList(Quiz quiz) {
		return super.findList(quiz);
	}
	
	public Page<Quiz> findPage(Page<Quiz> page, Quiz quiz) {
		return super.findPage(page, quiz);
	}
	
	@Transactional(readOnly = false)
	public void save(Quiz quiz) {
		super.save(quiz);
		ZxyUtils.removeCache("quizList");
	}
	
	@Transactional(readOnly = false)
	public void delete(Quiz quiz) {
		super.delete(quiz);
		ZxyUtils.removeCache("quizList");
	}
	
	@Transactional(readOnly = false)
	public int deleteAndCheck(Quiz quiz) {
		int flag=0;
		Locks lock=new Locks();
		lock.setQuizId(quiz.getId());
		List<Locks> quizList=locksService.findList(lock);
		if(quizList!=null&&quizList.size()>0){
			flag=1;
		}else{
			delete(quiz);
		}
		return flag;
	}
	
	@Transactional(readOnly = false)
	public int saveAndCheck(Quiz quiz) {
		int flag=0;
		if(super.dao.getByName(quiz)!=null){
			flag=1;
		}else{
			save(quiz);
		}
		return flag;
	}
}