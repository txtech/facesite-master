/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.product.dao.ProductBotDao;

/**
 * 产品机器人Service
 * @author face
 * @version 2021-05-12
 */
@Service
@Transactional(readOnly=true)
public class ProductBotService extends CrudService<ProductBotDao, ProductBot> {
	
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
	 * 查询分页数据
	 * @param productBot 查询条件
	 * @param productBot.page 分页对象
	 * @return
	 */
	@Override
	public Page<ProductBot> findPage(ProductBot productBot) {
		return super.findPage(productBot);
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