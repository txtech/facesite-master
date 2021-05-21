/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.lang.StringUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.InstanceContact;
import com.nabobsite.modules.nabob.api.service.simple.SimpleProductService;
import com.nabobsite.modules.nabob.cms.product.entity.*;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountWarehouse;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
			String userId  = this.getUserIdByToken(token);
			if(!CommonContact.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
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
					return ResultUtil.success(true);
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
			String userId  = this.getUserIdByToken(token);
			if(!CommonContact.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
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
					return ResultUtil.success(true);
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
			String userId  = this.getUserIdByToken(token);
			if(!CommonContact.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
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
					return ResultUtil.success(true);
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
					return ResultUtil.success(true);
				}
				return ResultUtil.failed(I18nCode.CODE_10004);
			}
		} catch (Exception e) {
			logger.error("无人机产品做任务异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}



	/**
	 * @desc 获取无人机产品列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	public CommonResult<List<ProductBot>> getProductBotList(ProductBot productBot) {
		try {
			List<ProductBot> result = productBotDao.findList(productBot);
			return ResultUtil.success(result);
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
	public CommonResult<ProductBot> getProductBotInfo(String token, ProductBot productBot) {
		try {
			ProductBot result = productBotDao.getByEntity(productBot);
			return ResultUtil.success(result);
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
	public CommonResult<UserProductBot> getUserBotInfo(String token,String botId) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!CommonContact.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserProductBot result = this.getUserProductBotByUserAndId(userId,botId);
			if(result ==  null){
				result = new UserProductBot();
			}
			ProductBot productBot = this.getProductBotInfoById(botId) ;
			if(productBot!=null){
				result.setDailyNum(productBot.getDailyNum());
			}
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("用户无人机产品详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}



	/**
	 * @desc 获取云仓库产品列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	public CommonResult<List<ProductWarehouse>> getProductWarehouseList(String token,ProductWarehouse productWarehouse) {
		try {
			String userId = "";
			if(StringUtils.isNotEmpty(token)){
				userId  = this.getUserIdByToken(token);
			}
			List<ProductWarehouse> newList = new ArrayList<>();
			List<ProductWarehouse> list = productWarehouseDao.findList(productWarehouse);
			for (ProductWarehouse entity : list) {
				String warehouseId = entity.getId();
				BigDecimal asstesHeldMoney = new BigDecimal("0");
				if(CommonContact.isOkUserId(userId)){
					UserProductWarehouse userProductWarehouse = this.getUserProductWarehouseByUserIdAndId(userId,warehouseId);
					if(userProductWarehouse != null && !CommonContact.isLesserOrEqualZero(userProductWarehouse.getAsstesHeldMoney())){
						asstesHeldMoney = userProductWarehouse.getAsstesHeldMoney();
					}
				}
				entity.setAsstesHeldMoney(asstesHeldMoney);
				newList.add(entity);
			}
			return ResultUtil.success(newList);
		} catch (Exception e) {
			logger.error("获取云仓库产品列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
	/**
	 * @desc 云仓库产品详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	public CommonResult<ProductWarehouse> getProductWarehouseInfo(String token,String warehouseId) {
		try {
			ProductWarehouse productWarehouse = new ProductWarehouse();
			productWarehouse.setId(warehouseId);
			ProductWarehouse productWarehouseInfo = productWarehouseDao.getByEntity(productWarehouse);
			return ResultUtil.success(productWarehouseInfo);
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
	public CommonResult<UserProductWarehouse> getUserWarehouseInfo(String token, String warehouseId) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!CommonContact.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserProductWarehouse result = this.getUserProductWarehouseByUserIdAndId(userId,warehouseId);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("用户云仓库产品详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
	/**
	 * @desc 用户云仓库产品账户
	 * @author nada
	 * @create 2021/5/20 9:45 下午
	*/
	public CommonResult<UserAccountWarehouse> getUserAccountWarehouseInfo(String token) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!CommonContact.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserAccountWarehouse result = this.getUserAccountWarehouseByUserId(userId);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("用户云仓库产品账户异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}



	/**
	 * @desc 用户云仓库个人收入记录列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	public CommonResult<List<UserProductWarehouseLog>> getUserWarehouseIncomeList(String token,int type,int productType) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!CommonContact.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserProductWarehouseLog userProductWarehouseLog = new UserProductWarehouseLog();
			userProductWarehouseLog.setUserId(userId);
			userProductWarehouseLog.setType(type);
			userProductWarehouseLog.setProductType(productType);
			List<UserProductWarehouseLog> result = userProductWarehouseLogDao.findList(userProductWarehouseLog);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("用户云仓库收入记录列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
	/**
	 * @desc 用户云仓库操纵记录列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	public CommonResult<List<UserProductWarehouseRecord>> getUserWarehouseOperationList(String token) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!CommonContact.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserProductWarehouseRecord userProductWarehouseRecord = new UserProductWarehouseRecord();
			userProductWarehouseRecord.setUserId(userId);
			List<UserProductWarehouseRecord> result = userProductWarehouseRecordDao.findList(userProductWarehouseRecord);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("用户云仓库操纵记录列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
}
