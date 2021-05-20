/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import com.nabobsite.modules.nabob.cms.product.dao.ProductWarehouseDao;

/**
 * 产品仓库Service
 * @author face
 * @version 2021-05-20
 */
@Service
@Transactional(readOnly=true)
public class ProductWarehouseService extends CrudService<ProductWarehouseDao, ProductWarehouse> {
	
	/**
	 * 获取单条数据
	 * @param productWarehouse
	 * @return
	 */
	@Override
	public ProductWarehouse get(ProductWarehouse productWarehouse) {
		return super.get(productWarehouse);
	}
	
	/**
	 * 查询分页数据
	 * @param productWarehouse 查询条件
	 * @param productWarehouse.page 分页对象
	 * @return
	 */
	@Override
	public Page<ProductWarehouse> findPage(ProductWarehouse productWarehouse) {
		return super.findPage(productWarehouse);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param productWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ProductWarehouse productWarehouse) {
		super.save(productWarehouse);
	}
	
	/**
	 * 更新状态
	 * @param productWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ProductWarehouse productWarehouse) {
		super.updateStatus(productWarehouse);
	}
	
	/**
	 * 删除数据
	 * @param productWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ProductWarehouse productWarehouse) {
		super.delete(productWarehouse);
	}
	
}