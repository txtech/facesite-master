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
import com.nabobsite.modules.nabob.cms.user.entity.UserInfoMembership;
import com.nabobsite.modules.nabob.cms.user.service.UserInfoMembershipService;

/**
 * 会员资格Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userInfoMembership")
public class UserInfoMembershipController extends BaseController {

	@Autowired
	private UserInfoMembershipService userInfoMembershipService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserInfoMembership get(String id, boolean isNewRecord) {
		return userInfoMembershipService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:userInfoMembership:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserInfoMembership userInfoMembership, Model model) {
		model.addAttribute("userInfoMembership", userInfoMembership);
		return "cms/user/userInfoMembershipList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:userInfoMembership:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserInfoMembership> listData(UserInfoMembership userInfoMembership, HttpServletRequest request, HttpServletResponse response) {
		userInfoMembership.setPage(new Page<>(request, response));
		Page<UserInfoMembership> page = userInfoMembershipService.findPage(userInfoMembership);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:userInfoMembership:view")
	@RequestMapping(value = "form")
	public String form(UserInfoMembership userInfoMembership, Model model) {
		model.addAttribute("userInfoMembership", userInfoMembership);
		return "cms/user/userInfoMembershipForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:userInfoMembership:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserInfoMembership userInfoMembership) {
		userInfoMembershipService.save(userInfoMembership);
		return renderResult(Global.TRUE, text("保存会员资格成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:userInfoMembership:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserInfoMembership userInfoMembership) {
		userInfoMembershipService.delete(userInfoMembership);
		return renderResult(Global.TRUE, text("删除会员资格成功！"));
	}
	
}