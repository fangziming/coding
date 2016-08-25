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
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.zxy.entity.IssueClasses;
import com.thinkgem.jeesite.modules.zxy.service.IssueClassesService;
import com.thinkgem.jeesite.modules.zxy.service.TeachersService;

/**
 * 班级管理Controller
 * @author sheungxin
 * @version 2016-06-21
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/issueClasses")
public class IssueClassesController extends BaseController {

	@Autowired
	private IssueClassesService issueClassesService;
	@Autowired
	private TeachersService teachersService;
	
	@ModelAttribute
	public IssueClasses get(@RequestParam(required=false) String id) {
		IssueClasses entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = issueClassesService.get(id);
		}
		if (entity == null){
			entity = new IssueClasses();
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:issueClasses:view")
	@RequestMapping(value = {"list", ""})
	public String list(IssueClasses issueClasses, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IssueClasses> page = issueClassesService.findPage(new Page<IssueClasses>(request, response), issueClasses); 
		model.addAttribute("page", page);
		return "modules/zxy/issueClassesList";
	}

	@RequiresPermissions("zxy:issueClasses:view")
	@RequestMapping(value = "findByIssueId")
	public String findByIssueId(@RequestParam(required=false) String issueId,HttpServletRequest request, HttpServletResponse response, Model model) {
		IssueClasses issueClasses=new IssueClasses();
		if(StringUtils.isNoneBlank(issueId)){
			issueClasses.setIssueId(issueId);
		}
		return renderString(response, issueClassesService.findList(issueClasses));
	}
	
	@RequiresPermissions("zxy:issueClasses:view")
	@RequestMapping(value = "form")
	public String form(IssueClasses issueClasses, Model model) {
		model.addAttribute("issueClasses", issueClasses);
		model.addAttribute("fdTeacher",teachersService.findList("1"));
		return "modules/zxy/issueClassesForm";
	}

	@RequiresPermissions("zxy:issueClasses:edit")
	@RequestMapping(value = "save")
	public String save(IssueClasses issueClasses, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, issueClasses)){
			return form(issueClasses, model);
		}
		int flag=issueClassesService.saveAndCheck(issueClasses);
		if(flag==0){
			addMessage(redirectAttributes, "保存班级成功");
			return "redirect:"+Global.getAdminPath()+"/zxy/issueClasses/?repage";
		}else{
			addMessage(model, "保存班级失败，该期次下此班级已创建");
			return form(issueClasses, model);
		}
	}
	
	@RequiresPermissions("zxy:issueClasses:edit")
	@RequestMapping(value = "delete")
	public String delete(IssueClasses issueClasses, RedirectAttributes redirectAttributes) {
		issueClassesService.delete(issueClasses);
		addMessage(redirectAttributes, "删除班级成功");
		return "redirect:"+Global.getAdminPath()+"/zxy/issueClasses/?repage";
	}

}