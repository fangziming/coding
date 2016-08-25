package com.thinkgem.jeesite.modules.zxy.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.zxy.dao.IssueTeachersDao;
import com.thinkgem.jeesite.modules.zxy.entity.IssueTeachers;
import com.thinkgem.jeesite.modules.zxy.entity.Teachers;

/**
 * 期次老师管理Service
 * @author sheungxin
 * @version 2016-05-29
 */
@Service
@Transactional(readOnly = true)
public class IssueTeacherService extends CrudService<IssueTeachersDao, IssueTeachers>{

	public IssueTeachers get(String id) {
		return super.get(id);
	}
	
	/**
	 * 获取期次老师list
	 * @param issueId
	 * @return
	 */
	public List<IssueTeachers> findListByIssueId(String issueId) {
		IssueTeachers issueTeacher=new IssueTeachers();
		issueTeacher.setIssueId(issueId);
		return super.findList(issueTeacher);
	}
	
	@Transactional(readOnly = false)
	public void save(IssueTeachers issueTeacher) {
		super.save(issueTeacher);
	}
	
	/**
	 * 保存期次老师关系
	 * @param fdTeachers 多个逗号隔开
	 * @param issueId
	 */
	@Transactional(readOnly = false)
	public void save(String teachers,String issueId) {
		if(StringUtils.isNoneBlank(teachers)){
			IssueTeachers condition=new IssueTeachers();
			condition.setIssueId(issueId);
			delete(condition);
			for(String tid:teachers.split(",")){
				IssueTeachers issueTeacher=new IssueTeachers();
				issueTeacher.setIssueId(issueId);
				issueTeacher.setTeacher(new Teachers(tid));
				save(issueTeacher);
			}
		}
	}
	
	/**
	 * 增量更新期次辅导老师，
	 * @param fdTeachers 辅导老师，多个逗号隔开
	 * @param issueId 期次编号
	 */
	@Transactional(readOnly = false)
	public void update(String fdTeachers,String issueId) {
		if(StringUtils.isNoneBlank(fdTeachers)){
			for(String tid:fdTeachers.split(",")){
				IssueTeachers condition=new IssueTeachers();
				condition.setIssueId(issueId);
				condition.setTeacher(new Teachers(tid));
				if(super.get(condition)==null){
					IssueTeachers issueTeacher=new IssueTeachers();
					issueTeacher.setIssueId(issueId);
					issueTeacher.setTeacher(new Teachers(tid));
					save(issueTeacher);
				}
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(IssueTeachers issueTeacher) {
		super.delete(issueTeacher);
	}
}
