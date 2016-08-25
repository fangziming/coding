/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.zxy.entity.Locks;

/**
 * 关卡管理DAO接口
 * @author sheungxin
 * @version 2016-06-15
 */
@MyBatisDao
public interface LocksDao extends CrudDao<Locks> {
	
	public Locks checkExist(Locks lock);
	
}