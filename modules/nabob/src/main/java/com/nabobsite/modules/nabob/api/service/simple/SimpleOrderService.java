/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service.simple;

import com.nabobsite.modules.nabob.cms.order.dao.OrderDao;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.product.dao.*;
import com.nabobsite.modules.nabob.cms.product.entity.*;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountWarehouse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员用户Service
 * @author face
 * @version 2021-05-10
 */
@Service
public class SimpleOrderService extends SimpleUserService {

	@Autowired
	public OrderDao orderDao;

	/**
	 * @desc 根据订单号获取
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public Order getOrderByOrderNo(String orderNo) {
		try {
			if(StringUtils.isEmpty(orderNo)){
				return null;
			}
			Order order = new Order();
			order.setOrderNo(orderNo);
			return orderDao.getByEntity(order);
		} catch (Exception e) {
			logger.error("根据订单号获取异常",e);
			return null;
		}
	}
}
