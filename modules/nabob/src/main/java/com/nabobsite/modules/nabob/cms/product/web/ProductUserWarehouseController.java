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
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserWarehouse;
import com.nabobsite.modules.nabob.cms.product.service.ProductUserWarehouseService;

/**
 * 用户产品仓库信息Controller
 * @author face
 * @version 2021-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productUserWarehouse")
public class ProductUserWarehouseController extends BaseController {

	@Autowired
	private ProductUserWarehouseService productUserWarehouseService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ProductUserWarehouse get(String id, boolean isNewRecord) {
		return productUserWarehouseService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:productUserWarehouse:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductUserWarehouse productUserWarehouse, Model model) {
		model.addAttribute("productUserWarehouse", productUserWarehouse);
		return "cms/product/productUserWarehouseList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:productUserWarehouse:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ProductUserWarehouse> listData(ProductUserWarehouse productUserWarehouse, HttpServletRequest request, HttpServletResponse response) {
		productUserWarehouse.setPage(new Page<>(request, response));
		Page<ProductUserWarehouse> page = productUserWarehouseService.findPage(productUserWarehouse);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:productUserWarehouse:view")
	@RequestMapping(value = "form")
	public String form(ProductUserWarehouse productUserWarehouse, Model model) {
		model.addAttribute("productUserWarehouse", productUserWarehouse);
		return "cms/product/productUserWarehouseForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:productUserWarehouse:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ProductUserWarehouse productUserWarehouse) {
		productUserWarehouseService.save(productUserWarehouse);
		return renderResult(Global.TRUE, text("保存用户产品仓库信息成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:productUserWarehouse:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ProductUserWarehouse productUserWarehouse) {
		productUserWarehouseService.delete(productUserWarehouse);
		return renderResult(Global.TRUE, text("删除用户产品仓库信息成功！"));
	}
	
}