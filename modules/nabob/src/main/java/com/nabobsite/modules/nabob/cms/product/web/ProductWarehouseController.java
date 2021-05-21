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
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import com.nabobsite.modules.nabob.cms.product.service.ProductWarehouseService;

/**
 * 产品仓库Controller
 * @author face
 * @version 2021-05-21
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productWarehouse")
public class ProductWarehouseController extends BaseController {

	@Autowired
	private ProductWarehouseService productWarehouseService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ProductWarehouse get(String id, boolean isNewRecord) {
		return productWarehouseService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:productWarehouse:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductWarehouse productWarehouse, Model model) {
		model.addAttribute("productWarehouse", productWarehouse);
		return "cms/product/productWarehouseList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:productWarehouse:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ProductWarehouse> listData(ProductWarehouse productWarehouse, HttpServletRequest request, HttpServletResponse response) {
		productWarehouse.setPage(new Page<>(request, response));
		Page<ProductWarehouse> page = productWarehouseService.findPage(productWarehouse);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:productWarehouse:view")
	@RequestMapping(value = "form")
	public String form(ProductWarehouse productWarehouse, Model model) {
		model.addAttribute("productWarehouse", productWarehouse);
		return "cms/product/productWarehouseForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:productWarehouse:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ProductWarehouse productWarehouse) {
		productWarehouseService.save(productWarehouse);
		return renderResult(Global.TRUE, text("保存产品仓库成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:productWarehouse:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ProductWarehouse productWarehouse) {
		productWarehouseService.delete(productWarehouse);
		return renderResult(Global.TRUE, text("删除产品仓库成功！"));
	}
	
}