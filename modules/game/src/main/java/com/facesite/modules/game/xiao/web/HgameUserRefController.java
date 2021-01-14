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
import com.facesite.modules.game.xiao.entity.HgameUserRef;
import com.facesite.modules.game.xiao.service.HgameUserRefService;

/**
 * 用户游戏记录Controller
 * @author nada
 * @version 2021-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/xiao/hgameUserRef")
public class HgameUserRefController extends BaseController {

	@Autowired
	private HgameUserRefService hgameUserRefService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HgameUserRef get(String id, boolean isNewRecord) {
		return hgameUserRefService.get(id, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("xiao:hgameUserRef:view")
	@RequestMapping(value = {"list", ""})
	public String list(HgameUserRef hgameUserRef, Model model) {
		model.addAttribute("hgameUserRef", hgameUserRef);
		return "game/xiao/hgameUserRefList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("xiao:hgameUserRef:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HgameUserRef> listData(HgameUserRef hgameUserRef, HttpServletRequest request, HttpServletResponse response) {
		hgameUserRef.setPage(new Page<>(request, response));
		Page<HgameUserRef> page = hgameUserRefService.findPage(hgameUserRef);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("xiao:hgameUserRef:view")
	@RequestMapping(value = "form")
	public String form(HgameUserRef hgameUserRef, Model model) {
		model.addAttribute("hgameUserRef", hgameUserRef);
		return "game/xiao/hgameUserRefForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("xiao:hgameUserRef:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HgameUserRef hgameUserRef) {
		hgameUserRefService.save(hgameUserRef);
		return renderResult(Global.TRUE, text("保存用户游戏记录成功！"));
	}

	/**
	 * 停用数据
	 */
	@RequiresPermissions("xiao:hgameUserRef:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(HgameUserRef hgameUserRef) {
		hgameUserRef.setStatus(HgameUserRef.STATUS_DISABLE);
		hgameUserRefService.updateStatus(hgameUserRef);
		return renderResult(Global.TRUE, text("停用用户游戏记录成功"));
	}

	/**
	 * 启用数据
	 */
	@RequiresPermissions("xiao:hgameUserRef:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(HgameUserRef hgameUserRef) {
		hgameUserRef.setStatus(HgameUserRef.STATUS_NORMAL);
		hgameUserRefService.updateStatus(hgameUserRef);
		return renderResult(Global.TRUE, text("启用用户游戏记录成功"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("xiao:hgameUserRef:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HgameUserRef hgameUserRef) {
		hgameUserRefService.delete(hgameUserRef);
		return renderResult(Global.TRUE, text("删除用户游戏记录成功！"));
	}

}
