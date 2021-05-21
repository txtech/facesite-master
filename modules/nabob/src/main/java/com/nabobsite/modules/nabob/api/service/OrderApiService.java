/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.service.simple.SimpleOrderService;
import com.nabobsite.modules.nabob.api.service.simple.SimpleUserService;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.InstanceContact;
import com.nabobsite.modules.nabob.cms.order.dao.OrderDao;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.sys.dao.SysChannelDao;
import com.nabobsite.modules.nabob.cms.sys.entity.SysChannel;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.pay.hander.OrderHander;
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
public class OrderApiService extends SimpleOrderService {

	@Autowired
	private OrderHander orderHander;

	@Autowired
	private SysChannelDao sysChannelDao;

	/**
	 * @desc 充值订单
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONObject> rechargeOrder(Order order, String token) {
		try {
			String name = order.getName();
			String email = order.getEmail();
			String phoneNumber = order.getPhoneNumber();
			BigDecimal payMoney = order.getPayMoney();
			if(StringUtils.isAnyEmpty(token,name,email,phoneNumber)){
				return ResultUtil.failed(I18nCode.CODE_10007);
			}
			if(CommonContact.isLesserOrEqual(payMoney, CommonContact.ZERO)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			String userId  = this.getUserIdByToken(token);
			if(!CommonContact.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			SysChannel channel = this.getOneChannel();
			if(channel == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			synchronized (userId) {
				order.setUserId(userId);
				String orderNo = SnowFlakeIDGenerator.getSnowFlakeNo();
				long dbResult = orderDao.insert(InstanceContact.initOrderInfo(order,orderNo,channel));
				if(!CommonContact.dbResult(dbResult)){
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				order.setOrderNo(orderNo);
				JSONObject resData = orderHander.doRestPay(order,channel);
				if(CommonContact.isOkResult(resData)){
					JSONObject result = new JSONObject();
					result.put("orderNo",orderNo);
					return ResultUtil.success(result);
				}else{
					JSONObject result = new JSONObject();
					result.put("orderNo",orderNo);
					return ResultUtil.failed(I18nCode.CODE_10103);
				}
			}
		} catch (Exception e) {
			logger.error("充值订单异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
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


	/**
	 * @desc 获取订单列表
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<List<Order>> getOrderList(Order order, String token) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!CommonContact.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			order.setUserId(userId);
			List<Order> result = orderDao.findList(order);
			return ResultUtil.success(result,true);
		} catch (Exception e) {
			logger.error("获取订单列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 根据ID修改
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean updateOrderById(String id,Order order) {
		try {
			if(StringUtils.isEmpty(id)){
				return null;
			}
			order.setId(id);
			long dbResult = orderDao.update(order);
			return CommonContact.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("根据ID修改异常",e);
			return null;
		}
	}

	/**
	 * @desc 获取订单详情
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	public CommonResult<Order> getOrderInfo(String token,String orderNo) {
		try {
			if(StringUtils.isEmpty(orderNo)){
				return ResultUtil.failed(I18nCode.CODE_10007);
			}
			String userId  = this.getUserIdByToken(token);
			if(!CommonContact.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			Order result = this.getOrderByOrderNo(orderNo);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("获取订单详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

}
