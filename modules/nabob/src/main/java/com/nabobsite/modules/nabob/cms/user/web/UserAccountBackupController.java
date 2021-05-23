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
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountBackup;
import com.nabobsite.modules.nabob.cms.user.service.UserAccountBackupService;

/**
 * 用户账户备份Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userAccountBackup")
public class UserAccountBackupController extends BaseController {

	@Autowired
	private UserAccountBackupService userAccountBackupService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserAccountBackup get(String id, boolean isNewRecord) {
		return userAccountBackupService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:userAccountBackup:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserAccountBackup userAccountBackup, Model model) {
		model.addAttribute("userAccountBackup", userAccountBackup);
		return "cms/user/userAccountBackupList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:userAccountBackup:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserAccountBackup> listData(UserAccountBackup userAccountBackup, HttpServletRequest request, HttpServletResponse response) {
		userAccountBackup.setPage(new Page<>(request, response));
		Page<UserAccountBackup> page = userAccountBackupService.findPage(userAccountBackup);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:userAccountBackup:view")
	@RequestMapping(value = "form")
	public String form(UserAccountBackup userAccountBackup, Model model) {
		model.addAttribute("userAccountBackup", userAccountBackup);
		return "cms/user/userAccountBackupForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:userAccountBackup:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserAccountBackup userAccountBackup) {
		userAccountBackupService.save(userAccountBackup);
		return renderResult(Global.TRUE, text("保存用户账户备份成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:userAccountBackup:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserAccountBackup userAccountBackup) {
		userAccountBackupService.delete(userAccountBackup);
		return renderResult(Global.TRUE, text("删除用户账户备份成功！"));
	}
	
}