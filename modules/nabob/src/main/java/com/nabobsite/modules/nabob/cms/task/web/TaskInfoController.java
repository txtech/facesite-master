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
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;
import com.nabobsite.modules.nabob.cms.task.service.TaskInfoService;

/**
 * 任务列表Controller
 * @author face
 * @version 2021-05-20
 */
@Controller
@RequestMapping(value = "${adminPath}/task/taskInfo")
public class TaskInfoController extends BaseController {

	@Autowired
	private TaskInfoService taskInfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TaskInfo get(String id, boolean isNewRecord) {
		return taskInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("task:taskInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(TaskInfo taskInfo, Model model) {
		model.addAttribute("taskInfo", taskInfo);
		return "cms/task/taskInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("task:taskInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TaskInfo> listData(TaskInfo taskInfo, HttpServletRequest request, HttpServletResponse response) {
		taskInfo.setPage(new Page<>(request, response));
		Page<TaskInfo> page = taskInfoService.findPage(taskInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("task:taskInfo:view")
	@RequestMapping(value = "form")
	public String form(TaskInfo taskInfo, Model model) {
		model.addAttribute("taskInfo", taskInfo);
		return "cms/task/taskInfoForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("task:taskInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TaskInfo taskInfo) {
		taskInfoService.save(taskInfo);
		return renderResult(Global.TRUE, text("保存任务列表成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("task:taskInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TaskInfo taskInfo) {
		taskInfoService.delete(taskInfo);
		return renderResult(Global.TRUE, text("删除任务列表成功！"));
	}
	
}