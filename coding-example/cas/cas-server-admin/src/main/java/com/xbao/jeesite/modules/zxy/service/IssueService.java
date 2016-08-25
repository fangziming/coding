/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.zxy.entity.Chapter;
import com.thinkgem.jeesite.modules.zxy.entity.Issue;
import com.thinkgem.jeesite.modules.zxy.entity.IssueLock;
import com.thinkgem.jeesite.modules.zxy.entity.Locks;
import com.thinkgem.jeesite.modules.zxy.entity.Major;
import com.thinkgem.jeesite.modules.zxy.entity.Unit;
import com.thinkgem.jeesite.modules.zxy.dao.IssueDao;

/**
 * 期次管理Service
 * @author sheungxin
 * @version 2016-06-16
 */
@Service
@Transactional(readOnly = true)
public class IssueService extends CrudService<IssueDao, Issue> {
	
	@Autowired
	private IssueLockService issueLockService;
	@Autowired
	private IssueTeacherService issueTeacherService;
	@Autowired
	private LocksService locksService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private ChapterService chapterService;
	@Autowired
	private MajorService majorService;
	
	public Issue get(String id) {
		Issue issue = super.get(id);
		issue.setIssueLockList(issueLockService.findListByIssueId(id));
		issue.setIssueTeachersList(issueTeacherService.findListByIssueId(id));
		issue.setFdTeachers(issue.toFdTeachers());
		return issue;
	}
	
	public List<Issue> findList(Issue issue) {
		return super.findList(issue);
	}
	
	public Page<Issue> findPage(Page<Issue> page, Issue issue) {
		return super.findPage(page, issue);
	}
	
	@Transactional(readOnly = false)
	public void save(Issue issue) {
		super.save(issue);
		if(issue.getIssueLockList()!=null){
			for(IssueLock issueLock:issue.getIssueLockList()){
				issueLock.setIssueId(issue.getId());
				if(IssueLock.DEL_FLAG_NORMAL.equals(issueLock.getDelFlag())){
					issueLockService.saveOrUpdate(issueLock);
				}else{
					if(StringUtils.isNoneBlank(issueLock.getId())){
						issueLockService.delete(issueLock);
					}
				}
			}
		}
		issueTeacherService.save(issue.getFdTeachers(), issue.getId());
	}
	
	@Transactional(readOnly = false)
	public void delete(Issue issue) {
		super.delete(issue);
	}
	
	@Transactional(readOnly = false)
	public int saveAndCheck(Issue issue){
		int flag=0;
		Issue exist=dao.checkExist(issue);
		if(exist==null){
			Major major=majorService.get(issue.getMajorId());
			if(major!=null&&Integer.parseInt(major.getLockCnt())<Integer.parseInt(issue.getLocknumdownpayment())){
				flag=2;
			}else{
				if(issue.getIssueLockList()!=null){
					int lockCnt=0;
					for(IssueLock issueLock:issue.getIssueLockList()){
						lockCnt+=Integer.parseInt(issueLock.getNum());
					}
					if(Integer.parseInt(major.getLockCnt())!=lockCnt){
						flag=3;
					}
				}
			}
			if(flag==0){
				save(issue);
			}
		}else{
			flag=1;
		}
		return flag;
	}
	
	public List<Map<String, Object>> treeData(Issue issue){
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String, Object> issueMap = Maps.newHashMap();
		issueMap.put("id", issue.getId());
		issueMap.put("pId", "0");
		issueMap.put("name", issue.getName()+" 第"+issue.getNum()+"期");
		issueMap.put("isParent", true);
		issueMap.put("mid", issue.getMajorId());
		issueMap.put("level", 0);
		
		Locks lock=new Locks();
		lock.setMajorId(issue.getMajorId());
		List<Locks> lockList = locksService.findList(lock);
		issueMap.put("sort", lockList.size()+1);
		mapList.add(issueMap);
		
		for(Locks lk:lockList){
			Map<String, Object> lkMap = Maps.newHashMap();
			lkMap.put("id", lk.getId());
			lkMap.put("pId", issue.getId());
			lkMap.put("name", lk.getName());
			lkMap.put("isParent", true);
			lkMap.put("cid", lk.getCourse().getId());
			lkMap.put("level", 1);
			
			Unit unit=new Unit();
			unit.setCourseId(lk.getCourse().getId());
			List<Unit> unitList =unitService.findList(unit);
			lkMap.put("sort", unitList.size()+1);
			mapList.add(lkMap);
			
			for(Unit ut:unitList){
				Map<String, Object> utMap = Maps.newHashMap();
				utMap.put("id", lk.getId()+ut.getId());
				utMap.put("pId", lk.getId());
				utMap.put("_id", ut.getId());
				utMap.put("name", ut.getName());
				utMap.put("isParent", true);
				utMap.put("cid", lk.getCourse().getId());
				utMap.put("level", 2);
				
				
				Chapter chapter=new Chapter();
				chapter.setUnitId(ut.getId());
				List<Chapter> chapterList =chapterService.findList(chapter);
				utMap.put("sort", chapterList.size()+1);
				mapList.add(utMap);
				
				for(Chapter ct:chapterList){
					Map<String, Object> ctMap = Maps.newHashMap();
					ctMap.put("id", utMap.get("id")+ct.getId());
					ctMap.put("pId", utMap.get("id"));
					ctMap.put("_id", ct.getId());
					ctMap.put("name", ct.getName());
					ctMap.put("isParent", false);
					ctMap.put("cid", ut.getCourseId());
					ctMap.put("uid", ut.getId());
					ctMap.put("level", 3);
					ctMap.put("sort", ct.getSort());
					mapList.add(ctMap);
				}
				
			}
		}
		return mapList;
	}
}