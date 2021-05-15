/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.InstanceContact;
import com.nabobsite.modules.nabob.api.model.UserInfoModel;
import com.nabobsite.modules.nabob.cms.user.dao.*;
import com.nabobsite.modules.nabob.cms.user.entity.*;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.ResultUtil;
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
	private UserAccountDao userAccountDao;
	@Autowired
	private UserAccountLogDao userAccountLogDao;
	@Autowired
	private UserAccountDetailDao userAccountDetailDao;
	@Autowired
	private UserInfoApiService userInfoApiService;

	/**
	 * @desc 获取用户详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<UserAccount> getUserAccountInfo(String token) {
		try {
			UserInfo userInfo = userInfoApiService.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed("获取失败,获取帐号信息为空");
			}
			UserAccount userAccount = this.getUserAccountByUserId(userInfo.getId());
			return ResultUtil.success(userAccount);
		} catch (Exception e) {
			logger.error("Failed to get userinfo!",e);
			return ResultUtil.failed("Failed to get userinfo!");
		}
	}

	/**
	 * @desc 修改账户总余额
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateAccountBalance(String userId,BigDecimal updateMoney,String uniqueId,String title) {
		try {
			synchronized (userId){
				int type = CommonContact.USER_ACCOUNT_DETAIL_TYPE_1;
				UserAccountDetail userAccountDetail = InstanceContact.initUserAccountDetail(userId,type,uniqueId,title);
				userAccountDetail.setTotalMoney(updateMoney);
				Boolean isPrepareOk = this.prepareUpdateAccount(userId,title,updateMoney,userAccountDetail);
				if(!isPrepareOk){
					logger.error("修改账户总余额失败,记录明细失败:{},{}",userId,updateMoney);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setUserId(userId);
				userAccount.setTotalMoney(updateMoney);
				userAccount.setAvailableMoney(updateMoney);
				long dbResult = userAccountDao.updateAccountMoney(userAccount);
				if(!CommonContact.dbResult(dbResult)){
					logger.error("修改账户总余额失败,修改账户失败:{},{}",userId,updateMoney);
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("修改账户总余额异常",e);
			return false;
		}
	}

	/**
	 * @desc 修改佣金账户
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateAccountCommissionMoney(String userId,BigDecimal commissionMoney,BigDecimal incrementMoney,String uniqueId, String title) {
		try {
			if(StringUtils.isEmpty(userId)){
				logger.error("修改佣金账户失败,userId信息为空:{}",userId);
				return false;
			}
			synchronized (userId){
				int type = CommonContact.USER_ACCOUNT_DETAIL_TYPE_2;
				UserAccountDetail userAccountDetail = InstanceContact.initUserAccountDetail(userId,type,uniqueId,title);
				userAccountDetail.setCommissionMoney(commissionMoney);
				userAccountDetail.setIncrementMoney(incrementMoney);
				Boolean isPrepareOk = this.prepareUpdateAccount(userId,title,commissionMoney,userAccountDetail);
				if(!isPrepareOk){
					logger.error("修改佣金账户失败,记录明细失败:{},{}",userId,commissionMoney);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setUserId(userId);
				userAccount.setCommissionMoney(commissionMoney);
				userAccount.setIncrementMoney(incrementMoney);
				long dbResult = userAccountDao.updateAccountMoney(userAccount);
				if(!CommonContact.dbResult(dbResult)){
					logger.error("修改佣金账户失败,修改账户失败:{},{}",userId,commissionMoney);
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("增加佣金账户余额异常",e);
			return false;
		}
	}

	/**
	 * @desc 修改任务奖励账户
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateAccountRewardMoney(String userId,BigDecimal updateMoney,String uniqueId, String title) {
		try {
			synchronized (userId){
				int type = CommonContact.USER_ACCOUNT_DETAIL_TYPE_4;
				UserAccountDetail userAccountDetail = InstanceContact.initUserAccountDetail(userId,type,uniqueId,title);
				userAccountDetail.setRewardMoney(updateMoney);
				Boolean isPrepareOk = this.prepareUpdateAccount(userId,title,updateMoney,userAccountDetail);
				if(!isPrepareOk){
					logger.error("修改任务奖励账户失败,记录明细失败:{},{}",userId,updateMoney);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setUserId(userId);
				userAccount.setRewardMoney(updateMoney);
				long dbResult = userAccountDao.updateAccountMoney(userAccount);
				if(!CommonContact.dbResult(dbResult)){
					logger.error("修改任务奖励账户失败,修改账户失败:{},{}",userId,updateMoney);
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("增加任务账户余额异常",e);
			return false;
		}
	}

	/**
	 * @desc 修改账户余额验证
	 * @author nada
	 * @create 2021/5/14 10:56 上午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean prepareUpdateAccount(String userId,String title,BigDecimal updateMoney,UserAccountDetail userAccountDetail) {
		try {
			if(StringUtils.isEmpty(userId)){
				logger.error("修改账户余额验证失败,userId信息为空:{}",userId);
				return false;
			}
			if(CommonContact.isEqualZero(updateMoney)){
				logger.error("修改账户余额验证失败,金额为零:{}",userId);
				return false;
			}
			synchronized (userId){
				UserAccount oldUserAccount = this.getUserAccountByUserId(userId);
				if(oldUserAccount == null){
					logger.error("修改账户余额验证失败,账户信息为空:{}",userId);
					return false;
				}
				String accountId = oldUserAccount.getId();
				userAccountDetail.setAccountId(accountId);
				long dbResult = userAccountDetailDao.insert(userAccountDetail);
				if(!CommonContact.dbResult(dbResult)){
					logger.error("修改账户余额验证失败,记录明细失败:{},{},{}",userId,accountId,updateMoney);
					return false;
				}
				String detailId = userAccountDetail.getId();
				UserAccountLog userAccountLog = InstanceContact.initUserAccountLog(detailId,title,oldUserAccount);
				dbResult = userAccountLogDao.insert(userAccountLog);
				if(!CommonContact.dbResult(dbResult)){
					logger.error("修改账户余额验证失败,记录日志失败:{},{},{}",userId,accountId,updateMoney);
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("修改账户余额验证异常:{},{}",userId,updateMoney,e);
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
			logger.error("获取账户信息异常",e);
			return null;
		}
	}
}
