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
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountRecord;
import com.nabobsite.modules.nabob.cms.user.service.UserAccountRecordService;

/**
 * 账户账务明显Controller
 * @author face
 * @version 2021-05-13
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userAccountRecord")
public class UserAccountRecordController extends BaseController {

	@Autowired
	private UserAccountRecordService userAccountRecordService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserAccountRecord get(String id, boolean isNewRecord) {
		return userAccountRecordService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:userAccountRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserAccountRecord userAccountRecord, Model model) {
		model.addAttribute("userAccountRecord", userAccountRecord);
		return "cms/user/userAccountRecordList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:userAccountRecord:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserAccountRecord> listData(UserAccountRecord userAccountRecord, HttpServletRequest request, HttpServletResponse response) {
		userAccountRecord.setPage(new Page<>(request, response));
		Page<UserAccountRecord> page = userAccountRecordService.findPage(userAccountRecord);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:userAccountRecord:view")
	@RequestMapping(value = "form")
	public String form(UserAccountRecord userAccountRecord, Model model) {
		model.addAttribute("userAccountRecord", userAccountRecord);
		return "cms/user/userAccountRecordForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:userAccountRecord:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserAccountRecord userAccountRecord) {
		userAccountRecordService.save(userAccountRecord);
		return renderResult(Global.TRUE, text("保存账户账务明显成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:userAccountRecord:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserAccountRecord userAccountRecord) {
		userAccountRecordService.delete(userAccountRecord);
		return renderResult(Global.TRUE, text("删除账户账务明显成功！"));
	}
	
}