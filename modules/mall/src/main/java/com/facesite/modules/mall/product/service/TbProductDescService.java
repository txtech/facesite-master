/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.mall.product.service;

import com.facesite.modules.mall.product.entity.TbProductDesc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.facesite.modules.mall.product.dao.TbProductDescDao;

/**
 * 商品描述Service
 * @author nada
 * @version 2021-01-07
 */
@Service
@Transactional(readOnly=true)
public class TbProductDescService extends CrudService<TbProductDescDao, TbProductDesc> {

	/**
	 * 获取单条数据
	 * @param tbProductDesc
	 * @return
	 */
	@Override
	public TbProductDesc get(TbProductDesc tbProductDesc) {
		return super.get(tbProductDesc);
	}

	/**
	 * 查询分页数据
	 * @param tbProductDesc 查询条件
	 * @return
	 */
	@Override
	public Page<TbProductDesc> findPage(TbProductDesc tbProductDesc) {
		return super.findPage(tbProductDesc);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param tbProductDesc
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbProductDesc tbProductDesc) {
		super.save(tbProductDesc);
	}

	/**
	 * 更新状态
	 * @param tbProductDesc
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbProductDesc tbProductDesc) {
		super.updateStatus(tbProductDesc);
	}

	/**
	 * 删除数据
	 * @param tbProductDesc
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbProductDesc tbProductDesc) {
		super.delete(tbProductDesc);
	}

}
