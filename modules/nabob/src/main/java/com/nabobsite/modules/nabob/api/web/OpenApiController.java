/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.model.SmsModel;
import com.nabobsite.modules.nabob.api.model.VerificationCodeModel;
import com.nabobsite.modules.nabob.api.service.ProductApiService;
import com.nabobsite.modules.nabob.api.service.SmsCodeApiService;
import com.nabobsite.modules.nabob.api.service.UserInfoApiService;
import com.nabobsite.modules.nabob.api.service.UserTaskApiService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import com.nabobsite.modules.nabob.cms.sys.entity.SysNotice;
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfoMembership;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
	private SmsCodeApiService smsCodeApiService;
	@Autowired
	private UserTaskApiService userTaskApiService;
	@Autowired
	private ProductApiService productApiService;
	@Autowired
	private UserInfoApiService userInfoApiService;

	@PostMapping(value = {"register"})
	@ApiOperation(value = "用户注册")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "UserInfoModel", name = "userInfoModel", value = "用户注册(必须账户、密码,其它可选)", required = true, type="String")
	})
	public CommonResult<Boolean> register(@RequestBody UserInfo userInfo, HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		userInfo.setRegistIp(ip);
		return userInfoApiService.register(userInfo);
	}

	@PostMapping(value = {"forgetPwd"})
	@ApiOperation(value = "忘记密码")
	@ApiImplicitParams({
			@ApiImplicitParam(dataType = "UserInfoModel", name = "userInfoModel", value = "用户注册(必须账户、密码、验证码,其它可选)", required = true, type="String")
	})
	public CommonResult<Boolean> forgetPwd(@RequestBody UserInfo userInfo,HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		userInfo.setRegistIp(ip);
		return userInfoApiService.forgetPwd(userInfo);
	}

	@PostMapping(value = {"login"})
	@ApiOperation(value = "用户登陆")
	@ApiImplicitParams({
		@ApiImplicitParam(dataType = "UserInfoModel", name = "userInfoModel", value = "用户登陆:(必须账户、密码)", required = true, type="String")
	})
	public CommonResult<JSONObject> login(@RequestBody UserInfo userInfo, HttpServletRequest request) {
		String loginIp = HttpBrowserTools.getIpAddr(request);
		userInfo.setLoginIp(loginIp);
		return userInfoApiService.login(userInfo);
	}

	@PostMapping(value = {"getSysConfig"})
	@ApiOperation(value = "获取系统配置")
	public CommonResult<JSONObject> getSysConfig(HttpServletRequest request){
		return userInfoApiService.getSysConfig();
	}


	@PostMapping(value = {"getSysNotice"})
	@ApiOperation(value = "获取通知信息")
	public CommonResult<List<SysNotice>> getSysNotice(@RequestBody SysNotice sysNotice, HttpServletRequest request){
		return userInfoApiService.getSysNotice(sysNotice);
	}


	@PostMapping(value = {"getMemberShipList"})
	@ApiOperation(value = "获取会员资格列表")
	public CommonResult<List<UserInfoMembership>> getMemberShipList(@RequestBody UserInfoMembership userInfoMembership, HttpServletRequest request){
		return userInfoApiService.getUserMemberShipList(userInfoMembership);
	}

	@PostMapping(value = {"getMemberShipInfo/{id}"})
	@ApiOperation(value = "获取会员资格详情")
	public CommonResult<UserInfoMembership> getMemberShipInfo(@PathVariable  String id, HttpServletRequest request){
		return userInfoApiService.getMemberShipInfo(id);
	}

	@RequestMapping(value = {"getTaskList"})
	@ApiOperation(value = "获取任务列表")
	public CommonResult<List<TaskInfo>> getTaskList(@RequestBody TaskInfo taskInfo, HttpServletRequest request) {
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return userTaskApiService.getTaskList(token,taskInfo);
	}

	@RequestMapping(value = {"getProductWarehouseList"})
	@ApiOperation(value = "云仓库产品列表")
	public CommonResult<List<ProductWarehouse>> getProductWarehouseList(@RequestBody ProductWarehouse productWarehouse, HttpServletRequest request) {
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return productApiService.getProductWarehouseList(token,productWarehouse);
	}

	@RequestMapping(value = {"getProductBotList"})
	@ApiOperation(value = "无人机产品列表")
	public CommonResult<List<ProductBot>> getProductBotList(@RequestBody ProductBot productBot, HttpServletRequest request) {
		return productApiService.getProductBotList(productBot);
	}

	@RequestMapping(value = {"sendSms"})
	@ApiOperation(value = "发送短信验证码")
	public CommonResult<Boolean> sendSms(@RequestBody SmsModel smsModel, HttpServletRequest request) {
		String ipAddr = HttpBrowserTools.getIpAddr(request);
		return smsCodeApiService.sendSms(smsModel);
	}

	@RequestMapping(value = {"checkSmsCode"})
	@ApiOperation(value = "验证短信验证码")
	public CommonResult<Boolean> checkSmsCode(@RequestBody SmsModel smsModel, HttpServletRequest request) {
		String ipAddr = HttpBrowserTools.getIpAddr(request);
		return smsCodeApiService.checkSmsCode(smsModel);
	}

	@RequestMapping(value = {"getRandomCode"})
	@ApiOperation(value = "获取随机码")
	public CommonResult<Boolean> getRandomCode(@RequestBody SmsModel smsModel,HttpServletRequest request) {
		return smsCodeApiService.getRandomCode(smsModel);
	}

	@RequestMapping(value = {"checkRandomCode"})
	@ApiOperation(value = "验证随机码")
	public CommonResult<Boolean> checkRandomCode(@RequestBody SmsModel smsModel, HttpServletRequest request) {
		String ipAddr = HttpBrowserTools.getIpAddr(request);
		return smsCodeApiService.checkRandomCode(smsModel);
	}

	@RequestMapping(value = {"getImgRandomCode"})
	@ApiOperation(value = "获取图片随机码")
	public CommonResult<JSONObject> getImgRandomCode(HttpServletRequest request) {
		return smsCodeApiService.getImgRandomCode();
	}

	@RequestMapping(value = {"checkImgRandomCode"})
	@ApiOperation(value = "验证图片随机码")
	public CommonResult<Boolean> checkImgRandomCode(@RequestBody VerificationCodeModel verificationCodeModel, HttpServletRequest request) {
		return smsCodeApiService.checkImgRandomCode(verificationCodeModel);
	}
}
