/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.front.auth;

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
import com.thinkgem.jeesite.modules.zxy.entity.UserIssue;
import com.thinkgem.jeesite.modules.zxy.service.UserIssueService;

/**
 * 用户期次Controller
 * @author sheungxin
 * @version 2016-07-26
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/userIssue")
public class UserIssueController extends BaseController {

	@Autowired
	private UserIssueService userIssueService;
	
	@ModelAttribute
	public UserIssue get(@RequestParam(required=false) String id) {
		UserIssue entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userIssueService.get(id);
		}
		if (entity == null){
			entity = new UserIssue();
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:userIssue:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserIssue userIssue, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserIssue> page = userIssueService.findPage(new Page<UserIssue>(request, response), userIssue); 
		model.addAttribute("page", page);
		
		return renderString(response, model);
	}

	@RequiresPermissions("zxy:userIssue:view")
	@RequestMapping(value = "form")
	public String form(HttpServletResponse response,UserIssue userIssue, Model model) {
		model.addAttribute("userIssue", userIssue);
		
		return renderString(response, model);
	}

	@RequiresPermissions("zxy:userIssue:edit")
	@RequestMapping(value = "save")
	public String save(HttpServletResponse response,UserIssue userIssue, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userIssue)){
			return form(response,userIssue, model);
		}
		userIssueService.save(userIssue);
		addMessage(redirectAttributes, "保存用户期次成功");
		return "redirect:"+Global.getAdminPath()+"/zxy/userIssue/?repage";
	}
	
	@RequiresPermissions("zxy:userIssue:edit")
	@RequestMapping(value = "delete")
	public String delete(UserIssue userIssue, RedirectAttributes redirectAttributes) {
		userIssueService.delete(userIssue);
		addMessage(redirectAttributes, "删除用户期次成功");
		return "redirect:"+Global.getAdminPath()+"/zxy/userIssue/?repage";
	}

}