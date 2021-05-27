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
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserBotAistart;
import com.nabobsite.modules.nabob.cms.product.service.ProductUserBotAistartService;

/**
 * 用户产品机器人智能任务Controller
 * @author face
 * @version 2021-05-27
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productUserBotAistart")
public class ProductUserBotAistartController extends BaseController {

	@Autowired
	private ProductUserBotAistartService productUserBotAistartService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ProductUserBotAistart get(String id, boolean isNewRecord) {
		return productUserBotAistartService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:productUserBotAistart:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductUserBotAistart productUserBotAistart, Model model) {
		model.addAttribute("productUserBotAistart", productUserBotAistart);
		return "cms/product/productUserBotAistartList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:productUserBotAistart:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ProductUserBotAistart> listData(ProductUserBotAistart productUserBotAistart, HttpServletRequest request, HttpServletResponse response) {
		productUserBotAistart.setPage(new Page<>(request, response));
		Page<ProductUserBotAistart> page = productUserBotAistartService.findPage(productUserBotAistart);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:productUserBotAistart:view")
	@RequestMapping(value = "form")
	public String form(ProductUserBotAistart productUserBotAistart, Model model) {
		model.addAttribute("productUserBotAistart", productUserBotAistart);
		return "cms/product/productUserBotAistartForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:productUserBotAistart:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ProductUserBotAistart productUserBotAistart) {
		productUserBotAistartService.save(productUserBotAistart);
		return renderResult(Global.TRUE, text("保存用户产品机器人智能任务成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:productUserBotAistart:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ProductUserBotAistart productUserBotAistart) {
		productUserBotAistartService.delete(productUserBotAistart);
		return renderResult(Global.TRUE, text("删除用户产品机器人智能任务成功！"));
	}
	
}