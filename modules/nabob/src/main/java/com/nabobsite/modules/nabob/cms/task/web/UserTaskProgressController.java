/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.task.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.nabobsite.modules.nabob.cms.task.entity.UserTaskProgress;
import com.nabobsite.modules.nabob.cms.task.service.UserTaskProgressService;

/**
 * 用户任务奖励Controller
 * @author face
 * @version 2021-05-21
 */
@Controller
@RequestMapping(value = "${adminPath}/task/userTaskProgress")
public class UserTaskProgressController extends BaseController {

	@Autowired
	private UserTaskProgressService userTaskProgressService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserTaskProgress get(String id, boolean isNewRecord) {
		return userTaskProgressService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("task:userTaskProgress:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserTaskProgress userTaskProgress, Model model) {
		model.addAttribute("userTaskProgress", userTaskProgress);
		return "cms/task/userTaskProgressList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("task:userTaskProgress:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserTaskProgress> listData(UserTaskProgress userTaskProgress, HttpServletRequest request, HttpServletResponse response) {
		userTaskProgress.setPage(new Page<>(request, response));
		Page<UserTaskProgress> page = userTaskProgressService.findPage(userTaskProgress);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("task:userTaskProgress:view")
	@RequestMapping(value = "form")
	public String form(UserTaskProgress userTaskProgress, Model model) {
		model.addAttribute("userTaskProgress", userTaskProgress);
		return "cms/task/userTaskProgressForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("task:userTaskProgress:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserTaskProgress userTaskProgress) {
		userTaskProgressService.save(userTaskProgress);
		return renderResult(Global.TRUE, text("保存用户任务奖励成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("task:userTaskProgress:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserTaskProgress userTaskProgress) {
		userTaskProgressService.delete(userTaskProgress);
		return renderResult(Global.TRUE, text("删除用户任务奖励成功！"));
	}
	
}