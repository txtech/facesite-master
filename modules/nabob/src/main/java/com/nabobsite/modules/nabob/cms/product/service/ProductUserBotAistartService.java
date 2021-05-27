/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserBotAistart;
import com.nabobsite.modules.nabob.cms.product.dao.ProductUserBotAistartDao;

/**
 * 用户产品机器人智能任务Service
 * @author face
 * @version 2021-05-27
 */
@Service
@Transactional(readOnly=true)
public class ProductUserBotAistartService extends CrudService<ProductUserBotAistartDao, ProductUserBotAistart> {
	
	/**
	 * 获取单条数据
	 * @param productUserBotAistart
	 * @return
	 */
	@Override
	public ProductUserBotAistart get(ProductUserBotAistart productUserBotAistart) {
		return super.get(productUserBotAistart);
	}
	
	/**
	 * 查询分页数据
	 * @param productUserBotAistart 查询条件
	 * @param productUserBotAistart.page 分页对象
	 * @return
	 */
	@Override
	public Page<ProductUserBotAistart> findPage(ProductUserBotAistart productUserBotAistart) {
		return super.findPage(productUserBotAistart);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param productUserBotAistart
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ProductUserBotAistart productUserBotAistart) {
		super.save(productUserBotAistart);
	}
	
	/**
	 * 更新状态
	 * @param productUserBotAistart
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ProductUserBotAistart productUserBotAistart) {
		super.updateStatus(productUserBotAistart);
	}
	
	/**
	 * 删除数据
	 * @param productUserBotAistart
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ProductUserBotAistart productUserBotAistart) {
		super.delete(productUserBotAistart);
	}
	
}