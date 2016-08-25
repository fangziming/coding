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
import com.thinkgem.jeesite.modules.zxy.entity.LiveCourse;
import com.thinkgem.jeesite.modules.zxy.service.LiveCourseService;

/**
 * 直播管理Controller
 * @author sheungxin
 * @version 2016-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/liveCourse")
public class LiveCourseController extends BaseController {

	@Autowired
	private LiveCourseService liveCourseService;
	
	@ModelAttribute
	public LiveCourse get(@RequestParam(required=false) String id) {
		LiveCourse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = liveCourseService.get(id);
		}
		if (entity == null){
			entity = new LiveCourse();
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:liveCourse:view")
	@RequestMapping(value = {"list", ""})
	public String list(LiveCourse liveCourse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LiveCourse> page = liveCourseService.findPage(new Page<LiveCourse>(request, response), liveCourse); 
		model.addAttribute("page", page);
		return "modules/zxy/liveCourseList";
	}

	@RequiresPermissions("zxy:liveCourse:view")
	@RequestMapping(value = {"all"})
	public String all(LiveCourse liveCourse, HttpServletRequest request, HttpServletResponse response, Model model) {
		return renderString(response, liveCourseService.findList(liveCourse));
	}
	
	@RequiresPermissions("zxy:liveCourse:view")
	@RequestMapping(value = "form")
	public String form(LiveCourse liveCourse, Model model) {
		model.addAttribute("liveCourse", liveCourse);
		return "modules/zxy/liveCourseForm";
	}

	@RequiresPermissions("zxy:liveCourse:edit")
	@RequestMapping(value = "save")
	public String save(LiveCourse liveCourse, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, liveCourse)){
			return form(liveCourse, model);
		}
		try {
			if(liveCourse.getLecturerpicUrl().contains(Global.USERFILES_BASE_URL)){
				liveCourse.setLecturerpicUrl(MongoDBUtil.insertHugeFile(Global.getConfig("mongodb.bucket_livecourse"), liveCourse.getLecturerpicUrl()));
			}
			if(liveCourse.getVideobigpicUrl().contains(Global.USERFILES_BASE_URL)){
				liveCourse.setVideobigpicUrl(MongoDBUtil.insertHugeFile(Global.getConfig("mongodb.bucket_livecourse"), liveCourse.getVideobigpicUrl()));
			}
			liveCourseService.save(liveCourse);
			if(liveCourse.getCode().equals("0")){
				addMessage(redirectAttributes, "保存直播成功");
				return "redirect:"+Global.getAdminPath()+"/zxy/liveCourse/?repage";
			}else{
				addMessage(model, liveCourse.getMessage());
				return form(liveCourse, model);
			}
		}catch(Exception e){e.printStackTrace();
			addMessage(model, "保存直播失败，附件上传失败");
			return form(liveCourse, model);
		}
	}
	
	@RequiresPermissions("zxy:liveCourse:edit")
	@RequestMapping(value = "delete")
	public String delete(LiveCourse liveCourse, RedirectAttributes redirectAttributes) {
		int flag=liveCourseService.deleteAndCheck(liveCourse);
		if(flag==0){
			addMessage(redirectAttributes, "删除直播成功");
		}else if(flag==1){
			addMessage(redirectAttributes, "删除直播失败，该直播已被使用");
		}else{
			addMessage(redirectAttributes, "删除直播失败，只能删除未开始的直播");
		}
		return "redirect:"+Global.getAdminPath()+"/zxy/liveCourse/?repage";
	}

}