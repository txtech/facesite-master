/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.modules.sys.utils.UserUtils;
import com.nabobsite.modules.nabob.api.service.UserInfoApiService;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.HiDesUtils;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

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

	@Autowired
	private UserInfoApiService userInfoApiService;

	@PostMapping(value = {"register"})
	@ApiOperation(value = "用户注册")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "accountNo", value = "注册账号(手机号码)", required = true, paramType="query", type="String"),
			@ApiImplicitParam(name = "password",  value = "登陆密码", required = true),
			@ApiImplicitParam(name = "param_parent",value = "邀请上级", required = false),
			@ApiImplicitParam(name = "inviteCode",value = "邀请码", required = false),
			@ApiImplicitParam(name = "favorite",  value = "最喜欢的人", required = false)
	})
	public String register(String accountNo, String password, String inviteCode,String param_parent,String favorite,HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		UserInfo userInfo = new UserInfo();
		userInfo.setIpaddress(ip);
		userInfo.setAccountNo(accountNo);
		userInfo.setPassword(password);
		userInfo.setFavorite(favorite);
		userInfo.setInviteCode(inviteCode);
		CommonResult<Boolean> result = userInfoApiService.register(userInfo,param_parent);
		return renderResult(Global.TRUE,text("register"), result);
	}

	@PostMapping(value = {"login"})
	@ApiOperation(value = "用户登陆")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "accountNo", value = "登陆账号", required = true, paramType="query", type="String"),
			@ApiImplicitParam(name = "password",  value = "登陆密码", required = true),
			@ApiImplicitParam(name = "param_lang",  value = "语言默认：zh_CN", required = false),
			@ApiImplicitParam(name = "param_deviceType",  value = "设备类型:mobileApp、H5", required = false),
	})
	public JSONObject login(String accountNo, String password,String param_lang,String param_deviceType,HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		return null;
	}

	@PostMapping(value = {"logout"})
	@ApiOperation(value = "用户退出")
	@ApiImplicitParams({ @ApiImplicitParam(name = "param_token", value = "会话token", required = true, paramType="query", type="String"),})
	public JSONObject logout(String userId,String param_token,HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		return null;
	}

	@ApiOperation(value = "用户获取详情")
	@PostMapping(value = {"getUserInfo"})
	@ApiImplicitParams({ @ApiImplicitParam(name = "param_token", value = "会话token", required = true, paramType="query", type="String"),})
	public String getUserInfo(String userId,String param_token,HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		UserInfo result = userInfoApiService.get(userId);
		return renderResult(Global.TRUE,text("getUserInfo"), result);
	}

	@PostMapping(value = {"updatePwd"})
	@ApiOperation(value = "用户修改密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "accountNo", value = "登陆账号", required = true, paramType="query", type="String"),
			@ApiImplicitParam(name = "oldPassword",  value = "旧密码", required = true),
			@ApiImplicitParam(name = "newPassword",  value = "新密码", required = true),
			@ApiImplicitParam(name = "param_token",  value = "会话token", required = true),
	})
	public JSONObject updatePwd(String accountNo, String password,String param_token,String param_lang,String param_deviceType,HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		return null;
	}

	@ApiOperation(value = "用户邀请好友注册")
	@PostMapping(value = {"shareFriends"})
	@ApiImplicitParams({ @ApiImplicitParam(name = "param_token", value = "会话token", required = true, paramType="query", type="String"),})
	public String shareFriends(String userId){
		if(StringUtils.isEmpty(userId)){
			userId = "0";
		}
		String registerUrl = "http://c-mart.phlife.phshare?pid="+ HiDesUtils.desEnCode(userId);
		return renderResult(Global.TRUE, text("shareFriends"),ResultUtil.success(registerUrl));
	}

	@ApiOperation(value = "用户切换语言")
	@RequestMapping(value = "switchLang/{Lang}")
	public String switchLang(@PathVariable String lang) {
		Session session = UserUtils.getSession();
		if (com.jeesite.common.lang.StringUtils.isNotBlank(lang)){
			session.setAttribute("__appLang", lang);
		}
		return renderResult(Global.TRUE, text("switchLang"),ResultUtil.success(true));
	}
}
