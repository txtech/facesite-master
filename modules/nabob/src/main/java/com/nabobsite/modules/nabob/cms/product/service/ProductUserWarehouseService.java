/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserWarehouse;
import com.nabobsite.modules.nabob.cms.product.dao.ProductUserWarehouseDao;

/**
 * 用户产品仓库信息Service
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class ProductUserWarehouseService extends CrudService<ProductUserWarehouseDao, ProductUserWarehouse> {
	
	/**
	 * 获取单条数据
	 * @param productUserWarehouse
	 * @return
	 */
	@Override
	public ProductUserWarehouse get(ProductUserWarehouse productUserWarehouse) {
		return super.get(productUserWarehouse);
	}
	
	/**
	 * 查询分页数据
	 * @param productUserWarehouse 查询条件
	 * @param productUserWarehouse.page 分页对象
	 * @return
	 */
	@Override
	public Page<ProductUserWarehouse> findPage(ProductUserWarehouse productUserWarehouse) {
		return super.findPage(productUserWarehouse);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param productUserWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ProductUserWarehouse productUserWarehouse) {
		super.save(productUserWarehouse);
	}
	
	/**
	 * 更新状态
	 * @param productUserWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ProductUserWarehouse productUserWarehouse) {
		super.updateStatus(productUserWarehouse);
	}
	
	/**
	 * 删除数据
	 * @param productUserWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ProductUserWarehouse productUserWarehouse) {
		super.delete(productUserWarehouse);
	}
	
}