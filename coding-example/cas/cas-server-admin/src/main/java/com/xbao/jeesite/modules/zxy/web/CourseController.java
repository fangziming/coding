/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.zxy.entity.Course;
import com.thinkgem.jeesite.modules.zxy.service.CourseService;
import com.thinkgem.jeesite.modules.zxy.service.TeachersService;

/**
 * 课程管理Controller
 * @author sheungxin
 * @version 2016-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/course")
public class CourseController extends BaseController {

	@Autowired
	private CourseService courseService;
	@Autowired
	private TeachersService teachersService;
	
	@ModelAttribute
	public Course get(@RequestParam(required=false) String id) {
		Course entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = courseService.get(id);
		}
		if (entity == null){
			entity = new Course();
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:course:view")
	@RequestMapping(value = {"index"})
	public String index(Course course, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/zxy/courseIndex";
	}
	
	@RequiresPermissions("zxy:course:view")
	@RequestMapping(value = {"list", ""})
	public String list(Course course, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Course> page = courseService.findPage(new Page<Course>(request, response), course); 
		model.addAttribute("page", page);
		return "modules/zxy/courseList";
	}

	@RequiresPermissions("zxy:course:view")
	@RequestMapping(value = "form")
	public String form(Course course, Model model) {
		model.addAttribute("course", course);
		model.addAttribute("teacherList",teachersService.findList("0"));
		return "modules/zxy/courseForm";
	}

	@RequiresPermissions("zxy:course:edit")
	@RequestMapping(value = "save")
	public String save(@RequestParam(required=false) String opType,Course course, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, course)){
			return form(course, model);
		}
		int flag=courseService.saveAndCheck(course);
		if(flag==0){
			if(StringUtils.isNoneBlank(opType)){
				addMessage(model, "保存课程成功");
				model.addAttribute("teacherList",teachersService.findList("0"));
				model.addAttribute("course", courseService.get(course.getId()));
				model.addAttribute("refresh", 0);
				return "modules/zxy/courseForm";
			}else{
				addMessage(redirectAttributes, "保存课程成功");
				return "redirect:"+Global.getAdminPath()+"/zxy/course/?repage";
			}
		}else{
			addMessage(model, "保存课程失败,该课程已存在");
			return form(course, model);
		}
	}
	
	@RequiresPermissions("zxy:course:edit")
	@RequestMapping(value = "delete")
	public String delete(Course course, RedirectAttributes redirectAttributes) {
		int flag=courseService.deleteAndCheck(course);
		if(flag==1){
			addMessage(redirectAttributes, "删除课程失败，该课程下有章节信息");
		}else{
			addMessage(redirectAttributes, "删除课程成功");
		}
		return "redirect:"+Global.getAdminPath()+"/zxy/course/?repage";
	}

	@RequiresPermissions("zxy:course:edit")
	@RequestMapping(value = "copy")
	public String copy(Course course, Model model, RedirectAttributes redirectAttributes) {
		courseService.copy(course);
		addMessage(redirectAttributes, "复制课程成功");
		return "redirect:"+Global.getAdminPath()+"/zxy/course/?repage";
	}
	
	@RequiresPermissions("zxy:course:view")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(Course course, HttpServletRequest request, HttpServletResponse response) {
		return courseService.treeData(course);
	}
}