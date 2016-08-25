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
import com.thinkgem.jeesite.modules.zxy.entity.Unit;
import com.thinkgem.jeesite.modules.zxy.utils.ZxyUtils;
import com.thinkgem.jeesite.modules.zxy.dao.UnitDao;

/**
 * 章节管理Service
 * @author sheungxin
 * @version 2016-05-29
 */
@Service
@Transactional(readOnly = true)
public class UnitService extends CrudService<UnitDao, Unit> {

	@Autowired ChapterService chapterService;
	
	public Unit get(String id) {
		return super.get(id);
	}
	
	public List<Unit> findList(Unit unit) {
		return super.findList(unit);
	}
	
	public Page<Unit> findPage(Page<Unit> page, Unit unit) {
		return super.findPage(page, unit);
	}
	
	@Transactional(readOnly = false)
	public void save(Unit unit) {
		super.save(unit);
		ZxyUtils.removeCache("unitList");
	}
	
	@Transactional(readOnly = false)
	public int saveAndCheck(Unit unit) {
		int flag=0;
		if(super.dao.getByName(unit)!=null){
			flag=1;
		}else{
			save(unit);
		}
		return flag;
	}
	
	@Transactional(readOnly = false)
	public void delete(Unit unit) {
		super.delete(unit);
		ZxyUtils.removeCache("unitList");
	}
	
	/**
	 * 删除时检查章节下是否存在小节
	 * @param unitId
	 */
	@Transactional(readOnly = false)
	public int deleteAndCheck(Unit unit) {
		int flag=0;
		Chapter chapter=new Chapter();
		chapter.setUnitId(unit.getId());
		List<Chapter> chapterList=chapterService.findList(chapter);
		if(chapterList!=null&&chapterList.size()>0){
			flag=1;
		}else{
			delete(unit);
		}
		return flag;
	}
}