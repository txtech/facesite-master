/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.nabobsite.modules.nabob.api.pool.TriggerApiService;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.service.simple.SimpleUserService;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.InstanceUtils;
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserWarehouse;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import com.nabobsite.modules.nabob.cms.user.dao.*;
import com.nabobsite.modules.nabob.cms.user.entity.*;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
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
public class UserAccountApiService extends SimpleUserService {

	/**
	 * @desc 增值收益提取账户
	 * @author nada
	 * @create 2021/5/11 10:33 下午ø
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean>  updateAccountClaim(String token) {
		try {
			UserAccount userAccount = this.getUserAccountByToken(token);
			if(userAccount == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			BigDecimal claimableMoney = userAccount.getClaimableMoney();
			if(ContactUtils.isLesserOrEqualZero(claimableMoney)){
				return ResultUtil.failed(I18nCode.CODE_10104);
			}
			long dbResult = userAccountDao.updateAccountClaimable();
			if(ContactUtils.dbResult(dbResult)){
				return ResultUtil.success(true);
			}
			return ResultUtil.failed(I18nCode.CODE_10004);
		} catch (Exception e) {
			logger.error("用户认领增值账户异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 修改充值订单到账户
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateAccountPayOrder(String userId,BigDecimal updateMoney,String uniqueId,String title) {
		try {
			synchronized (userId){
				UserAccountDetail userAccountDetail = InstanceUtils.initUserAccountDetail(userId,ContactUtils.USER_ACCOUNT_DETAIL_TYPE_1,uniqueId,title);
				userAccountDetail.setTotalMoney(updateMoney);
				userAccountDetail.setAvailableMoney(updateMoney);
				userAccountDetail.setRechargeMoney(updateMoney);
				Boolean isPrepareOk = this.prepareUpdateAccount(title,updateMoney,userAccountDetail);
				if(!isPrepareOk){
					logger.error("修改充值订单到账户失败,记录明细失败:{},{}",userId,updateMoney);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setUserId(userId);
				userAccount.setTotalMoney(updateMoney);
				userAccount.setAvailableMoney(updateMoney);
				userAccount.setRechargeMoney(updateMoney);
				long dbResult = userAccountDao.updateAccountMoney(userAccount);
				if(!ContactUtils.dbResult(dbResult)){
					logger.error("修改充值订单到账户失败,修改账户失败:{},{}",userId,updateMoney);
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("修改充值订单到账户异常,{}",userId,e);
			return false;
		}
	}

	/**
	 * @desc 修改注册奖励到账户
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateAccountRegister(String userId,BigDecimal updateMoney,String uniqueId,String title) {
		try {
			synchronized (userId){
				UserAccountDetail userAccountDetail = InstanceUtils.initUserAccountDetail(userId,ContactUtils.USER_ACCOUNT_DETAIL_TYPE_2,uniqueId,title);
				userAccountDetail.setTotalMoney(updateMoney);
				userAccountDetail.setAvailableMoney(updateMoney);
				userAccountDetail.setRechargeMoney(updateMoney);
				Boolean isPrepareOk = this.prepareUpdateAccount(title,updateMoney,userAccountDetail);
				if(!isPrepareOk){
					logger.error("修改注册奖励到账户失败,记录明细失败:{},{}",userId,updateMoney);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setUserId(userId);
				userAccount.setTotalMoney(updateMoney);
				userAccount.setAvailableMoney(updateMoney);
				userAccount.setRechargeMoney(updateMoney);
				long dbResult = userAccountDao.updateAccountMoney(userAccount);
				if(!ContactUtils.dbResult(dbResult)){
					logger.error("修改注册奖励到账户失败,修改账户失败:{},{}",userId,updateMoney);
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("修改注册奖励到账户异常,{}",userId,e);
			return false;
		}
	}

	/**
	 * @desc 修改分润收益到账户
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateAccountProfit(String userId,BigDecimal profitMoney,String uniqueId, String title) {
		try {
			if(ContactUtils.isOkUserId(userId)){
				return false;
			}
			synchronized (userId){
				UserAccountDetail userAccountDetail = InstanceUtils.initUserAccountDetail(userId,ContactUtils.USER_ACCOUNT_DETAIL_TYPE_50,uniqueId,title);
				userAccountDetail.setTotalMoney(profitMoney);
				userAccountDetail.setAvailableMoney(profitMoney);
				Boolean isPrepareOk = this.prepareUpdateAccount(title,profitMoney,userAccountDetail);
				if(!isPrepareOk){
					logger.error("修改分润收益到账户失败,记录明细失败:{},{}",userId,profitMoney);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setUserId(userId);
				userAccount.setTotalMoney(profitMoney);
				userAccount.setAvailableMoney(profitMoney);
				long dbResult = userAccountDao.updateAccountMoney(userAccount);
				if(!ContactUtils.dbResult(dbResult)){
					logger.error("修改分润收益到账户失败,修改账户失败:{},{}",userId,profitMoney);
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("修改分润收益到账户异常:{},{}",userId,e);
			return false;
		}
	}

	/**
	 * @desc 修改无人机佣金账户
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateAccountBotCommissionMoney(String userId,BigDecimal commissionMoney,String uniqueId, String title) {
		try {
			if(StringUtils.isEmpty(userId)){
				return false;
			}
			synchronized (userId){
				int type = ContactUtils.USER_ACCOUNT_DETAIL_TYPE_20;
				UserAccountDetail userAccountDetail = InstanceUtils.initUserAccountDetail(userId,type,uniqueId,title);
				userAccountDetail.setTotalMoney(commissionMoney);
				userAccountDetail.setAvailableMoney(commissionMoney);
				userAccountDetail.setCommissionMoney(commissionMoney);
				userAccountDetail.setIncrementMoney(ContactUtils.multiply(commissionMoney,new BigDecimal("0.5")));
				Boolean isPrepareOk = this.prepareUpdateAccount(title,commissionMoney,userAccountDetail);
				if(!isPrepareOk){
					logger.error("修改无人机佣金账户失败,记录明细失败:{},{}",userId,commissionMoney);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setUserId(userId);
				userAccount.setTotalMoney(commissionMoney);
				userAccount.setAvailableMoney(commissionMoney);
				userAccount.setCommissionMoney(commissionMoney);
				userAccount.setIncrementMoney(ContactUtils.multiply(commissionMoney,new BigDecimal("0.5")));
				long dbResult = userAccountDao.updateAccountMoney(userAccount);
				if(!ContactUtils.dbResult(dbResult)){
					logger.error("修改无人机佣金账户失败,修改账户失败:{},{}",userId,commissionMoney);
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("修改无人机佣金账户异常:{},{}",userId,e);
			return false;
		}
	}

	/**
	 * @desc 云仓库收益提取到账户
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateAccountWarehouseMoney(String userId,BigDecimal updateMoney,String uniqueId, String title) {
		try {
			if(StringUtils.isEmpty(userId)){
				return false;
			}
			synchronized (userId){
				UserAccountDetail userAccountDetail = InstanceUtils.initUserAccountDetail(userId,ContactUtils.USER_ACCOUNT_DETAIL_TYPE_30,uniqueId,title);
				userAccountDetail.setTotalMoney(updateMoney);
				userAccountDetail.setAvailableMoney(updateMoney);
				Boolean isPrepareOk = this.prepareUpdateAccount(title,updateMoney,userAccountDetail);
				if(!isPrepareOk){
					logger.error("修改云仓库账户失败,记录明细失败:{},{}",userId,updateMoney);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setUserId(userId);
				userAccount.setAvailableMoney(updateMoney);
				userAccount.setTotalMoney(updateMoney);
				long dbResult = userAccountDao.updateAccountMoney(userAccount);
				if(!ContactUtils.dbResult(dbResult)){
					logger.error("修改云仓库账户失败,修改账户失败:{},{}",userId,updateMoney);
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("增加佣金账户余额异常:{},{}",userId,e);
			return false;
		}
	}

	/**
	 * @desc 奖励金额提取到账户
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateAccountRewardMoney(String userId,BigDecimal updateMoney,String uniqueId, String title) {
		try {
			synchronized (userId){
				UserAccountDetail userAccountDetail = InstanceUtils.initUserAccountDetail(userId,ContactUtils.USER_ACCOUNT_DETAIL_TYPE_40,uniqueId,title);
				userAccountDetail.setAvailableMoney(updateMoney);
				userAccountDetail.setTotalMoney(updateMoney);
				Boolean isPrepareOk = this.prepareUpdateAccount(title,updateMoney,userAccountDetail);
				if(!isPrepareOk){
					logger.error("修改奖励账户失败,记录明细失败:{},{}",userId,updateMoney);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setUserId(userId);
				userAccount.setTotalMoney(updateMoney);
				userAccount.setAvailableMoney(updateMoney);
				long dbResult = userAccountDao.updateAccountMoney(userAccount);
				if(!ContactUtils.dbResult(dbResult)){
					logger.error("修改奖励账户失败,修改账户失败:{},{}",userId,updateMoney);
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("修改奖励账户异常:{}",userId,e);
			return false;
		}
	}


	/**
	 * @desc 修改账户验证
	 * @author nada
	 * @create 2021/5/14 10:56 上午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean prepareUpdateAccount(String title,BigDecimal updateMoney,UserAccountDetail userAccountDetail) {
		try {
			if(userAccountDetail == null){
				return false;
			}
			String userId = userAccountDetail.getUserId();
			if(ContactUtils.isOkUserId(userId)){
				return false;
			}
			if(ContactUtils.isEqualZero(updateMoney)){
				return false;
			}
			synchronized (userId){
				UserAccount oldUserAccount = this.getUserAccountByUserId(userId);
				if(oldUserAccount == null){
					return false;
				}
				String accountId = oldUserAccount.getId();
				userAccountDetail.setAccountId(accountId);
				long dbResult = userAccountDetailDao.insert(userAccountDetail);
				if(!ContactUtils.dbResult(dbResult)){
					logger.error("修改账户失败,记录明细失败:{},{},{}",userId,accountId,updateMoney);
					return false;
				}
				String detailId = userAccountDetail.getId();
				UserAccountBackup userAccountBackup = InstanceUtils.initUserAccountLog(detailId,title,oldUserAccount);
				dbResult = userAccountBackupDao.insert(userAccountBackup);
				if(!ContactUtils.dbResult(dbResult)){
					logger.error("修改账失败,记录日志失败:{},{},{}",userId,accountId,updateMoney);
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("修改账户验证异常:{}",e);
			return false;
		}
	}

	/**
	 * @desc 获取用户账户
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	public CommonResult<UserAccount> getUserAccountInfo(String token) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserAccount result = this.getUserAccountByUserId(userId);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("获取用户账户异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取收支总账记录
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	public CommonResult<List<UserAccountDetail>> getLedgerRecordList(String token,int ledgerType) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserAccountDetail userAccountDetail = new UserAccountDetail();
			if(ledgerType >0){
				userAccountDetail.setLedgerType(ledgerType);
			}
			userAccountDetail.setUserId(userId);
			List<UserAccountDetail> result = userAccountDetailDao.findList(userAccountDetail);
			return ResultUtil.success(result,true);
		} catch (Exception e) {
			logger.error("获取收支总账记录异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
}
