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
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.zxy.entity.UserInfo;
import com.thinkgem.jeesite.modules.zxy.dao.UserInfoDao;

/**
 * 学生管理Service
 * @author sheungxin
 * @version 2016-06-14
 */
@Service
@Transactional(readOnly = true)
public class UserInfoService extends CrudService<UserInfoDao, UserInfo> {
	@Autowired
	private SystemService systemService;
	
	public UserInfo get(String id) {
		return super.get(id);
	}
	
	public List<UserInfo> findList(UserInfo userInfo) {
		return super.findList(userInfo);
	}
	
	public Page<UserInfo> findPage(Page<UserInfo> page, UserInfo userInfo) {
		return super.findPage(page, userInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(UserInfo userInfo) {
		super.save(userInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserInfo userInfo) {
		super.delete(userInfo);
	}
	
	@Transactional(readOnly = false)
	public int saveAndCheck(UserInfo userInfo) {
		int flag=0;
		if(StringUtils.isBlank(userInfo.getTel())&&StringUtils.isBlank(userInfo.getEmail())){
			flag=1;
		}else{
			if(StringUtils.isNoneBlank(userInfo.getTel())){
				UserInfo info=dao.getByTel(userInfo.getTel());
				if(info!=null&&!info.getId().equals(userInfo.getId())){
					flag=2;
				}
			}
			if(StringUtils.isNoneBlank(userInfo.getEmail())){
				UserInfo info=dao.getByEmail(userInfo.getEmail());
				if(info!=null&&!info.getId().equals(userInfo.getId())){
					flag=3;
				}
			}
			if(flag==0){
				super.save(userInfo);
				systemService.saveUser(userInfo);
			}
		}
		return flag;
	}
}