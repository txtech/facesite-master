/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserWarehouseRecord;
import com.nabobsite.modules.nabob.cms.product.dao.ProductUserWarehouseRecordDao;

/**
 * 用户云仓库操作记录Service
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class ProductUserWarehouseRecordService extends CrudService<ProductUserWarehouseRecordDao, ProductUserWarehouseRecord> {
	
	/**
	 * 获取单条数据
	 * @param productUserWarehouseRecord
	 * @return
	 */
	@Override
	public ProductUserWarehouseRecord get(ProductUserWarehouseRecord productUserWarehouseRecord) {
		return super.get(productUserWarehouseRecord);
	}
	
	/**
	 * 查询分页数据
	 * @param productUserWarehouseRecord 查询条件
	 * @param productUserWarehouseRecord.page 分页对象
	 * @return
	 */
	@Override
	public Page<ProductUserWarehouseRecord> findPage(ProductUserWarehouseRecord productUserWarehouseRecord) {
		return super.findPage(productUserWarehouseRecord);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param productUserWarehouseRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ProductUserWarehouseRecord productUserWarehouseRecord) {
		super.save(productUserWarehouseRecord);
	}
	
	/**
	 * 更新状态
	 * @param productUserWarehouseRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ProductUserWarehouseRecord productUserWarehouseRecord) {
		super.updateStatus(productUserWarehouseRecord);
	}
	
	/**
	 * 删除数据
	 * @param productUserWarehouseRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ProductUserWarehouseRecord productUserWarehouseRecord) {
		super.delete(productUserWarehouseRecord);
	}
	
}