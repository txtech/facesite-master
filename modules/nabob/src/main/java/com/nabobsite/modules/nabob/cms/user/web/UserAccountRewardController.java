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
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountReward;
import com.nabobsite.modules.nabob.cms.user.service.UserAccountRewardService;

/**
 * 用户账户明细Controller
 * @author face
 * @version 2021-05-15
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userAccountReward")
public class UserAccountRewardController extends BaseController {

	@Autowired
	private UserAccountRewardService userAccountRewardService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserAccountReward get(String id, boolean isNewRecord) {
		return userAccountRewardService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:userAccountReward:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserAccountReward userAccountReward, Model model) {
		model.addAttribute("userAccountReward", userAccountReward);
		return "cms/user/userAccountRewardList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:userAccountReward:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserAccountReward> listData(UserAccountReward userAccountReward, HttpServletRequest request, HttpServletResponse response) {
		userAccountReward.setPage(new Page<>(request, response));
		Page<UserAccountReward> page = userAccountRewardService.findPage(userAccountReward);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:userAccountReward:view")
	@RequestMapping(value = "form")
	public String form(UserAccountReward userAccountReward, Model model) {
		model.addAttribute("userAccountReward", userAccountReward);
		return "cms/user/userAccountRewardForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("user:userAccountReward:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserAccountReward userAccountReward) {
		userAccountRewardService.save(userAccountReward);
		return renderResult(Global.TRUE, text("保存用户账户奖励成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("user:userAccountReward:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserAccountReward userAccountReward) {
		userAccountRewardService.delete(userAccountReward);
		return renderResult(Global.TRUE, text("删除用户账户奖励成功！"));
	}
	
}