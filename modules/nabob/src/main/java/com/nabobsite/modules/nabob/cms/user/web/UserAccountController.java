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
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.service.UserAccountService;

/**
 * 用户账户Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userAccount")
public class UserAccountController extends BaseController {

	@Autowired
	private UserAccountService userAccountService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserAccount get(String id, boolean isNewRecord) {
		return userAccountService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:userAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserAccount userAccount, Model model) {
		model.addAttribute("userAccount", userAccount);
		return "cms/user/userAccountList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:userAccount:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserAccount> listData(UserAccount userAccount, HttpServletRequest request, HttpServletResponse response) {
		userAccount.setPage(new Page<>(request, response));
		Page<UserAccount> page = userAccountService.findPage(userAccount);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:userAccount:view")
	@RequestMapping(value = "form")
	public String form(UserAccount userAccount, Model model) {
		model.addAttribute("userAccount", userAccount);
		return "cms/user/userAccountForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:userAccount:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserAccount userAccount) {
		userAccountService.save(userAccount);
		return renderResult(Global.TRUE, text("保存用户账户成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:userAccount:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserAccount userAccount) {
		userAccountService.delete(userAccount);
		return renderResult(Global.TRUE, text("删除用户账户成功！"));
	}
	
}