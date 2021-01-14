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
import com.facesite.modules.game.xiao.entity.HgameInfo;
import com.facesite.modules.game.xiao.service.HgameInfoService;

/**
 * 游戏信息Controller
 * @author nada
 * @version 2021-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/xiao/hgameInfo")
public class HgameInfoController extends BaseController {

	@Autowired
	private HgameInfoService hgameInfoService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HgameInfo get(String id, boolean isNewRecord) {
		return hgameInfoService.get(id, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("xiao:hgameInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HgameInfo hgameInfo, Model model) {
		model.addAttribute("hgameInfo", hgameInfo);
		return "game/xiao/hgameInfoList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("xiao:hgameInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HgameInfo> listData(HgameInfo hgameInfo, HttpServletRequest request, HttpServletResponse response) {
		hgameInfo.setPage(new Page<>(request, response));
		Page<HgameInfo> page = hgameInfoService.findPage(hgameInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("xiao:hgameInfo:view")
	@RequestMapping(value = "form")
	public String form(HgameInfo hgameInfo, Model model) {
		model.addAttribute("hgameInfo", hgameInfo);
		return "game/xiao/hgameInfoForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("xiao:hgameInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HgameInfo hgameInfo) {
		hgameInfoService.save(hgameInfo);
		return renderResult(Global.TRUE, text("保存游戏信息成功！"));
	}

	/**
	 * 停用数据
	 */
	@RequiresPermissions("xiao:hgameInfo:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(HgameInfo hgameInfo) {
		hgameInfo.setStatus(HgameInfo.STATUS_DISABLE);
		hgameInfoService.updateStatus(hgameInfo);
		return renderResult(Global.TRUE, text("停用游戏信息成功"));
	}

	/**
	 * 启用数据
	 */
	@RequiresPermissions("xiao:hgameInfo:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(HgameInfo hgameInfo) {
		hgameInfo.setStatus(HgameInfo.STATUS_NORMAL);
		hgameInfoService.updateStatus(hgameInfo);
		return renderResult(Global.TRUE, text("启用游戏信息成功"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("xiao:hgameInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HgameInfo hgameInfo) {
		hgameInfoService.delete(hgameInfo);
		return renderResult(Global.TRUE, text("删除游戏信息成功！"));
	}

}
