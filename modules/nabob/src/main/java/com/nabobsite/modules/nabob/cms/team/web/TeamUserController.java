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
import com.nabobsite.modules.nabob.cms.team.entity.TeamUser;
import com.nabobsite.modules.nabob.cms.team.service.TeamUserService;

/**
 * 用户团队Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/team/teamUser")
public class TeamUserController extends BaseController {

	@Autowired
	private TeamUserService teamUserService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TeamUser get(String id, boolean isNewRecord) {
		return teamUserService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("team:teamUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(TeamUser teamUser, Model model) {
		model.addAttribute("teamUser", teamUser);
		return "cms/team/teamUserList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("team:teamUser:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TeamUser> listData(TeamUser teamUser, HttpServletRequest request, HttpServletResponse response) {
		teamUser.setPage(new Page<>(request, response));
		Page<TeamUser> page = teamUserService.findPage(teamUser);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("team:teamUser:view")
	@RequestMapping(value = "form")
	public String form(TeamUser teamUser, Model model) {
		model.addAttribute("teamUser", teamUser);
		return "cms/team/teamUserForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("team:teamUser:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TeamUser teamUser) {
		teamUserService.save(teamUser);
		return renderResult(Global.TRUE, text("保存用户团队成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("team:teamUser:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TeamUser teamUser) {
		teamUserService.delete(teamUser);
		return renderResult(Global.TRUE, text("删除用户团队成功！"));
	}
	
}