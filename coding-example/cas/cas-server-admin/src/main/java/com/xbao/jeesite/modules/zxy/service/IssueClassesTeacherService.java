package com.thinkgem.jeesite.modules.zxy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.zxy.dao.IssueClassesTeacherDao;
import com.thinkgem.jeesite.modules.zxy.entity.IssueClassesTeacher;
import com.thinkgem.jeesite.modules.zxy.entity.Teachers;

/**
 * 班级老师管理Service
 * @author sheungxin
 * @version 2016-06-21
 */
@Service
@Transactional(readOnly = true)
public class IssueClassesTeacherService extends CrudService<IssueClassesTeacherDao, IssueClassesTeacher>{
	
	/**
	 * 保存班级老师信息
	 * @param classId 班级id
	 * @param teacherStr 老师id，多个逗号隔开
	 */
	@Transactional(readOnly = false)
	public void save(String classId,String teacherStr) {
		delete(classId);
		if(StringUtils.isNoneBlank(teacherStr)){
			for(String tid:teacherStr.split(",")){
				if(StringUtils.isNoneBlank(tid)){
					IssueClassesTeacher bean=new IssueClassesTeacher();
					bean.setClassId(classId);
					bean.setTeacher(new Teachers(tid));
					super.save(bean);
				}
			}
		}
	}

	/**
	 * 获取辅导老师列表
	 * @param classId 班级id
	 * @return
	 */
	public List<IssueClassesTeacher> findList(String classId){
		IssueClassesTeacher bean=new IssueClassesTeacher();
		bean.setClassId(classId);
		return super.findList(bean);
	}
	
	/**
	 * 删除班级老师信息
	 * @param classId 班级id
	 */
	@Transactional(readOnly = false)
	public void delete(String classId) {
		IssueClassesTeacher bean=new IssueClassesTeacher();
		bean.setClassId(classId);
		super.delete(bean);
	}
}
