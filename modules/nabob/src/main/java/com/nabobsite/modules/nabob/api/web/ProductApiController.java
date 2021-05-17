/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.model.req.BotTaskReqModel;
import com.nabobsite.modules.nabob.api.service.ProductApiService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductWarehouse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 产品控制
 * @author nada
 * @version 2021-01-11
 */
@RestController
@RequestMapping(value = "${frontPath}/api/product")
@ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "产品接口(需要登陆)")
public class ProductApiController extends BaseController {

	@Autowired
	private ProductApiService productApiService;

	@PostMapping(value = {"doBotTask"})
	@ApiOperation(value = "产品刷单接口")
	public CommonResult<Boolean> doBotTask(@RequestBody BotTaskReqModel botTaskReqModel, HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return productApiService.doBotTask(token,botTaskReqModel);
	}

	@RequestMapping(value = {"getProductBotInfo/{botId}"})
	@ApiOperation(value = "无人机产品详情")
	public CommonResult<ProductBot> getProductBotInfo(@PathVariable String botId,HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		ProductBot productBot = new ProductBot();
		productBot.setId(botId);
		return productApiService.getProductBotInfo(token,productBot);
	}

	@RequestMapping(value = {"getProductWarehouseInfo/{warehouseId}"})
	@ApiOperation(value = "云仓库产品详情")
	public CommonResult<ProductWarehouse> getProductWarehouseInfo(@PathVariable String warehouseId,HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		ProductWarehouse productWarehouse = new ProductWarehouse();
		productWarehouse.setId(warehouseId);
		return productApiService.getProductWarehouseInfo(token,productWarehouse);
	}


	@RequestMapping(value = {"getUserBotInfo/{botId}"})
	@ApiOperation(value = "用户无人机产品详情")
	public CommonResult<UserProductBot> getUserBotInfo(@PathVariable String botId, HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return productApiService.getUserBotInfo(token,botId);
	}

	@RequestMapping(value = {"getUserWarehouseInfo/{warehouseId}"})
	@ApiOperation(value = "用户云仓库产品详情")
	public CommonResult<UserProductWarehouse> getUserWarehouseInfo(@PathVariable String warehouseId, HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return productApiService.getUserWarehouseInfo(token,warehouseId);
	}
}
