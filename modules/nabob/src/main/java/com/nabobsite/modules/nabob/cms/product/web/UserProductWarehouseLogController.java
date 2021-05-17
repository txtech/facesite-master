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
import com.nabobsite.modules.nabob.cms.product.entity.UserProductWarehouseLog;
import com.nabobsite.modules.nabob.cms.product.service.UserProductWarehouseLogService;

/**
 * 用户产品仓库日志Controller
 * @author face
 * @version 2021-05-17
 */
@Controller
@RequestMapping(value = "${adminPath}/product/userProductWarehouseLog")
public class UserProductWarehouseLogController extends BaseController {

	@Autowired
	private UserProductWarehouseLogService userProductWarehouseLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserProductWarehouseLog get(String id, boolean isNewRecord) {
		return userProductWarehouseLogService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:userProductWarehouseLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserProductWarehouseLog userProductWarehouseLog, Model model) {
		model.addAttribute("userProductWarehouseLog", userProductWarehouseLog);
		return "cms/product/userProductWarehouseLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:userProductWarehouseLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserProductWarehouseLog> listData(UserProductWarehouseLog userProductWarehouseLog, HttpServletRequest request, HttpServletResponse response) {
		userProductWarehouseLog.setPage(new Page<>(request, response));
		Page<UserProductWarehouseLog> page = userProductWarehouseLogService.findPage(userProductWarehouseLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:userProductWarehouseLog:view")
	@RequestMapping(value = "form")
	public String form(UserProductWarehouseLog userProductWarehouseLog, Model model) {
		model.addAttribute("userProductWarehouseLog", userProductWarehouseLog);
		return "cms/product/userProductWarehouseLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:userProductWarehouseLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserProductWarehouseLog userProductWarehouseLog) {
		userProductWarehouseLogService.save(userProductWarehouseLog);
		return renderResult(Global.TRUE, text("保存用户产品仓库日志成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:userProductWarehouseLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserProductWarehouseLog userProductWarehouseLog) {
		userProductWarehouseLogService.delete(userProductWarehouseLog);
		return renderResult(Global.TRUE, text("删除用户产品仓库日志成功！"));
	}
	
}