/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.entity.CommonStaticContact;
import com.nabobsite.modules.nabob.api.entity.DbInstanceEntity;
import com.nabobsite.modules.nabob.api.entity.RedisPrefixContant;
import com.nabobsite.modules.nabob.cms.product.dao.ProductBotDao;
import com.nabobsite.modules.nabob.cms.product.dao.ProductWarehouseDao;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountRecordDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountRecord;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户账户Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class UserAccountApiService extends CrudService<UserAccountDao, UserAccount> {

	@Autowired
	private UserAccountDao userAccountDao;
	@Autowired
	private UserAccountRecordDao userAccountRecordDao;

	/**
	 * @desc 根据ID增加账户并记录日志
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean addAccount(String userId, BigDecimal actualMoney) {
		try {
			if(StringUtils.isEmpty(userId)){
				logger.error("增加账户失败,userId信息为空:{}",userId);
				return false;
			}
			synchronized (userId){
				UserAccount oldUserAccount = this.getUserAccountByUserId(userId);
				if(oldUserAccount == null){
					logger.error("增加账户失败,账户信息为空:{}",userId);
					return false;
				}
				String accountId = oldUserAccount.getId();
				BigDecimal totalMoney = oldUserAccount.getTotalMoney();
				String remark = "充值:"+actualMoney;
				UserAccountRecord userAccountRecord = DbInstanceEntity.initUserAccountRecord(userId,accountId,actualMoney,totalMoney,remark);
				long dbResult = userAccountRecordDao.insert(userAccountRecord);
				if(!CommonStaticContact.dbResult(dbResult)){
					logger.error("增加账户失败,记录明细失败:{},{}",userId,accountId);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setId(accountId);
				userAccount.setUserId(userId);
				userAccount.setTotalMoney(actualMoney);
				dbResult = userAccountDao.updateAccountTotalMoney(userAccount);
				if(CommonStaticContact.dbResult(dbResult)){
					logger.info("增加账户成功,记录明细成功:{},{}",userId,accountId);
					return true;
				}
				logger.info("增加账户失败,修改账户失败:{},{}",userId,accountId);
				return false;
			}
		} catch (Exception e) {
			logger.error("根据ID增加账户异常",e);
			return false;
		}
	}

	/**
	 * @desc 根据ID减去账户并记录日志
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean subtractAccount(String userId, BigDecimal actualMoney) {
		try {
			//金额转为负数
			actualMoney = actualMoney.negate();
			synchronized (userId){
				UserAccount oldUserAccount = this.getUserAccountByUserId(userId);
				if(oldUserAccount == null){
					logger.error("减去账户失败,账户信息为空:{}",userId);
					return false;
				}
				String accountId = oldUserAccount.getId();
				BigDecimal totalMoney = oldUserAccount.getTotalMoney();
				String remark = "减少:"+actualMoney;
				UserAccountRecord userAccountRecord = DbInstanceEntity.initUserAccountRecord(userId,accountId,actualMoney,totalMoney,remark);
				long dbResult = userAccountRecordDao.insert(userAccountRecord);
				if(!CommonStaticContact.dbResult(dbResult)){
					logger.error("减去账户失败,记录明细失败:{},{}",userId,accountId);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setId(accountId);
				userAccount.setUserId(userId);
				userAccount.setTotalMoney(actualMoney);
				dbResult = userAccountDao.updateAccountTotalMoney(userAccount);
				if(CommonStaticContact.dbResult(dbResult)){
					logger.info("减去账户成功,记录明细成功:{},{}",userId,accountId);
					return true;
				}
				logger.info("减去账户失败,减去账户失败:{},{}",userId,accountId);
				return false;
			}
		} catch (Exception e) {
			logger.error("根据ID减去账户异常",e);
			return false;
		}
	}

	/**
	 * @desc 根据账号ID获取账户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = true, rollbackFor = Exception.class)
	public UserAccount getUserAccountByUserId(String userId) {
		try {
			if(StringUtils.isEmpty(userId)){
				return null;
			}
			UserAccount userAccount = new UserAccount();
			userAccount.setUserId(userId);
			return userAccountDao.getByEntity(userAccount);
		} catch (Exception e) {
			logger.error("根据账号ID获取账户信息异常",e);
			return null;
		}
	}
}
