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
import com.nabobsite.modules.nabob.cms.task.entity.UserTask;
import com.nabobsite.modules.nabob.cms.task.service.UserTaskService;

/**
 * 用户任务Controller
 * @author face
 * @version 2021-05-13
 */
@Controller
@RequestMapping(value = "${adminPath}/task/userTask")
public class UserTaskController extends BaseController {

	@Autowired
	private UserTaskService userTaskService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserTask get(String id, boolean isNewRecord) {
		return userTaskService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("task:userTask:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserTask userTask, Model model) {
		model.addAttribute("userTask", userTask);
		return "cms/task/userTaskList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("task:userTask:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserTask> listData(UserTask userTask, HttpServletRequest request, HttpServletResponse response) {
		userTask.setPage(new Page<>(request, response));
		Page<UserTask> page = userTaskService.findPage(userTask);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("task:userTask:view")
	@RequestMapping(value = "form")
	public String form(UserTask userTask, Model model) {
		model.addAttribute("userTask", userTask);
		return "cms/task/userTaskForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("task:userTask:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserTask userTask) {
		userTaskService.save(userTask);
		return renderResult(Global.TRUE, text("保存用户任务成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("task:userTask:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserTask userTask) {
		userTaskService.delete(userTask);
		return renderResult(Global.TRUE, text("删除用户任务成功！"));
	}
	
}