/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.cms.order.dao.CashDao;
import com.nabobsite.modules.nabob.cms.order.entity.Cash;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 出款Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class CashApiService extends CrudService<CashDao, Cash> {

	@Autowired
	private CashDao cashDao;

	/**
	 * @desc 提款订单
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> cashOrder(String token, Cash cash) {
		try {
			long dbResult = cashDao.insert(cash);
			return ResultUtil.successToBoolean(true);
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
	public CommonResult<JSONArray> getCashOrderList(String token, Cash cash) {
		try {
			List<Cash> cashDaoList = cashDao.findList(cash);
			JSONArray result = new JSONArray();
			for (Cash entity : cashDaoList) {
				result.add(CommonContact.toJSONObject(entity));
			}
			return ResultUtil.successToJsonArray(result);
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
	public CommonResult<JSONObject> getCashOrderInfo(String token,Cash cash) {
		try {
			Cash result = cashDao.getByEntity(cash);
			return ResultUtil.successToJson(result);
		} catch (Exception e) {
			logger.error("获取提款订单详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
}
