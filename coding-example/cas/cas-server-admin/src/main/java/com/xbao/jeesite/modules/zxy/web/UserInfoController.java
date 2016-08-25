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
import com.thinkgem.jeesite.modules.zxy.entity.UserInfo;
import com.thinkgem.jeesite.modules.zxy.service.UserInfoService;

/**
 * 学生管理Controller
 * @author sheungxin
 * @version 2016-06-14
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/userInfo")
public class UserInfoController extends BaseController {

	@Autowired
	private UserInfoService userInfoService;
	
	@ModelAttribute
	public UserInfo get(@RequestParam(required=false) String id) {
		UserInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userInfoService.get(id);
		}
		if (entity == null){
			entity = new UserInfo();
			entity.setSex("3");
			entity.setTelchecked(Global.YES);
			entity.setEmailchecked(Global.YES);
			entity.setIsmarried(Global.NO);
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:userInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserInfo> page = userInfoService.findPage(new Page<UserInfo>(request, response), userInfo); 
		model.addAttribute("page", page);
		return "modules/zxy/userInfoList";
	}

	@RequiresPermissions("zxy:userInfo:view")
	@RequestMapping(value = "form")
	public String form(UserInfo userInfo, Model model) {
		model.addAttribute("userInfo", userInfo);
		return "modules/zxy/userInfoForm";
	}

	@RequiresPermissions("zxy:userInfo:edit")
	@RequestMapping(value = "save")
	public String save(UserInfo userInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userInfo)){
			return form(userInfo, model);
		}
		try {
			if(userInfo.getPhoto().contains(Global.USERFILES_BASE_URL)){
				userInfo.setPhoto(MongoDBUtil.insertHugeFile(Global.getConfig("mongodb.bucket_portrait"), userInfo.getPhoto()));
			}
			int flag=userInfoService.saveAndCheck(userInfo);
			if(flag==0){
				addMessage(redirectAttributes, "保存学生成功");
				return "redirect:"+Global.getAdminPath()+"/zxy/userInfo/?repage";
			}else{
				if(flag==1){
					addMessage(model, "保存学生失败，手机或邮箱请至少提供一项");
				}else if(flag==2){
					addMessage(model, "保存学生失败，该手机号已被使用");
				}else if(flag==3){
					addMessage(model, "保存学生失败，该邮箱已被使用");
				}
				return form(userInfo, model);
			}
		}catch(Exception e){
			addMessage(model, "保存学生失败，上传学生头像失败");
			return form(userInfo, model);
		}
	}
	
	@RequiresPermissions("zxy:userInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(UserInfo userInfo, RedirectAttributes redirectAttributes) {
		userInfoService.delete(userInfo);
		addMessage(redirectAttributes, "删除学生成功");
		return "redirect:"+Global.getAdminPath()+"/zxy/userInfo/?repage";
	}

}