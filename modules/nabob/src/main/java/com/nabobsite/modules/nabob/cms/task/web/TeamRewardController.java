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
import com.nabobsite.modules.nabob.cms.task.entity.TeamReward;
import com.nabobsite.modules.nabob.cms.task.service.TeamRewardService;

/**
 * 团队任务Controller
 * @author face
 * @version 2021-05-17
 */
@Controller
@RequestMapping(value = "${adminPath}/task/teamReward")
public class TeamRewardController extends BaseController {

	@Autowired
	private TeamRewardService teamRewardService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TeamReward get(String id, boolean isNewRecord) {
		return teamRewardService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("task:teamReward:view")
	@RequestMapping(value = {"list", ""})
	public String list(TeamReward teamReward, Model model) {
		model.addAttribute("teamReward", teamReward);
		return "cms/task/teamRewardList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("task:teamReward:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TeamReward> listData(TeamReward teamReward, HttpServletRequest request, HttpServletResponse response) {
		teamReward.setPage(new Page<>(request, response));
		Page<TeamReward> page = teamRewardService.findPage(teamReward);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("task:teamReward:view")
	@RequestMapping(value = "form")
	public String form(TeamReward teamReward, Model model) {
		model.addAttribute("teamReward", teamReward);
		return "cms/task/teamRewardForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("task:teamReward:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TeamReward teamReward) {
		teamRewardService.save(teamReward);
		return renderResult(Global.TRUE, text("保存团队任务成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("task:teamReward:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TeamReward teamReward) {
		teamRewardService.delete(teamReward);
		return renderResult(Global.TRUE, text("删除团队任务成功！"));
	}
	
}