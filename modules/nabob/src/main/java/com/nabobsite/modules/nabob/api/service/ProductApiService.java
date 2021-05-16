/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.LogicStaticContact;
import com.nabobsite.modules.nabob.api.model.req.BotTaskReqModel;
import com.nabobsite.modules.nabob.cms.product.dao.ProductBotDao;
import com.nabobsite.modules.nabob.cms.product.dao.ProductWarehouseDao;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
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
				return ResultUtil.failed("获取失败,用户信息为空");
			}
			String userId = userInfo.getId();
			String botId = botTaskReqModel.getBotId();
			String orderNo = botTaskReqModel.getOrderNo();
			ProductBot productBot = this.getProductBotInfoById(botId);
			if(productBot == null){
				return ResultUtil.failed("任务失败,产品不存在");
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
				return ResultUtil.failed("Failed to do the task!");
			}
		} catch (Exception e) {
			logger.error("Failed to do the task!",e);
			return ResultUtil.failed("Failed to do the task!");
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
			logger.error("Failed to get product bot list!",e);
			return ResultUtil.failed("Failed to get product bot list!");
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
			logger.error("Failed to get product warehouse list!",e);
			return ResultUtil.failed("Failed to get product warehouse list!");
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
			logger.error("Failed to get product bot info!",e);
			return ResultUtil.failed("Failed to get product bot info!");
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
			logger.error("Failed to get product bot info!",e);
			return null;
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
			logger.error("Failed to get product warehouse info!",e);
			return ResultUtil.failed("Failed to get product warehouse info!");
		}
	}
}
