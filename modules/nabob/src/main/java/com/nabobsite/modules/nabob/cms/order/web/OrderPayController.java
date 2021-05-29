/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.order.web;

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
import com.nabobsite.modules.nabob.cms.order.entity.OrderPay;
import com.nabobsite.modules.nabob.cms.order.service.OrderPayService;

/**
 * 交易订单Controller
 * @author face
 * @version 2021-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderPay")
public class OrderPayController extends BaseController {

	@Autowired
	private OrderPayService orderPayService;
	@Autowired
	private BaseDataScopeFilter baseDataScopeFilter;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public OrderPay get(String id, boolean isNewRecord) {
		return orderPayService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("order:orderPay:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderPay orderPay, Model model) {
		model.addAttribute("orderPay", orderPay);
		return "cms/order/orderPayList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("order:orderPay:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<OrderPay> listData(OrderPay orderPay, HttpServletRequest request, HttpServletResponse response) {
		orderPay.setPage(new Page<>(request, response));
		// 调用数据权限过滤方法（重点）
		baseDataScopeFilter.addDataScopeFilter(orderPay);
		Page<OrderPay> page = orderPayService.findPage(orderPay);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("order:orderPay:view")
	@RequestMapping(value = "form")
	public String form(OrderPay orderPay, Model model) {
		model.addAttribute("orderPay", orderPay);
		return "cms/order/orderPayForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("order:orderPay:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated OrderPay orderPay) {
		orderPayService.save(orderPay);
		return renderResult(Global.TRUE, text("保存交易订单成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("order:orderPay:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(OrderPay orderPay) {
		orderPayService.delete(orderPay);
		return renderResult(Global.TRUE, text("删除交易订单成功！"));
	}
	
}