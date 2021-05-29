/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nabobsite.modules.nabob.cms.base.BaseDataScopeFilter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountTask;
import com.nabobsite.modules.nabob.cms.user.service.UserAccountTaskService;

/**
 * 用户奖励账户Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userAccountTask")
public class UserAccountTaskController extends BaseController {

	@Autowired
	private UserAccountTaskService userAccountTaskService;
	@Autowired
	private BaseDataScopeFilter baseDataScopeFilter;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserAccountTask get(String id, boolean isNewRecord) {
		return userAccountTaskService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:userAccountTask:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserAccountTask userAccountTask, Model model) {
		model.addAttribute("userAccountTask", userAccountTask);
		return "cms/user/userAccountTaskList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:userAccountTask:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserAccountTask> listData(UserAccountTask userAccountTask, HttpServletRequest request, HttpServletResponse response) {
		userAccountTask.setPage(new Page<>(request, response));
		baseDataScopeFilter.addDataScopeFilter(userAccountTask);
		Page<UserAccountTask> page = userAccountTaskService.findPage(userAccountTask);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:userAccountTask:view")
	@RequestMapping(value = "form")
	public String form(UserAccountTask userAccountTask, Model model) {
		model.addAttribute("userAccountTask", userAccountTask);
		return "cms/user/userAccountTaskForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:userAccountTask:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserAccountTask userAccountTask) {
		userAccountTaskService.save(userAccountTask);
		return renderResult(Global.TRUE, text("保存用户奖励账户成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:userAccountTask:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserAccountTask userAccountTask) {
		userAccountTaskService.delete(userAccountTask);
		return renderResult(Global.TRUE, text("删除用户奖励账户成功！"));
	}
	
}