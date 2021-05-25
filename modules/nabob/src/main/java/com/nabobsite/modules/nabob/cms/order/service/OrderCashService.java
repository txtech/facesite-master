/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.order.entity.OrderCash;
import com.nabobsite.modules.nabob.cms.order.dao.OrderCashDao;

/**
 * 出款订单Service
 * @author face
 * @version 2021-05-25
 */
@Service
@Transactional(readOnly=true)
public class OrderCashService extends CrudService<OrderCashDao, OrderCash> {
	
	/**
	 * 获取单条数据
	 * @param orderCash
	 * @return
	 */
	@Override
	public OrderCash get(OrderCash orderCash) {
		return super.get(orderCash);
	}
	
	/**
	 * 查询分页数据
	 * @param orderCash 查询条件
	 * @param orderCash.page 分页对象
	 * @return
	 */
	@Override
	public Page<OrderCash> findPage(OrderCash orderCash) {
		return super.findPage(orderCash);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param orderCash
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(OrderCash orderCash) {
		super.save(orderCash);
	}
	
	/**
	 * 更新状态
	 * @param orderCash
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(OrderCash orderCash) {
		super.updateStatus(orderCash);
	}
	
	/**
	 * 删除数据
	 * @param orderCash
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(OrderCash orderCash) {
		super.delete(orderCash);
	}
	
}