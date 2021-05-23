/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserBot;
import com.nabobsite.modules.nabob.cms.product.dao.ProductUserBotDao;

/**
 * 用户产品机器人信息Service
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class ProductUserBotService extends CrudService<ProductUserBotDao, ProductUserBot> {
	
	/**
	 * 获取单条数据
	 * @param productUserBot
	 * @return
	 */
	@Override
	public ProductUserBot get(ProductUserBot productUserBot) {
		return super.get(productUserBot);
	}
	
	/**
	 * 查询分页数据
	 * @param productUserBot 查询条件
	 * @param productUserBot.page 分页对象
	 * @return
	 */
	@Override
	public Page<ProductUserBot> findPage(ProductUserBot productUserBot) {
		return super.findPage(productUserBot);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param productUserBot
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ProductUserBot productUserBot) {
		super.save(productUserBot);
	}
	
	/**
	 * 更新状态
	 * @param productUserBot
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ProductUserBot productUserBot) {
		super.updateStatus(productUserBot);
	}
	
	/**
	 * 删除数据
	 * @param productUserBot
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ProductUserBot productUserBot) {
		super.delete(productUserBot);
	}
	
}