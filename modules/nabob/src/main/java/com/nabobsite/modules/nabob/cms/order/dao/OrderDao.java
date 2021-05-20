/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.order.entity.Order;

/**
 * 订单DAO接口
 * @author face
 * @version 2021-05-20
 */
@MyBatisDao
public interface OrderDao extends CrudDao<Order> {
	
}