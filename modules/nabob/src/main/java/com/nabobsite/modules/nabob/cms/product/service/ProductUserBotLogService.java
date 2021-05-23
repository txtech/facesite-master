/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserBotLog;
import com.nabobsite.modules.nabob.cms.product.dao.ProductUserBotLogDao;

/**
 * 用户产品机器人记录Service
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class ProductUserBotLogService extends CrudService<ProductUserBotLogDao, ProductUserBotLog> {
	
	/**
	 * 获取单条数据
	 * @param productUserBotLog
	 * @return
	 */
	@Override
	public ProductUserBotLog get(ProductUserBotLog productUserBotLog) {
		return super.get(productUserBotLog);
	}
	
	/**
	 * 查询分页数据
	 * @param productUserBotLog 查询条件
	 * @param productUserBotLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<ProductUserBotLog> findPage(ProductUserBotLog productUserBotLog) {
		return super.findPage(productUserBotLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param productUserBotLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ProductUserBotLog productUserBotLog) {
		super.save(productUserBotLog);
	}
	
	/**
	 * 更新状态
	 * @param productUserBotLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ProductUserBotLog productUserBotLog) {
		super.updateStatus(productUserBotLog);
	}
	
	/**
	 * 删除数据
	 * @param productUserBotLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ProductUserBotLog productUserBotLog) {
		super.delete(productUserBotLog);
	}
	
}