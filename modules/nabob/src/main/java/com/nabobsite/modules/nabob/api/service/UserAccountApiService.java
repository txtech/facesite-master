/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.common.TriggerApiService;
import com.nabobsite.modules.nabob.api.entity.CommonStaticContact;
import com.nabobsite.modules.nabob.api.entity.DbInstanceContact;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountRecordDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserRewardRecordDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountRecord;
import com.nabobsite.modules.nabob.cms.user.entity.UserRewardRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 用户账户Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class UserAccountApiService extends CrudService<UserAccountDao, UserAccount> {
	@Autowired
	private UserRewardRecordDao userRewardRecordDao;
	@Autowired
	private UserAccountDao userAccountDao;
	@Autowired
	private UserAccountRecordDao userAccountRecordDao;

	/**
	 * @desc 记录奖励明细
	 * @author nada
	 * @create 2021/5/13 3:09 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean addRewardRecord(String userId,int type,BigDecimal rewardMoney,String title,String remarks) {
		try {
			UserRewardRecord userRewardRecord = DbInstanceContact.initUserRewardRecord(userId,type,rewardMoney,rewardMoney,title,remarks);
			long dbResult = userRewardRecordDao.insert(userRewardRecord);
			if(!CommonStaticContact.dbResult(dbResult)){
				logger.error("记录奖励明细失败,记录奖励明细失败:{},{}",userId,rewardMoney);
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("记录奖励明细异常",e);
			return false;
		}
	}
	/**
	 * @desc 增加任务账户余额
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean addAccountTaskBalance(String userId,int type,BigDecimal actualMoney,String title,String remarks) {
		try {
			if(StringUtils.isEmpty(userId)){
				logger.error("增加任务账户余额失败,userId信息为空:{}",userId);
				return false;
			}
			synchronized (userId){
				UserAccount oldUserAccount = this.getUserAccountByUserId(userId);
				if(oldUserAccount == null){
					logger.error("增加任务账户余额失败,账户信息为空:{}",userId);
					return false;
				}
				String accountId = oldUserAccount.getId();
				BigDecimal totalTaskMoney = oldUserAccount.getTaskMoney();
				UserAccountRecord userAccountRecord = DbInstanceContact.initUserAccountRecord(userId,accountId,type,actualMoney,totalTaskMoney,title,remarks);
				long dbResult = userAccountRecordDao.insert(userAccountRecord);
				if(!CommonStaticContact.dbResult(dbResult)){
					logger.error("增加任务账户余额失败,记录明细失败:{},{}",userId,accountId);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setId(accountId);
				userAccount.setUserId(userId);
				userAccount.setTaskMoney(actualMoney);
				dbResult = userAccountDao.updateAccountTaskMoney(userAccount);
				if(CommonStaticContact.dbResult(dbResult)){
					logger.info("增加任务账户余额成功:{},{}",userId,accountId);
					return true;
				}
				logger.info("增加任务账户余额失败,修改账户失败:{},{}",userId,accountId);
				return false;
			}
		} catch (Exception e) {
			logger.error("增加任务账户余额异常",e);
			return false;
		}
	}

	/**
	 * @desc 增加账户总余额
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean addAccountBalance(String userId,int type,BigDecimal actualMoney,String title,String remarks) {
		try {
			if(StringUtils.isEmpty(userId)){
				logger.error("增加账户总余额失败,userId信息为空:{}",userId);
				return false;
			}
			synchronized (userId){
				UserAccount oldUserAccount = this.getUserAccountByUserId(userId);
				if(oldUserAccount == null){
					logger.error("增加账户总余额失败,账户信息为空:{}",userId);
					return false;
				}
				String accountId = oldUserAccount.getId();
				BigDecimal totalMoney = oldUserAccount.getTotalMoney();
				UserAccountRecord userAccountRecord = DbInstanceContact.initUserAccountRecord(userId,accountId,type,actualMoney,totalMoney,title,remarks);
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
					logger.info("增加账户总余额成功:{},{}",userId,accountId);
					return true;
				}
				logger.info("增加账户总余额失败,修改账户失败:{},{}",userId,accountId);
				return false;
			}
		} catch (Exception e) {
			logger.error("增加账户总余额异常",e);
			return false;
		}
	}

	/**
	 * @desc 获取账户信息
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
