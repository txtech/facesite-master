/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
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
import com.nabobsite.modules.nabob.cms.product.entity.UserProductBotLog;
import com.nabobsite.modules.nabob.cms.product.service.UserProductBotLogService;

/**
 * 用户产品机器人记录Controller
 * @author face
 * @version 2021-05-17
 */
@Controller
@RequestMapping(value = "${adminPath}/product/userProductBotLog")
public class UserProductBotLogController extends BaseController {

	@Autowired
	private UserProductBotLogService userProductBotLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserProductBotLog get(String id, boolean isNewRecord) {
		return userProductBotLogService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:userProductBotLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserProductBotLog userProductBotLog, Model model) {
		model.addAttribute("userProductBotLog", userProductBotLog);
		return "cms/product/userProductBotLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:userProductBotLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserProductBotLog> listData(UserProductBotLog userProductBotLog, HttpServletRequest request, HttpServletResponse response) {
		userProductBotLog.setPage(new Page<>(request, response));
		Page<UserProductBotLog> page = userProductBotLogService.findPage(userProductBotLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:userProductBotLog:view")
	@RequestMapping(value = "form")
	public String form(UserProductBotLog userProductBotLog, Model model) {
		model.addAttribute("userProductBotLog", userProductBotLog);
		return "cms/product/userProductBotLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:userProductBotLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserProductBotLog userProductBotLog) {
		userProductBotLogService.save(userProductBotLog);
		return renderResult(Global.TRUE, text("保存用户产品机器人记录成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:userProductBotLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserProductBotLog userProductBotLog) {
		userProductBotLogService.delete(userProductBotLog);
		return renderResult(Global.TRUE, text("删除用户产品机器人记录成功！"));
	}
	
}