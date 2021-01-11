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
import com.facesite.modules.game.xiao.entity.HgameUserInfo;
import com.facesite.modules.game.xiao.service.HgameUserInfoService;

/**
 * 用户信息Controller
 * @author nada
 * @version 2021-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/xiao/hgameUserInfo")
public class HgameUserInfoController extends BaseController {

	@Autowired
	private HgameUserInfoService hgameUserInfoService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HgameUserInfo get(Long id, boolean isNewRecord) {
		return hgameUserInfoService.get(String.valueOf(id), isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("xiao:hgameUserInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HgameUserInfo hgameUserInfo, Model model) {
		model.addAttribute("hgameUserInfo", hgameUserInfo);
		return "game/xiao/hgameUserInfoList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("xiao:hgameUserInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HgameUserInfo> listData(HgameUserInfo hgameUserInfo, HttpServletRequest request, HttpServletResponse response) {
		hgameUserInfo.setPage(new Page<>(request, response));
		Page<HgameUserInfo> page = hgameUserInfoService.findPage(hgameUserInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("xiao:hgameUserInfo:view")
	@RequestMapping(value = "form")
	public String form(HgameUserInfo hgameUserInfo, Model model) {
		model.addAttribute("hgameUserInfo", hgameUserInfo);
		return "game/xiao/hgameUserInfoForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("xiao:hgameUserInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HgameUserInfo hgameUserInfo) {
		hgameUserInfoService.save(hgameUserInfo);
		return renderResult(Global.TRUE, text("保存用户信息成功！"));
	}

	/**
	 * 停用数据
	 */
	@RequiresPermissions("xiao:hgameUserInfo:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(HgameUserInfo hgameUserInfo) {
		hgameUserInfo.setStatus(HgameUserInfo.STATUS_DISABLE);
		hgameUserInfoService.updateStatus(hgameUserInfo);
		return renderResult(Global.TRUE, text("停用用户信息成功"));
	}

	/**
	 * 启用数据
	 */
	@RequiresPermissions("xiao:hgameUserInfo:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(HgameUserInfo hgameUserInfo) {
		hgameUserInfo.setStatus(HgameUserInfo.STATUS_NORMAL);
		hgameUserInfoService.updateStatus(hgameUserInfo);
		return renderResult(Global.TRUE, text("启用用户信息成功"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("xiao:hgameUserInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HgameUserInfo hgameUserInfo) {
		hgameUserInfoService.delete(hgameUserInfo);
		return renderResult(Global.TRUE, text("删除用户信息成功！"));
	}

}
