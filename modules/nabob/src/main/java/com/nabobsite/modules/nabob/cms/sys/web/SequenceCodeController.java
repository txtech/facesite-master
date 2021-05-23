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
import com.nabobsite.modules.nabob.cms.sys.entity.SequenceCode;
import com.nabobsite.modules.nabob.cms.sys.service.SequenceCodeService;

/**
 * t1_sequence_codeController
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sequenceCode")
public class SequenceCodeController extends BaseController {

	@Autowired
	private SequenceCodeService sequenceCodeService;
	
	/**
	 * 获取数据
	 */
//	@ModelAttribute
//	public SequenceCode get(Long id, boolean isNewRecord) {
//		return sequenceCodeService.get(id, isNewRecord);
//	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sys:sequenceCode:view")
	@RequestMapping(value = {"list", ""})
	public String list(SequenceCode sequenceCode, Model model) {
		model.addAttribute("sequenceCode", sequenceCode);
		return "cms/sys/sequenceCodeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sys:sequenceCode:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SequenceCode> listData(SequenceCode sequenceCode, HttpServletRequest request, HttpServletResponse response) {
		sequenceCode.setPage(new Page<>(request, response));
		Page<SequenceCode> page = sequenceCodeService.findPage(sequenceCode);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sys:sequenceCode:view")
	@RequestMapping(value = "form")
	public String form(SequenceCode sequenceCode, Model model) {
		model.addAttribute("sequenceCode", sequenceCode);
		return "cms/sys/sequenceCodeForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("sys:sequenceCode:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SequenceCode sequenceCode) {
		sequenceCodeService.save(sequenceCode);
		return renderResult(Global.TRUE, text("保存t1_sequence_code成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("sys:sequenceCode:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SequenceCode sequenceCode) {
		sequenceCodeService.delete(sequenceCode);
		return renderResult(Global.TRUE, text("删除t1_sequence_code成功！"));
	}
	
}