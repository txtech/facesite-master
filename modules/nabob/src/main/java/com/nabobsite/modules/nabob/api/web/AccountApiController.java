/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.service.UserAccountApiService;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 账户中心
 * @author nada
 * @version 2021-01-11
 */
@RestController
@RequestMapping(value = "${frontPath}/api/account")
@ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "账户接口(需要登陆)")
public class AccountApiController extends BaseController {
	@Autowired
	private UserAccountApiService userAccountApiService;

	@PostMapping(value = {"claim"})
	@ApiOperation(value = "认领增值账户")
	public CommonResult<Boolean> claim(HttpServletRequest request){
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return userAccountApiService.claim(token);
	}

	@PostMapping(value = {"getUserAccountInfo"})
	@ApiOperation(value = "获取账户详情")
	public CommonResult<UserAccount> getUserAccountInfo(HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return userAccountApiService.getUserAccountInfo(token);
	}

	@PostMapping(value = {"getLedgerRecordList/{type}"})
	@ApiOperation(value = "获取收支总账记录列表")
	public CommonResult<List<UserAccountDetail>> getLedgerRecordList(@PathVariable int type,HttpServletRequest request){
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return userAccountApiService.getLedgerRecordList(token,type);
	}
}
