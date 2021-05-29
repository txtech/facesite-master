/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nabobsite.modules.nabob.cms.base.BaseDataScopeFilter;
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
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserBotLog;
import com.nabobsite.modules.nabob.cms.product.service.ProductUserBotLogService;

/**
 * 用户产品机器人记录Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productUserBotLog")
public class ProductUserBotLogController extends BaseController {

	@Autowired
	private ProductUserBotLogService productUserBotLogService;
	@Autowired
	private BaseDataScopeFilter baseDataScopeFilter;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ProductUserBotLog get(String id, boolean isNewRecord) {
		return productUserBotLogService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:productUserBotLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductUserBotLog productUserBotLog, Model model) {
		model.addAttribute("productUserBotLog", productUserBotLog);
		return "cms/product/productUserBotLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:productUserBotLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ProductUserBotLog> listData(ProductUserBotLog productUserBotLog, HttpServletRequest request, HttpServletResponse response) {
		productUserBotLog.setPage(new Page<>(request, response));
		// 调用数据权限过滤方法（重点）
		baseDataScopeFilter.addDataScopeFilter(productUserBotLog);
		Page<ProductUserBotLog> page = productUserBotLogService.findPage(productUserBotLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:productUserBotLog:view")
	@RequestMapping(value = "form")
	public String form(ProductUserBotLog productUserBotLog, Model model) {
		model.addAttribute("productUserBotLog", productUserBotLog);
		return "cms/product/productUserBotLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:productUserBotLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ProductUserBotLog productUserBotLog) {
		productUserBotLogService.save(productUserBotLog);
		return renderResult(Global.TRUE, text("保存用户产品机器人记录成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:productUserBotLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ProductUserBotLog productUserBotLog) {
		productUserBotLogService.delete(productUserBotLog);
		return renderResult(Global.TRUE, text("删除用户产品机器人记录成功！"));
	}
	
}