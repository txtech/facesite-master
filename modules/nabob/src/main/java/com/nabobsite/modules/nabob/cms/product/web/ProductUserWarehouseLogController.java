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
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserWarehouseLog;
import com.nabobsite.modules.nabob.cms.product.service.ProductUserWarehouseLogService;

/**
 * 用户产品仓库日志Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productUserWarehouseLog")
public class ProductUserWarehouseLogController extends BaseController {

	@Autowired
	private ProductUserWarehouseLogService productUserWarehouseLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ProductUserWarehouseLog get(String id, boolean isNewRecord) {
		return productUserWarehouseLogService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:productUserWarehouseLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductUserWarehouseLog productUserWarehouseLog, Model model) {
		model.addAttribute("productUserWarehouseLog", productUserWarehouseLog);
		return "cms/product/productUserWarehouseLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:productUserWarehouseLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ProductUserWarehouseLog> listData(ProductUserWarehouseLog productUserWarehouseLog, HttpServletRequest request, HttpServletResponse response) {
		productUserWarehouseLog.setPage(new Page<>(request, response));
		Page<ProductUserWarehouseLog> page = productUserWarehouseLogService.findPage(productUserWarehouseLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:productUserWarehouseLog:view")
	@RequestMapping(value = "form")
	public String form(ProductUserWarehouseLog productUserWarehouseLog, Model model) {
		model.addAttribute("productUserWarehouseLog", productUserWarehouseLog);
		return "cms/product/productUserWarehouseLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:productUserWarehouseLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ProductUserWarehouseLog productUserWarehouseLog) {
		productUserWarehouseLogService.save(productUserWarehouseLog);
		return renderResult(Global.TRUE, text("保存用户产品仓库日志成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:productUserWarehouseLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ProductUserWarehouseLog productUserWarehouseLog) {
		productUserWarehouseLogService.delete(productUserWarehouseLog);
		return renderResult(Global.TRUE, text("删除用户产品仓库日志成功！"));
	}
	
}