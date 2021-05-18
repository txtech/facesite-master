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
import com.nabobsite.modules.nabob.cms.task.entity.UserTaskReward;
import com.nabobsite.modules.nabob.cms.task.service.UserTaskRewardService;

/**
 * 用户任务奖励Controller
 * @author face
 * @version 2021-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/task/userTaskReward")
public class UserTaskRewardController extends BaseController {

	@Autowired
	private UserTaskRewardService userTaskRewardService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserTaskReward get(String id, boolean isNewRecord) {
		return userTaskRewardService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("task:userTaskReward:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserTaskReward userTaskReward, Model model) {
		model.addAttribute("userTaskReward", userTaskReward);
		return "cms/task/userTaskRewardList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("task:userTaskReward:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserTaskReward> listData(UserTaskReward userTaskReward, HttpServletRequest request, HttpServletResponse response) {
		userTaskReward.setPage(new Page<>(request, response));
		Page<UserTaskReward> page = userTaskRewardService.findPage(userTaskReward);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("task:userTaskReward:view")
	@RequestMapping(value = "form")
	public String form(UserTaskReward userTaskReward, Model model) {
		model.addAttribute("userTaskReward", userTaskReward);
		return "cms/task/userTaskRewardForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("task:userTaskReward:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserTaskReward userTaskReward) {
		userTaskRewardService.save(userTaskReward);
		return renderResult(Global.TRUE, text("保存用户任务奖励成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("task:userTaskReward:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserTaskReward userTaskReward) {
		userTaskRewardService.delete(userTaskReward);
		return renderResult(Global.TRUE, text("删除用户任务奖励成功！"));
	}
	
}