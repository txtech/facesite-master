/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;
import com.jeesite.common.config.Global;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.model.req.BotTaskReqModel;
import com.nabobsite.modules.nabob.api.service.ProductApiService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.jeesite.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
	public String doBotTask(@RequestBody BotTaskReqModel botTaskReqModel, HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		CommonResult<Boolean> result = productApiService.doBotTask(token,botTaskReqModel);
		return renderResult(Global.TRUE,text("doBotTask"), result);
	}

	@RequestMapping(value = {"getProductBotInfo/{botId}"})
	@ApiOperation(value = "无人机产品详情")
	public String getProductBotInfo(@PathVariable String botId,HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		ProductBot productBot = new ProductBot();
		productBot.setId(botId);
		CommonResult<ProductBot> result = productApiService.getProductBotInfo(productBot);
		return renderResult(Global.TRUE,text("getProductBotInfo"), result);
	}

	@RequestMapping(value = {"getProductWarehouseInfo/{warehouseId}"})
	@ApiOperation(value = "云仓库产品详情")
	public String getProductWarehouseInfo(@PathVariable String warehouseId,HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		ProductWarehouse productWarehouse = new ProductWarehouse();
		productWarehouse.setId(warehouseId);
		CommonResult<ProductWarehouse> result = productApiService.getProductWarehouseInfo(productWarehouse);
		return renderResult(Global.TRUE,text("getProductWarehouseInfo"), result);
	}
}
