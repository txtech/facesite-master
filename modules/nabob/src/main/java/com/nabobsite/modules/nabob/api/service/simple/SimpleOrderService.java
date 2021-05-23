/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service.simple;

import com.nabobsite.modules.nabob.cms.order.dao.OrderPayDao;
import com.nabobsite.modules.nabob.cms.order.entity.OrderPay;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员用户Service
 * @author face
 * @version 2021-05-10
 */
@Service
public class SimpleOrderService extends SimpleUserService {

	@Autowired
	public OrderPayDao orderDao;

	/**
	 * @desc 根据订单号获取
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public OrderPay getOrderByOrderNo(String orderNo) {
		try {
			if(StringUtils.isEmpty(orderNo)){
				return null;
			}
			OrderPay order = new OrderPay();
			order.setOrderNo(orderNo);
			return orderDao.getByEntity(order);
		} catch (Exception e) {
			logger.error("根据订单号获取异常",e);
			return null;
		}
	}
}
