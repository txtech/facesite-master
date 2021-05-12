/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.game.cms.product.web;

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
import com.nabobsite.modules.game.cms.product.entity.ProductBot;
import com.nabobsite.modules.game.cms.product.service.ProductBotService;

/**
 * 产品机器人Controller
 * @author face
 * @version 2021-05-12
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productBot")
public class ProductBotController extends BaseController {

	@Autowired
	private ProductBotService productBotService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ProductBot get(String id, boolean isNewRecord) {
		return productBotService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:productBot:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductBot productBot, Model model) {
		model.addAttribute("productBot", productBot);
		return "cms/product/productBotList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:productBot:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ProductBot> listData(ProductBot productBot, HttpServletRequest request, HttpServletResponse response) {
		productBot.setPage(new Page<>(request, response));
		Page<ProductBot> page = productBotService.findPage(productBot);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:productBot:view")
	@RequestMapping(value = "form")
	public String form(ProductBot productBot, Model model) {
		model.addAttribute("productBot", productBot);
		return "cms/product/productBotForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:productBot:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ProductBot productBot) {
		productBotService.save(productBot);
		return renderResult(Global.TRUE, text("保存产品机器人成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:productBot:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ProductBot productBot) {
		productBotService.delete(productBot);
		return renderResult(Global.TRUE, text("删除产品机器人成功！"));
	}
	
}