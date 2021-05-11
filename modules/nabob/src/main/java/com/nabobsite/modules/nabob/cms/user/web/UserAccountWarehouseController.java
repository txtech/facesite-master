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
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountWarehouse;
import com.nabobsite.modules.nabob.cms.user.service.UserAccountWarehouseService;

/**
 * 用户账户仓库信息Controller
 * @author face
 * @version 2021-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userAccountWarehouse")
public class UserAccountWarehouseController extends BaseController {

	@Autowired
	private UserAccountWarehouseService userAccountWarehouseService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserAccountWarehouse get(String id, boolean isNewRecord) {
		return userAccountWarehouseService.get(id, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:userAccountWarehouse:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserAccountWarehouse userAccountWarehouse, Model model) {
		model.addAttribute("userAccountWarehouse", userAccountWarehouse);
		return "cms/user/userAccountWarehouseList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:userAccountWarehouse:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserAccountWarehouse> listData(UserAccountWarehouse userAccountWarehouse, HttpServletRequest request, HttpServletResponse response) {
		userAccountWarehouse.setPage(new Page<>(request, response));
		Page<UserAccountWarehouse> page = userAccountWarehouseService.findPage(userAccountWarehouse);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:userAccountWarehouse:view")
	@RequestMapping(value = "form")
	public String form(UserAccountWarehouse userAccountWarehouse, Model model) {
		model.addAttribute("userAccountWarehouse", userAccountWarehouse);
		return "cms/user/userAccountWarehouseForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:userAccountWarehouse:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserAccountWarehouse userAccountWarehouse) {
		userAccountWarehouseService.save(userAccountWarehouse);
		return renderResult(Global.TRUE, text("保存用户账户仓库信息成功！"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:userAccountWarehouse:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserAccountWarehouse userAccountWarehouse) {
		userAccountWarehouseService.delete(userAccountWarehouse);
		return renderResult(Global.TRUE, text("删除用户账户仓库信息成功！"));
	}

}
