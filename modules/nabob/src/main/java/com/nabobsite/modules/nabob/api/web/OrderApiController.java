/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.model.OrderInfoModel;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;
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
@Api(tags = "订单接口(需要登陆)")
@ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
public class OrderApiController extends BaseController {

	@Autowired
	private OrderApiService orderApiService;

	@PostMapping(value = {"rechargeOrder"})
	@ApiOperation(value = "充值订单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "payMoney", value = "充值金额", required = true),
			@ApiImplicitParam(name = "name",  value = "名称", required = true),
			@ApiImplicitParam(name = "email",value = "邮箱", required = true),
			@ApiImplicitParam(name = "phoneNumber",value = "电话号码", required = true),
			@ApiImplicitParam(name = "mark", value = "来者不善", required = false, type="String"),
	})
	public CommonResult<OrderInfoModel> rechargeOrder(@RequestBody OrderInfoModel orderInfoModel,HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		orderInfoModel.setIpaddress(ip);
		return orderApiService.rechargeOrder(orderInfoModel,token);
	}

	@PostMapping(value = {"getOrderList"})
	@ApiOperation(value = "获取订单列表")
	public CommonResult<List<Order>> getOrderList(HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return orderApiService.getOrderList(new Order(),token);
	}

	@PostMapping(value = {"getOrderInfo/{orderNo}"})
	@ApiOperation(value = "获取订单详情")
	public CommonResult<OrderInfoModel> getOrderInfo(@PathVariable String orderNo, HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		Order order = new Order();
		order.setOrderNo(orderNo);
		return orderApiService.getOrderInfo(order,token);
	}
}
