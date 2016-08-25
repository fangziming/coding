/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.web;

import java.util.List;

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
import com.thinkgem.jeesite.modules.zxy.entity.Homework;
import com.thinkgem.jeesite.modules.zxy.service.HomeworkService;

/**
 * 作业管理Controller
 * @author sheungxin
 * @version 2016-06-06
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/homework")
public class HomeworkController extends BaseController {

	@Autowired
	private HomeworkService homeworkService;
	
	@ModelAttribute
	public Homework get(@RequestParam(required=false) String id) {
		Homework entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = homeworkService.get(id);
		}
		if (entity == null){
			entity = new Homework();
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:homework:view")
	@RequestMapping(value = {"list", ""})
	public String list(Homework homework, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Homework> page = homeworkService.findPage(new Page<Homework>(request, response), homework); 
		model.addAttribute("page", page);
		return "modules/zxy/homeworkList";
	}

	@RequiresPermissions("zxy:homework:view")
	@RequestMapping(value = {"all"})
	public String all(Homework homework, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Homework> list = homeworkService.findList(homework);
		return renderString(response, list);
	}
	
	@RequiresPermissions("zxy:homework:view")
	@RequestMapping(value = "form")
	public String form(Homework homework, Model model) {
		model.addAttribute("homework", homework);
		return "modules/zxy/homeworkForm";
	}

	@RequiresPermissions("zxy:homework:edit")
	@RequestMapping(value = "save")
	public String save(Homework homework, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, homework)){
			return form(homework, model);
		}
		try {
			if(homework.getAttachment().contains(Global.USERFILES_BASE_URL)){
				homework.setAttachment(MongoDBUtil.insertHugeFile(Global.getConfig("mongodb.bucket_homework"), homework.getAttachment()));
			}
			int flag=homeworkService.saveAndCheck(homework);
			if(flag==0){
				addMessage(redirectAttributes, "保存作业成功");
				return "redirect:"+Global.getAdminPath()+"/zxy/homework?resourseType.id="+homework.getResourseType().getId();
			}else{
				addMessage(model, "保存作业失败，该作业已存在");
				return form(homework, model);
			}
		}catch(Exception e){e.printStackTrace();
			addMessage(model, "保存作业失败，附件上传失败");
			return form(homework, model);
		}
	}
	
	@RequiresPermissions("zxy:homework:edit")
	@RequestMapping(value = "delete")
	public String delete(Homework homework, RedirectAttributes redirectAttributes) {
		int flag=homeworkService.deleteAndCheck(homework);
		if(flag==0){
			addMessage(redirectAttributes, "删除作业成功");
		}else{
			addMessage(redirectAttributes, "删除作业失败，该作业已被使用");
		}
		return "redirect:"+Global.getAdminPath()+"/zxy/homework/?repage";
	}

}