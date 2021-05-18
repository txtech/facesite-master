/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.sys.web;

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
import com.nabobsite.modules.nabob.cms.sys.entity.SysChannel;
import com.nabobsite.modules.nabob.cms.sys.service.SysChannelService;

/**
 * 通道配置Controller
 * @author face
 * @version 2021-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysChannel")
public class SysChannelController extends BaseController {

	@Autowired
	private SysChannelService sysChannelService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SysChannel get(String id, boolean isNewRecord) {
		return sysChannelService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sys:sysChannel:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysChannel sysChannel, Model model) {
		model.addAttribute("sysChannel", sysChannel);
		return "cms/sys/sysChannelList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sys:sysChannel:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SysChannel> listData(SysChannel sysChannel, HttpServletRequest request, HttpServletResponse response) {
		sysChannel.setPage(new Page<>(request, response));
		Page<SysChannel> page = sysChannelService.findPage(sysChannel);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sys:sysChannel:view")
	@RequestMapping(value = "form")
	public String form(SysChannel sysChannel, Model model) {
		model.addAttribute("sysChannel", sysChannel);
		return "cms/sys/sysChannelForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("sys:sysChannel:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SysChannel sysChannel) {
		sysChannelService.save(sysChannel);
		return renderResult(Global.TRUE, text("保存通道配置成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("sys:sysChannel:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SysChannel sysChannel) {
		sysChannelService.delete(sysChannel);
		return renderResult(Global.TRUE, text("删除通道配置成功！"));
	}
	
}