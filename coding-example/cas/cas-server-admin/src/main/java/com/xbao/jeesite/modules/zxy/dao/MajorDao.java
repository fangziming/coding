/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.zxy.entity.Major;

/**
 * 专业管理DAO接口
 * @author sheungxin
 * @version 2016-05-29
 */
@MyBatisDao
public interface MajorDao extends CrudDao<Major> {
	
	public List<Major> getByName(Major major);
	
}