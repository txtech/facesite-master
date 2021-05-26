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
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfoMembership;
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
	private CashHander cashHander;

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
			BigDecimal cashMoney = orderCash.getCashMoney();
			if(StringUtils.isAnyEmpty(token,accountName,accountCard,email,phoneNumber)){
				return ResultUtil.failed(I18nCode.CODE_10007);
			}
			if(ContactUtils.isLesserOrEqual(cashMoney, ContactUtils.ZERO)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			UserInfo userInfo  = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserAccount userAccount = this.getUserAccountByUserId(userId);
			if(userAccount == null){
				return ResultUtil.failed(I18nCode.CODE_10009);
			}
			BigDecimal availableMoney = userAccount.getAvailableMoney();
			if(ContactUtils.isLesserOrEqualZero(availableMoney)){
				return ResultUtil.failed(I18nCode.CODE_10104);
			}
			if(ContactUtils.isLesser(availableMoney,cashMoney)){
				return ResultUtil.failed(I18nCode.CODE_10104);
			}

			UserInfoMembership parms = new UserInfoMembership();
			parms.setLevel(userInfo.getLevel());
			UserInfoMembership userInfoMembership = userInfoMembershipDao.getByEntity(parms);
			if(userInfoMembership == null){
				return ResultUtil.failed(I18nCode.CODE_10104);
			}
			BigDecimal minWithdraw = userInfoMembership.getWithdrawMin();
			BigDecimal maxWithdraw = userInfoMembership.getWithdrawMax();
			if(ContactUtils.isLesserOrEqual(cashMoney,minWithdraw)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			if(ContactUtils.isLesserOrEqual(maxWithdraw,cashMoney)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			SysChannel channel = this.getOneChannel();
			if(channel == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			synchronized (userId) {
				orderCash.setUserId(userId);
				String orderNo = SnowFlakeIDGenerator.getSnowFlakeNo();
				long dbResult = orderCashDao.insert(InstanceUtils.initOrderCash(orderCash,orderNo,channel));
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
	 * @desc 获取提款订单列表
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<List<OrderCash>> getCashOrderList(String token,OrderCash cash) {
		try {
			List<OrderCash> retsult = orderCashDao.findList(cash);
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
	public CommonResult<OrderCash> getCashOrderInfo(String token,String orderNo) {
		try {
			OrderCash cash = new OrderCash();
			cash.setOrderNo(orderNo);
			OrderCash result = orderCashDao.getByEntity(cash);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("获取提款订单详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
}
