/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.order.dao.CashDao;
import com.nabobsite.modules.nabob.cms.order.dao.OrderDao;
import com.nabobsite.modules.nabob.cms.order.entity.Cash;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.ResultUtil;
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
	public CommonResult<Cash> cashOrder(String token,Cash cash) {
		try {
			long dbResult = cashDao.insert(cash);
			return ResultUtil.success(cash);
		} catch (Exception e) {
			logger.error("Failed to recharge order!",e);
			return ResultUtil.failed("Failed to recharge order!");
		}
	}

	/**
	 * @desc 获取提款订单列表
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<List<Cash>> getCashOrderList(String token,Cash cash) {
		try {
			List<Cash> result = cashDao.findList(cash);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("Failed to get Cash order list!",e);
			return ResultUtil.failed("Failed to get Cash order list!");
		}
	}

	/**
	 * @desc 获取提款订单详情
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Cash> getCashOrderInfo(String token,Cash cash) {
		try {
			Cash result = cashDao.getByEntity(cash);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("Failed to get Cash order info!",e);
			return ResultUtil.failed("Failed to get Cash order info!");
		}
	}

	/**
	 * 获取单条数据
	 * @param cash
	 * @return
	 */
	@Override
	public Cash get(Cash cash) {
		return super.get(cash);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param cash
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Cash cash) {
		super.save(cash);
	}

	/**
	 * 更新状态
	 * @param cash
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Cash cash) {
		super.updateStatus(cash);
	}

	/**
	 * 删除数据
	 * @param cash
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Cash cash) {
		super.delete(cash);
	}

}
