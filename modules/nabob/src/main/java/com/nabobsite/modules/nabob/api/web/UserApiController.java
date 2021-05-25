/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.service.UserInfoApiService;
import com.nabobsite.modules.nabob.cms.team.entity.TeamUser;
import com.nabobsite.modules.nabob.cms.team.entity.TeamUserReward;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import io.swagger.annotations.Api;
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
@RequestMapping(value = "${frontPath}/api/user")
@ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "用户接口(需要登陆)")
public class UserApiController extends BaseController {

	@Autowired
	private UserInfoApiService userInfoApiService;

	@PostMapping(value = {"logout"})
	@ApiOperation(value = "用户退出")
	public CommonResult<Boolean> logout(HttpServletRequest request){
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return userInfoApiService.logout(token);
	}

	@ApiOperation(value = "用户修改密码")
	@PostMapping(value = {"updatePwd"})
	public CommonResult<Boolean> updatePwd(@RequestBody UserInfo userInfo, HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		logger.info("用户修改密码,来者何人:{}",ip);
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return userInfoApiService.updatePwd(token,userInfo);
	}

	@ApiOperation(value = "用户获取详情")
	@PostMapping(value = {"getUserInfo"})
	public CommonResult<UserInfo> getUserInfo(HttpServletRequest request){
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return userInfoApiService.getUserInfo(token);
	}

	@ApiOperation(value = "用户获取直接团队列表")
	@PostMapping(value = {"getUserDirectTeamList"})
	public CommonResult<List<UserInfo>> getUserDirectTeamList(HttpServletRequest request){
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return userInfoApiService.getUserDirectTeamList(token);
	}

	@ApiOperation(value = "用户获取团队信息")
	@PostMapping(value = {"getUserTeamInfo"})
	public CommonResult<TeamUser> getTeamUserInfo(HttpServletRequest request){
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return userInfoApiService.getTeamUserInfo(token);
	}

	@ApiOperation(value = "用户获取团队奖励列表")
	@PostMapping(value = {"getUserTeamRewardList"})
	public CommonResult<List<TeamUserReward>> getTeamUserRewardList(HttpServletRequest request){
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return userInfoApiService.getTeamUserRewardList(token);
	}

	@ApiOperation(value = "用户邀请好友链接")
	@RequestMapping(value = {"shareFriends"})
	public CommonResult<JSONObject> shareFriends(HttpServletRequest request){
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return userInfoApiService.shareFriends(token);
	}
}
