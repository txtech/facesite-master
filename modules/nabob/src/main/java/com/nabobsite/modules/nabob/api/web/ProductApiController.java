/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.service.ProductApiService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductBotLog;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductWarehouseRecord;
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

	//操作接口
	@PostMapping(value = {"doWarehouseDeposit"})
	@ApiOperation(value = "云仓库定投产品存款")
	public CommonResult<Boolean> doWarehouseDeposit(@RequestBody UserProductWarehouseRecord userProductWarehouseRecord, HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return productApiService.doWarehouseDeposit(token,userProductWarehouseRecord);
	}
	@PostMapping(value = {"doWarehouseWithdraw"})
	@ApiOperation(value = "云仓库定投产品撤资")
	public CommonResult<Boolean> doWarehouseWithdraw(@RequestBody UserProductWarehouseRecord userProductWarehouseRecord, HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return productApiService.doWarehouseWithdraw(token,userProductWarehouseRecord);
	}
	@PostMapping(value = {"doWarehouseToBalance"})
	@ApiOperation(value = "云仓库收益提取到余额")
	public CommonResult<Boolean> doWarehouseToBalance(@RequestBody UserProductWarehouseRecord userProductWarehouseRecord, HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return productApiService.doWarehouseToBalance(token,userProductWarehouseRecord);
	}
	@PostMapping(value = {"doBotTask"})
	@ApiOperation(value = "无人机产品刷单接口")
	public CommonResult<Boolean> doBotTask(@RequestBody UserProductBotLog userProductBotLog, HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return productApiService.doBotTask(token,userProductBotLog);
	}


	//无人机接口
	@RequestMapping(value = {"getProductBotInfo/{botId}"})
	@ApiOperation(value = "无人机产品详情")
	public CommonResult<JSONObject> getProductBotInfo(@PathVariable String botId, HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		ProductBot productBot = new ProductBot();
		productBot.setId(botId);
		return productApiService.getProductBotInfo(token,productBot);
	}
	@RequestMapping(value = {"getUserBotInfo/{botId}"})
	@ApiOperation(value = "用户无人机产品详情")
	public CommonResult<JSONObject> getUserBotInfo(@PathVariable String botId, HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return productApiService.getUserBotInfo(token,botId);
	}



	//云仓库接口
	@RequestMapping(value = {"getProductWarehouseInfo/{warehouseId}"})
	@ApiOperation(value = "云仓库产品详情")
	public CommonResult<JSONObject> getProductWarehouseInfo(@PathVariable String warehouseId,HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return productApiService.getProductWarehouseInfo(token,warehouseId);
	}
	@RequestMapping(value = {"getUserWarehouseInfo/{warehouseId}"})
	@ApiOperation(value = "用户云仓库产品详情")
	public CommonResult<JSONObject> getUserWarehouseInfo(@PathVariable String warehouseId, HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return productApiService.getUserWarehouseInfo(token,warehouseId);
	}
	@RequestMapping(value = {"getUserAccountWarehouseInfo"})
	@ApiOperation(value = "用户云仓库账户")
	public CommonResult<JSONObject> getUserAccountWarehouseInfo(HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return productApiService.getUserAccountWarehouseInfo(token);
	}


	//云仓库明细接口
	@RequestMapping(value = {"getUserWarehousePersonalIncomeList"})
	@ApiOperation(value = "用户云仓库个人收入记录列表")
	public CommonResult<JSONArray> getUserWarehousePersonalIncomeList(HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return productApiService.getUserWarehousePersonalIncomeList(token);
	}
	@RequestMapping(value = {"getUserWarehouseTeamIncomeList"})
	@ApiOperation(value = "用户云仓库团队收入记录列表")
	public CommonResult<JSONArray> getUserWarehouseTeamIncomeList(HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return productApiService.getUserWarehouseTeamIncomeList(token);
	}
	@RequestMapping(value = {"getUserWarehouseOperationList"})
	@ApiOperation(value = "用户云仓库操纵记录列表")
	public CommonResult<JSONArray> getUserWarehouseOperationList(HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return productApiService.getUserWarehouseOperationList(token);
	}
}
