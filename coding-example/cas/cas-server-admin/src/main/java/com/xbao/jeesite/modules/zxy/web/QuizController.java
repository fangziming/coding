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
import com.thinkgem.jeesite.modules.zxy.entity.Quiz;
import com.thinkgem.jeesite.modules.zxy.service.QuizService;

/**
 * 测验Controller
 * @author sheungxin
 * @version 2016-06-06
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/quiz")
public class QuizController extends BaseController {

	@Autowired
	private QuizService quizService;
	
	@ModelAttribute
	public Quiz get(@RequestParam(required=false) String id) {
		Quiz entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = quizService.get(id);
		}
		if (entity == null){
			entity = new Quiz();
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:quiz:view")
	@RequestMapping(value = {"list", ""})
	public String list(Quiz quiz, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Quiz> page = quizService.findPage(new Page<Quiz>(request, response), quiz); 
		model.addAttribute("page", page);
		return "modules/zxy/quizList";
	}

	@RequiresPermissions("zxy:quiz:view")
	@RequestMapping(value = {"all"})
	public String all(Quiz quiz, HttpServletRequest request, HttpServletResponse response, Model model) {
		return renderString(response, quizService.findList(quiz));
	}
	
	@RequiresPermissions("zxy:quiz:view")
	@RequestMapping(value = "form")
	public String form(Quiz quiz, Model model) {
		model.addAttribute("quiz", quiz);
		return "modules/zxy/quizForm";
	}

	@RequiresPermissions("zxy:quiz:edit")
	@RequestMapping(value = "save")
	public String save(Quiz quiz, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, quiz)){
			return form(quiz, model);
		}
		try {
			if(quiz.getAttachment().contains(Global.USERFILES_BASE_URL)){
				quiz.setAttachment(MongoDBUtil.insertHugeFile(Global.getConfig("mongodb.bucket_quiz"), quiz.getAttachment()));
			}
			int flag=quizService.saveAndCheck(quiz);
			if(flag==0){
				addMessage(redirectAttributes, "保存测验成功");
				return "redirect:"+Global.getAdminPath()+"/zxy/quiz/?repage";
			}else{
				addMessage(model, "保存测验失败，该测验已存在");
				return form(quiz, model);
			}
		}catch(Exception e){e.printStackTrace();
			addMessage(model, "保存测验失败，附件上传失败");
			return form(quiz, model);
		}
	}
	
	@RequiresPermissions("zxy:quiz:edit")
	@RequestMapping(value = "delete")
	public String delete(Quiz quiz, RedirectAttributes redirectAttributes) {
		int flag=quizService.deleteAndCheck(quiz);
		if(flag==0){
			addMessage(redirectAttributes, "删除测验成功");
		}else{
			addMessage(redirectAttributes, "删除测验失败，该测验已被使用");
		}
		return "redirect:"+Global.getAdminPath()+"/zxy/quiz/?repage";
	}

}