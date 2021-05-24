/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.lang.StringUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.InstanceUtils;
import com.nabobsite.modules.nabob.api.service.simple.SimpleProductService;
import com.nabobsite.modules.nabob.cms.product.entity.*;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountWarehouse;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
	public CommonResult<Boolean> doWarehouseToBalance(String token) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserAccount oldUserAccount = this.getUserAccountByUserId(userId);
			if(oldUserAccount == null){
				return ResultUtil.failed(I18nCode.CODE_10011);
			}
			if(ContactUtils.isLesserOrEqualZero(oldUserAccount.getWarehouseMoney())){
				return ResultUtil.failed(I18nCode.CODE_10104);
			}
			List<ProductUserWarehouse> productUserWarehouseList = this.getProductUserWarehouseListByUserId(userId);
			if(productUserWarehouseList == null || productUserWarehouseList.isEmpty()){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			synchronized (userId) {
				for (ProductUserWarehouse oldUserWarehouse : productUserWarehouseList) {
					String id = oldUserWarehouse.getId();
					String warehouseId = oldUserWarehouse.getWarehouseId();
					ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
					if(productWarehouse == null){
						continue;
					}
					//产品类型：1 随存随取 2:定投
					int productType = productWarehouse.getType();
					if(productType != ContactUtils.WAREHOUSE_PRODUCT_TYPE_1){
						continue;
					}
					long psersonUpdateTime = oldUserWarehouse.getPsersonUpdateTime().getTime();
					long diffTime = (new Date().getTime() - psersonUpdateTime)/1000;
					BigDecimal warehouseMoney = oldUserAccount.getWarehouseMoney();
					BigDecimal dailyInterestRate = productWarehouse.getDailyInterestRate();
					BigDecimal incomeMoney = dailyInterestRate.multiply(new BigDecimal(diffTime)).multiply(warehouseMoney);
					incomeMoney = incomeMoney.divide(ContactUtils.HOUR,5, BigDecimal.ROUND_HALF_UP);
					incomeMoney = incomeMoney.divide(ContactUtils.MINUTE,5, BigDecimal.ROUND_HALF_UP);
					incomeMoney = incomeMoney.divide(ContactUtils.SECOND,5, BigDecimal.ROUND_HALF_UP);

					//保存云仓库记录日志 类型 1:个人 2:团队
					String title = "收益提取";
					int logType = ContactUtils.WAREHOUSE_TYPE_1;
					ProductUserWarehouseLog productUserWarehouseLog = InstanceUtils.initUserProductWarehouseLog(userId,warehouseId,productType,logType,title,incomeMoney);
					long dbResult = userProductWarehouseLogDao.insert(productUserWarehouseLog);
					if(!ContactUtils.dbResult(dbResult)){
						return ResultUtil.failed(I18nCode.CODE_10004);
					}

					//保存操作记录 类型 1:存款 2:撤资 3:提现
					int type =  ContactUtils.WAREHOUSE_RECORD_TYPE_1;
					Boolean isOk = this.doWarehouseRecord(userId,warehouseId,incomeMoney,type,title,oldUserWarehouse);
					if(isOk){
						return ResultUtil.success(true);
					}
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
	public CommonResult<Boolean> doWarehouseWithdraw(String token,ProductUserWarehouseRecord userProductWarehouseRecord) {
		try {
			String warehouseId = userProductWarehouseRecord.getWarehouseId();
			BigDecimal money = userProductWarehouseRecord.getMoney();
			if(StringUtils.isAnyEmpty(warehouseId)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			if(ContactUtils.isLesserOrEqualZero(money)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
			if(productWarehouse == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			BigDecimal limitPrice = productWarehouse.getLimitPrice();
			if(ContactUtils.isLesser(money,limitPrice)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			synchronized (userId) {
				UserAccount oldUserAccount = this.getUserAccountByUserId(userId);
				if(oldUserAccount == null){
					return ResultUtil.failed(I18nCode.CODE_10011);
				}
				if(ContactUtils.isLesser(oldUserAccount.getWarehouseMoney(),money)){
					return ResultUtil.failed(I18nCode.CODE_10104);
				}
				//保存操作记录 类型 1:存款 2:撤资 3:提现
				String title = "产品撤资";
				int type = ContactUtils.WAREHOUSE_RECORD_TYPE_2;
				ProductUserWarehouse oldUserProductWarehouse = this.getUserProductWarehouseByUserIdAndId(userId,warehouseId);
				Boolean isOk = this.doWarehouseRecord(userId,warehouseId,money,type,title,oldUserProductWarehouse);
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
	public CommonResult<Boolean> doWarehouseDeposit(String token,ProductUserWarehouseRecord productUserWarehouseRecord) {
		try {
			String warehouseId = productUserWarehouseRecord.getWarehouseId();
			BigDecimal money = productUserWarehouseRecord.getMoney();
			if(StringUtils.isAnyEmpty(warehouseId)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			if(ContactUtils.isLesserOrEqualZero(money)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
			if(productWarehouse == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			BigDecimal limitPrice = productWarehouse.getLimitPrice();
			if(ContactUtils.isLesser(money,limitPrice)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			synchronized (userId) {
				UserAccount oldUserAccount = this.getUserAccountByUserId(userId);
				if(oldUserAccount == null){
					return ResultUtil.failed(I18nCode.CODE_10011);
				}
				BigDecimal diffMoney = ContactUtils.subtract(oldUserAccount.getTotalMoney(),oldUserAccount.getWarehouseMoney());
				if(ContactUtils.isLesser(diffMoney,money)){
					return ResultUtil.failed(I18nCode.CODE_10104);
				}

				//保存操作记录 类型 1:存款 2:撤资 3:提现
				String title = "产品定投";
				int type =  ContactUtils.WAREHOUSE_RECORD_TYPE_1;
				ProductUserWarehouse oldUserProductWarehouse = this.getUserProductWarehouseByUserIdAndId(userId,warehouseId);
				Boolean isOk = this.doWarehouseRecord(userId,warehouseId,money,type,title,oldUserProductWarehouse);
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
	 * 云仓空操作记录
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean doWarehouseRecord(String userId,String warehouseId,BigDecimal money,int type,String title,ProductUserWarehouse oldUserProductWarehouse) {
		if(oldUserProductWarehouse == null){
			return false;
		}

		//保存操作记录 类型 1:存款 2:撤资 3:提现
		ProductUserWarehouseRecord userProductWarehouseRecord = new ProductUserWarehouseRecord();
		userProductWarehouseRecord.setUserId(userId);
		userProductWarehouseRecord.setType(type);
		if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_1){
			userProductWarehouseRecord.setMoney(money);
		}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_2){
			userProductWarehouseRecord.setMoney(money.negate());
		}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_3){
			userProductWarehouseRecord.setMoney(money);
		}
		long dbResult = userProductWarehouseRecordDao.insert(userProductWarehouseRecord);
		if(!ContactUtils.dbResult(dbResult)){
			return false;
		}

		//更新仓库账户
		ProductUserWarehouse userProductWarehouse = new ProductUserWarehouse();
		userProductWarehouse.setId(oldUserProductWarehouse.getId());
		if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_1){
			userProductWarehouse.setAsstesHeldMoney(money);
		}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_2){
			userProductWarehouse.setAsstesHeldMoney(money.negate());
		}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_3){
			userProductWarehouse.setAsstesHeldMoney(oldUserProductWarehouse.getAsstesHeldMoney().add(money));
			userProductWarehouse.setPsersonUpdateTime(new Date());
		}
		dbResult = userProductWarehouseDao.update(userProductWarehouse);
		if(!ContactUtils.dbResult(dbResult)){
			return false;
		}

		//更新总账户
		return userAccountApiService.updateAccountWarehouseMoney(userId,type,money,warehouseId,title);
	}

	/**
	 * @desc 无人机产品做任务
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> doBotTask(String token,ProductUserBotLog userProductBotLog) {
		try {
			String botId = userProductBotLog.getBotId();
			String orderNo = userProductBotLog.getOrderNo();
			BigDecimal incomeRate = userProductBotLog.getIncomeRate();
			BigDecimal incomeMoney = userProductBotLog.getIncomeMoney();
			BigDecimal orderAmount = userProductBotLog.getOrderAmount();
			if(StringUtils.isAnyEmpty(botId,orderNo)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			if(ContactUtils.isLesserOrEqualZero(incomeRate) || ContactUtils.isLesserOrEqualZero(incomeMoney)  || ContactUtils.isLesserOrEqualZero(orderAmount) ){
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
				String title = ContactUtils.USER_ACCOUNT_DETAIL_TITLE_4;
				BigDecimal commissionMoney = ContactUtils.multiply(productBotPrice,commissionRate);
				if(!ContactUtils.isEqual(commissionRate,incomeRate)){
					return ResultUtil.failed(I18nCode.CODE_10100);
				}
				if(!ContactUtils.isEqual(commissionMoney,incomeMoney)){
					return ResultUtil.failed(I18nCode.CODE_10100);
				}
				if(!ContactUtils.isEqual(mustPrice,orderAmount)){
					return ResultUtil.failed(I18nCode.CODE_10100);
				}
				ProductUserBotLog checkUserProductBotLog =	this.getUserProductBotLogByOrderNo(orderNo);
				if(checkUserProductBotLog!=null){
					return ResultUtil.failed(I18nCode.CODE_10008);
				}
				userProductBotLog.setUserId(userId);
				long dbResult = userProductBotLogDao.insert(userProductBotLog);
				if(!ContactUtils.dbResult(dbResult)){
					logger.error("无人机刷单记录失败:{},{},{},{}",userId,orderNo,userId,botId);
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				ProductUserBot oldUserProductBot =	this.getUserProductBotByUserAndId(userId,botId);
				if(oldUserProductBot == null){
					ProductUserBot userProductBot = InstanceUtils.initUserProductBot(userProductBotLog);
					dbResult = userProductBotDao.insert(userProductBot);
					if(!ContactUtils.dbResult(dbResult)){
						logger.error("用户无人机刷单汇总初始化失败:{},{},{},{}",userId,orderNo,userId,botId);
						return ResultUtil.failed(I18nCode.CODE_10004);
					}
				}else{
					int doTaskNum = oldUserProductBot.getTodayOrders();
					if(doTaskNum > dailyNum){
						return ResultUtil.failed(I18nCode.CODE_10102);
					}
					BigDecimal todayIncomeMoney = ContactUtils.add(oldUserProductBot.getTodayIncomeMoney(),commissionMoney);
					ProductUserBot userProductBot = new ProductUserBot();
					userProductBot.setId(oldUserProductBot.getId());
					userProductBot.setTodayOrders(oldUserProductBot.getTodayOrders()+1);
					userProductBot.setTodayIncomeMoney(todayIncomeMoney);
					dbResult = userProductBotDao.update(userProductBot);
					if(!ContactUtils.dbResult(dbResult)){
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
	public CommonResult<ProductUserBot> getUserBotInfo(String token,String botId) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			ProductUserBot result = this.getUserProductBotByUserAndId(userId,botId);
			if(result ==  null){
				result = new ProductUserBot();
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
				if(ContactUtils.isOkUserId(userId)){
					ProductUserWarehouse userProductWarehouse = this.getUserProductWarehouseByUserIdAndId(userId,warehouseId);
					if(userProductWarehouse != null && !ContactUtils.isLesserOrEqualZero(userProductWarehouse.getAsstesHeldMoney())){
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
	public CommonResult<ProductWarehouse> getProductWarehouseInfo(String warehouseId) {
		try {
			ProductWarehouse productWarehouseInfo = this.getProductWarehouseById(warehouseId);
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
	public CommonResult<ProductUserWarehouse> getUserWarehouseInfo(String token, String warehouseId) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			ProductUserWarehouse result = this.getUserProductWarehouseByUserIdAndId(userId,warehouseId);
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
			if(!ContactUtils.isOkUserId(userId)){
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
	public CommonResult<List<ProductUserWarehouseLog>> getUserWarehouseIncomeList(String token,int type,int productType) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			ProductUserWarehouseLog userProductWarehouseLog = new ProductUserWarehouseLog();
			userProductWarehouseLog.setUserId(userId);
			userProductWarehouseLog.setType(type);
			userProductWarehouseLog.setProductType(productType);
			List<ProductUserWarehouseLog> result = userProductWarehouseLogDao.findList(userProductWarehouseLog);
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
	public CommonResult<List<ProductUserWarehouseRecord>> getUserWarehouseOperationList(String token) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			ProductUserWarehouseRecord userProductWarehouseRecord = new ProductUserWarehouseRecord();
			userProductWarehouseRecord.setUserId(userId);
			List<ProductUserWarehouseRecord> result = userProductWarehouseRecordDao.findList(userProductWarehouseRecord);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("用户云仓库操纵记录列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
}
