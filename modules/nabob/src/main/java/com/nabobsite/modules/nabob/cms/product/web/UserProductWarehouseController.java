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
import com.nabobsite.modules.nabob.cms.product.entity.UserProductWarehouse;
import com.nabobsite.modules.nabob.cms.product.service.UserProductWarehouseService;

/**
 * 用户产品仓库信息Controller
 * @author face
 * @version 2021-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/product/userProductWarehouse")
public class UserProductWarehouseController extends BaseController {

	@Autowired
	private UserProductWarehouseService userProductWarehouseService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserProductWarehouse get(String id, boolean isNewRecord) {
		return userProductWarehouseService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:userProductWarehouse:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserProductWarehouse userProductWarehouse, Model model) {
		model.addAttribute("userProductWarehouse", userProductWarehouse);
		return "cms/product/userProductWarehouseList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:userProductWarehouse:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserProductWarehouse> listData(UserProductWarehouse userProductWarehouse, HttpServletRequest request, HttpServletResponse response) {
		userProductWarehouse.setPage(new Page<>(request, response));
		Page<UserProductWarehouse> page = userProductWarehouseService.findPage(userProductWarehouse);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:userProductWarehouse:view")
	@RequestMapping(value = "form")
	public String form(UserProductWarehouse userProductWarehouse, Model model) {
		model.addAttribute("userProductWarehouse", userProductWarehouse);
		return "cms/product/userProductWarehouseForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:userProductWarehouse:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserProductWarehouse userProductWarehouse) {
		userProductWarehouseService.save(userProductWarehouse);
		return renderResult(Global.TRUE, text("保存用户产品仓库信息成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:userProductWarehouse:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserProductWarehouse userProductWarehouse) {
		userProductWarehouseService.delete(userProductWarehouse);
		return renderResult(Global.TRUE, text("删除用户产品仓库信息成功！"));
	}
	
}