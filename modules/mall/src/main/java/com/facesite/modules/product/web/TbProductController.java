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
import com.facesite.modules.product.entity.TbProduct;
import com.facesite.modules.product.service.TbProductService;

/**
 * 商品表Controller
 * @author nada
 * @version 2021-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/product/tbProduct")
public class TbProductController extends BaseController {

	@Autowired
	private TbProductService tbProductService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TbProduct get(Long id, boolean isNewRecord) {
		return tbProductService.get(String.valueOf(id), isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:tbProduct:view")
	@RequestMapping(value = {"list", ""})
	public String list(TbProduct tbProduct, Model model) {
		model.addAttribute("tbProduct", tbProduct);
		return "modules/product/tbProductList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:tbProduct:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TbProduct> listData(TbProduct tbProduct, HttpServletRequest request, HttpServletResponse response) {
		tbProduct.setPage(new Page<>(request, response));
		Page<TbProduct> page = tbProductService.findPage(tbProduct);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:tbProduct:view")
	@RequestMapping(value = "form")
	public String form(TbProduct tbProduct, Model model) {
		model.addAttribute("tbProduct", tbProduct);
		return "modules/product/tbProductForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("product:tbProduct:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TbProduct tbProduct) {
		tbProductService.save(tbProduct);
		return renderResult(Global.TRUE, text("保存商品表成功！"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("product:tbProduct:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TbProduct tbProduct) {
		tbProductService.delete(tbProduct);
		return renderResult(Global.TRUE, text("删除商品表成功！"));
	}

	/**
	 * 列表选择对话框
	 */
	@RequiresPermissions("product:tbProduct:view")
	@RequestMapping(value = "tbProductSelect")
	public String empUserSelect(TbProduct tbProduct, String selectData, Model model) {
		String selectDataJson = EncodeUtils.decodeUrl(selectData);
		if (selectDataJson != null && JSONValidator.from(selectDataJson).validate()){
			model.addAttribute("selectData", selectDataJson);
		}
		model.addAttribute("TbProduct", tbProduct);
		return "modules/product/tbProductSelect";
	}

}
