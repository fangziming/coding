/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.xbao.modules.sys.dao;

import com.xbao.common.persistence.CrudDao;
import com.xbao.common.persistence.annotation.MyBatisDao;
import com.xbao.modules.sys.entity.Log;

/**
 * 日志DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

}
