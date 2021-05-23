/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouseProgress;
import com.nabobsite.modules.nabob.cms.product.dao.ProductWarehouseProgressDao;

/**
 * 用户任务进行中Service
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class ProductWarehouseProgressService extends CrudService<ProductWarehouseProgressDao, ProductWarehouseProgress> {
	
	/**
	 * 获取单条数据
	 * @param productWarehouseProgress
	 * @return
	 */
	@Override
	public ProductWarehouseProgress get(ProductWarehouseProgress productWarehouseProgress) {
		return super.get(productWarehouseProgress);
	}
	
	/**
	 * 查询分页数据
	 * @param productWarehouseProgress 查询条件
	 * @param productWarehouseProgress.page 分页对象
	 * @return
	 */
	@Override
	public Page<ProductWarehouseProgress> findPage(ProductWarehouseProgress productWarehouseProgress) {
		return super.findPage(productWarehouseProgress);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param productWarehouseProgress
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ProductWarehouseProgress productWarehouseProgress) {
		super.save(productWarehouseProgress);
	}
	
	/**
	 * 更新状态
	 * @param productWarehouseProgress
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ProductWarehouseProgress productWarehouseProgress) {
		super.updateStatus(productWarehouseProgress);
	}
	
	/**
	 * 删除数据
	 * @param productWarehouseProgress
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ProductWarehouseProgress productWarehouseProgress) {
		super.delete(productWarehouseProgress);
	}
	
}