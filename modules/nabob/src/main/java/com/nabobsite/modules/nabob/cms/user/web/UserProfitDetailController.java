/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nabobsite.modules.nabob.cms.base.BaseDataScopeFilter;
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
import com.nabobsite.modules.nabob.cms.user.entity.UserProfitDetail;
import com.nabobsite.modules.nabob.cms.user.service.UserProfitDetailService;

/**
 * 用户分润Controller
 * @author face
 * @version 2021-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userProfitDetail")
public class UserProfitDetailController extends BaseController {

	@Autowired
	private UserProfitDetailService userProfitDetailService;
	@Autowired
	private BaseDataScopeFilter baseDataScopeFilter;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserProfitDetail get(String id, boolean isNewRecord) {
		return userProfitDetailService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:userProfitDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserProfitDetail userProfitDetail, Model model) {
		model.addAttribute("userProfitDetail", userProfitDetail);
		return "cms/user/userProfitDetailList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:userProfitDetail:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserProfitDetail> listData(UserProfitDetail userProfitDetail, HttpServletRequest request, HttpServletResponse response) {
		userProfitDetail.setPage(new Page<>(request, response));
		baseDataScopeFilter.addDataScopeFilter(userProfitDetail);
		Page<UserProfitDetail> page = userProfitDetailService.findPage(userProfitDetail);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:userProfitDetail:view")
	@RequestMapping(value = "form")
	public String form(UserProfitDetail userProfitDetail, Model model) {
		model.addAttribute("userProfitDetail", userProfitDetail);
		return "cms/user/userProfitDetailForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:userProfitDetail:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserProfitDetail userProfitDetail) {
		userProfitDetailService.save(userProfitDetail);
		return renderResult(Global.TRUE, text("保存用户分润成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:userProfitDetail:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserProfitDetail userProfitDetail) {
		userProfitDetailService.delete(userProfitDetail);
		return renderResult(Global.TRUE, text("删除用户分润成功！"));
	}
	
}