/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.nabobsite.modules.nabob.api.service.ProductApiService;
import com.nabobsite.modules.nabob.api.service.UserInfoApiService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import com.jeesite.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 产品控制
 * @author nada
 * @version 2021-01-11
 */
@RestController
@RequestMapping(value = "${frontPath}/api/product")
@ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "产品接口")
public class ProductApiController extends BaseController {

	@Autowired
	private ProductApiService productApiService;

	@PostMapping(value = {"doBotTask"})
	@ApiOperation(value = "无人机产品刷单任务")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "botId", value = "产品ID", required = true, paramType="query", type="String"),
			@ApiImplicitParam(name = "orderNo", value = "订单号", required = true, paramType="query", type="String"),
			@ApiImplicitParam(name = "param_token",  value = "会话token", required = true),
	})
	public String doBotTask(String botId,String param_token,String orderNo) {
		CommonResult<Boolean> result = productApiService.doBotTask(botId,param_token,orderNo);
		return renderResult(Global.TRUE,text("doBotTask"), result);
	}

	@PostMapping(value = {"getProductBotList"})
	@ApiOperation(value = "无人机产品列表")
	public String getProductBotList() {
		CommonResult<List<ProductBot>> result = productApiService.getProductBotList(new ProductBot());
		return renderResult(Global.TRUE,text("getProductBotList"), result);
	}

	@PostMapping(value = {"getProductWarehouseList"})
	@ApiOperation(value = "云仓库产品列表")
	public String getProductWarehouseList() {
		CommonResult<List<ProductWarehouse>> result = productApiService.getProductWarehouseList(new ProductWarehouse());
		return renderResult(Global.TRUE,text("getProductWarehouseList"), result);
	}

	@PostMapping(value = {"getProductBotInfo"})
	@ApiOperation(value = "无人机产品详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "botId", value = "无人机产品ID", required = true, paramType="query", type="String"),})
	public String getProductBotInfo(String botId) {
		ProductBot productBot = new ProductBot();
		productBot.setId(botId);
		CommonResult<ProductBot> result = productApiService.getProductBotInfo(productBot);
		return renderResult(Global.TRUE,text("getProductBotInfo"), result);
	}

	@PostMapping(value = {"getProductWarehouseInfo"})
	@ApiOperation(value = "云仓库产品详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "warehouseId", value = "云仓库产品ID", required = true, paramType="query", type="String"),})
	public String getProductWarehouseInfo(String warehouseId) {
		ProductWarehouse productWarehouse = new ProductWarehouse();
		productWarehouse.setId(warehouseId);
		CommonResult<ProductWarehouse> result = productApiService.getProductWarehouseInfo(productWarehouse);
		return renderResult(Global.TRUE,text("getProductWarehouseInfo"), result);
	}
}
