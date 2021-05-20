/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.service.CashApiService;
import com.nabobsite.modules.nabob.cms.order.entity.Cash;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
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
	public CommonResult<Boolean> rechargeOrder(@RequestBody Cash cash,HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return cashApiService.cashOrder(token,new Cash());
	}

	@PostMapping(value = {"getCashOrderList"})
	@ApiOperation(value = "获取订单列表")
	public CommonResult<JSONArray> getCashOrderList(HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return cashApiService.getCashOrderList(token,new Cash());
	}

	@PostMapping(value = {"getCashOrderInfo/{orderNo}"})
	@ApiOperation(value = "获取订单详情")
	public CommonResult<JSONObject> getCashOrderInfo(@PathVariable String orderNo,HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return cashApiService.getCashOrderInfo(token,new Cash());
	}
}
