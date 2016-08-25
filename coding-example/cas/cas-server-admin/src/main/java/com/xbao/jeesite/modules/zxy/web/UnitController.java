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
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.zxy.entity.Unit;
import com.thinkgem.jeesite.modules.zxy.service.UnitService;

/**
 * 章节管理Controller
 * @author sheungxin
 * @version 2016-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/unit")
public class UnitController extends BaseController {

	@Autowired
	private UnitService unitService;
	
	@ModelAttribute
	public Unit get(@RequestParam(required=false) String id) {
		Unit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = unitService.get(id);
		}
		if (entity == null){
			entity = new Unit();
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:unit:view")
	@RequestMapping(value = {"list", ""})
	public String list(Unit unit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Unit> page = unitService.findPage(new Page<Unit>(request, response), unit); 
		model.addAttribute("page", page);
		return "modules/zxy/unitList";
	}

	@RequiresPermissions("zxy:unit:view")
	@RequestMapping(value = "form")
	public String form(Unit unit, Model model) {
		model.addAttribute("unit", unit);
		return "modules/zxy/unitForm";
	}

	@RequiresPermissions("zxy:unit:edit")
	@RequestMapping(value = "save")
	public String save(@RequestParam(required=false) String opType,Unit unit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, unit)){
			return form(unit, model);
		}
		int flag=unitService.saveAndCheck(unit);
		if(flag==0){
			if(StringUtils.isNoneBlank(opType)){
				addMessage(model, "保存章节成功");
				model.addAttribute("unit", unit);
				model.addAttribute("refresh", 0);
				return "modules/zxy/unitForm";
			}else{
				addMessage(redirectAttributes, "保存章节成功");
				return "redirect:"+Global.getAdminPath()+"/zxy/unit/?repage";
			}
		}else{
			addMessage(model, "保存章节失败，该章节已存在");
			return form(unit, model);
		}
	}
	
	@RequiresPermissions("zxy:unit:edit")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam(required=false) String opType,Unit unit, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		int flag=unitService.deleteAndCheck(unit);
		if(StringUtils.isNoneBlank(opType)){
			return renderString(response, flag==1?"删除章节失败，该章节下有小节信息":"");
		}else{
			if(flag==1){
				addMessage(redirectAttributes, "删除章节失败，该章节下有小节信息");
			}else{
				addMessage(redirectAttributes, "删除章节成功");
			}
			return "redirect:"+Global.getAdminPath()+"/zxy/unit/?repage";
		}
	}

	@RequiresPermissions("zxy:unit:view")
	@RequestMapping(value = "listByCourseId")
	public String listByCourseId(HttpServletResponse response,@RequestParam String courseId) {
		Unit unit=new Unit();
		if(StringUtils.isNoneBlank(courseId)){
			unit.setCourseId(courseId);
		}
		List<Unit> list = unitService.findList(unit); 
		return renderString(response, list);
	}
}