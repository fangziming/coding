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
import com.thinkgem.jeesite.common.mongo.MongoDBUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.zxy.entity.Issue;
import com.thinkgem.jeesite.modules.zxy.service.IssueService;
import com.thinkgem.jeesite.modules.zxy.service.TeachersService;

/**
 * 期次管理Controller
 * @author sheungxin
 * @version 2016-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/issue")
public class IssueController extends BaseController {

	@Autowired
	private IssueService issueService;
	@Autowired
	private TeachersService teachersService;
	
	@ModelAttribute
	public Issue get(@RequestParam(required=false) String id) {
		Issue entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = issueService.get(id);
		}
		if (entity == null){
			entity = new Issue();
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:issue:view")
	@RequestMapping(value = {"index"})
	public String index(Issue issue, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/zxy/issueIndex";
	}
	
	@RequiresPermissions("zxy:issue:view")
	@RequestMapping(value = {"list", ""})
	public String list(Issue issue, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Issue> page = issueService.findPage(new Page<Issue>(request, response), issue); 
		model.addAttribute("page", page);
		return "modules/zxy/issueList";
	}  

	@RequiresPermissions("zxy:issue:view")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(Issue issue, HttpServletRequest request, HttpServletResponse response) {
		return issueService.treeData(issue);
	}
	
	@RequiresPermissions("zxy:issue:view")
	@RequestMapping(value = "form")
	public String form(Issue issue, Model model) {
		model.addAttribute("jpTeacher",teachersService.findList("0"));
		model.addAttribute("fdTeacher",teachersService.findList("1"));
		model.addAttribute("issue", issue);
		return "modules/zxy/issueForm";
	}

	@RequiresPermissions("zxy:issue:edit")
	@RequestMapping(value = "save")
	public String save(@RequestParam(required=false) String opType,Issue issue, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, issue)){
			return form(issue, model);
		}
		try {
			if(issue.getPicUrl().contains(Global.USERFILES_BASE_URL)){
				issue.setPicUrl(MongoDBUtil.insertHugeFile(Global.getConfig("mongodb.bucket_issue"), issue.getPicUrl()));
			}
			int flag=issueService.saveAndCheck(issue);
			if(flag==0){
				if(StringUtils.isNoneBlank(opType)){
					addMessage(model, "保存期次成功");
					model.addAttribute("jpTeacher",teachersService.findList("0"));
					model.addAttribute("fdTeacher",teachersService.findList("1"));
					model.addAttribute("issue", issue);
					model.addAttribute("refresh", 0);
					return "modules/zxy/issueForm";
				}else{
					addMessage(redirectAttributes, "保存期次成功");
					return "redirect:"+Global.getAdminPath()+"/zxy/issue/?repage";
				}
			}else{
				if(flag==1){
					addMessage(model, "保存期次失败，该专业下此期次已创建");
				}else if(flag==2){
					addMessage(model, "保存期次失败，部分购买关卡数超过专业关卡数");
				}else if(flag==3){
					addMessage(model, "保存期次失败，关卡设置关卡数与专业关卡数不一致");
				}
				return form(issue, model);
			}
		}catch(Exception e){
			addMessage(model, "保存期次失败，本期封面上传失败");
			return form(issue, model);
		}
	}
	
	@RequiresPermissions("zxy:issue:edit")
	@RequestMapping(value = "delete")
	public String delete(Issue issue, RedirectAttributes redirectAttributes) {
		if(issue.getStatus().equals("0")){
			issueService.delete(issue);
			addMessage(redirectAttributes, "删除期次成功");
		}else{
			addMessage(redirectAttributes, "删除章节失败，仅能删除未发布的期次信息");
		}
		return "redirect:"+Global.getAdminPath()+"/zxy/issue/?repage";
	}

}