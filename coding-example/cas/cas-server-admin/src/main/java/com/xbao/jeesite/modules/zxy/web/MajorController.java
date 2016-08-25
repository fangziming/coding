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
import com.thinkgem.jeesite.modules.zxy.entity.Major;
import com.thinkgem.jeesite.modules.zxy.service.MajorService;

/**
 * 专业管理Controller
 * @author sheungxin
 * @version 2016-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/major")
public class MajorController extends BaseController {

	@Autowired
	private MajorService majorService;
	
	@ModelAttribute
	public Major get(@RequestParam(required=false) String id) {
		Major entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = majorService.get(id);
		}
		if (entity == null){
			entity = new Major();
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:major:view")
	@RequestMapping(value = {"list", ""})
	public String list(Major major, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Major> page = majorService.findPage(new Page<Major>(request, response), major); 
		model.addAttribute("page", page);
		return "modules/zxy/majorList";
	}

	@RequiresPermissions("zxy:major:view")
	@RequestMapping(value = "form")
	public String form(Major major, Model model) {
		model.addAttribute("major", major);
		return "modules/zxy/majorForm";
	}

	@RequiresPermissions("zxy:major:edit")
	@RequestMapping(value = "save")
	public String save(Major major, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, major)){
			return form(major, model);
		}
		try {
			if(major.getLogo().contains(Global.USERFILES_BASE_URL)){
				major.setLogo(MongoDBUtil.insertHugeFile(Global.getConfig("mongodb.bucket_major"), major.getLogo()));
			}
			int flag=majorService.saveAndCheck(major);
			if(flag==0){
				addMessage(redirectAttributes, "保存专业成功");
				return "redirect:"+Global.getAdminPath()+"/zxy/major/?repage";
			}else{
				addMessage(model, "保存专业失败,该专业已存在");
				return form(major, model);
			}
		} catch (Exception e) {
			addMessage(model, "保存专业失败,文件上传失败");
			return form(major, model);
		}
		
	}
	
	@RequiresPermissions("zxy:major:edit")
	@RequestMapping(value = "delete")
	public String delete(Major major, RedirectAttributes redirectAttributes) {
		majorService.delete(major);
		addMessage(redirectAttributes, "删除专业成功");
		return "redirect:"+Global.getAdminPath()+"/zxy/major/?repage";
	}

	@RequiresPermissions("zxy:major:edit")
	@RequestMapping(value = "copy")
	public String copy(Major major, Model model, RedirectAttributes redirectAttributes) {
		majorService.copy(major);
		addMessage(redirectAttributes, "复制专业成功");
		return "redirect:"+Global.getAdminPath()+"/zxy/major/?repage";
	}
}