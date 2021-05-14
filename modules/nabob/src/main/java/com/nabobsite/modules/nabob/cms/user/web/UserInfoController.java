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
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.cms.user.service.UserInfoService;

/**
 * 会员用户Controller
 * @author face
 * @version 2021-05-14
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userInfo")
public class UserInfoController extends BaseController {

	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserInfo get(String id, boolean isNewRecord) {
		return userInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:userInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserInfo userInfo, Model model) {
		model.addAttribute("userInfo", userInfo);
		return "cms/user/userInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:userInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserInfo> listData(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response) {
		userInfo.setPage(new Page<>(request, response));
		Page<UserInfo> page = userInfoService.findPage(userInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:userInfo:view")
	@RequestMapping(value = "form")
	public String form(UserInfo userInfo, Model model) {
		model.addAttribute("userInfo", userInfo);
		return "cms/user/userInfoForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:userInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserInfo userInfo) {
		userInfoService.save(userInfo);
		return renderResult(Global.TRUE, text("保存会员用户成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:userInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserInfo userInfo) {
		userInfoService.delete(userInfo);
		return renderResult(Global.TRUE, text("删除会员用户成功！"));
	}
	
}