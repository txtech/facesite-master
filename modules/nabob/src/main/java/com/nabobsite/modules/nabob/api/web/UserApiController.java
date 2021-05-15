/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.modules.sys.utils.UserUtils;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.model.UserInfoModel;
import com.nabobsite.modules.nabob.api.service.UserInfoApiService;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "${frontPath}/api/user")
@ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "用户接口(需要登陆)")
public class UserApiController extends BaseController {

	@Autowired
	private UserInfoApiService userInfoApiService;

	@PostMapping(value = {"logout"})
	@ApiOperation(value = "用户退出")
	public String logout(HttpServletRequest request){
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		CommonResult<Boolean> result = userInfoApiService.logout(token);
		return renderResult(Global.TRUE,text("logout"), result);
	}

	@ApiOperation(value = "用户获取详情")
	@PostMapping(value = {"getUserInfo"})
	public String getUserInfo(HttpServletRequest request){
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		CommonResult<UserInfoModel> result = userInfoApiService.getUserInfo(token);
		return renderResult(Global.TRUE,text("getUserInfo"), result);
	}

	@PostMapping(value = {"updatePwd"})
	@ApiOperation(value = "用户修改密码")
	public String updatePwd(@RequestBody UserInfoModel userInfoModel,HttpServletRequest request) {
		String ip = HttpBrowserTools.getIpAddr(request);
		logger.info("用户修改密码,来者何人:{}",ip);
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		CommonResult<Boolean> result = userInfoApiService.updatePwd(userInfoModel,token);
		return renderResult(Global.TRUE,text("updatePwd"),result);
	}

	@ApiOperation(value = "用户邀请好友链接")
	@RequestMapping(value = {"shareFriends"})
	public String shareFriends(HttpServletRequest request){
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		CommonResult<String> result = userInfoApiService.shareFriends(token);
		return renderResult(Global.TRUE,text("shareFriends"),result);
	}

	@ApiOperation(value = "用户切换语言")
	@RequestMapping(value = "switchLang/{lang}")
	public String switchLang(@PathVariable String lang,HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		CommonResult<Boolean> result = userInfoApiService.switchLang(token,lang);
		return renderResult(Global.TRUE, text("switchLang"),ResultUtil.success(result));
	}
}
