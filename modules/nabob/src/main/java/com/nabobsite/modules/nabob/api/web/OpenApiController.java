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
	public CommonResult<Boolean> register(@RequestBody UserInfoModel userInfoModel,HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		userInfoModel.setRegistIp(ip);
		return userInfoApiService.register(userInfoModel);
	}

	@PostMapping(value = {"login"})
	@ApiOperation(value = "用户登陆")
	@ApiImplicitParams({
		@ApiImplicitParam(dataType = "UserInfoModel", name = "userInfoModel", value = "用户登陆:(必须账户、密码)", required = true, type="String")
	})
	public CommonResult<UserInfoModel> login(@RequestBody UserInfoModel userInfoModel, HttpServletRequest request) {
		String loginIp = HttpBrowserTools.getIpAddr(request);
		userInfoModel.setLoginIp(loginIp);
		return userInfoApiService.login(userInfoModel);
	}

	@ApiOperation(value = "获取系统配置")
	@PostMapping(value = {"getSysConfig"})
	public CommonResult<JSONObject> getSysConfig(HttpServletRequest request){
		return userInfoApiService.getSysConfig();
	}

	@RequestMapping(value = {"getTaskList"})
	@ApiOperation(value = "获取任务列表")
	public CommonResult<List<TaskInfo>> getTaskList(HttpServletRequest request) {
		return taskApiService.getTaskList(new TaskInfo());
	}

	@RequestMapping(value = {"getProductWarehouseList"})
	@ApiOperation(value = "云仓库产品列表")
	public CommonResult<List<ProductWarehouse>> getProductWarehouseList(HttpServletRequest request) {
		return productApiService.getProductWarehouseList(new ProductWarehouse());
	}

	@RequestMapping(value = {"getProductBotList"})
	@ApiOperation(value = "无人机产品列表")
	public CommonResult<List<ProductBot>> getProductBotList(HttpServletRequest request) {
		return productApiService.getProductBotList(new ProductBot());
	}
}