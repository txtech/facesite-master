/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.order.entity.OrderPay;

/**
 * 交易订单DAO接口
 * @author face
 * @version 2021-05-23
 */
@MyBatisDao
public interface OrderPayDao extends CrudDao<OrderPay> {
	
}