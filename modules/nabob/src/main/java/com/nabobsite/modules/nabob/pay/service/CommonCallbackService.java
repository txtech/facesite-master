/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.pay.service;

import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.common.TriggerApiService;
import com.nabobsite.modules.nabob.api.entity.CommonStaticContact;
import com.nabobsite.modules.nabob.api.entity.DbInstanceEntity;
import com.nabobsite.modules.nabob.api.entity.NabobLogicContact;
import com.nabobsite.modules.nabob.api.entity.RedisPrefixContant;
import com.nabobsite.modules.nabob.api.service.UserInfoApiService;
import com.nabobsite.modules.nabob.cms.order.dao.OrderDao;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.config.RedisOpsUtil;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import com.nabobsite.modules.nabob.utils.SnowFlakeIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class CommonCallbackService extends CrudService<OrderDao, Order> {

	@Autowired
	private RedisOpsUtil redisOpsUtil;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private UserInfoApiService userInfoApiService;
	@Autowired
	private TriggerApiService triggerApiService;

	/**
	 * @desc 充值订单回调
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	*/
	public boolean callBack(String orderNo,String pOrderNo,int backStatus,String message) {
		try {
			synchronized (orderNo) {
				Order order = new Order();
				order.setOrderNo(orderNo);
				Order oldOrder = orderDao.getByEntity(order);
				if(oldOrder == null){
					logger.error("充值订单回调失败,订单不存在:{},{}",orderNo,pOrderNo);
					return false;
				}
				int oldStatus = oldOrder.getOrderStatus();
				switch (oldStatus) {
					case CommonStaticContact.ORDER_STATUS_1:
					case CommonStaticContact.ORDER_STATUS_2:
						return this.doExecute(oldOrder,pOrderNo,backStatus,message);
					case CommonStaticContact.ORDER_STATUS_3:
						logger.error("充值订单回调失败,订单已经失败:{},{}",orderNo,pOrderNo);
						return true;
					case CommonStaticContact.ORDER_STATUS_4:
						logger.error("充值订单回调失败,订单已经成功:{},{}",orderNo,pOrderNo);
						return true;
					case CommonStaticContact.ORDER_STATUS_9:
						logger.error("充值订单回调失败,订单已经退款:{},{}",orderNo,pOrderNo);
						return false;
					default:
						logger.error("充值订单回调失败,订单状态未知:{},{}",orderNo,pOrderNo);
						return false;
				}
			}
		} catch (Exception e) {
			logger.error("Failed to recharge order!",e);
			return false;
		}
	}


	/**
	 * @desc 执行回调逻辑
	 * @author nada
	 * @create 2021/5/12 7:03 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean doExecute(Order order,String pOrderNo,int backStatus,String message) {
		try {
			String userId = order.getUserId();
			String orderNo = order.getOrderNo();
			Order newOrder = new Order();
			newOrder.setId(order.getId());
			newOrder.setPorderNo(pOrderNo);
			newOrder.setRemarks(message);
			if(backStatus == CommonStaticContact.ORDER_STATUS_3){
				newOrder.setOrderStatus(CommonStaticContact.ORDER_STATUS_3);
			}else if(backStatus == CommonStaticContact.ORDER_STATUS_4){
				newOrder.setOrderStatus(CommonStaticContact.ORDER_STATUS_4);
			}
			long dbResult = orderDao.update(newOrder);
			if(!CommonStaticContact.dbResult(dbResult)){
				logger.error("充值订单回调失败,更新订单失败:{},{}",orderNo,pOrderNo);
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("Failed to doExecute order!",e);
			return false;
		}
	}
}
