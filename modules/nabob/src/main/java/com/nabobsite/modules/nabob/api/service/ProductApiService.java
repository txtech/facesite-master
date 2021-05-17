/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.LogicStaticContact;
import com.nabobsite.modules.nabob.api.model.BotTaskReqModel;
import com.nabobsite.modules.nabob.api.model.WarehouseTaskReqModel;
import com.nabobsite.modules.nabob.cms.product.dao.*;
import com.nabobsite.modules.nabob.cms.product.entity.*;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.basic.BasicMenuItemUI;
import java.math.BigDecimal;
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
	 * @desc 用户云仓库个人收入记录
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<UserProductWarehouseLog> getUserWarehousePersonalIncomeList(String token) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			UserProductWarehouseLog userProductWarehouseLog = new UserProductWarehouseLog();
			userProductWarehouseLog.setUserId(userInfo.getId());
			userProductWarehouseLog.setType(CommonContact.WAREHOUSE_TYPE_1);
			UserProductWarehouseLog result = userProductWarehouseLogDao.getByEntity(userProductWarehouseLog);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("用户云仓库产品异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
	/**
	 * @desc 用户云仓库团队收入记录
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<UserProductWarehouseLog> getUserWarehouseTeamIncomeList(String token) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			UserProductWarehouseLog userProductWarehouseLog = new UserProductWarehouseLog();
			userProductWarehouseLog.setUserId(userInfo.getId());
			userProductWarehouseLog.setType(CommonContact.WAREHOUSE_TYPE_2);
			UserProductWarehouseLog result = userProductWarehouseLogDao.getByEntity(userProductWarehouseLog);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("用户云仓库产品异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
	/**
	 * @desc 用户云仓库操纵记录
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<UserProductWarehouseRecord> getUserWarehouseOperationList(String token) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			UserProductWarehouseRecord userProductWarehouseRecord = new UserProductWarehouseRecord();
			userProductWarehouseRecord.setUserId(userInfo.getId());
			UserProductWarehouseRecord result = userProductWarehouseRecordDao.getByEntity(userProductWarehouseRecord);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("用户云仓库产品异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}


	/**
	 * @desc 云仓库收益提取到余额
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> doWarehouseToBalance(String token, WarehouseTaskReqModel warehouseTaskReqModel) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo== null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			String warehouseId = warehouseTaskReqModel.getWarehouseId();
			BigDecimal amount = warehouseTaskReqModel.getAmount();
			ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
			if(productWarehouse == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			BigDecimal limitPrice = productWarehouse.getLimitPrice();
			if(CommonContact.isLesser(amount,limitPrice)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			synchronized (userId){
				UserProductWarehouse userProductWarehouse = new UserProductWarehouse();
				userProductWarehouse.setUserId(userId);
				userProductWarehouse.setWarehouseId(warehouseId);
				userProductWarehouse.setAsstesHeldMoney(amount);
				long dbResult = userProductWarehouseDao.insert(userProductWarehouse);
				if(CommonContact.dbResult(dbResult)){
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
	public CommonResult<Boolean> doWarehouseWithdraw(String token, WarehouseTaskReqModel warehouseTaskReqModel) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo== null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			String warehouseId = warehouseTaskReqModel.getWarehouseId();
			BigDecimal amount = warehouseTaskReqModel.getAmount();
			ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
			if(productWarehouse == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			BigDecimal limitPrice = productWarehouse.getLimitPrice();
			if(CommonContact.isLesser(amount,limitPrice)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			synchronized (userId){
				UserProductWarehouse userProductWarehouse = new UserProductWarehouse();
				userProductWarehouse.setUserId(userId);
				userProductWarehouse.setWarehouseId(warehouseId);
				userProductWarehouse.setAsstesHeldMoney(amount);
				long dbResult = userProductWarehouseDao.insert(userProductWarehouse);
				if(CommonContact.dbResult(dbResult)){
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
	public CommonResult<Boolean> doWarehouseDeposit(String token, WarehouseTaskReqModel warehouseTaskReqModel) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo== null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			String warehouseId = warehouseTaskReqModel.getWarehouseId();
			BigDecimal amount = warehouseTaskReqModel.getAmount();
			ProductWarehouse productWarehouse = this.getProductWarehouseById(warehouseId);
			if(productWarehouse == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			BigDecimal limitPrice = productWarehouse.getLimitPrice();
			if(CommonContact.isLesser(amount,limitPrice)){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			synchronized (userId) {
				UserProductWarehouse userProductWarehouse = new UserProductWarehouse();
				userProductWarehouse.setUserId(userId);
				userProductWarehouse.setWarehouseId(warehouseId);
				userProductWarehouse.setAsstesHeldMoney(amount);
				long dbResult = userProductWarehouseDao.insert(userProductWarehouse);
				if(CommonContact.dbResult(dbResult)){
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
	public CommonResult<Boolean> doBotTask(String token, BotTaskReqModel botTaskReqModel) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo== null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			String botId = botTaskReqModel.getBotId();
			String orderNo = botTaskReqModel.getOrderNo();
			ProductBot productBot = this.getProductBotInfoById(botId);
			if(productBot == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			synchronized (userId) {
				int userLevel = userInfo.getLevel();
				int mustLevel = productBot.getLevel();
				BigDecimal productBotPrice = productBot.getPrice();
				if(userLevel < mustLevel){
					return ResultUtil.failed("任务失败,当前等级不符合要求");
				}
				UserProductBotLog userProductBotLog = new UserProductBotLog();
				BeanUtils.copyProperties(botTaskReqModel, userProductBotLog);
				userProductBotLog.setUserId(userId);
				long dbResult = userProductBotLogDao.insert(userProductBotLog);
				if(CommonContact.dbResult(dbResult)){
					return ResultUtil.failed("任务失败,保存记录失败");
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
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<List<ProductBot>> getProductBotList(ProductBot productBot) {
		try {
			List<ProductBot> list = productBotDao.findList(productBot);
			return ResultUtil.success(list);
		} catch (Exception e) {
			logger.error("获取无人机产品列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取云仓库产品列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<List<ProductWarehouse>> getProductWarehouseList(ProductWarehouse productWarehouse) {
		try {
			List<ProductWarehouse> list = productWarehouseDao.findList(productWarehouse);
			return ResultUtil.success(list);
		} catch (Exception e) {
			logger.error("获取云仓库产品列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取无人机产品详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<ProductBot> getProductBotInfo(String token,ProductBot productBot) {
		try {
			ProductBot productBotInfo = productBotDao.getByEntity(productBot);
			return ResultUtil.success(productBotInfo);
		} catch (Exception e) {
			logger.error("获取无人机产品详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取云仓库产品详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<ProductWarehouse> getProductWarehouseInfo(String token,ProductWarehouse productWarehouse) {
		try {
			ProductWarehouse productWarehouseInfo = productWarehouseDao.getByEntity(productWarehouse);
			return ResultUtil.success(productWarehouseInfo);
		} catch (Exception e) {
			logger.error("获取云仓库产品详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 用户无人机产品
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<UserProductBot> getUserBotInfo(String token,String botId) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			UserProductBot userProductBot = new UserProductBot();
			userProductBot.setUserId(userInfo.getId());
			userProductBot.setId(botId);
			UserProductBot result = userProductBotDao.getByEntity(userProductBot);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("用户无人机产品异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 用户云仓库产品
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<UserProductWarehouse> getUserWarehouseInfo(String token, String warehouseId) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10006);
			}
			UserProductWarehouse userProductWarehouse = new UserProductWarehouse();
			userProductWarehouse.setUserId(userInfo.getId());
			userProductWarehouse.setWarehouseId(warehouseId);
			UserProductWarehouse result = userProductWarehouseDao.getByEntity(userProductWarehouse);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("用户云仓库产品异常",e);
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
