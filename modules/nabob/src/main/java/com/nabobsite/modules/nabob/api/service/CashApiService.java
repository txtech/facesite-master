/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.InstanceUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.service.simple.SimpleOrderService;
import com.nabobsite.modules.nabob.cms.order.dao.OrderCashDao;
import com.nabobsite.modules.nabob.cms.order.entity.OrderCash;
import com.nabobsite.modules.nabob.cms.sys.dao.SysChannelDao;
import com.nabobsite.modules.nabob.cms.sys.entity.SysChannel;
import com.nabobsite.modules.nabob.pay.hander.CashHander;
import com.nabobsite.modules.nabob.pay.hander.OrderHander;
import com.nabobsite.modules.nabob.utils.SnowFlakeIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 出款Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class CashApiService extends SimpleOrderService {

	@Autowired
	private OrderCashDao cashDao;
	@Autowired
	private CashHander cashHander;
	@Autowired
	private SysChannelDao sysChannelDao;

	/**
	 * @desc 提款订单
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONObject> cashOrder(String token, OrderCash orderCash) {
		try {
			String email = orderCash.getEmail();
			String accountName = orderCash.getAccountName();
			String phoneNumber = orderCash.getPhoneNumber();
			String accountCard = orderCash.getAccountCard();
			BigDecimal payMoney = orderCash.getCashMoney();
			if(StringUtils.isAnyEmpty(token,accountName,accountCard,email,phoneNumber)){
				return ResultUtil.failed(I18nCode.CODE_10007);
			}
			if(ContactUtils.isLesserOrEqual(payMoney, ContactUtils.ZERO)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			SysChannel channel = this.getOneChannel();
			if(channel == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			synchronized (userId) {
				orderCash.setUserId(userId);
				String orderNo = SnowFlakeIDGenerator.getSnowFlakeNo();
				long dbResult = cashDao.insert(InstanceUtils.initOrderCash(orderCash,orderNo,channel));
				if(!ContactUtils.dbResult(dbResult)){
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				orderCash.setOrderNo(orderNo);
				JSONObject resData = cashHander.doRestReplace(orderCash,channel);
				if(ContactUtils.isOkResult(resData)){
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
			logger.error("提款订单异常",e);
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
	 * @desc 获取提款订单列表
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<List<OrderCash>> getCashOrderList(String token, OrderCash cash) {
		try {
			List<OrderCash> retsult = cashDao.findList(cash);
			return ResultUtil.success(retsult,true);
		} catch (Exception e) {
			logger.error("获取提款订单列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取提款订单详情
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<OrderCash> getCashOrderInfo(String token, OrderCash cash) {
		try {
			OrderCash result = cashDao.getByEntity(cash);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("获取提款订单详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
}
