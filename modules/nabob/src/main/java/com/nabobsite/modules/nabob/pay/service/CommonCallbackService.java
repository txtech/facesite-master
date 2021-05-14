/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.pay.service;

import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.common.TriggerApiService;
import com.nabobsite.modules.nabob.api.entity.CommonStaticContact;
import com.nabobsite.modules.nabob.api.service.OrderApiService;
import com.nabobsite.modules.nabob.api.service.UserAccountApiService;
import com.nabobsite.modules.nabob.cms.order.dao.OrderDao;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 订单Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class CommonCallbackService extends CrudService<OrderDao, Order> {

	@Autowired
	private TriggerApiService triggerApiService;
	@Autowired
	private OrderApiService orderApiService;
	@Autowired
	private UserAccountApiService userAccountApiService;

	/**
	 * @desc 充值订单回调
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean callBack(String orderNo,String pOrderNo,int backStatus,String message) {
		try {
			synchronized (orderNo) {
				Order oldOrder = orderApiService.getOrderByOrderNo(orderNo);
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
	public boolean doExecute(Order oldOrder,String pOrderNo,int backStatus,String message) {
		try {
			String id = oldOrder.getId();
			int type = oldOrder.getType();
			String userId = oldOrder.getUserId();
			String orderNo = oldOrder.getOrderNo();
			BigDecimal payMoney = oldOrder.getPayMoney();
			BigDecimal actualMoney = oldOrder.getActualMoney();
			Order newOrder = new Order();
			newOrder.setId(id);
			newOrder.setPorderNo(pOrderNo);
			newOrder.setRemarks(message);
			if(backStatus == CommonStaticContact.ORDER_STATUS_3){
				newOrder.setOrderStatus(CommonStaticContact.ORDER_STATUS_3);
			}else if(backStatus == CommonStaticContact.ORDER_STATUS_4){
				newOrder.setOrderStatus(CommonStaticContact.ORDER_STATUS_4);
			}
			Boolean isOk = orderApiService.updateOrderById(id,newOrder);
			if(!isOk){
				logger.error("充值订单回调失败,更新订单失败:{},{}",orderNo,pOrderNo);
				return false;
			}
			if(backStatus == CommonStaticContact.ORDER_STATUS_3){
				logger.error("充值订单回调失败,更新订单成功:{},{}",orderNo,pOrderNo);
				return true;
			}
			if(backStatus != CommonStaticContact.ORDER_STATUS_4){
				logger.error("充值订单回调失败,更新订单失败:{},{}",orderNo,pOrderNo);
				return false;
			}
			String title = "充值成功:"+actualMoney;
			isOk = userAccountApiService.addAccountBalance(userId,CommonStaticContact.USER_ACCOUNT_RECORD_TYPE_1,actualMoney,orderNo,title,orderNo);
			if(!isOk){
				logger.error("充值订单回调失败,更新账户失败:{},{}",orderNo,pOrderNo);
				return false;
			}
			if(type == CommonStaticContact.ORDER_TYPE_RECHANGE){
				triggerApiService.balanceTrigger(userId,payMoney);
			}
			return true;
		} catch (Exception e) {
			logger.error("Failed to doExecute order!",e);
			return false;
		}
	}
}
