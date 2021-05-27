/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service.simple;

import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.cms.order.dao.OrderCashDao;
import com.nabobsite.modules.nabob.cms.order.dao.OrderPayDao;
import com.nabobsite.modules.nabob.cms.order.entity.OrderPay;
import com.nabobsite.modules.nabob.cms.sys.dao.SysChannelDao;
import com.nabobsite.modules.nabob.cms.sys.entity.SysChannel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会员用户Service
 * @author face
 * @version 2021-05-10
 */
@Service
public class SimpleOrderService extends SimpleUserService {

	@Autowired
	public SysChannelDao sysChannelDao;
	@Autowired
	public OrderPayDao orderPayDao;
	@Autowired
	public OrderCashDao orderCashDao;


	/**
	 * @desc 根据ID修改
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Boolean updateOrderById(String id,OrderPay order) {
		try {
			if(!ContactUtils.isOkUserId(id)){
				return false;
			}
			order.setId(id);
			long dbResult = orderPayDao.update(order);
			return ContactUtils.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("根据ID修改异常",e);
			return false;
		}
	}

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
			return orderPayDao.getByEntity(order);
		} catch (Exception e) {
			logger.error("根据订单号获取异常",e);
			return null;
		}
	}

	/**
	 * @desc 获取一个通道
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public SysChannel getOneChannel() {
		try {
			List<SysChannel> channelList = sysChannelDao.findList(new SysChannel());
			if(channelList == null || channelList.isEmpty()){
				return null;
			}
			return channelList.get(0);
		} catch (Exception e) {
			logger.error("根据订单号获取异常",e);
			return null;
		}
	}
}
