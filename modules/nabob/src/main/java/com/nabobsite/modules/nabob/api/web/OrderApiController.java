/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.service.OrderApiService;
import com.nabobsite.modules.nabob.cms.order.entity.OrderPay;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 账户中心
 * @author nada
 * @version 2021-01-11
 */
@RestController
@RequestMapping(value = "${frontPath}/api/order")
@Api(tags = "订单接口(需要登陆)")
@ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
public class OrderApiController extends BaseController {

	@Autowired
	private OrderApiService orderApiService;

	@PostMapping(value = {"rechargeOrder"})
	@ApiOperation(value = "充值订单")
	public CommonResult<JSONObject> rechargeOrder(@RequestBody OrderPay order, HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		order.setIpaddress(ip);
		return orderApiService.rechargeOrder(order,token);
	}

	@PostMapping(value = {"getOrderList"})
	@ApiOperation(value = "获取订单列表")
	public CommonResult<List<OrderPay>> getOrderList(@RequestBody OrderPay orderPay, HttpServletRequest request) {
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return orderApiService.getOrderList(token,orderPay);
	}

	@PostMapping(value = {"getOrderInfo/{orderNo}"})
	@ApiOperation(value = "获取订单详情")
	public CommonResult<OrderPay> getOrderInfo(@PathVariable String orderNo, HttpServletRequest request) {
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return orderApiService.getOrderInfo(token,orderNo);
	}
}
