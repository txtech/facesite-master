/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.dao.ProductBotDao;
import com.nabobsite.modules.nabob.cms.product.dao.ProductWarehouseDao;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 产品机器人Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class ProductApiService extends CrudService<ProductBotDao, ProductBot> {


	@Autowired
	private ProductBotDao productBotDao;
	@Autowired
	private ProductWarehouseDao productWarehouseDao;


	/**
	 * @desc 获取无人机产品列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<List<ProductBot>> getProductBotList(ProductBot productBot) {
		List<ProductBot> list = productBotDao.findList(productBot);
		return ResultUtil.success(list);
	}

	/**
	 * @desc 获取云仓库产品列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<List<ProductWarehouse>> getProductWarehouseList(ProductWarehouse productWarehouse) {
		List<ProductWarehouse> list = productWarehouseDao.findList(productWarehouse);
		return ResultUtil.success(list);
	}

	/**
	 * 获取单条数据
	 * @param productBot
	 * @return
	 */
	@Override
	public ProductBot get(ProductBot productBot) {
		return super.get(productBot);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param productBot
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ProductBot productBot) {
		super.save(productBot);
	}

	/**
	 * 更新状态
	 * @param productBot
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ProductBot productBot) {
		super.updateStatus(productBot);
	}

	/**
	 * 删除数据
	 * @param productBot
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ProductBot productBot) {
		super.delete(productBot);
	}

}
