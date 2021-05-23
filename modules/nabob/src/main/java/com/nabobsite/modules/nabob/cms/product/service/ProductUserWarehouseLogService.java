/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserWarehouseLog;
import com.nabobsite.modules.nabob.cms.product.dao.ProductUserWarehouseLogDao;

/**
 * 用户产品仓库日志Service
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class ProductUserWarehouseLogService extends CrudService<ProductUserWarehouseLogDao, ProductUserWarehouseLog> {
	
	/**
	 * 获取单条数据
	 * @param productUserWarehouseLog
	 * @return
	 */
	@Override
	public ProductUserWarehouseLog get(ProductUserWarehouseLog productUserWarehouseLog) {
		return super.get(productUserWarehouseLog);
	}
	
	/**
	 * 查询分页数据
	 * @param productUserWarehouseLog 查询条件
	 * @param productUserWarehouseLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<ProductUserWarehouseLog> findPage(ProductUserWarehouseLog productUserWarehouseLog) {
		return super.findPage(productUserWarehouseLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param productUserWarehouseLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ProductUserWarehouseLog productUserWarehouseLog) {
		super.save(productUserWarehouseLog);
	}
	
	/**
	 * 更新状态
	 * @param productUserWarehouseLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ProductUserWarehouseLog productUserWarehouseLog) {
		super.updateStatus(productUserWarehouseLog);
	}
	
	/**
	 * 删除数据
	 * @param productUserWarehouseLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ProductUserWarehouseLog productUserWarehouseLog) {
		super.delete(productUserWarehouseLog);
	}
	
}