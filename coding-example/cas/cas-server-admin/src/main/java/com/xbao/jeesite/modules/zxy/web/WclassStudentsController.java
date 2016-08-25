/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.web;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.zxy.entity.UserInfo;
import com.thinkgem.jeesite.modules.zxy.entity.WclassStudents;
import com.thinkgem.jeesite.modules.zxy.service.UserInfoService;
import com.thinkgem.jeesite.modules.zxy.service.WclassStudentsService;

/**
 * 班级学生管理Controller
 * @author sheungxin
 * @version 2016-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/wclassStudents")
public class WclassStudentsController extends BaseController {

	@Autowired
	private WclassStudentsService wclassStudentsService;
	@Autowired
	private UserInfoService userInfoService;
	
	@ModelAttribute
	public WclassStudents get(@RequestParam(required=false) String id) {
		WclassStudents entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wclassStudentsService.get(id);
		}
		if (entity == null){
			entity = new WclassStudents();
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:wclassStudents:view")
	@RequestMapping(value = {"list", ""})
	public String list(WclassStudents wclassStudents, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WclassStudents> page = wclassStudentsService.findPage(new Page<WclassStudents>(request, response), wclassStudents); 
		model.addAttribute("page", page);
		return "modules/zxy/wclassStudentsList";
	}

	@RequiresPermissions("zxy:wclassStudents:view")
	@RequestMapping(value = "findStuByName")
	public String findStuByName(@RequestParam(required=true) String name,HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo=new UserInfo();
		userInfo.setName(name);
		List<UserInfo> userList=userInfoService.findList(userInfo);
		List<Map<String, String>> resultList=new ArrayList<Map<String,String>>();
		for(UserInfo user:userList){
			Map<String, String> data=new HashMap<String, String>();
			data.put("id", user.getId());
			data.put("text", user.getName());
			resultList.add(data);
		}
		return renderString(response, resultList);
	}
	
	@RequiresPermissions("zxy:wclassStudents:view")
	@RequestMapping(value = "form")
	public String form(WclassStudents wclassStudents, Model model) {
		model.addAttribute("wclassStudents", wclassStudents);
		return "modules/zxy/wclassStudentsForm";
	}

	@RequiresPermissions("zxy:wclassStudents:edit")
	@RequestMapping(value = "save")
	public String save(WclassStudents wclassStudents, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wclassStudents)){
			return form(wclassStudents, model);
		}
		wclassStudentsService.save(wclassStudents);
		addMessage(redirectAttributes, "保存班级学生成功");
		return "redirect:"+Global.getAdminPath()+"/zxy/wclassStudents/?repage";
	}
	
	@RequiresPermissions("zxy:wclassStudents:edit")
	@RequestMapping(value = "delete")
	public String delete(WclassStudents wclassStudents, RedirectAttributes redirectAttributes) {
		wclassStudentsService.delete(wclassStudents);
		addMessage(redirectAttributes, "删除班级学生成功");
		return "redirect:"+Global.getAdminPath()+"/zxy/wclassStudents/?repage";
	}

}