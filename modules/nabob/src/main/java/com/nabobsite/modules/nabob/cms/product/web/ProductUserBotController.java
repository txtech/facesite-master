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
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserBot;
import com.nabobsite.modules.nabob.cms.product.service.ProductUserBotService;

/**
 * 用户产品机器人信息Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productUserBot")
public class ProductUserBotController extends BaseController {

	@Autowired
	private ProductUserBotService productUserBotService;
	@Autowired
	private BaseDataScopeFilter baseDataScopeFilter;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ProductUserBot get(String id, boolean isNewRecord) {
		return productUserBotService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:productUserBot:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductUserBot productUserBot, Model model) {
		model.addAttribute("productUserBot", productUserBot);
		return "cms/product/productUserBotList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:productUserBot:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ProductUserBot> listData(ProductUserBot productUserBot, HttpServletRequest request, HttpServletResponse response) {
		productUserBot.setPage(new Page<>(request, response));
		// 调用数据权限过滤方法（重点）
		baseDataScopeFilter.addDataScopeFilter(productUserBot);
		Page<ProductUserBot> page = productUserBotService.findPage(productUserBot);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:productUserBot:view")
	@RequestMapping(value = "form")
	public String form(ProductUserBot productUserBot, Model model) {
		model.addAttribute("productUserBot", productUserBot);
		return "cms/product/productUserBotForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:productUserBot:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ProductUserBot productUserBot) {
		productUserBotService.save(productUserBot);
		return renderResult(Global.TRUE, text("保存用户产品机器人信息成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:productUserBot:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ProductUserBot productUserBot) {
		productUserBotService.delete(productUserBot);
		return renderResult(Global.TRUE, text("删除用户产品机器人信息成功！"));
	}
	
}