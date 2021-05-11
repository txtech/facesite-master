/**
 * Copyright (c) 2013-Now  All rights reserved.
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
import com.nabobsite.modules.nabob.cms.user.entity.UserRewardRecord;
import com.nabobsite.modules.nabob.cms.user.service.UserRewardRecordService;

/**
 * 用户收益明细Controller
 * @author face
 * @version 2021-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userRewardRecord")
public class UserRewardRecordController extends BaseController {

	@Autowired
	private UserRewardRecordService userRewardRecordService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserRewardRecord get(String id, boolean isNewRecord) {
		return userRewardRecordService.get(id, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:userRewardRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserRewardRecord userRewardRecord, Model model) {
		model.addAttribute("userRewardRecord", userRewardRecord);
		return "cms/user/userRewardRecordList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:userRewardRecord:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserRewardRecord> listData(UserRewardRecord userRewardRecord, HttpServletRequest request, HttpServletResponse response) {
		userRewardRecord.setPage(new Page<>(request, response));
		Page<UserRewardRecord> page = userRewardRecordService.findPage(userRewardRecord);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:userRewardRecord:view")
	@RequestMapping(value = "form")
	public String form(UserRewardRecord userRewardRecord, Model model) {
		model.addAttribute("userRewardRecord", userRewardRecord);
		return "cms/user/userRewardRecordForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:userRewardRecord:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserRewardRecord userRewardRecord) {
		userRewardRecordService.save(userRewardRecord);
		return renderResult(Global.TRUE, text("保存用户收益明细成功！"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:userRewardRecord:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserRewardRecord userRewardRecord) {
		userRewardRecordService.delete(userRewardRecord);
		return renderResult(Global.TRUE, text("删除用户收益明细成功！"));
	}

}
