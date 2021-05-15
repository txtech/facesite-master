/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.service.CashApiService;
import com.nabobsite.modules.nabob.api.service.UserAccountApiService;
import com.nabobsite.modules.nabob.cms.order.entity.Cash;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 账户中心
 * @author nada
 * @version 2021-01-11
 */
@RestController
@RequestMapping(value = "${frontPath}/api/account")
@Api(tags = "账户接口(需要登陆)")
public class AccountApiController extends BaseController {
	@Autowired
	private UserAccountApiService userAccountApiService;

	@PostMapping(value = {"getUserAccountInfo"})
	@ApiOperation(value = "获取账户详情")
	public CommonResult<UserAccount> getUserAccountInfo(HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return userAccountApiService.getUserAccountInfo(token);
	}
}
