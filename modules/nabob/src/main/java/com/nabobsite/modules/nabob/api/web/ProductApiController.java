/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;
import com.alibaba.fastjson.JSONObject;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import com.jeesite.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 产品控制
 * @author nada
 * @version 2021-01-11
 */
@RestController
@RequestMapping(value = "${frontPath}/product/api")
@ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "产品接口：无人机产品列表、云仓库产品列表")
public class ProductApiController extends BaseController {

	@PostMapping(value = {"getProductBotList"})
	@ApiOperation(value = "无人机产品列表")
	public JSONObject getProductBotList(@RequestBody ProductBot productBot, HttpServletRequest request, HttpServletResponse response) {
		String ip = HttpBrowserTools.getIpAddr(request);
		return null;
	}

	@PostMapping(value = {"getProductWarehouseList"})
	@ApiOperation(value = "云仓库产品列表")
	public JSONObject getProductWarehouseList(@RequestBody ProductWarehouse productWarehouse, HttpServletRequest request, HttpServletResponse response) {
		String ip = HttpBrowserTools.getIpAddr(request);
		return null;
	}
}
