/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.order.web;

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
import com.nabobsite.modules.nabob.cms.order.entity.Cash;
import com.nabobsite.modules.nabob.cms.order.service.CashService;

/**
 * 出款Controller
 * @author face
 * @version 2021-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/order/cash")
public class CashController extends BaseController {

	@Autowired
	private CashService cashService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Cash get(String id, boolean isNewRecord) {
		return cashService.get(id, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("order:cash:view")
	@RequestMapping(value = {"list", ""})
	public String list(Cash cash, Model model) {
		model.addAttribute("cash", cash);
		return "cms/order/cashList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("order:cash:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Cash> listData(Cash cash, HttpServletRequest request, HttpServletResponse response) {
		cash.setPage(new Page<>(request, response));
		Page<Cash> page = cashService.findPage(cash);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("order:cash:view")
	@RequestMapping(value = "form")
	public String form(Cash cash, Model model) {
		model.addAttribute("cash", cash);
		return "cms/order/cashForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("order:cash:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Cash cash) {
		cashService.save(cash);
		return renderResult(Global.TRUE, text("保存出款成功！"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("order:cash:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Cash cash) {
		cashService.delete(cash);
		return renderResult(Global.TRUE, text("删除出款成功！"));
	}

}
