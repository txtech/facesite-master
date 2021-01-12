/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.web;
import com.alibaba.fastjson.JSONObject;
import com.facesite.modules.game.xiao.service.HgameUserInfoApiService;
import com.jeesite.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 游戏信息Controller
 * @author nada
 * @version 2021-01-11
 */
@Controller
@CrossOrigin("*")
@RequestMapping(value = "${frontPath}/xiao/hgameapi")
public class HgameApiController extends BaseController {

	@Autowired
	private HgameUserInfoApiService userInfoApiService;

	/**
	 * 获取用户信息
	 * http://localhost:8980/js/f/xiao/hgameapi/getUserInfo?token=
	 */
	@RequestMapping(value = {"getUserInfo"})
	@ResponseBody
	public JSONObject getUserInfo(String token) {
		return userInfoApiService.getUserInfo(token);
	}
}
