/**
 * Copyright (c) 2013-Now  All rights reserved.
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
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountBot;
import com.nabobsite.modules.nabob.cms.user.service.UserAccountBotService;

/**
 * 用户账户机器人信息Controller
 * @author face
 * @version 2021-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userAccountBot")
public class UserAccountBotController extends BaseController {

	@Autowired
	private UserAccountBotService userAccountBotService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserAccountBot get(String id, boolean isNewRecord) {
		return userAccountBotService.get(id, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:userAccountBot:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserAccountBot userAccountBot, Model model) {
		model.addAttribute("userAccountBot", userAccountBot);
		return "cms/user/userAccountBotList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:userAccountBot:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserAccountBot> listData(UserAccountBot userAccountBot, HttpServletRequest request, HttpServletResponse response) {
		userAccountBot.setPage(new Page<>(request, response));
		Page<UserAccountBot> page = userAccountBotService.findPage(userAccountBot);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:userAccountBot:view")
	@RequestMapping(value = "form")
	public String form(UserAccountBot userAccountBot, Model model) {
		model.addAttribute("userAccountBot", userAccountBot);
		return "cms/user/userAccountBotForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:userAccountBot:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserAccountBot userAccountBot) {
		userAccountBotService.save(userAccountBot);
		return renderResult(Global.TRUE, text("保存用户账户机器人信息成功！"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:userAccountBot:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserAccountBot userAccountBot) {
		userAccountBotService.delete(userAccountBot);
		return renderResult(Global.TRUE, text("删除用户账户机器人信息成功！"));
	}

}
