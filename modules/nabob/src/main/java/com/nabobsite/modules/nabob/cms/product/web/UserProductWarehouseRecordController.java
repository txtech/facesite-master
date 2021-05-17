/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.web;

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
import com.nabobsite.modules.nabob.cms.product.entity.UserProductWarehouseRecord;
import com.nabobsite.modules.nabob.cms.product.service.UserProductWarehouseRecordService;

/**
 * 用户产品仓库日志Controller
 * @author face
 * @version 2021-05-17
 */
@Controller
@RequestMapping(value = "${adminPath}/product/userProductWarehouseRecord")
public class UserProductWarehouseRecordController extends BaseController {

	@Autowired
	private UserProductWarehouseRecordService userProductWarehouseRecordService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserProductWarehouseRecord get(String id, boolean isNewRecord) {
		return userProductWarehouseRecordService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:userProductWarehouseRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserProductWarehouseRecord userProductWarehouseRecord, Model model) {
		model.addAttribute("userProductWarehouseRecord", userProductWarehouseRecord);
		return "cms/product/userProductWarehouseRecordList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:userProductWarehouseRecord:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserProductWarehouseRecord> listData(UserProductWarehouseRecord userProductWarehouseRecord, HttpServletRequest request, HttpServletResponse response) {
		userProductWarehouseRecord.setPage(new Page<>(request, response));
		Page<UserProductWarehouseRecord> page = userProductWarehouseRecordService.findPage(userProductWarehouseRecord);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:userProductWarehouseRecord:view")
	@RequestMapping(value = "form")
	public String form(UserProductWarehouseRecord userProductWarehouseRecord, Model model) {
		model.addAttribute("userProductWarehouseRecord", userProductWarehouseRecord);
		return "cms/product/userProductWarehouseRecordForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:userProductWarehouseRecord:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserProductWarehouseRecord userProductWarehouseRecord) {
		userProductWarehouseRecordService.save(userProductWarehouseRecord);
		return renderResult(Global.TRUE, text("保存用户产品仓库日志成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:userProductWarehouseRecord:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserProductWarehouseRecord userProductWarehouseRecord) {
		userProductWarehouseRecordService.delete(userProductWarehouseRecord);
		return renderResult(Global.TRUE, text("删除用户产品仓库日志成功！"));
	}
	
}