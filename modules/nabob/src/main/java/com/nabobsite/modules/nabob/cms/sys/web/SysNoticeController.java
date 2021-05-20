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
import com.alibaba.fastjson.JSONValidator;
import com.jeesite.common.codec.EncodeUtils;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.cms.sys.entity.SysNotice;
import com.nabobsite.modules.nabob.cms.sys.service.SysNoticeService;

/**
 * 系统通知Controller
 * @author face
 * @version 2021-05-20
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysNotice")
public class SysNoticeController extends BaseController {

	@Autowired
	private SysNoticeService sysNoticeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SysNotice get(String id, boolean isNewRecord) {
		return sysNoticeService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sys:sysNotice:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysNotice sysNotice, Model model) {
		model.addAttribute("sysNotice", sysNotice);
		return "cms/sys/sysNoticeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sys:sysNotice:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SysNotice> listData(SysNotice sysNotice, HttpServletRequest request, HttpServletResponse response) {
		sysNotice.setPage(new Page<>(request, response));
		Page<SysNotice> page = sysNoticeService.findPage(sysNotice);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sys:sysNotice:view")
	@RequestMapping(value = "form")
	public String form(SysNotice sysNotice, Model model) {
		model.addAttribute("sysNotice", sysNotice);
		return "cms/sys/sysNoticeForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("sys:sysNotice:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SysNotice sysNotice) {
		sysNoticeService.save(sysNotice);
		return renderResult(Global.TRUE, text("保存系统通知成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("sys:sysNotice:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SysNotice sysNotice) {
		sysNoticeService.delete(sysNotice);
		return renderResult(Global.TRUE, text("删除系统通知成功！"));
	}
	
	/**
	 * 列表选择对话框
	 */
	@RequiresPermissions("sys:sysNotice:view")
	@RequestMapping(value = "sysNoticeSelect")
	public String empUserSelect(SysNotice sysNotice, String selectData, Model model) {
		String selectDataJson = EncodeUtils.decodeUrl(selectData);
		if (selectDataJson != null && JSONValidator.from(selectDataJson).validate()){
			model.addAttribute("selectData", selectDataJson);
		}
		model.addAttribute("SysNotice", sysNotice);
		return "cms/sys/sysNoticeSelect";
	}
	
}