/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.InstanceContact;
import com.nabobsite.modules.nabob.api.entity.RedisPrefixContant;
import com.nabobsite.modules.nabob.api.model.OrderInfoModel;
import com.nabobsite.modules.nabob.api.model.UserInfoModel;
import com.nabobsite.modules.nabob.cms.order.dao.OrderDao;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.config.RedisOpsUtil;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.ResultUtil;
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
public class OrderApiService extends CrudService<OrderDao, Order> {

	@Autowired
	private RedisOpsUtil redisOpsUtil;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private UserInfoApiService userInfoApiService;

	/**
	 * @desc 充值订单
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<OrderInfoModel> rechargeOrder(OrderInfoModel orderInfoModel,String token) {
		try {
			String name = orderInfoModel.getName();
			String email = orderInfoModel.getEmail();
			String phoneNumber = orderInfoModel.getPhoneNumber();
			BigDecimal payMoney = orderInfoModel.getPayMoney();
			if(StringUtils.isAnyEmpty(token,name,email,phoneNumber)){
				return ResultUtil.failed("充值失败,获取充值参数为空");
			}
			if(CommonContact.isLesserOrEqual(payMoney, CommonContact.ZERO)){
				return ResultUtil.failed("充值失败,充值金额小于0");
			}
			UserInfo oldUserInfo = userInfoApiService.getUserInfoByToken(token);
			if(oldUserInfo == null){
				return ResultUtil.failed("充值失败,获取帐号信息为空");
			}
			String userId = oldUserInfo.getId();
			Order order = (Order)orderInfoModel.clone();
			order.setUserId(userId);
			String orderNo = SnowFlakeIDGenerator.getSnowFlakeNo();
			synchronized (orderNo) {
				long dbResult = orderDao.insert(InstanceContact.initOrderInfo(order,orderNo));
				if(CommonContact.dbResult(dbResult)){
					orderInfoModel.setOrderNo(orderNo);
					return ResultUtil.success(orderInfoModel);
				}
			}
			return ResultUtil.failed("Failed to recharge order!");
		} catch (Exception e) {
			logger.error("Failed to recharge order!",e);
			return ResultUtil.failed("Failed to recharge order!");
		}
	}

	/**
	 * @desc 获取订单列表
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<List<Order>> getOrderList(Order order,String token) {
		try {
			if(StringUtils.isAnyEmpty(token)){
				return ResultUtil.failed("充值失败,获取令牌为空");
			}
			String userId = (String) redisOpsUtil.get(RedisPrefixContant.getTokenUserKey(token));
			if(StringUtils.isEmpty(userId)){
				return ResultUtil.failed("获取失败,登陆令牌失效");
			}
			order.setUserId(userId);
			List<Order> result = orderDao.findList(order);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("Failed to get order list!",e);
			return ResultUtil.failed("Failed to get order list!");
		}
	}

	/**
	 * @desc 获取订单详情
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<OrderInfoModel> getOrderInfo(Order order, String token) {
		try {
			if(StringUtils.isAnyEmpty(token)){
				return ResultUtil.failed("获取订单详情,获取令牌为空");
			}
			if(order.getOrderNo() == null){
				return ResultUtil.failed("获取订单详情,获取订单号为空");
			}
			String userId = (String) redisOpsUtil.get(RedisPrefixContant.getTokenUserKey(token));
			if(StringUtils.isEmpty(userId)){
				return ResultUtil.failed("获取失败,登陆令牌失效");
			}
			order.setUserId(userId);
			Order orderInfo = orderDao.getByEntity(order);
			OrderInfoModel result = new OrderInfoModel();
			BeanUtils.copyProperties(orderInfo, result);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("Failed to get order info!",e);
			return ResultUtil.failed("Failed to get order info!");
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
