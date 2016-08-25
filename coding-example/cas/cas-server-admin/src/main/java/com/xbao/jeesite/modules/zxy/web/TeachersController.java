/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mongo.MongoDBUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.zxy.entity.Teachers;
import com.thinkgem.jeesite.modules.zxy.service.TeachersService;

/**
 * 老师管理Controller
 * @author sheungxin
 * @version 2016-06-15
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/teachers")
public class TeachersController extends BaseController {

	@Autowired
	private TeachersService teachersService;
	
	@ModelAttribute
	public Teachers get(@RequestParam(required=false) String id) {
		Teachers entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = teachersService.get(id);
		}
		if (entity == null){
			entity = new Teachers();
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:teachers:view")
	@RequestMapping(value = {"list", ""})
	public String list(Teachers teachers, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Teachers> page = teachersService.findPage(new Page<Teachers>(request, response), teachers); 
		model.addAttribute("page", page);
		return "modules/zxy/teachersList";
	}

	@RequiresPermissions("zxy:teachers:view")
	@RequestMapping(value = "form")
	public String form(Teachers teachers, Model model) {
		model.addAttribute("teachers", teachers);
		return "modules/zxy/teachersForm";
	}

	@RequiresPermissions("zxy:teachers:edit")
	@RequestMapping(value = "save")
	public String save(Teachers teachers, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, teachers)){
			return form(teachers, model);
		}
		try {
			if(teachers.getPhoto().contains(Global.USERFILES_BASE_URL)){
				teachers.setPhoto(MongoDBUtil.insertHugeFile(Global.getConfig("mongodb.bucket_portrait"), teachers.getPhoto()));
			}
			teachersService.save(teachers);
			addMessage(redirectAttributes, "保存老师成功");
			return "redirect:"+Global.getAdminPath()+"/zxy/teachers/?repage";
		}catch(Exception e){
			addMessage(model, "保存老师失败，上传老师头像失败");
			return form(teachers, model);
		}
	}
	
	@RequiresPermissions("zxy:teachers:edit")
	@RequestMapping(value = "delete")
	public String delete(Teachers teachers, RedirectAttributes redirectAttributes) {
		teachersService.delete(teachers);
		addMessage(redirectAttributes, "删除老师成功");
		return "redirect:"+Global.getAdminPath()+"/zxy/teachers/?repage";
	}

}