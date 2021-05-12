/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.service.CashApiService;
import com.nabobsite.modules.nabob.api.service.OrderApiService;
import com.nabobsite.modules.nabob.cms.order.entity.Cash;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 账户中心
 * @author nada
 * @version 2021-01-11
 */
@RestController
@RequestMapping(value = "${frontPath}/api/cash")
@Api(tags = "出款接口")
public class CashApiController extends BaseController {


	@Autowired
	private CashApiService cashApiService;

	@PostMapping(value = {"cashOrder"})
	@ApiOperation(value = "提款订单")
	@ApiImplicitParams({ @ApiImplicitParam(name = "param_token", value = "会话令牌", required = true, paramType="query", type="String"),})
	public String rechargeOrder(String param_token,HttpServletRequest request) {
		CommonResult<Cash> result = cashApiService.cashOrder(new Cash());
		return renderResult(Global.TRUE,text("cashOrder"), result);
	}

	@PostMapping(value = {"getCashOrderList"})
	@ApiOperation(value = "获取订单列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "param_token", value = "会话令牌", required = true, paramType="query", type="String"),})
	public String getCashOrderList(String param_token,HttpServletRequest request) {
		CommonResult<List<Cash>> result = cashApiService.getCashOrderList(new Cash());
		return renderResult(Global.TRUE,text("getCashOrderList"), result);
	}

	@PostMapping(value = {"getCashOrderInfo"})
	@ApiOperation(value = "获取订单详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "param_token", value = "会话令牌", required = true, paramType="query", type="String"),})
	public String getCashOrderInfo(String param_token,HttpServletRequest request) {
		CommonResult<Cash> result = cashApiService.getCashOrderInfo(new Cash());
		return renderResult(Global.TRUE,text("getCashOrderInfo"), result);
	}
}
