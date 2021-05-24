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
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountDetail;
import com.nabobsite.modules.nabob.cms.user.service.UserAccountDetailService;

/**
 * 用户账户明细Controller
 * @author face
 * @version 2021-05-24
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userAccountDetail")
public class UserAccountDetailController extends BaseController {

	@Autowired
	private UserAccountDetailService userAccountDetailService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserAccountDetail get(String id, boolean isNewRecord) {
		return userAccountDetailService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:userAccountDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserAccountDetail userAccountDetail, Model model) {
		model.addAttribute("userAccountDetail", userAccountDetail);
		return "cms/user/userAccountDetailList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:userAccountDetail:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserAccountDetail> listData(UserAccountDetail userAccountDetail, HttpServletRequest request, HttpServletResponse response) {
		userAccountDetail.setPage(new Page<>(request, response));
		Page<UserAccountDetail> page = userAccountDetailService.findPage(userAccountDetail);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:userAccountDetail:view")
	@RequestMapping(value = "form")
	public String form(UserAccountDetail userAccountDetail, Model model) {
		model.addAttribute("userAccountDetail", userAccountDetail);
		return "cms/user/userAccountDetailForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:userAccountDetail:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserAccountDetail userAccountDetail) {
		userAccountDetailService.save(userAccountDetail);
		return renderResult(Global.TRUE, text("保存用户账户明细成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:userAccountDetail:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserAccountDetail userAccountDetail) {
		userAccountDetailService.delete(userAccountDetail);
		return renderResult(Global.TRUE, text("删除用户账户明细成功！"));
	}
	
}