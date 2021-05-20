/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.StringUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.InstanceContact;
import com.nabobsite.modules.nabob.api.service.simple.SimpleProductService;
import com.nabobsite.modules.nabob.cms.product.entity.*;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品机器人Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class ProductApiService extends SimpleProductService {

	@Autowired
	protected UserAccountApiService userAccountApiService;

	/**
	 * @desc 云仓库收益提取到余额
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> doWarehouseToBalance(String token,UserProductWarehouseRecord userProductWarehouseRecord) {
		try {
			String warehouseId = userProductWarehouseRecord.getWarehouseId();
			BigDecimal money = userProductWarehouseRecord.getMoney();
			if(StringUtils.isAnyEmpty(warehouseId)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			if(CommonContact.isLesserOrEqualZero(money)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo== null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
			if(productWarehouse == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			BigDecimal limitPrice = productWarehouse.getLimitPrice();
			if(CommonContact.isLesser(money,limitPrice)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			synchronized (userId) {
				String title = "收益提取";
				UserProductWarehouseLog userProductWarehouseLog = InstanceContact.initUserProductWarehouseLog(userId,warehouseId,1,title,money);
				long dbResult = userProductWarehouseLogDao.insert(userProductWarehouseLog);
				if(!CommonContact.dbResult(dbResult)){
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				int type = CommonContact.WAREHOUSE_RECORD_TYPE_3;
				userProductWarehouseRecord.setUserId(userId);
				userProductWarehouseRecord.setType(type);
				dbResult = userProductWarehouseRecordDao.insert(userProductWarehouseRecord);
				if(!CommonContact.dbResult(dbResult)){
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				UserProductWarehouse oldUserProductWarehouse = this.getUserProductWarehouseByUserIdAndId(userId,warehouseId);
				if(oldUserProductWarehouse == null){
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				UserProductWarehouse userProductWarehouse = new UserProductWarehouse();
				userProductWarehouse.setId(oldUserProductWarehouse.getId());
				userProductWarehouse.setAsstesHeldMoney(money);
				dbResult = userProductWarehouseDao.update(userProductWarehouse);
				if(!CommonContact.dbResult(dbResult)){
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				Boolean isOk = userAccountApiService.updateAccountWarehouseMoney(userId,type,money,warehouseId,title);
				if(isOk){
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
	public CommonResult<Boolean> doWarehouseWithdraw(String token,UserProductWarehouseRecord userProductWarehouseRecord) {
		try {
			String warehouseId = userProductWarehouseRecord.getWarehouseId();
			BigDecimal money = userProductWarehouseRecord.getMoney();
			if(StringUtils.isAnyEmpty(warehouseId)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			if(CommonContact.isLesserOrEqualZero(money)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo== null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
			if(productWarehouse == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			BigDecimal limitPrice = productWarehouse.getLimitPrice();
			if(CommonContact.isLesser(money,limitPrice)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			synchronized (userId) {
				int type = CommonContact.WAREHOUSE_RECORD_TYPE_2;
				userProductWarehouseRecord.setUserId(userId);
				userProductWarehouseRecord.setMoney(money.negate());
				userProductWarehouseRecord.setType(type);
				long dbResult = userProductWarehouseRecordDao.insert(userProductWarehouseRecord);
				if(!CommonContact.dbResult(dbResult)){
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				UserProductWarehouse oldUserProductWarehouse = this.getUserProductWarehouseByUserIdAndId(userId,warehouseId);
				if(oldUserProductWarehouse == null){
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				UserProductWarehouse userProductWarehouse = new UserProductWarehouse();
				userProductWarehouse.setId(oldUserProductWarehouse.getId());
				userProductWarehouse.setAsstesHeldMoney(money.negate());
				dbResult = userProductWarehouseDao.update(userProductWarehouse);
				if(!CommonContact.dbResult(dbResult)){
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				String title = "产品撤资";
				Boolean isOk = userAccountApiService.updateAccountWarehouseMoney(userId,type,money,warehouseId,title);
				if(isOk){
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
			String warehouseId = userProductWarehouseRecord.getWarehouseId();
			BigDecimal money = userProductWarehouseRecord.getMoney();
			if(StringUtils.isAnyEmpty(warehouseId)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			if(CommonContact.isLesserOrEqualZero(money)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo== null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
			if(productWarehouse == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			BigDecimal limitPrice = productWarehouse.getLimitPrice();
			if(CommonContact.isLesser(money,limitPrice)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			synchronized (userId) {
				int type = CommonContact.WAREHOUSE_RECORD_TYPE_1;
				userProductWarehouseRecord.setUserId(userId);
				userProductWarehouseRecord.setType(type);
				long dbResult = userProductWarehouseRecordDao.insert(userProductWarehouseRecord);
				if(!CommonContact.dbResult(dbResult)){
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				UserProductWarehouse oldUserProductWarehouse = this.getUserProductWarehouseByUserIdAndId(userId,warehouseId);
				if(oldUserProductWarehouse == null){
					UserProductWarehouse userProductWarehouse = InstanceContact.initUserProductWarehouse(userId,warehouseId,money);
					dbResult = userProductWarehouseDao.insert(userProductWarehouse);
					if(!CommonContact.dbResult(dbResult)){
						return ResultUtil.failed(I18nCode.CODE_10004);
					}
				}else{
					UserProductWarehouse userProductWarehouse = new UserProductWarehouse();
					userProductWarehouse.setId(oldUserProductWarehouse.getId());
					userProductWarehouse.setAsstesHeldMoney(money);
					dbResult = userProductWarehouseDao.update(userProductWarehouse);
					if(!CommonContact.dbResult(dbResult)){
						return ResultUtil.failed(I18nCode.CODE_10004);
					}
				}
				String title = "产品定投";
				Boolean isOk = userAccountApiService.updateAccountWarehouseMoney(userId,type,money,warehouseId,title);
				if(isOk){
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
			String botId = userProductBotLog.getBotId();
			String orderNo = userProductBotLog.getOrderNo();
			BigDecimal incomeRate = userProductBotLog.getIncomeRate();
			BigDecimal incomeMoney = userProductBotLog.getIncomeMoney();
			BigDecimal orderAmount = userProductBotLog.getOrderAmount();
			if(StringUtils.isAnyEmpty(botId,orderNo)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			if(CommonContact.isLesserOrEqualZero(incomeRate) || CommonContact.isLesserOrEqualZero(incomeMoney)  || CommonContact.isLesserOrEqualZero(orderAmount) ){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}

			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo== null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			ProductBot productBot = this.getProductBotInfoById(botId);
			if(productBot == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			synchronized (userId) {
				int userLevel = userInfo.getLevel();
				int mustLevel = productBot.getLevel();
				int dailyNum = productBot.getDailyNum();
				BigDecimal mustPrice = productBot.getPrice();
				BigDecimal productBotPrice = productBot.getPrice();
				BigDecimal commissionRate = productBot.getCommissionRate();
				if(userLevel < mustLevel){
					return ResultUtil.failed(I18nCode.CODE_10101);
				}
				//佣金
				String title = CommonContact.USER_ACCOUNT_DETAIL_TITLE_4;
				BigDecimal commissionMoney = CommonContact.multiply(productBotPrice,commissionRate);
				if(!CommonContact.isEqual(commissionRate,incomeRate)){
					return ResultUtil.failed(I18nCode.CODE_10100);
				}
				if(!CommonContact.isEqual(commissionMoney,incomeMoney)){
					return ResultUtil.failed(I18nCode.CODE_10100);
				}
				if(!CommonContact.isEqual(mustPrice,orderAmount)){
					return ResultUtil.failed(I18nCode.CODE_10100);
				}
				UserProductBotLog checkUserProductBotLog =	this.getUserProductBotLogByOrderNo(orderNo);
				if(checkUserProductBotLog!=null){
					return ResultUtil.failed(I18nCode.CODE_10008);
				}
				userProductBotLog.setUserId(userId);
				long dbResult = userProductBotLogDao.insert(userProductBotLog);
				if(!CommonContact.dbResult(dbResult)){
					logger.error("无人机刷单记录失败:{},{},{},{}",userId,orderNo,userId,botId);
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				UserProductBot oldUserProductBot =	this.getUserProductBotByUserAndId(userId,botId);
				if(oldUserProductBot == null){
					UserProductBot userProductBot = InstanceContact.initUserProductBot(userProductBotLog);
					dbResult = userProductBotDao.insert(userProductBot);
					if(!CommonContact.dbResult(dbResult)){
						logger.error("用户无人机刷单汇总初始化失败:{},{},{},{}",userId,orderNo,userId,botId);
						return ResultUtil.failed(I18nCode.CODE_10004);
					}
				}else{
					int doTaskNum = oldUserProductBot.getTodayOrders();
					if(doTaskNum > dailyNum){
						return ResultUtil.failed(I18nCode.CODE_10102);
					}
					BigDecimal todayIncomeMoney = CommonContact.add(oldUserProductBot.getTodayIncomeMoney(),commissionMoney);
					UserProductBot userProductBot = new UserProductBot();
					userProductBot.setId(oldUserProductBot.getId());
					userProductBot.setTodayOrders(oldUserProductBot.getTodayOrders()+1);
					userProductBot.setTodayIncomeMoney(todayIncomeMoney);
					dbResult = userProductBotDao.update(userProductBot);
					if(!CommonContact.dbResult(dbResult)){
						logger.error("用户无人机刷单汇总更新失败:{},{},{},{}",userId,orderNo,userId,botId);
						return ResultUtil.failed(I18nCode.CODE_10004);
					}
				}
				Boolean isOk = userAccountApiService.updateAccountCommissionMoney(userId,commissionMoney,botId,title);
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
			UserProductBot result = this.getUserProductBotByUserAndId(userInfo.getId(),botId);
			if(result ==  null){
				result = new UserProductBot();
			}
			ProductBot productBot = this.getProductBotInfoById(botId) ;
			if(productBot!=null){
				result.setDailyNum(productBot.getDailyNum());
			}
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
			UserProductWarehouse result = this.getUserProductWarehouseByUserIdAndId(userInfo.getId(),warehouseId);
			if(result == null){
				result = new UserProductWarehouse();
			}
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
}
