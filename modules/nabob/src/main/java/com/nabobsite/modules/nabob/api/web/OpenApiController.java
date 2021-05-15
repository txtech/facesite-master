/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONObject;
import com.ctc.wstx.shaded.msv_core.datatype.xsd.DateType;
import com.jeesite.common.config.Global;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.model.UserInfoModel;
import com.nabobsite.modules.nabob.api.service.ProductApiService;
import com.nabobsite.modules.nabob.api.service.TaskApiService;
import com.nabobsite.modules.nabob.api.service.UserInfoApiService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountLog;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户控制
 * @author nada
 * @version 2021-01-11
 */
@RestController
@RequestMapping(value = "${frontPath}/api/open")
@ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "开放接口(无需登陆)")
public class OpenApiController extends BaseController {

	@Autowired
	private TaskApiService taskApiService;
	@Autowired
	private ProductApiService productApiService;
	@Autowired
	private UserInfoApiService userInfoApiService;

	@PostMapping(value = {"register"})
	@ApiOperation(value = "用户注册")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "UserInfoModel", name = "userInfoModel", value = "用户注册(必须账户、密码,其它可选)", required = true, type="String")
	})
	public String register(@RequestBody UserInfoModel userInfoModel,HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		userInfoModel.setRegistIp(ip);
		CommonResult<Boolean> result = userInfoApiService.register(userInfoModel);
		return renderResult(Global.TRUE,text("register"),result);
	}

	@PostMapping(value = {"login"})
	@ApiOperation(value = "用户登陆")
	@ApiImplicitParams({
		@ApiImplicitParam(dataType = "UserInfoModel", name = "userInfoModel", value = "用户登陆:(必须账户、密码)", required = true, type="String")
	})
	public String login(@RequestBody UserInfoModel userInfoModel, HttpServletRequest request) {
		String loginIp = HttpBrowserTools.getIpAddr(request);
		userInfoModel.setLoginIp(loginIp);
		CommonResult<UserInfoModel> result = userInfoApiService.login(userInfoModel);
		return renderResult(Global.TRUE,text("login"), result);
	}

	@ApiOperation(value = "获取系统配置")
	@PostMapping(value = {"getSysConfig"})
	public String getSysConfig(HttpServletRequest request){
		CommonResult<JSONObject> result = userInfoApiService.getSysConfig();
		return renderResult(Global.TRUE,text("getCountdown"),result);
	}

	@RequestMapping(value = {"getTaskList"})
	@ApiOperation(value = "获取任务列表")
	public String getTaskList(HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		CommonResult<List<TaskInfo>> result = taskApiService.getTaskList(new TaskInfo());
		return renderResult(Global.TRUE,text("getTaskList"), result);
	}

	@RequestMapping(value = {"getProductWarehouseList"})
	@ApiOperation(value = "云仓库产品列表")
	public String getProductWarehouseList(HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		CommonResult<List<ProductWarehouse>> result = productApiService.getProductWarehouseList(new ProductWarehouse());
		return renderResult(Global.TRUE,text("getProductWarehouseList"), result);
	}

	@RequestMapping(value = {"getProductBotList"})
	@ApiOperation(value = "无人机产品列表")
	public String getProductBotList(HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		CommonResult<List<ProductBot>> result = productApiService.getProductBotList(new ProductBot());
		return renderResult(Global.TRUE,text("getProductBotList"), result);
	}
}
