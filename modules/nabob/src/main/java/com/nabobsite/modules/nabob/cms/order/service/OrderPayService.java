/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.order.entity.OrderPay;
import com.nabobsite.modules.nabob.cms.order.dao.OrderPayDao;

/**
 * 交易订单Service
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class OrderPayService extends CrudService<OrderPayDao, OrderPay> {
	
	/**
	 * 获取单条数据
	 * @param orderPay
	 * @return
	 */
	@Override
	public OrderPay get(OrderPay orderPay) {
		return super.get(orderPay);
	}
	
	/**
	 * 查询分页数据
	 * @param orderPay 查询条件
	 * @param orderPay.page 分页对象
	 * @return
	 */
	@Override
	public Page<OrderPay> findPage(OrderPay orderPay) {
		return super.findPage(orderPay);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param orderPay
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(OrderPay orderPay) {
		super.save(orderPay);
	}
	
	/**
	 * 更新状态
	 * @param orderPay
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(OrderPay orderPay) {
		super.updateStatus(orderPay);
	}
	
	/**
	 * 删除数据
	 * @param orderPay
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(OrderPay orderPay) {
		super.delete(orderPay);
	}
	
}