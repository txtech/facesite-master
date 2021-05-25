/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.shiro.realms.Da;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.InstanceUtils;
import com.nabobsite.modules.nabob.api.service.simple.SimpleProductService;
import com.nabobsite.modules.nabob.cms.product.entity.*;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountWarehouseDao;
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
			List<ProductUserWarehouse> productUserWarehouseList = this.getProductUserWarehouseListByUserId(userId);
			if(productUserWarehouseList == null || productUserWarehouseList.isEmpty()){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			synchronized (userId) {
				for (ProductUserWarehouse oldUserWarehouse : productUserWarehouseList) {
					String warehouseId = oldUserWarehouse.getWarehouseId();
					ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
					if(productWarehouse == null){
						continue;
					}
					Boolean isOk = this.doWithdrawToTotalAmount(userId,warehouseId,ContactUtils.WAREHOUSE_RECORD_TYPE_3,oldUserWarehouse,productWarehouse);
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
			synchronized (userId) {
				ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
				if(productWarehouse == null){
					return ResultUtil.failed(I18nCode.CODE_10006);
				}
				BigDecimal limitPrice = productWarehouse.getLimitPrice();
				if(ContactUtils.isLesser(money,limitPrice)){
					return ResultUtil.failed(I18nCode.CODE_10100);
				}
				Boolean isOk = this.doWarehouseDepositAndWithdraw(userId,warehouseId,money,ContactUtils.WAREHOUSE_RECORD_TYPE_2);
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
			synchronized (userId) {
				ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
				if(productWarehouse == null){
					return ResultUtil.failed(I18nCode.CODE_10006);
				}
				BigDecimal limitPrice = productWarehouse.getLimitPrice();
				if(ContactUtils.isLesser(money,limitPrice)){
					return ResultUtil.failed(I18nCode.CODE_10100);
				}

				ProductUserWarehouse productUserWarehouse = this.getProductUserWarehouseByUserIdAndId(userId,warehouseId);
				if(productUserWarehouse == null){
					productUserWarehouse = InstanceUtils.initProductUserWarehouse(userId,warehouseId,new BigDecimal("0"));
					long dbResult = productUserWarehouseDao.insert(productUserWarehouse);
					if(!ContactUtils.dbResult(dbResult)){
						return ResultUtil.failed(I18nCode.CODE_10004);
					}
				}

				Boolean isOk = this.doWarehouseDepositAndWithdraw(userId,warehouseId,money,ContactUtils.WAREHOUSE_RECORD_TYPE_1);
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
	 * 云仓库收益提现
	 * 产品类型：1 随存随取 2:定投
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean doWithdrawToTotalAmount(String userId,String warehouseId,int type,ProductUserWarehouse oldUserWarehouse,ProductWarehouse productWarehouse) {
		try {
			int productType = productWarehouse.getType();
			if(productType != ContactUtils.WAREHOUSE_PRODUCT_TYPE_1){
				return false;
			}
			long psersonUpdateTime = oldUserWarehouse.getPsersonUpdateTime().getTime();
			long diffTime = (new Date().getTime() - psersonUpdateTime)/1000;
			BigDecimal warehouseMoney = oldUserWarehouse.getAsstesHeldMoney();
			BigDecimal dailyInterestRate = productWarehouse.getDailyInterestRate();
			BigDecimal incomeMoney = dailyInterestRate.multiply(new BigDecimal(diffTime)).multiply(warehouseMoney);
			incomeMoney = incomeMoney.divide(ContactUtils.HOUR,5, BigDecimal.ROUND_HALF_UP);
			incomeMoney = incomeMoney.divide(ContactUtils.MINUTE,5, BigDecimal.ROUND_HALF_UP);
			incomeMoney = incomeMoney.divide(ContactUtils.SECOND,5, BigDecimal.ROUND_HALF_UP);

			if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_3){
				String title = "收益提取";
				int logType = ContactUtils.WAREHOUSE_TYPE_1;
				ProductUserWarehouseLog productUserWarehouseLog = InstanceUtils.initProductUserWarehouseLog(userId,warehouseId,productType,logType,title,incomeMoney);
				long dbResult = productUserWarehouseLogDao.insert(productUserWarehouseLog);
				if(!ContactUtils.dbResult(dbResult)){
					return false;
				}
			}

			ProductUserWarehouse updateWarehouse = new ProductUserWarehouse();
			updateWarehouse.setUserId(userId);
			updateWarehouse.setWarehouseId(warehouseId);
			updateWarehouse.setPersonalAccumulativeIncomeMoney(incomeMoney); // 累计个人收益
			updateWarehouse.setAccumulativeIncomeMoney(incomeMoney); //累计总收益
			updateWarehouse.setPsersonUpdateTime(new Date()); //个人动态收益更新时间
			if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_1){
				updateWarehouse.setIncomeMoney(incomeMoney);//当前收益
				updateWarehouse.setPersonalIncomeMoney(incomeMoney); //个人收益
			}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_2){
				updateWarehouse.setIncomeMoney(incomeMoney);//当前收益
				updateWarehouse.setPersonalIncomeMoney(incomeMoney); //个人收益
			}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_3){
				if(!ContactUtils.isLesserOrEqualZero(oldUserWarehouse.getIncomeMoney())){
					updateWarehouse.setIncomeMoney(oldUserWarehouse.getIncomeMoney().negate());//当前收益
					updateWarehouse.setPersonalIncomeMoney(oldUserWarehouse.getIncomeMoney().negate()); //个人收益
				}
			}
			long dbResult = productUserWarehouseDao.updateUserWarehousee(updateWarehouse);
			if(!ContactUtils.dbResult(dbResult)){
				return false;
			}

			UserAccountWarehouse userAccountWarehouse = new UserAccountWarehouse();
			userAccountWarehouse.setUserId(userId);
			userAccountWarehouse.setPersonalAccumulativeIncomeMoney(incomeMoney); //个人累计收益
			userAccountWarehouse.setAccumulativeIncomeMoney(incomeMoney); //累计总收益
			userAccountWarehouse.setPsersonUpdateTime(new Date()); //收益更新时间
			if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_1){
				userAccountWarehouse.setIncomeMoney(incomeMoney);//当前收益
				userAccountWarehouse.setPersonalIncomeMoney(incomeMoney); //个人收益
			}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_2){
				userAccountWarehouse.setIncomeMoney(incomeMoney);//当前收益
				userAccountWarehouse.setPersonalIncomeMoney(incomeMoney); //个人收益
			}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_3){
				if(!ContactUtils.isLesserOrEqualZero(oldUserWarehouse.getIncomeMoney())){
					incomeMoney = incomeMoney.add(oldUserWarehouse.getIncomeMoney());
					userAccountWarehouse.setIncomeMoney(oldUserWarehouse.getIncomeMoney().negate());//当前收益
					userAccountWarehouse.setPersonalIncomeMoney(oldUserWarehouse.getIncomeMoney().negate()); //个人收益
				}
			}
			dbResult = userAccountWarehouseDao.updateAccountWarehouse(userAccountWarehouse);
			if(!ContactUtils.dbResult(dbResult)){
				return false;
			}

			if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_3){
				String title = ContactUtils.USER_ACCOUNT_DETAIL_TITLE_5;
				return userAccountApiService.updateAccountWarehouseMoney(userId,incomeMoney,warehouseId,title);
			}
			return true;
		} catch (Exception e) {
			logger.error("云仓库收益提现异常",e);
			return false;
		}
	}

	/**
	 * 云仓库账户操作
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean doWarehouseDepositAndWithdraw(String userId,String warehouseId,BigDecimal money,int type) {
		try {
			//保存操作记录 类型 1:存款 2:撤资
			ProductUserWarehouseRecord productUserWarehouseRecord = new ProductUserWarehouseRecord();
			productUserWarehouseRecord.setUserId(userId);
			productUserWarehouseRecord.setType(type);
			if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_1){
				productUserWarehouseRecord.setMoney(money);
			}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_2){
				productUserWarehouseRecord.setMoney(money.negate());
			}
			long dbResult = productUserWarehouseRecordDao.insert(productUserWarehouseRecord);
			if(!ContactUtils.dbResult(dbResult)){
				return false;
			}

			//更新用户云仓库 类型 1:存款 2:撤资
			ProductUserWarehouse updateWarehouse = new ProductUserWarehouse();
			updateWarehouse.setUserId(userId);
			updateWarehouse.setWarehouseId(warehouseId);
			if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_1){
				updateWarehouse.setAsstesHeldMoney(money); //持有理财资产
			}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_2){
				updateWarehouse.setAsstesHeldMoney(money.negate()); //持有理财资产
			}
			dbResult = productUserWarehouseDao.updateUserWarehousee(updateWarehouse);
			if(!ContactUtils.dbResult(dbResult)){
				return false;
			}

			//更新仓库账户 类型 1:存款 2:撤资
			UserAccountWarehouse userAccountWarehouse = new UserAccountWarehouse();
			userAccountWarehouse.setUserId(userId);
			if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_1){
				userAccountWarehouse.setAsstesHeldMoney(money); //仓库持有资产
			}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_2){
				userAccountWarehouse.setAsstesHeldMoney(money.negate());//仓库持有资产
			}
			dbResult = userAccountWarehouseDao.updateAccountWarehouse(userAccountWarehouse);
			if(!ContactUtils.dbResult(dbResult)){
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("云仓库账户操作异常",e);
			return false;
		}
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
			if(StringUtils.isAnyEmpty(botId,orderNo)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo== null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			ProductBot productBot = this.getProductBotById(botId);
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
				ProductUserBotLog checkUserProductBotLog =	this.getProductUserBotLogByOrderNo(orderNo);
				if(checkUserProductBotLog!=null){
					return ResultUtil.failed(I18nCode.CODE_10008);
				}
				userProductBotLog.setUserId(userId);
				long dbResult = userProductBotLogDao.insert(userProductBotLog);
				if(!ContactUtils.dbResult(dbResult)){
					logger.error("无人机刷单记录失败:{},{},{},{}",userId,orderNo,userId,botId);
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				ProductUserBot oldUserProductBot =	this.getProductUserBotByUserAndId(userId,botId);
				if(oldUserProductBot == null){
					ProductUserBot userProductBot = InstanceUtils.initProductUserBot(userProductBotLog);
					dbResult = productUserBotDao.insert(userProductBot);
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
					dbResult = productUserBotDao.update(userProductBot);
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
			ProductUserBot result = this.getProductUserBotByUserAndId(userId,botId);
			if(result ==  null){
				result = new ProductUserBot();
			}
			ProductBot productBot = this.getProductBotById(botId) ;
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
					ProductUserWarehouse userProductWarehouse = this.getProductUserWarehouseByUserIdAndId(userId,warehouseId);
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
			ProductUserWarehouse result = this.getProductUserWarehouseByUserIdAndId(userId,warehouseId);
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
			List<ProductUserWarehouseLog> result = productUserWarehouseLogDao.findList(userProductWarehouseLog);
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
			List<ProductUserWarehouseRecord> result = productUserWarehouseRecordDao.findList(userProductWarehouseRecord);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("用户云仓库操纵记录列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
}
