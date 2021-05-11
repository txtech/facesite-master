/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.web;

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
import com.nabobsite.modules.nabob.cms.product.entity.UserProductBot;
import com.nabobsite.modules.nabob.cms.product.service.UserProductBotService;

/**
 * 用户产品机器人信息Controller
 * @author face
 * @version 2021-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/product/userProductBot")
public class UserProductBotController extends BaseController {

	@Autowired
	private UserProductBotService userProductBotService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserProductBot get(String id, boolean isNewRecord) {
		return userProductBotService.get(id, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:userProductBot:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserProductBot userProductBot, Model model) {
		model.addAttribute("userProductBot", userProductBot);
		return "cms/product/userProductBotList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:userProductBot:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserProductBot> listData(UserProductBot userProductBot, HttpServletRequest request, HttpServletResponse response) {
		userProductBot.setPage(new Page<>(request, response));
		Page<UserProductBot> page = userProductBotService.findPage(userProductBot);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:userProductBot:view")
	@RequestMapping(value = "form")
	public String form(UserProductBot userProductBot, Model model) {
		model.addAttribute("userProductBot", userProductBot);
		return "cms/product/userProductBotForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:userProductBot:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserProductBot userProductBot) {
		userProductBotService.save(userProductBot);
		return renderResult(Global.TRUE, text("保存用户产品机器人信息成功！"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:userProductBot:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserProductBot userProductBot) {
		userProductBotService.delete(userProductBot);
		return renderResult(Global.TRUE, text("删除用户产品机器人信息成功！"));
	}

}
