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
import com.thinkgem.jeesite.modules.zxy.entity.Vedio;
import com.thinkgem.jeesite.modules.zxy.service.VedioService;

/**
 * 视频管理Controller
 * @author sheungxin
 * @version 2016-06-06
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/vedio")
public class VedioController extends BaseController {
	

	@Autowired
	private VedioService vedioService;
	
	@ModelAttribute
	public Vedio get(@RequestParam(required=false) String id) {
		Vedio entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = vedioService.get(id);
		}
		if (entity == null){
			entity = new Vedio();
		}
		return entity;
	}
	
	/**
	 * polyvs视频同步
	 * @return
	 */
	@RequestMapping(value = "videosSync")
	public String videosSync(){
		
		vedioService.videoSync();
		
		return "redirect:list";
	}
	
	@RequiresPermissions("zxy:vedio:view")
	@RequestMapping(value = {"list", ""})
	public String list(Vedio vedio, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Vedio> page = vedioService.findPage(new Page<Vedio>(request, response), vedio); 
		model.addAttribute("page", page);
		response.setHeader("Access-Control-Allow-Origin", "*");
		return "modules/zxy/vedioList";
	}

	@RequiresPermissions("zxy:vedio:view")
	@RequestMapping(value = {"all"})
	public String all(Vedio vedio, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Vedio> list = vedioService.findList(vedio);
		return renderString(response, list);
	}
	
	@RequiresPermissions("zxy:vedio:view")
	@RequestMapping(value = "form")
	public String form(Vedio vedio, Model model) {
		model.addAttribute("vedio", vedio);
		return "modules/zxy/vedioForm";
	}
	
	@RequestMapping(value = "vedioJson")
	public String vedioJson(Vedio vedio,HttpServletResponse response) {
//		model.addAttribute("vedio", vedio);
		return renderString(response,vedio);
	}

	@RequiresPermissions("zxy:vedio:edit")
	@RequestMapping(value = "save")
	public String save(Vedio vedio, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vedio)){
			return form(vedio, model);
		}
		int flag=vedioService.saveAndCheck(vedio);
		if(flag==0){
			addMessage(redirectAttributes, "保存视频成功");
			return "redirect:"+Global.getAdminPath()+"/zxy/vedio/?repage";
		}else{
			addMessage(model, "保存视频失败，该视频已存在");
			return form(vedio, model);
		}
	}
	
	@RequiresPermissions("zxy:vedio:edit")
	@RequestMapping(value = "delete")
	public String delete(Vedio vedio, RedirectAttributes redirectAttributes) {
		int flag=vedioService.deleteAndCheck(vedio);
		if(flag==0){
			addMessage(redirectAttributes, "删除视频成功");
		}else{
			addMessage(redirectAttributes, "删除视频失败，该视频已被使用");
		}
		return "redirect:"+Global.getAdminPath()+"/zxy/vedio/?repage";
	}
	
	
}