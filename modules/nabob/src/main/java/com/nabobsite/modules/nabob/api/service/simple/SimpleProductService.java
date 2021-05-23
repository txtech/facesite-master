/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service.simple;

import com.nabobsite.modules.nabob.cms.product.dao.*;
import com.nabobsite.modules.nabob.cms.product.entity.*;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员用户Service
 * @author face
 * @version 2021-05-10
 */
@Service
public class SimpleProductService extends SimpleUserService {

	@Autowired
	public ProductBotDao productBotDao;
	@Autowired
	public ProductWarehouseDao productWarehouseDao;
	@Autowired
	public ProductUserBotDao userProductBotDao;
	@Autowired
	public ProductUserBotLogDao userProductBotLogDao;
	@Autowired
	public ProductUserWarehouseDao userProductWarehouseDao;
	@Autowired
	public ProductUserWarehouseLogDao userProductWarehouseLogDao;
	@Autowired
	public ProductUserWarehouseRecordDao userProductWarehouseRecordDao;

	/**
	 * @desc 用户云仓库产品
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public ProductUserWarehouse getUserProductWarehouseByUserIdAndId(String userId,String warehouseId) {
		try {
			ProductUserWarehouse userProductWarehouse = new ProductUserWarehouse();
			userProductWarehouse.setUserId(userId);
			userProductWarehouse.setWarehouseId(warehouseId);
			return userProductWarehouseDao.getByEntity(userProductWarehouse);
		} catch (Exception e) {
			logger.error("用户云仓库产品异常,{},{}",userId,warehouseId,e);
			return null;
		}
	}
	/**
	 * @desc 用户云仓库产品账户
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public UserAccountWarehouse getUserAccountWarehouseByUserId(String userId) {
		try {
			UserAccountWarehouse userAccountWarehouse = new UserAccountWarehouse();
			userAccountWarehouse.setUserId(userId);
			return userAccountWarehouseDao.getByEntity(userAccountWarehouse);
		} catch (Exception e) {
			logger.error("用户云仓库产品账户异常,{}",userId,e);
			return null;
		}
	}

	/**
	 * @desc 用户无人机产品
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public ProductUserBot getUserProductBotByUserAndId(String userId, String botId) {
		try {
			ProductUserBot userProductBot = new ProductUserBot();
			userProductBot.setUserId(userId);
			userProductBot.setBotId(botId);
			return userProductBotDao.getByEntity(userProductBot);
		} catch (Exception e) {
			logger.error("用户无人机产品异常,{},{}",userId,botId,e);
			return null;
		}
	}


	/**
	 * @desc 获取云仓库产品详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	public ProductWarehouse getProductWarehouseById(String id) {
		try {
			ProductWarehouse productWarehouse = new ProductWarehouse();
			productWarehouse.setId(id);
			return productWarehouseDao.getByEntity(productWarehouse);
		} catch (Exception e) {
			logger.error("获取云仓库产品详情异常,{}",id,e);
			return null;
		}
	}

	/**
	 * @desc 获取无人机产品详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	public ProductBot getProductBotInfoById(String id) {
		try {
			ProductBot productBotInfo = new ProductBot();
			productBotInfo.setId(id);
			return productBotDao.getByEntity(productBotInfo);
		} catch (Exception e) {
			logger.error("获取无人机产品详情异常,{}",id,e);
			return null;
		}
	}
	/**
	 * @desc 获取无人机产品刷单记录
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	public ProductUserBotLog getUserProductBotLogByOrderNo(String orderNo) {
		try {
			ProductUserBotLog userProductBotLog = new ProductUserBotLog();
			userProductBotLog.setOrderNo(orderNo);
			return userProductBotLogDao.getByEntity(userProductBotLog);
		} catch (Exception e) {
			logger.error("获取无人机产品刷单记录异常,{}",orderNo,e);
			return null;
		}
	}

}
