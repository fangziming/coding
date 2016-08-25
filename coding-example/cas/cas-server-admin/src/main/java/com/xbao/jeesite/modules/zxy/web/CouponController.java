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
import com.thinkgem.jeesite.modules.zxy.entity.Coupon;
import com.thinkgem.jeesite.modules.zxy.service.CouponService;

/**
 * 优惠券管理Controller
 * @author sheungxin
 * @version 2016-06-14
 */
@Controller
@RequestMapping(value = "${adminPath}/zxy/coupon")
public class CouponController extends BaseController {

	@Autowired
	private CouponService couponService;
	
	@ModelAttribute
	public Coupon get(@RequestParam(required=false) String id) {
		Coupon entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = couponService.get(id);
		}
		if (entity == null){
			entity = new Coupon();
		}
		return entity;
	}
	
	@RequiresPermissions("zxy:coupon:view")
	@RequestMapping(value = {"list", ""})
	public String list(Coupon coupon, HttpServletRequest request, HttpServletResponse response, Model model) {
		couponService.updateExpired();
		Page<Coupon> page = couponService.findPage(new Page<Coupon>(request, response), coupon); 
		model.addAttribute("page", page);
		return "modules/zxy/couponList";
	}

	@RequiresPermissions("zxy:coupon:view")
	@RequestMapping(value = "form")
	public String form(Coupon coupon, Model model) {
		model.addAttribute("coupon", coupon);
		return "modules/zxy/couponForm";
	}

	@RequiresPermissions("zxy:coupon:edit")
	@RequestMapping(value = "save")
	public String save(Coupon coupon, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, coupon)){
			return form(coupon, model);
		}
		couponService.save(coupon);
		addMessage(redirectAttributes, "保存优惠券成功");
		return "redirect:"+Global.getAdminPath()+"/zxy/coupon/?repage";
	}
	
	@RequiresPermissions("zxy:coupon:edit")
	@RequestMapping(value = "delete")
	public String delete(Coupon coupon, RedirectAttributes redirectAttributes) {
		couponService.delete(coupon);
		addMessage(redirectAttributes, "删除优惠券成功");
		return "redirect:"+Global.getAdminPath()+"/zxy/coupon/?repage";
	}

}