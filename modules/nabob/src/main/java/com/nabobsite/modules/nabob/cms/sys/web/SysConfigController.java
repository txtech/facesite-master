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
import com.nabobsite.modules.nabob.cms.sys.entity.SysConfig;
import com.nabobsite.modules.nabob.cms.sys.service.SysConfigService;

/**
 * 用户任务Controller
 * @author face
 * @version 2021-05-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysConfig")
public class SysConfigController extends BaseController {

	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SysConfig get(String id, boolean isNewRecord) {
		return sysConfigService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sys:sysConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysConfig sysConfig, Model model) {
		model.addAttribute("sysConfig", sysConfig);
		return "cms/sys/sysConfigList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sys:sysConfig:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SysConfig> listData(SysConfig sysConfig, HttpServletRequest request, HttpServletResponse response) {
		sysConfig.setPage(new Page<>(request, response));
		Page<SysConfig> page = sysConfigService.findPage(sysConfig);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sys:sysConfig:view")
	@RequestMapping(value = "form")
	public String form(SysConfig sysConfig, Model model) {
		model.addAttribute("sysConfig", sysConfig);
		return "cms/sys/sysConfigForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("sys:sysConfig:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SysConfig sysConfig) {
		sysConfigService.save(sysConfig);
		return renderResult(Global.TRUE, text("保存用户任务成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("sys:sysConfig:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SysConfig sysConfig) {
		sysConfigService.delete(sysConfig);
		return renderResult(Global.TRUE, text("删除用户任务成功！"));
	}
	
}