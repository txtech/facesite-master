/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.pay.service;

import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.common.TriggerApiService;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
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
					case CommonContact.ORDER_STATUS_1:
					case CommonContact.ORDER_STATUS_2:
						return this.doExecute(orderNo,pOrderNo,oldOrder,backStatus,message);
					case CommonContact.ORDER_STATUS_3:
						logger.error("充值订单回调失败,订单已经失败:{},{}",orderNo,pOrderNo);
						return true;
					case CommonContact.ORDER_STATUS_4:
						logger.error("充值订单回调失败,订单已经成功:{},{}",orderNo,pOrderNo);
						return true;
					case CommonContact.ORDER_STATUS_9:
						logger.error("充值订单回调失败,订单已经退款:{},{}",orderNo,pOrderNo);
						return false;
					default:
						logger.error("充值订单回调失败,订单状态未知:{},{}",orderNo,pOrderNo);
						return false;
				}
			}
		} catch (Exception e) {
			logger.error("充值订单回调异常",e);
			return false;
		}
	}


	/**
	 * @desc 执行回调逻辑
	 * @author nada
	 * @create 2021/5/12 7:03 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean doExecute(String orderNo,String pOrderNo,Order oldOrder,int backStatus,String message) {
		try {
			String id = oldOrder.getId();
			int type = oldOrder.getType();
			String userId = oldOrder.getUserId();
			BigDecimal payMoney = oldOrder.getPayMoney();
			BigDecimal actualMoney = oldOrder.getActualMoney();
			Order newOrder = new Order();
			newOrder.setId(id);
			newOrder.setPorderNo(pOrderNo);
			newOrder.setRemarks(message);
			if(backStatus == CommonContact.ORDER_STATUS_3){
				newOrder.setOrderStatus(CommonContact.ORDER_STATUS_3);
			}else if(backStatus == CommonContact.ORDER_STATUS_4){
				newOrder.setOrderStatus(CommonContact.ORDER_STATUS_4);
			}
			Boolean isOk = orderApiService.updateOrderById(id,newOrder);
			if(!isOk){
				logger.error("充值订单回调失败,更新订单失败:{},{}",orderNo,pOrderNo);
				return false;
			}
			if(backStatus == CommonContact.ORDER_STATUS_3){
				logger.error("充值订单回调失败,更新订单成功:{},{}",orderNo,pOrderNo);
				return true;
			}
			if(backStatus != CommonContact.ORDER_STATUS_4){
				logger.error("充值订单回调失败,更新订单失败:{},{}",orderNo,pOrderNo);
				return false;
			}
			int updateType = CommonContact.USER_ACCOUNT_DETAIL_TYPE_2;
			isOk = userAccountApiService.updateAccountBalance(userId,updateType,payMoney,orderNo,CommonContact.USER_ACCOUNT_DETAIL_TITLE_1);
			if(!isOk){
				logger.error("充值订单回调失败,更新账户失败:{},{}",orderNo,pOrderNo);
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("执行回调逻辑异常,{}",orderNo,e);
			return false;
		}
	}
}
