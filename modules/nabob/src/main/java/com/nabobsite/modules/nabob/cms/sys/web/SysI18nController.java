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
import com.nabobsite.modules.nabob.cms.sys.entity.SysI18n;
import com.nabobsite.modules.nabob.cms.sys.service.SysI18nService;

/**
 * 国际化配置Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysI18n")
public class SysI18nController extends BaseController {

	@Autowired
	private SysI18nService sysI18nService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SysI18n get(String id, boolean isNewRecord) {
		return sysI18nService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sys:sysI18n:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysI18n sysI18n, Model model) {
		model.addAttribute("sysI18n", sysI18n);
		return "cms/sys/sysI18nList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sys:sysI18n:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SysI18n> listData(SysI18n sysI18n, HttpServletRequest request, HttpServletResponse response) {
		sysI18n.setPage(new Page<>(request, response));
		Page<SysI18n> page = sysI18nService.findPage(sysI18n);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sys:sysI18n:view")
	@RequestMapping(value = "form")
	public String form(SysI18n sysI18n, Model model) {
		model.addAttribute("sysI18n", sysI18n);
		return "cms/sys/sysI18nForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("sys:sysI18n:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SysI18n sysI18n) {
		sysI18nService.save(sysI18n);
		return renderResult(Global.TRUE, text("保存国际化配置成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("sys:sysI18n:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SysI18n sysI18n) {
		sysI18nService.delete(sysI18n);
		return renderResult(Global.TRUE, text("删除国际化配置成功！"));
	}
	
}