/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.front.auth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.security.Principal;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.zxy.entity.Issue;
import com.thinkgem.jeesite.modules.zxy.entity.Locks;
import com.thinkgem.jeesite.modules.zxy.service.IssueLockService;
import com.thinkgem.jeesite.modules.zxy.service.IssueService;
import com.thinkgem.jeesite.modules.zxy.service.LocksService;

/**
 * 关卡管理Controller
 * @author sheungxin
 * @version 2016-06-15
 */
@Controller
@RequestMapping(value = "${frontPath}/zxy/locks")
public class LocksFrontController extends BaseController {

	@Autowired
	private LocksService locksService;
	@Autowired
	private IssueService issueService;
	@Autowired
	private IssueLockService issueLockService;
	
	@ResponseBody
	@RequestMapping(value = "list/{issueId}")
	public Map<String, Object> listByMajor(@PathVariable String issueId, HttpServletRequest request, HttpServletResponse response, Model model) {
		Issue issue= issueService.get(issueId);
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(issue!=null){
			resultMap.put("issue", issue);
			resultMap.put("issueLock", issueLockService.findListByIssueId(issueId));
			
			Locks condition=new Locks();
			condition.setMajorId(issue.getMajorId());
			resultMap.put("lockList", locksService.findList(condition));
		}
		return resultMap;
	}

}