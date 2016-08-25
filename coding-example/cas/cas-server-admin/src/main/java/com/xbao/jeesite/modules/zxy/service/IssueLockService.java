package com.thinkgem.jeesite.modules.zxy.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.zxy.dao.IssueLockDao;
import com.thinkgem.jeesite.modules.zxy.entity.IssueLock;

/**
 * 期次关卡设计管理Service
 * @author sheungxin
 * @version 2016-05-29
 */
@Service
@Transactional(readOnly = true)
public class IssueLockService extends CrudService<IssueLockDao, IssueLock>{
	@Autowired
	private IssueTeacherService issueTeacherService;
	
	public IssueLock get(String id) {
		return super.get(id);
	}
	
	public List<IssueLock> findListByIssueId(String issueId) {
		IssueLock issueLock=new IssueLock();
		issueLock.setIssueId(issueId);
		return super.findList(issueLock);
	}
	
	public Page<IssueLock> findPage(Page<IssueLock> page, IssueLock issueLock) {
		return super.findPage(page, issueLock);
	}
	
	@Transactional(readOnly = false)
	public void saveOrUpdate(IssueLock issueLock) {
		super.save(issueLock);
	}
	
	@Transactional(readOnly = false)
	public void delete(IssueLock issueLock) {
		super.delete(issueLock);
	}
}
