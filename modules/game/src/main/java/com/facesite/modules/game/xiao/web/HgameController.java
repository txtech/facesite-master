/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.web;
import com.alibaba.fastjson.JSONObject;
import com.facesite.modules.game.xiao.entity.GameData;
import com.facesite.modules.game.xiao.service.HgameUserInfoApiService;
import com.facesite.modules.game.xiao.utils.HttpBrowserTools;
import com.jeesite.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 游戏信息Controller
 * @author nada
 * @version 2021-01-11
 */
//http://47.110.43.93/game/f/h5/index
@Controller
@CrossOrigin("*")
@RequestMapping(value = "${frontPath}/h5")
public class HgameController extends BaseController {

	@Autowired
	private HgameUserInfoApiService userInfoApiService;

	@RequestMapping(value = "index")
	public String getUserInfo(RedirectAttributes attr, HttpServletRequest request, HttpServletResponse response) {
		String ip = HttpBrowserTools.getIpAddr(request);
		String token = request.getParameter("tokne");
		String uid = userInfoApiService.initUid(token,ip);
		String url = "redirect:http://test88.prxgg.cn/game/8/index.html?uid="+uid;
		return url;
	}
}