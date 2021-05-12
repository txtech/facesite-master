/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.service.OrderApiService;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.service.ApiListing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * 账户中心
 * @author nada
 * @version 2021-01-11
 */
@RestController
@RequestMapping(value = "${frontPath}/api/order")
@Api(tags = "订单接口")
public class OrderApiController extends BaseController {

	@Autowired
	private OrderApiService orderApiService;

	@PostMapping(value = {"rechargeOrder"})
	@ApiOperation(value = "充值订单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param_token", value = "会话令牌", required = true, paramType="query", type="String"),
			@ApiImplicitParam(name = "payMoney", value = "充值金额", required = true),
			@ApiImplicitParam(name = "name",  value = "名称", required = true),
			@ApiImplicitParam(name = "email",value = "邮箱", required = true),
			@ApiImplicitParam(name = "phoneNumber",value = "电话号码", required = true),
	})
	public String rechargeOrder(String param_token,String payMoney,String name,String email,String phoneNumber,HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		Order order = new Order();
		order.setName(name);
		order.setEmail(email);
		order.setIpaddress(ip);
		order.setPhoneNumber(phoneNumber);
		order.setPayMoney(new BigDecimal(payMoney));
		CommonResult<Order> result = orderApiService.rechargeOrder(order,param_token);
		return renderResult(Global.TRUE,text("rechargeOrder"), result);
	}

	@PostMapping(value = {"getOrderList"})
	@ApiOperation(value = "获取订单列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "param_token", value = "会话令牌", required = true, paramType="query", type="String"),})
	public String getOrderList(String param_token) {
		CommonResult<List<Order>> result = orderApiService.getOrderList(new Order(),param_token);
		return renderResult(Global.TRUE,text("getOrderList"), result);
	}

	@PostMapping(value = {"getOrderInfo"})
	@ApiOperation(value = "获取订单详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param_token", value = "会话令牌", required = true, paramType="query", type="String"),
			@ApiImplicitParam(name = "orderNo", value = "订单号"),
	})
	public String getOrderInfo(String param_token,String orderNo) {
		Order order = new Order();
		order.setOrderNo(orderNo);
		CommonResult<Order> result = orderApiService.getOrderInfo(order,param_token);
		return renderResult(Global.TRUE,text("getOrderInfo"), result);
	}
}
