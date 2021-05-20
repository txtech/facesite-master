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
import com.nabobsite.modules.nabob.cms.task.entity.UserTeamReward;
import com.nabobsite.modules.nabob.cms.task.service.UserTeamRewardService;

/**
 * 团队任务奖励Controller
 * @author face
 * @version 2021-05-17
 */
@Controller
@RequestMapping(value = "${adminPath}/task/userTeamReward")
public class UserTeamRewardController extends BaseController {

	@Autowired
	private UserTeamRewardService userTeamRewardService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserTeamReward get(String id, boolean isNewRecord) {
		return userTeamRewardService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("task:userTeamReward:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserTeamReward userTeamReward, Model model) {
		model.addAttribute("userTeamReward", userTeamReward);
		return "cms/task/userTeamRewardList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("task:userTeamReward:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserTeamReward> listData(UserTeamReward userTeamReward, HttpServletRequest request, HttpServletResponse response) {
		userTeamReward.setPage(new Page<>(request, response));
		Page<UserTeamReward> page = userTeamRewardService.findPage(userTeamReward);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("task:userTeamReward:view")
	@RequestMapping(value = "form")
	public String form(UserTeamReward userTeamReward, Model model) {
		model.addAttribute("userTeamReward", userTeamReward);
		return "cms/task/userTeamRewardForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("task:userTeamReward:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserTeamReward userTeamReward) {
		userTeamRewardService.save(userTeamReward);
		return renderResult(Global.TRUE, text("保存团队任务奖励成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("task:userTeamReward:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserTeamReward userTeamReward) {
		userTeamRewardService.delete(userTeamReward);
		return renderResult(Global.TRUE, text("删除团队任务奖励成功！"));
	}
	
}