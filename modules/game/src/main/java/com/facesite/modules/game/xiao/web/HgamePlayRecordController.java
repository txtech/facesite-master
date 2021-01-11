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
import com.facesite.modules.game.xiao.entity.HgamePlayRecord;
import com.facesite.modules.game.xiao.service.HgamePlayRecordService;

/**
 * 玩游戏记录Controller
 * @author nada
 * @version 2021-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/xiao/hgamePlayRecord")
public class HgamePlayRecordController extends BaseController {

	@Autowired
	private HgamePlayRecordService hgamePlayRecordService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HgamePlayRecord get(Long id, boolean isNewRecord) {
		return hgamePlayRecordService.get(String.valueOf(id), isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("xiao:hgamePlayRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(HgamePlayRecord hgamePlayRecord, Model model) {
		model.addAttribute("hgamePlayRecord", hgamePlayRecord);
		return "game/xiao/hgamePlayRecordList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("xiao:hgamePlayRecord:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HgamePlayRecord> listData(HgamePlayRecord hgamePlayRecord, HttpServletRequest request, HttpServletResponse response) {
		hgamePlayRecord.setPage(new Page<>(request, response));
		Page<HgamePlayRecord> page = hgamePlayRecordService.findPage(hgamePlayRecord);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("xiao:hgamePlayRecord:view")
	@RequestMapping(value = "form")
	public String form(HgamePlayRecord hgamePlayRecord, Model model) {
		model.addAttribute("hgamePlayRecord", hgamePlayRecord);
		return "game/xiao/hgamePlayRecordForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("xiao:hgamePlayRecord:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HgamePlayRecord hgamePlayRecord) {
		hgamePlayRecordService.save(hgamePlayRecord);
		return renderResult(Global.TRUE, text("保存玩游戏记录成功！"));
	}

	/**
	 * 停用数据
	 */
	@RequiresPermissions("xiao:hgamePlayRecord:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(HgamePlayRecord hgamePlayRecord) {
		hgamePlayRecord.setStatus(HgamePlayRecord.STATUS_DISABLE);
		hgamePlayRecordService.updateStatus(hgamePlayRecord);
		return renderResult(Global.TRUE, text("停用玩游戏记录成功"));
	}

	/**
	 * 启用数据
	 */
	@RequiresPermissions("xiao:hgamePlayRecord:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(HgamePlayRecord hgamePlayRecord) {
		hgamePlayRecord.setStatus(HgamePlayRecord.STATUS_NORMAL);
		hgamePlayRecordService.updateStatus(hgamePlayRecord);
		return renderResult(Global.TRUE, text("启用玩游戏记录成功"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("xiao:hgamePlayRecord:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HgamePlayRecord hgamePlayRecord) {
		hgamePlayRecordService.delete(hgamePlayRecord);
		return renderResult(Global.TRUE, text("删除玩游戏记录成功！"));
	}

}
