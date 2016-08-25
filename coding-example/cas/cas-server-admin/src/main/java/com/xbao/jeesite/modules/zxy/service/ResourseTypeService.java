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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.zxy.entity.Homework;
import com.thinkgem.jeesite.modules.zxy.entity.ResourseType;
import com.thinkgem.jeesite.modules.zxy.dao.ResourseTypeDao;

/**
 * 资源分类Service
 * @author sheungxin
 * @version 2016-05-29
 */
@Service
@Transactional(readOnly = true)
public class ResourseTypeService extends CrudService<ResourseTypeDao, ResourseType> {
	
	@Autowired
	private VedioService vedioService;
	@Autowired
	private HomeworkService homeworkService;
	@Autowired
	private QuizService quizService;
	
	public ResourseType get(String id) {
		ResourseType resourseType=super.get(id);
		return resourseType;
	}
	
	public List<ResourseType> findList(ResourseType resourseType) {
		return super.findList(resourseType);
	}
	
	public Page<ResourseType> findPage(Page<ResourseType> page, ResourseType resourseType) {
		return super.findPage(page, resourseType);
	}
	
	@Transactional(readOnly = false)
	public void save(ResourseType resourseType) {
		if(StringUtils.isBlank(resourseType.getPid())){
			resourseType.setPid("0");
			resourseType.setPids("0,");
		}else if(resourseType.getPid().equals("0")){
			resourseType.setPids("0,");
		}else{
			ResourseType parent=get(new ResourseType(resourseType.getPid()));
			resourseType.setPids(parent.getPids()+parent.getId()+",");
		}
		//判断是否为修改
		if(!resourseType.getIsNewRecord()){
			//判断分类位置是否发生变化
			ResourseType old=get(new ResourseType(resourseType.getId()));
			if(!old.getPid().equals(resourseType.getPid())){
				//位置发生变化，更新所有下级节点的pids信息
				ResourseType condition=new ResourseType();
				condition.setPids(resourseType.getId());
				for(ResourseType child:findList(condition)){
					child.setPids(child.getPids().replace(old.getPid(), resourseType.getPid()));
					super.save(child);
				}
			}
		}
		super.save(resourseType);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourseType resourseType) {
		super.delete(resourseType);
	}
	
	@Transactional(readOnly = false)
	public int deleteAndCheck(ResourseType resourseType) {
		int flag=0;
		//分类下是否存在数据
		if(resourseType.getType().equals("0")){//视频
			
		}else if(resourseType.getType().equals("1")){//作业
			Homework homework=new Homework();
			homework.setResourseType(new ResourseType(resourseType.getId()));
			List<Homework> resultList= homeworkService.findList(homework);
			if(resultList!=null&&resultList.size()>0){
				flag=1;//分类下存在数据
			}
		}else{//测验
			
		}
		if(flag==0){
			delete(resourseType);
		}
		return flag;
	}

}