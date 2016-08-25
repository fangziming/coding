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
import com.thinkgem.jeesite.modules.zxy.entity.Chapter;
import com.thinkgem.jeesite.modules.zxy.service.ChapterService;

/**
 * 小节管理Controller
 * @author sheungxin
 * @version 2016-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/chapter")
public class ChapterController extends BaseController {

	@Autowired
	private ChapterService chapterService;
	
	@ModelAttribute
	public Chapter get(@RequestParam(required=false) String id) {
		Chapter entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = chapterService.get(id);
		}
		if (entity == null){
			entity = new Chapter();
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:chapter:view")
	@RequestMapping(value = {"list", ""})
	public String list(Chapter chapter, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Chapter> page = chapterService.findPage(new Page<Chapter>(request, response), chapter); 
		model.addAttribute("page", page);
		return "modules/zxy/chapterList";
	}

	@RequiresPermissions("zxy:chapter:view")
	@RequestMapping(value = "form")
	public String form(Chapter chapter, Model model) {
		model.addAttribute("chapter", chapter);
		return "modules/zxy/chapterForm";
	}

	@RequiresPermissions("zxy:chapter:edit")
	@RequestMapping(value = "save")
	public String save(@RequestParam(required=false) String opType,Chapter chapter, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, chapter)){
			return form(chapter, model);
		}
		int flag=chapterService.saveAndCheck(chapter);
		if(flag==0){
			if(StringUtils.isNoneBlank(opType)){
				addMessage(model, "保存小节成功");
				model.addAttribute("chapter", chapter);
				model.addAttribute("refresh", 0);
				return "modules/zxy/chapterForm";
			}else{
				addMessage(redirectAttributes, "保存小节成功");
				return "redirect:"+Global.getAdminPath()+"/zxy/chapter/?repage";
			}
		}else{
			addMessage(model, "保存小节失败，该小节已存在");
			return form(chapter, model);
		}
	}
	
	@RequiresPermissions("zxy:chapter:edit")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam(required=false) String opType,Chapter chapter, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		chapterService.delete(chapter);
		if(StringUtils.isNoneBlank(opType)){
			return renderString(response, "");
		}else{
			addMessage(redirectAttributes, "删除小节成功");
			return "redirect:"+Global.getAdminPath()+"/zxy/chapter/?repage";
		}
	}

}