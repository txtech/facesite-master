/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.dao.ProductBotDao;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 产品机器人Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class ProductApiService extends CrudService<ProductBotDao, ProductBot> {

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
