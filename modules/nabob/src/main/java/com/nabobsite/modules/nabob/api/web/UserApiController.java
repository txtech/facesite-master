/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.model.UserInfoModel;
import com.nabobsite.modules.nabob.api.service.UserInfoApiService;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import com.jeesite.common.web.BaseController;
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
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return userInfoApiService.logout(token);
	}

	@ApiOperation(value = "用户获取详情")
	@PostMapping(value = {"getUserInfo"})
	public CommonResult<UserInfoModel> getUserInfo(HttpServletRequest request){
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return userInfoApiService.getUserInfo(token);
	}

	@ApiOperation(value = "用户获取直接团队列表")
	@PostMapping(value = {"getUserDirectTeamList"})
	public CommonResult<List<UserInfoModel>> getUserDirectTeamList(HttpServletRequest request){
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return userInfoApiService.getUserDirectTeamList(token);
	}

	@PostMapping(value = {"updatePwd"})
	@ApiOperation(value = "用户修改密码")
	public CommonResult<Boolean> updatePwd(@RequestBody UserInfoModel userInfoModel,HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		logger.info("用户修改密码,来者何人:{}",ip);
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return userInfoApiService.updatePwd(token,userInfoModel);
	}

	@ApiOperation(value = "用户邀请好友链接")
	@RequestMapping(value = {"shareFriends"})
	public CommonResult<String> shareFriends(HttpServletRequest request){
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return userInfoApiService.shareFriends(token);
	}

	@ApiOperation(value = "用户切换语言")
	@RequestMapping(value = "switchLang/{lang}")
	public CommonResult<Boolean> switchLang(@PathVariable String lang,HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return userInfoApiService.switchLang(token,lang);
	}
}
