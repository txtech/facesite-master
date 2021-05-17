/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.LogicStaticContact;
import com.nabobsite.modules.nabob.api.model.req.BotTaskReqModel;
import com.nabobsite.modules.nabob.cms.product.dao.ProductBotDao;
import com.nabobsite.modules.nabob.cms.product.dao.ProductWarehouseDao;
import com.nabobsite.modules.nabob.cms.product.dao.UserProductBotDao;
import com.nabobsite.modules.nabob.cms.product.dao.UserProductWarehouseDao;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductWarehouse;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
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
	private UserProductWarehouseDao userProductWarehouseDao;

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
				return ResultUtil.failed(I18nCode.CODE_105);
			}
			String userId = userInfo.getId();
			String botId = botTaskReqModel.getBotId();
			String orderNo = botTaskReqModel.getOrderNo();
			ProductBot productBot = this.getProductBotInfoById(botId);
			if(productBot == null){
				return ResultUtil.failed(I18nCode.CODE_106);
			}
			synchronized (userId) {
				int userLevel = userInfo.getLevel();
				int mustLevel = productBot.getLevel();
				BigDecimal productBotPrice = productBot.getPrice();
				if(userLevel < mustLevel){
					return ResultUtil.failed("任务失败,当前等级不符合要求");
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
				return ResultUtil.failed(I18nCode.CODE_104);
			}
		} catch (Exception e) {
			logger.error("无人机产品做任务异常",e);
			return ResultUtil.failed(I18nCode.CODE_104);
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
			return ResultUtil.failed(I18nCode.CODE_104);
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
			return ResultUtil.failed(I18nCode.CODE_104);
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
			return ResultUtil.failed(I18nCode.CODE_104);
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
			return ResultUtil.failed(I18nCode.CODE_104);
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
				return ResultUtil.failed(I18nCode.CODE_106);
			}
			UserProductBot userProductBot = new UserProductBot();
			userProductBot.setUserId(userInfo.getId());
			userProductBot.setId(botId);
			UserProductBot result = userProductBotDao.getByEntity(userProductBot);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("用户无人机产品异常",e);
			return ResultUtil.failed(I18nCode.CODE_104);
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
				return ResultUtil.failed(I18nCode.CODE_106);
			}
			UserProductWarehouse userProductWarehouse = new UserProductWarehouse();
			userProductWarehouse.setUserId(userInfo.getId());
			userProductWarehouse.setWarehouseId(warehouseId);
			UserProductWarehouse result = userProductWarehouseDao.getByEntity(userProductWarehouse);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("用户云仓库产品异常",e);
			return ResultUtil.failed(I18nCode.CODE_104);
		}
	}
}
