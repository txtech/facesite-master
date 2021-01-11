/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.product.web;

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
import com.alibaba.fastjson.JSONValidator;
import com.jeesite.common.codec.EncodeUtils;
import com.jeesite.common.web.BaseController;
import com.facesite.modules.product.entity.TbProductCat;
import com.facesite.modules.product.service.TbProductCatService;

/**
 * 商品分类Controller
 * @author nada
 * @version 2021-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/product/tbProductCat")
public class TbProductCatController extends BaseController {

	@Autowired
	private TbProductCatService tbProductCatService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbProductCat get(Long id, boolean isNewRecord) {
		return tbProductCatService.get(String.valueOf(id), isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:tbProductCat:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbProductCat tbProductCat, Model model) {
		model.addAttribute("tbProductCat", tbProductCat);
		return "modules/product/tbProductCatList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:tbProductCat:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbProductCat> listData(TbProductCat tbProductCat, HttpServletRequest request, HttpServletResponse response) {
		tbProductCat.setPage(new Page<>(request, response));
		Page<TbProductCat> page = tbProductCatService.findPage(tbProductCat);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:tbProductCat:view")
	@RequestMapping(value = "form")
	public String form(TbProductCat tbProductCat, Model model) {
		model.addAttribute("tbProductCat", tbProductCat);
		return "modules/product/tbProductCatForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:tbProductCat:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbProductCat tbProductCat) {
		tbProductCatService.save(tbProductCat);
		return renderResult(Global.TRUE, text("保存商品分类成功！"));
	}

	/**
	 * 停用数据
	 */
	@RequiresPermissions("product:tbProductCat:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TbProductCat tbProductCat) {
		tbProductCat.setStatus(TbProductCat.STATUS_DISABLE);
		tbProductCatService.updateStatus(tbProductCat);
		return renderResult(Global.TRUE, text("停用商品分类成功"));
	}

	/**
	 * 启用数据
	 */
	@RequiresPermissions("product:tbProductCat:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TbProductCat tbProductCat) {
		tbProductCat.setStatus(TbProductCat.STATUS_NORMAL);
		tbProductCatService.updateStatus(tbProductCat);
		return renderResult(Global.TRUE, text("启用商品分类成功"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:tbProductCat:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbProductCat tbProductCat) {
		tbProductCatService.delete(tbProductCat);
		return renderResult(Global.TRUE, text("删除商品分类成功！"));
	}

	/**
	 * 列表选择对话框
	 */
	@RequiresPermissions("product:tbProductCat:view")
	@RequestMapping(value = "tbProductCatSelect")
	public String empUserSelect(TbProductCat tbProductCat, String selectData, Model model) {
		String selectDataJson = EncodeUtils.decodeUrl(selectData);
		if (selectDataJson != null && JSONValidator.from(selectDataJson).validate()){
			model.addAttribute("selectData", selectDataJson);
		}
		model.addAttribute("TbProductCat", tbProductCat);
		return "modules/product/tbProductCatSelect";
	}

}
