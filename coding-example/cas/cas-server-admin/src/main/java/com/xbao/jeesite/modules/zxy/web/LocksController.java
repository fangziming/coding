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
import com.thinkgem.jeesite.modules.zxy.entity.Locks;
import com.thinkgem.jeesite.modules.zxy.service.LocksService;

/**
 * 关卡管理Controller
 * @author sheungxin
 * @version 2016-06-15
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/locks")
public class LocksController extends BaseController {

	@Autowired
	private LocksService locksService;
	
	@ModelAttribute
	public Locks get(@RequestParam(required=false) String id) {
		Locks entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = locksService.get(id);
		}
		if (entity == null){
			entity = new Locks();
			entity.setIsreward(Global.YES);
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:locks:view")
	@RequestMapping(value = {"list", ""})
	public String list(Locks locks, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Locks> page = locksService.findPage(new Page<Locks>(request, response), locks); 
		model.addAttribute("page", page);
		return "modules/zxy/locksList";
	}

	@RequiresPermissions("zxy:locks:view")
	@RequestMapping(value = "form")
	public String form(Locks locks, Model model) {
		model.addAttribute("locks", locks);
		return "modules/zxy/locksForm";
	}

	@RequiresPermissions("zxy:locks:edit")
	@RequestMapping(value = "save")
	public String save(@RequestParam(required=false) String opType,Locks locks, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, locks)){
			return form(locks, model);
		}
		int flag=locksService.saveAndCheck(locks);
		if(flag==0){
			if(StringUtils.isNoneBlank(opType)){
				addMessage(model, "保存关卡成功");
				model.addAttribute("locks", locks);
				model.addAttribute("refresh", 0);
				return "modules/zxy/locksForm";
			}else{
				addMessage(redirectAttributes, "保存关卡成功");
				return "redirect:"+Global.getAdminPath()+"/zxy/locks/?repage";
			}
		}else{
			addMessage(model, "保存关卡失败，该专业下此关卡已创建");
			return form(locks, model);
		}
		
	}
	
	@RequiresPermissions("zxy:locks:edit")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam(required=false) String opType,Locks locks, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		locksService.delete(locks);
		if(StringUtils.isNoneBlank(opType)){
			return renderString(response, "");
		}else{
			addMessage(redirectAttributes, "删除关卡成功");
			return "redirect:"+Global.getAdminPath()+"/zxy/locks/?repage";
		}
	}

}