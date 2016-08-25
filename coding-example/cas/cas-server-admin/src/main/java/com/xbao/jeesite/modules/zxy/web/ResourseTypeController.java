/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.zxy.entity.ResourseType;
import com.thinkgem.jeesite.modules.zxy.service.ResourseTypeService;

/**
 * 资源分类Controller
 * @author sheungxin
 * @version 2016-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/resourseType")
public class ResourseTypeController extends BaseController {

	@Autowired
	private ResourseTypeService resourseTypeService;
	
	@ModelAttribute
	public ResourseType get(@RequestParam(required=false) String id) {
		ResourseType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resourseTypeService.get(id);
		}
		if (entity == null){
			entity = new ResourseType();
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:resourseType:view")
	@RequestMapping(value = "index")
	public String index() {
		return "modules/zxy/resourseTreeIndex";
	}
	
	@RequiresPermissions("zxy:resourseType:view")
	@RequestMapping(value = {"jsonList/{type}"})
	public String jsonList(@PathVariable String type, HttpServletResponse response) {
		ResourseType resourseType=new ResourseType();
		resourseType.setType(type);
		List<ResourseType> list=resourseTypeService.findList(resourseType);
		List<Map<String, Object>> mapList=new ArrayList<Map<String,Object>>();
		for(ResourseType e:list){
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getPid());
			map.put("pIds", e.getPids());
			map.put("name", e.getName());
			mapList.add(map);
		}
		return renderString(response, mapList);
	}

	@RequiresPermissions("zxy:resourseType:view")
	@RequestMapping(value = {"list",""})
	public String list(ResourseType resourseType,HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResourseType> page = resourseTypeService.findPage(new Page<ResourseType>(request, response), resourseType); 
		model.addAttribute("page", page);
		return "modules/zxy/resourseTypeList";
	}
	
	@RequiresPermissions("zxy:resourseType:view")
	@RequestMapping(value = "form")
	public String form(ResourseType resourseType, Model model) {
		model.addAttribute("resourseType", resourseType);
		return "modules/zxy/resourseTypeForm";
	}

	@RequiresPermissions("zxy:resourseType:edit")
	@RequestMapping(value = "save")
	public String save(ResourseType resourseType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, resourseType)){
			return form(resourseType, model);
		}
		resourseTypeService.save(resourseType);
		model.addAttribute("refresh", 0);
		addMessage(model, "保存资源分类成功");
		return form(resourseType, model);
	}
	
	@RequiresPermissions("zxy:resourseType:edit")
	@RequestMapping(value = "delete")
	public String delete(ResourseType resourseType, HttpServletResponse response) {
		int flag=resourseTypeService.deleteAndCheck(resourseType);
		String mes=flag==0?"":"删除资源分类失败，该分类下存在资源数据";
		return renderString(response, mes);
	}

}