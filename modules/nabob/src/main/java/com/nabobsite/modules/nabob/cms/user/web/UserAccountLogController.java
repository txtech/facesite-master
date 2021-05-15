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
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountLog;
import com.nabobsite.modules.nabob.cms.user.service.UserAccountLogService;

/**
 * 用户账户Controller
 * @author face
 * @version 2021-05-15
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userAccountLog")
public class UserAccountLogController extends BaseController {

	@Autowired
	private UserAccountLogService userAccountLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserAccountLog get(String id, boolean isNewRecord) {
		return userAccountLogService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:userAccountLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserAccountLog userAccountLog, Model model) {
		model.addAttribute("userAccountLog", userAccountLog);
		return "cms/user/userAccountLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:userAccountLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserAccountLog> listData(UserAccountLog userAccountLog, HttpServletRequest request, HttpServletResponse response) {
		userAccountLog.setPage(new Page<>(request, response));
		Page<UserAccountLog> page = userAccountLogService.findPage(userAccountLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:userAccountLog:view")
	@RequestMapping(value = "form")
	public String form(UserAccountLog userAccountLog, Model model) {
		model.addAttribute("userAccountLog", userAccountLog);
		return "cms/user/userAccountLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:userAccountLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserAccountLog userAccountLog) {
		userAccountLogService.save(userAccountLog);
		return renderResult(Global.TRUE, text("保存用户账户成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:userAccountLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserAccountLog userAccountLog) {
		userAccountLogService.delete(userAccountLog);
		return renderResult(Global.TRUE, text("删除用户账户成功！"));
	}
	
}