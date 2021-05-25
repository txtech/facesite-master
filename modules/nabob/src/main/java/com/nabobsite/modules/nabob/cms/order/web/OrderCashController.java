/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.order.web;

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
import com.nabobsite.modules.nabob.cms.order.entity.OrderCash;
import com.nabobsite.modules.nabob.cms.order.service.OrderCashService;

/**
 * 出款订单Controller
 * @author face
 * @version 2021-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderCash")
public class OrderCashController extends BaseController {

	@Autowired
	private OrderCashService orderCashService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public OrderCash get(String id, boolean isNewRecord) {
		return orderCashService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("order:orderCash:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderCash orderCash, Model model) {
		model.addAttribute("orderCash", orderCash);
		return "cms/order/orderCashList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("order:orderCash:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<OrderCash> listData(OrderCash orderCash, HttpServletRequest request, HttpServletResponse response) {
		orderCash.setPage(new Page<>(request, response));
		Page<OrderCash> page = orderCashService.findPage(orderCash);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("order:orderCash:view")
	@RequestMapping(value = "form")
	public String form(OrderCash orderCash, Model model) {
		model.addAttribute("orderCash", orderCash);
		return "cms/order/orderCashForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("order:orderCash:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated OrderCash orderCash) {
		orderCashService.save(orderCash);
		return renderResult(Global.TRUE, text("保存出款订单成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("order:orderCash:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(OrderCash orderCash) {
		orderCashService.delete(orderCash);
		return renderResult(Global.TRUE, text("删除出款订单成功！"));
	}
	
}