/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.service.CashApiService;
import com.nabobsite.modules.nabob.cms.order.entity.OrderCash;
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
@RequestMapping(value = "${frontPath}/api/cash")
@ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "出款接口(需要登陆)")
public class CashApiController extends BaseController {

	@Autowired
	private CashApiService cashApiService;

	@PostMapping(value = {"cashOrder"})
	@ApiOperation(value = "提款接口")
	public CommonResult<JSONObject> rechargeOrder(@RequestBody OrderCash cash, HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		cash.setIpaddress(ip);
		return cashApiService.cashOrder(token,cash);
	}

	@PostMapping(value = {"getCashOrderList"})
	@ApiOperation(value = "获取订单列表")
	public CommonResult<List<OrderCash>> getCashOrderList(@RequestBody OrderCash cash, HttpServletRequest request) {
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return cashApiService.getCashOrderList(token,cash);
	}

	@PostMapping(value = {"getCashOrderInfo/{orderNo}"})
	@ApiOperation(value = "获取订单详情")
	public CommonResult<OrderCash> getCashOrderInfo(@PathVariable String orderNo,HttpServletRequest request) {
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return cashApiService.getCashOrderInfo(token,orderNo);
	}
}
