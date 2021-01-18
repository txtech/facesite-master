/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.web;
import com.alibaba.fastjson.JSONObject;
import com.facesite.modules.game.xiao.entity.GameData;
import com.facesite.modules.game.xiao.service.HgameUserInfoApiService;
import com.facesite.modules.game.xiao.utils.BaseGameContact;
import com.facesite.modules.game.xiao.utils.HttpBrowserTools;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

	/**
	 * @desc http://localhost:9998/game/f/h5/1?tokne=12313
	 * @author nada
	 * @create 2021/1/18 8:16 下午
	*/
	@RequestMapping(value = "{type}")
	public String getUserInfo(@PathVariable Integer type,RedirectAttributes attr, HttpServletRequest request, Model model) {
		try {
			String ip = HttpBrowserTools.getIpAddr(request);
			String token = request.getParameter("tokne");
			logger.info("请求IP:{},获取token:{},type:{}",ip,token,type);
			GameData gameData = userInfoApiService.init(token,type);
			if(gameData == null || StringUtils.isEmpty(gameData.getUrl())){
				model.addAttribute("msg","游戏暂停啦，敬请等待哦");
				return "game/xiao/failed";
			}
			Long minLimit = BaseGameContact.getLong(gameData.getMinLimit());
			Long hBeans = BaseGameContact.getLong(gameData.getGold());
			if(hBeans < minLimit){
				model.addAttribute("msg","您的和逗不足:"+minLimit+"，无法进入游戏哦");
				return "game/xiao/failed";
			}
			attr.addAttribute("uid",gameData.getUid());
			attr.addAttribute("gid",gameData.getGid());
			return "redirect:"+gameData.getUrl();
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg","亲爱的服务器开小差啦，请联系管理员哦");
			return "game/xiao/failed";
		}
	}
}
