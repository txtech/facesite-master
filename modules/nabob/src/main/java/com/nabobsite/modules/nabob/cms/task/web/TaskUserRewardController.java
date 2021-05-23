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
import com.nabobsite.modules.nabob.cms.task.entity.TaskUserReward;
import com.nabobsite.modules.nabob.cms.task.service.TaskUserRewardService;

/**
 * 用户任务奖励Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/task/taskUserReward")
public class TaskUserRewardController extends BaseController {

	@Autowired
	private TaskUserRewardService taskUserRewardService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TaskUserReward get(String id, boolean isNewRecord) {
		return taskUserRewardService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("task:taskUserReward:view")
	@RequestMapping(value = {"list", ""})
	public String list(TaskUserReward taskUserReward, Model model) {
		model.addAttribute("taskUserReward", taskUserReward);
		return "cms/task/taskUserRewardList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("task:taskUserReward:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TaskUserReward> listData(TaskUserReward taskUserReward, HttpServletRequest request, HttpServletResponse response) {
		taskUserReward.setPage(new Page<>(request, response));
		Page<TaskUserReward> page = taskUserRewardService.findPage(taskUserReward);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("task:taskUserReward:view")
	@RequestMapping(value = "form")
	public String form(TaskUserReward taskUserReward, Model model) {
		model.addAttribute("taskUserReward", taskUserReward);
		return "cms/task/taskUserRewardForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("task:taskUserReward:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TaskUserReward taskUserReward) {
		taskUserRewardService.save(taskUserReward);
		return renderResult(Global.TRUE, text("保存用户任务奖励成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("task:taskUserReward:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TaskUserReward taskUserReward) {
		taskUserRewardService.delete(taskUserReward);
		return renderResult(Global.TRUE, text("删除用户任务奖励成功！"));
	}
	
}