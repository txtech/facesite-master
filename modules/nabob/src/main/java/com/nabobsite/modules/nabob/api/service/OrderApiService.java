/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.order.dao.OrderDao;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class OrderApiService extends CrudService<OrderDao, Order> {

	/**
	 * 获取单条数据
	 * @param order
	 * @return
	 */
	@Override
	public Order get(Order order) {
		return super.get(order);
	}

	/**
	 * 查询分页数据
	 * @param order 查询条件
	 * @param order.page 分页对象
	 * @return
	 */
	@Override
	public Page<Order> findPage(Order order) {
		return super.findPage(order);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param order
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Order order) {
		super.save(order);
	}

	/**
	 * 更新状态
	 * @param order
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Order order) {
		super.updateStatus(order);
	}

	/**
	 * 删除数据
	 * @param order
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Order order) {
		super.delete(order);
	}

}
