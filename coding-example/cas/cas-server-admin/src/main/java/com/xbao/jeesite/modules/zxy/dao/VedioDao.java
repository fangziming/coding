/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.zxy.entity.Vedio;

/**
 * 视频管理DAO接口
 * @author sheungxin
 * @version 2016-06-06
 */
@MyBatisDao
public interface VedioDao extends CrudDao<Vedio> {
	
	public Vedio getByName(Vedio vedio);
	
	public Vedio getByUrl(Vedio vedio);
	
	public void deleteAll();
	
}