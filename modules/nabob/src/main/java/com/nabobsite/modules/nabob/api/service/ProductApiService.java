/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.LogicStaticContact;
import com.nabobsite.modules.nabob.cms.product.dao.*;
import com.nabobsite.modules.nabob.cms.product.entity.*;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 产品机器人Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class ProductApiService extends BaseUserService {

	@Autowired
	private ProductBotDao productBotDao;
	@Autowired
	private ProductWarehouseDao productWarehouseDao;
	@Autowired
	private UserAccountApiService userAccountApiService;
	@Autowired
	private UserProductBotDao userProductBotDao;
	@Autowired
	private UserProductBotLogDao userProductBotLogDao;
	@Autowired
	private UserProductWarehouseDao userProductWarehouseDao;
	@Autowired
	private UserProductWarehouseLogDao userProductWarehouseLogDao;
	@Autowired
	private UserProductWarehouseRecordDao userProductWarehouseRecordDao;

	/**
	 * @desc 云仓库收益提取到余额
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> doWarehouseToBalance(String token,UserProductWarehouseRecord userProductWarehouseRecord) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo== null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			String warehouseId = userProductWarehouseRecord.getWarehouseId();
			BigDecimal amount = userProductWarehouseRecord.getMoney();
			ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
			if(productWarehouse == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			BigDecimal limitPrice = productWarehouse.getLimitPrice();
			if(CommonContact.isLesser(amount,limitPrice)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			synchronized (userId){
				UserProductWarehouse userProductWarehouse = new UserProductWarehouse();
				userProductWarehouse.setUserId(userId);
				userProductWarehouse.setWarehouseId(warehouseId);
				userProductWarehouse.setAsstesHeldMoney(amount);
				long dbResult = userProductWarehouseDao.insert(userProductWarehouse);
				if(CommonContact.dbResult(dbResult)){
					return ResultUtil.successToBoolean(true);
				}
				return ResultUtil.failed(I18nCode.CODE_10004);
			}
		} catch (Exception e) {
			logger.error("云仓库收益提取到余额异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 云仓库产品定投撤资
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> doWarehouseWithdraw(String token, UserProductWarehouseRecord userProductWarehouseRecord) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo== null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			String warehouseId = userProductWarehouseRecord.getWarehouseId();
			BigDecimal amount = userProductWarehouseRecord.getMoney();
			ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
			if(productWarehouse == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			BigDecimal limitPrice = productWarehouse.getLimitPrice();
			if(CommonContact.isLesser(amount,limitPrice)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			synchronized (userId){
				UserProductWarehouse userProductWarehouse = new UserProductWarehouse();
				userProductWarehouse.setUserId(userId);
				userProductWarehouse.setWarehouseId(warehouseId);
				userProductWarehouse.setAsstesHeldMoney(amount);
				userProductWarehouse.setTeamUpdateTime(new Date());
				userProductWarehouse.setPsersonUpdateTime(new Date());
				long dbResult = userProductWarehouseDao.insert(userProductWarehouse);
				if(CommonContact.dbResult(dbResult)){
					return ResultUtil.successToBoolean(true);
				}
				return ResultUtil.failed(I18nCode.CODE_10004);
			}
		} catch (Exception e) {
			logger.error("云仓库产品任务异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 云仓库产品存款
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> doWarehouseDeposit(String token,UserProductWarehouseRecord userProductWarehouseRecord) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo== null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			String warehouseId = userProductWarehouseRecord.getWarehouseId();
			BigDecimal amount = userProductWarehouseRecord.getMoney();
			ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
			if(productWarehouse == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			BigDecimal limitPrice = productWarehouse.getLimitPrice();
			if(CommonContact.isLesser(amount,limitPrice)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			synchronized (userId) {
				UserProductWarehouse userProductWarehouse = new UserProductWarehouse();
				userProductWarehouse.setUserId(userId);
				userProductWarehouse.setWarehouseId(warehouseId);
				userProductWarehouse.setAsstesHeldMoney(amount);
				userProductWarehouse.setTeamUpdateTime(new Date());
				userProductWarehouse.setPsersonUpdateTime(new Date());
				long dbResult = userProductWarehouseDao.insert(userProductWarehouse);
				if(CommonContact.dbResult(dbResult)){
					return ResultUtil.successToBoolean(true);
				}
				return ResultUtil.failed(I18nCode.CODE_10004);
			}
		} catch (Exception e) {
			logger.error("云仓库产品任务异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 无人机产品做任务
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> doBotTask(String token,UserProductBotLog userProductBotLog) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo== null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			String botId = userProductBotLog.getId();
			ProductBot productBot = this.getProductBotInfoById(botId);
			if(productBot == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			synchronized (userId) {
				int userLevel = userInfo.getLevel();
				int mustLevel = productBot.getLevel();
				BigDecimal productBotPrice = productBot.getPrice();
				if(userLevel < mustLevel){
					return ResultUtil.failed(I18nCode.CODE_10101);
				}
				userProductBotLog.setUserId(userId);
				long dbResult = userProductBotLogDao.insert(userProductBotLog);
				if(CommonContact.dbResult(dbResult)){
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				String title = CommonContact.USER_ACCOUNT_DETAIL_TITLE_4;
				//增值比例
				BigDecimal commissionOtherRate = LogicStaticContact.PRODUCT_COMMISSION_OTHER_RATE;
				//产品佣金比例
				BigDecimal commissionRate = LogicStaticContact.LEVEL_BALANCE_COMMISSION_RATE.get(mustLevel);
				//佣金
				BigDecimal commissionMoney = CommonContact.multiply(productBotPrice,commissionRate);
				//增值佣金
				BigDecimal incrementMoney = CommonContact.multiply(commissionMoney,commissionOtherRate);
				Boolean isOk = userAccountApiService.updateAccountCommissionMoney(userId,commissionMoney,incrementMoney,botId,title);
				if(isOk){
					return ResultUtil.successToBoolean(true);
				}
				return ResultUtil.failed(I18nCode.CODE_10004);
			}
		} catch (Exception e) {
			logger.error("无人机产品做任务异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}


	/**
	 * @desc 获取云仓库产品列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONArray> getProductWarehouseList(ProductWarehouse productWarehouse) {
		try {
			List<ProductWarehouse> list = productWarehouseDao.findList(productWarehouse);
			JSONArray result = new JSONArray();
			for (ProductWarehouse entity : list) {
				result.add(CommonContact.toJSONObject(entity));
			}
			return ResultUtil.successToJsonArray(result);
		} catch (Exception e) {
			logger.error("获取云仓库产品列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取无人机产品列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONArray> getProductBotList(ProductBot productBot) {
		try {
			List<ProductBot> list = productBotDao.findList(productBot);
			JSONArray result = new JSONArray();
			for (ProductBot entity : list) {
				result.add(CommonContact.toJSONObject(entity));
			}
			return ResultUtil.successToJsonArray(result);
		} catch (Exception e) {
			logger.error("获取无人机产品列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
	/**
	 * @desc 无人机产品详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONObject> getProductBotInfo(String token, ProductBot productBot) {
		try {
			ProductBot productBotInfo = productBotDao.getByEntity(productBot);
			return ResultUtil.successToJson(productBotInfo);
		} catch (Exception e) {
			logger.error("无人机产品详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
	/**
	 * @desc 用户无人机产品详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONObject> getUserBotInfo(String token,String botId) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserProductBot userProductBot = new UserProductBot();
			userProductBot.setUserId(userInfo.getId());
			userProductBot.setId(botId);
			UserProductBot result = userProductBotDao.getByEntity(userProductBot);
			return ResultUtil.successToJson(result);
		} catch (Exception e) {
			logger.error("用户无人机产品详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}



	/**
	 * @desc 云仓库产品详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONObject> getProductWarehouseInfo(String token,String warehouseId) {
		try {
			ProductWarehouse productWarehouse = new ProductWarehouse();
			productWarehouse.setId(warehouseId);
			ProductWarehouse productWarehouseInfo = productWarehouseDao.getByEntity(productWarehouse);
			return ResultUtil.successToJson(productWarehouseInfo);
		} catch (Exception e) {
			logger.error("云仓库产品详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
	/**
	 * @desc 用户云仓库产品详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONObject> getUserWarehouseInfo(String token, String warehouseId) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserProductWarehouse userProductWarehouse = new UserProductWarehouse();
			userProductWarehouse.setUserId(userInfo.getId());
			userProductWarehouse.setWarehouseId(warehouseId);
			UserProductWarehouse result = userProductWarehouseDao.getByEntity(userProductWarehouse);
			return ResultUtil.successToJson(result);
		} catch (Exception e) {
			logger.error("用户云仓库产品详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}




	/**
	 * @desc 用户云仓库个人收入记录列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONArray> getUserWarehousePersonalIncomeList(String token) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserProductWarehouseLog userProductWarehouseLog = new UserProductWarehouseLog();
			userProductWarehouseLog.setUserId(userInfo.getId());
			userProductWarehouseLog.setType(CommonContact.WAREHOUSE_TYPE_1);
			List<UserProductWarehouseLog> userProductWarehouseLogList = userProductWarehouseLogDao.findList(userProductWarehouseLog);
			JSONArray result = new JSONArray();
			for (UserProductWarehouseLog entity : userProductWarehouseLogList) {
				result.add(CommonContact.toJSONObject(entity));
			}
			return ResultUtil.successToJsonArray(result);
		} catch (Exception e) {
			logger.error("用户云仓库个人收入记录列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
	/**
	 * @desc 用户云仓库团队收入记录列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONArray> getUserWarehouseTeamIncomeList(String token) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserProductWarehouseLog userProductWarehouseLog = new UserProductWarehouseLog();
			userProductWarehouseLog.setUserId(userInfo.getId());
			userProductWarehouseLog.setType(CommonContact.WAREHOUSE_TYPE_2);
			List<UserProductWarehouseLog> userProductWarehouseLogList = userProductWarehouseLogDao.findList(userProductWarehouseLog);
			JSONArray result = new JSONArray();
			for (UserProductWarehouseLog entity : userProductWarehouseLogList) {
				result.add(CommonContact.toJSONObject(entity));
			}
			return ResultUtil.successToJsonArray(result);
		} catch (Exception e) {
			logger.error("用户云仓库团队收入记录列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
	/**
	 * @desc 用户云仓库操纵记录列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONArray> getUserWarehouseOperationList(String token) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserProductWarehouseRecord userProductWarehouseRecord = new UserProductWarehouseRecord();
			userProductWarehouseRecord.setUserId(userInfo.getId());
			List<UserProductWarehouseRecord> userProductWarehouseRecordList = userProductWarehouseRecordDao.findList(userProductWarehouseRecord);
			JSONArray result = new JSONArray();
			for (UserProductWarehouseRecord entity : userProductWarehouseRecordList) {
				result.add(CommonContact.toJSONObject(entity));
			}
			return ResultUtil.successToJsonArray(result);
		} catch (Exception e) {
			logger.error("用户云仓库操纵记录列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}


	/**
	 * @desc 获取云仓库产品详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
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
	@Transactional (readOnly = false, rollbackFor = Exception.class)
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
}
