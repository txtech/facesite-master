/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.team.web;

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
import com.nabobsite.modules.nabob.cms.team.entity.TeamUserReward;
import com.nabobsite.modules.nabob.cms.team.service.TeamUserRewardService;

/**
 * 团队任务奖励Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/team/teamUserReward")
public class TeamUserRewardController extends BaseController {

	@Autowired
	private TeamUserRewardService teamUserRewardService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TeamUserReward get(String id, boolean isNewRecord) {
		return teamUserRewardService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("team:teamUserReward:view")
	@RequestMapping(value = {"list", ""})
	public String list(TeamUserReward teamUserReward, Model model) {
		model.addAttribute("teamUserReward", teamUserReward);
		return "cms/team/teamUserRewardList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("team:teamUserReward:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TeamUserReward> listData(TeamUserReward teamUserReward, HttpServletRequest request, HttpServletResponse response) {
		teamUserReward.setPage(new Page<>(request, response));
		Page<TeamUserReward> page = teamUserRewardService.findPage(teamUserReward);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("team:teamUserReward:view")
	@RequestMapping(value = "form")
	public String form(TeamUserReward teamUserReward, Model model) {
		model.addAttribute("teamUserReward", teamUserReward);
		return "cms/team/teamUserRewardForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("team:teamUserReward:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TeamUserReward teamUserReward) {
		teamUserRewardService.save(teamUserReward);
		return renderResult(Global.TRUE, text("保存团队任务奖励成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("team:teamUserReward:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TeamUserReward teamUserReward) {
		teamUserRewardService.delete(teamUserReward);
		return renderResult(Global.TRUE, text("删除团队任务奖励成功！"));
	}
	
}