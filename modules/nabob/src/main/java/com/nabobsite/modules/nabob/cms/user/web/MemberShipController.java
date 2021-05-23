/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.web;

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
import com.nabobsite.modules.nabob.cms.user.entity.MemberShip;
import com.nabobsite.modules.nabob.cms.user.service.MemberShipService;

/**
 * 任务管理Controller
 * @author face
 * @version 2021-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/user/memberShip")
public class MemberShipController extends BaseController {

	@Autowired
	private MemberShipService memberShipService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public MemberShip get(String id, boolean isNewRecord) {
		return memberShipService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:memberShip:view")
	@RequestMapping(value = {"list", ""})
	public String list(MemberShip memberShip, Model model) {
		model.addAttribute("memberShip", memberShip);
		return "cms/user/memberShipList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:memberShip:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<MemberShip> listData(MemberShip memberShip, HttpServletRequest request, HttpServletResponse response) {
		memberShip.setPage(new Page<>(request, response));
		Page<MemberShip> page = memberShipService.findPage(memberShip);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:memberShip:view")
	@RequestMapping(value = "form")
	public String form(MemberShip memberShip, Model model) {
		model.addAttribute("memberShip", memberShip);
		return "cms/user/memberShipForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:memberShip:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated MemberShip memberShip) {
		memberShipService.save(memberShip);
		return renderResult(Global.TRUE, text("保存任务管理成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:memberShip:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(MemberShip memberShip) {
		memberShipService.delete(memberShip);
		return renderResult(Global.TRUE, text("删除任务管理成功！"));
	}
	
}