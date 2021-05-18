/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.InstanceContact;
import com.nabobsite.modules.nabob.cms.order.dao.OrderDao;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.utils.SnowFlakeIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
public class OrderApiService extends BaseUserService {

	@Autowired
	private OrderDao orderDao;

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
				return ResultUtil.failed(I18nCode.CODE_10007);
			}
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10009);
			}
			String userId = userInfo.getId();
			order.setUserId(userId);
			String orderNo = SnowFlakeIDGenerator.getSnowFlakeNo();
			synchronized (orderNo) {
				long dbResult = orderDao.insert(InstanceContact.initOrderInfo(order,orderNo));
				if(CommonContact.dbResult(dbResult)){
					order.setOrderNo(orderNo);
					return ResultUtil.successToJson(order);
				}
			}
			return ResultUtil.failed(I18nCode.CODE_10004);
		} catch (Exception e) {
			logger.error("充值订单异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取订单列表
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONArray> getOrderList(Order order, String token) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10009);
			}
			String userId = userInfo.getId();
			order.setUserId(userId);
			List<Order> orderList = orderDao.findList(order);
			JSONArray result = new JSONArray();
			for (Order entity : orderList) {
				result.add(CommonContact.toJSONObject(entity));
			}
			return ResultUtil.successToJsonArray(result);
		} catch (Exception e) {
			logger.error("获取订单列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取订单详情
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONObject> getOrderInfo(Order order, String token) {
		try {
			if(order.getOrderNo() == null){
				return ResultUtil.failed(I18nCode.CODE_10007);
			}
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10009);
			}
			String userId = userInfo.getId();
			order.setUserId(userId);
			Order result = orderDao.getByEntity(order);
			return ResultUtil.successToJson(result);
		} catch (Exception e) {
			logger.error("获取订单详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

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
}
