/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.web;

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
import com.nabobsite.modules.nabob.cms.user.entity.UserTeam;
import com.nabobsite.modules.nabob.cms.user.service.UserTeamService;

/**
 * 会员用户Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userTeam")
public class UserTeamController extends BaseController {

	@Autowired
	private UserTeamService userTeamService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserTeam get(String id, boolean isNewRecord) {
		return userTeamService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:userTeam:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserTeam userTeam, Model model) {
		model.addAttribute("userTeam", userTeam);
		return "cms/user/userTeamList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:userTeam:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserTeam> listData(UserTeam userTeam, HttpServletRequest request, HttpServletResponse response) {
		userTeam.setPage(new Page<>(request, response));
		Page<UserTeam> page = userTeamService.findPage(userTeam);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:userTeam:view")
	@RequestMapping(value = "form")
	public String form(UserTeam userTeam, Model model) {
		model.addAttribute("userTeam", userTeam);
		return "cms/user/userTeamForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:userTeam:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserTeam userTeam) {
		userTeamService.save(userTeam);
		return renderResult(Global.TRUE, text("保存会员用户成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:userTeam:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserTeam userTeam) {
		userTeamService.delete(userTeam);
		return renderResult(Global.TRUE, text("删除会员用户成功！"));
	}
	
}