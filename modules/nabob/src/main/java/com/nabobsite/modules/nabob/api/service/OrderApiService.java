/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.order.dao.OrderDao;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 订单Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class OrderApiService extends CrudService<OrderDao, Order> {

	@Autowired
	private OrderDao orderDao;

	/**
	 * @desc 充值订单
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Order> rechargeOrder(Order order) {
		try {
			long dbResult = orderDao.insert(order);
			return ResultUtil.success(order);
		} catch (Exception e) {
			logger.error("Failed to recharge order!",e);
			return ResultUtil.failed("Failed to recharge order!");
		}
	}

	/**
	 * @desc 获取订单列表
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<List<Order>> getOrderList(Order order) {
		try {
			List<Order> result = orderDao.findList(order);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("Failed to get order list!",e);
			return ResultUtil.failed("Failed to get order list!");
		}
	}

	/**
	 * @desc 获取订单详情
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Order> getOrderInfo(Order order) {
		try {
			Order result = orderDao.getByEntity(order);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("Failed to get order info!",e);
			return ResultUtil.failed("Failed to get order info!");
		}
	}

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
