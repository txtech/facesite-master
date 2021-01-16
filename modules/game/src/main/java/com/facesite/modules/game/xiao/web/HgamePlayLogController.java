/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.facesite.modules.game.xiao.entity.HgamePlayLog;
import com.facesite.modules.game.xiao.service.HgamePlayLogService;

/**
 * 消消乐游戏日志Controller
 * @author nada
 * @version 2021-01-16
 */
@Controller
@RequestMapping(value = "${adminPath}/xiao/hgamePlayLog")
public class HgamePlayLogController extends BaseController {

	@Autowired
	private HgamePlayLogService hgamePlayLogService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HgamePlayLog get(String id, boolean isNewRecord) {
		return hgamePlayLogService.get(id, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("xiao:hgamePlayLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(HgamePlayLog hgamePlayLog, Model model) {
		model.addAttribute("hgamePlayLog", hgamePlayLog);
		return "game/xiao/hgamePlayLogList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("xiao:hgamePlayLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HgamePlayLog> listData(HgamePlayLog hgamePlayLog, HttpServletRequest request, HttpServletResponse response) {
		hgamePlayLog.setPage(new Page<>(request, response));
		Page<HgamePlayLog> page = hgamePlayLogService.findPage(hgamePlayLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("xiao:hgamePlayLog:view")
	@RequestMapping(value = "form")
	public String form(HgamePlayLog hgamePlayLog, Model model) {
		model.addAttribute("hgamePlayLog", hgamePlayLog);
		return "game/xiao/hgamePlayLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("xiao:hgamePlayLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HgamePlayLog hgamePlayLog) {
		hgamePlayLogService.save(hgamePlayLog);
		return renderResult(Global.TRUE, text("保存消消乐游戏日志成功！"));
	}

}
