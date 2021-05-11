/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;
import com.alibaba.fastjson.JSONObject;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import com.jeesite.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户控制
 * @author nada
 * @version 2021-01-11
 */
@RestController
@RequestMapping(value = "${frontPath}/user/api")
@ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "用户接口：账号注册、账号登录、找回密码、获取用户详情")
public class UserApiController extends BaseController {

	/**
	 * @desc 用户注册
	 * @author nada
	 * @create 2021/5/10 9:34 下午
	*/
	@PostMapping(value = {"register"})
	@ApiOperation(value = "用户注册")
	public JSONObject register(@RequestBody UserInfo userInfo, HttpServletRequest request, HttpServletResponse response) {
		String ip = HttpBrowserTools.getIpAddr(request);
		return null;
	}

	/**
	 * @desc 获取用户详情
	 * @author nada
	 * @create 2021/5/10 9:34 下午
	 */
	@ApiOperation(value = "获取用户详情")
	@PostMapping(value = {"getUserInfo"})
	public JSONObject getUserInfo(@RequestBody UserInfo userInfo, HttpServletRequest request, HttpServletResponse response) {
		String ip = HttpBrowserTools.getIpAddr(request);
		return null;
	}

	/**
	 * @desc 用户登陆
	 * @author nada
	 * @create 2021/5/10 9:34 下午
	 */
	@PostMapping(value = {"login"})
	@ApiOperation(value = "用户登陆")
	public JSONObject login(@RequestBody UserInfo userInfo, HttpServletRequest request, HttpServletResponse response) {
		String ip = HttpBrowserTools.getIpAddr(request);
		return null;
	}
}
