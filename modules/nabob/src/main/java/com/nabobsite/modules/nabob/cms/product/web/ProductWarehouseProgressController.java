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
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouseProgress;
import com.nabobsite.modules.nabob.cms.product.service.ProductWarehouseProgressService;

/**
 * 用户任务进行中Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productWarehouseProgress")
public class ProductWarehouseProgressController extends BaseController {

	@Autowired
	private ProductWarehouseProgressService productWarehouseProgressService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ProductWarehouseProgress get(String id, boolean isNewRecord) {
		return productWarehouseProgressService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:productWarehouseProgress:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductWarehouseProgress productWarehouseProgress, Model model) {
		model.addAttribute("productWarehouseProgress", productWarehouseProgress);
		return "cms/product/productWarehouseProgressList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:productWarehouseProgress:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ProductWarehouseProgress> listData(ProductWarehouseProgress productWarehouseProgress, HttpServletRequest request, HttpServletResponse response) {
		productWarehouseProgress.setPage(new Page<>(request, response));
		Page<ProductWarehouseProgress> page = productWarehouseProgressService.findPage(productWarehouseProgress);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:productWarehouseProgress:view")
	@RequestMapping(value = "form")
	public String form(ProductWarehouseProgress productWarehouseProgress, Model model) {
		model.addAttribute("productWarehouseProgress", productWarehouseProgress);
		return "cms/product/productWarehouseProgressForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:productWarehouseProgress:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ProductWarehouseProgress productWarehouseProgress) {
		productWarehouseProgressService.save(productWarehouseProgress);
		return renderResult(Global.TRUE, text("保存用户任务进行中成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:productWarehouseProgress:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ProductWarehouseProgress productWarehouseProgress) {
		productWarehouseProgressService.delete(productWarehouseProgress);
		return renderResult(Global.TRUE, text("删除用户任务进行中成功！"));
	}
	
}