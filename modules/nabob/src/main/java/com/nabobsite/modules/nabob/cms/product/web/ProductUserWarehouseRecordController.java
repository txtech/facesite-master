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
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserWarehouseRecord;
import com.nabobsite.modules.nabob.cms.product.service.ProductUserWarehouseRecordService;

/**
 * 用户云仓库操作记录Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productUserWarehouseRecord")
public class ProductUserWarehouseRecordController extends BaseController {

	@Autowired
	private ProductUserWarehouseRecordService productUserWarehouseRecordService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ProductUserWarehouseRecord get(String id, boolean isNewRecord) {
		return productUserWarehouseRecordService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:productUserWarehouseRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductUserWarehouseRecord productUserWarehouseRecord, Model model) {
		model.addAttribute("productUserWarehouseRecord", productUserWarehouseRecord);
		return "cms/product/productUserWarehouseRecordList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:productUserWarehouseRecord:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ProductUserWarehouseRecord> listData(ProductUserWarehouseRecord productUserWarehouseRecord, HttpServletRequest request, HttpServletResponse response) {
		productUserWarehouseRecord.setPage(new Page<>(request, response));
		Page<ProductUserWarehouseRecord> page = productUserWarehouseRecordService.findPage(productUserWarehouseRecord);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:productUserWarehouseRecord:view")
	@RequestMapping(value = "form")
	public String form(ProductUserWarehouseRecord productUserWarehouseRecord, Model model) {
		model.addAttribute("productUserWarehouseRecord", productUserWarehouseRecord);
		return "cms/product/productUserWarehouseRecordForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:productUserWarehouseRecord:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ProductUserWarehouseRecord productUserWarehouseRecord) {
		productUserWarehouseRecordService.save(productUserWarehouseRecord);
		return renderResult(Global.TRUE, text("保存用户云仓库操作记录成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:productUserWarehouseRecord:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ProductUserWarehouseRecord productUserWarehouseRecord) {
		productUserWarehouseRecordService.delete(productUserWarehouseRecord);
		return renderResult(Global.TRUE, text("删除用户云仓库操作记录成功！"));
	}
	
}