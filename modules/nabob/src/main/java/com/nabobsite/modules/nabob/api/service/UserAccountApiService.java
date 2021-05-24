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

	@Autowired
	private UserAccountBackupDao userAccountLogDao;
	@Autowired
	private UserAccountDetailDao userAccountDetailDao;
	@Autowired
	private TriggerApiService triggerApiService;

	/**
	 * @desc 用户认领增值账户
	 * @author nada
	 * @create 2021/5/11 10:33 下午ø
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> claim(String token) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}

			return ResultUtil.success(true);
		} catch (Exception e) {
			logger.error("用户认领增值账户异常",e);
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
			List<UserAccountDetail> userAccountDetailList = userAccountDetailDao.findList(userAccountDetail);
			return ResultUtil.success(userAccountDetailList,true);
		} catch (Exception e) {
			logger.error("获取收支总账记录异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
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
	 * @desc 修改账户总余额
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateAccountBalance(String userId,int type,BigDecimal updateMoney,String uniqueId,String title) {
		try {
			synchronized (userId){
				UserAccountDetail userAccountDetail = InstanceUtils.initUserAccountDetail(userId,type,uniqueId,title);
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
				if(type == ContactUtils.USER_ACCOUNT_DETAIL_TYPE_1 || type == ContactUtils.USER_ACCOUNT_DETAIL_TYPE_2){
					userAccount.setRechargeMoney(updateMoney);
				}
				long dbResult = userAccountDao.updateAccountMoney(userAccount);
				if(!ContactUtils.dbResult(dbResult)){
					logger.error("修改账户总余额失败,修改账户失败:{},{}",userId,updateMoney);
					return false;
				}
				triggerApiService.balanceTrigger(userId,type,updateMoney);
				return true;
			}
		} catch (Exception e) {
			logger.error("修改账户总余额异常,{}",userId,e);
			return false;
		}
	}

	/**
	 * @desc 修改佣金账户
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateAccountCommissionMoney(String userId,BigDecimal commissionMoney,String uniqueId, String title) {
		try {
			if(StringUtils.isEmpty(userId)){
				return false;
			}
			synchronized (userId){
				int type = ContactUtils.USER_ACCOUNT_DETAIL_TYPE_20;
				UserAccountDetail userAccountDetail = InstanceUtils.initUserAccountDetail(userId,type,uniqueId,title);
				userAccountDetail.setCommissionMoney(commissionMoney);
				Boolean isPrepareOk = this.prepareUpdateAccount(userId,title,commissionMoney,userAccountDetail);
				if(!isPrepareOk){
					logger.error("修改佣金账户失败,记录明细失败:{},{}",userId,commissionMoney);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setUserId(userId);
				userAccount.setTotalMoney(commissionMoney);
				userAccount.setCommissionMoney(commissionMoney);
				long dbResult = userAccountDao.updateAccountMoney(userAccount);
				if(!ContactUtils.dbResult(dbResult)){
					logger.error("修改佣金账户失败,修改账户失败:{},{}",userId,commissionMoney);
					return false;
				}
				triggerApiService.commissionTrigger(userId,type,commissionMoney);
				return true;
			}
		} catch (Exception e) {
			logger.error("增加佣金账户余额异常:{},{}",userId,e);
			return false;
		}
	}

	/**
	 * @desc 修改云仓库账户
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateAccountWarehouseMoney(String userId,int type,BigDecimal updateMoney,String uniqueId, String title) {
		try {
			if(StringUtils.isEmpty(userId)){
				return false;
			}
			synchronized (userId){
				int detailType = ContactUtils.USER_ACCOUNT_DETAIL_TYPE_30;
				UserAccountDetail userAccountDetail = InstanceUtils.initUserAccountDetail(userId,detailType,uniqueId,title);
				if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_1){
					userAccountDetail.setWarehouseMoney(updateMoney);
					userAccountDetail.setAvailableMoney(updateMoney.negate());
				}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_2){
					userAccountDetail.setWarehouseMoney(updateMoney.negate());
					userAccountDetail.setAvailableMoney(updateMoney);
				}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_3){
					userAccountDetail.setIncomeMoney(updateMoney);
					userAccountDetail.setAvailableMoney(updateMoney);
				}

				Boolean isPrepareOk = this.prepareUpdateAccount(userId,title,updateMoney,userAccountDetail);
				if(!isPrepareOk){
					logger.error("修改云仓库账户失败,记录明细失败:{},{}",userId,updateMoney);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setUserId(userId);
				if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_1){
					userAccount.setWarehouseMoney(updateMoney);
					userAccount.setAvailableMoney(updateMoney.negate());
				}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_2){
					userAccount.setWarehouseMoney(updateMoney.negate());
					userAccount.setAvailableMoney(updateMoney);
				}else if(type == ContactUtils.WAREHOUSE_RECORD_TYPE_3){
					userAccount.setIncomeMoney(updateMoney);
					userAccount.setAvailableMoney(updateMoney);
				}
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
	 * @desc 修改奖励账户
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateAccountRewardMoney(String userId,BigDecimal updateMoney,String uniqueId, String title) {
		try {
			synchronized (userId){
				int type = ContactUtils.USER_ACCOUNT_DETAIL_TYPE_40;
				UserAccountDetail userAccountDetail = InstanceUtils.initUserAccountDetail(userId,type,uniqueId,title);
				userAccountDetail.setRewardMoney(updateMoney);
				Boolean isPrepareOk = this.prepareUpdateAccount(userId,title,updateMoney,userAccountDetail);
				if(!isPrepareOk){
					logger.error("修改奖励账户失败,记录明细失败:{},{}",userId,updateMoney);
					return false;
				}
				UserAccount userAccount = new UserAccount();
				userAccount.setUserId(userId);
				userAccount.setRewardMoney(updateMoney);
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
	 * @desc 修改账户余额验证
	 * @author nada
	 * @create 2021/5/14 10:56 上午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean prepareUpdateAccount(String userId,String title,BigDecimal updateMoney,UserAccountDetail userAccountDetail) {
		try {
			if(StringUtils.isEmpty(userId)){
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
				UserAccountBackup userAccountLog = InstanceUtils.initUserAccountLog(detailId,title,oldUserAccount);
				dbResult = userAccountLogDao.insert(userAccountLog);
				if(!ContactUtils.dbResult(dbResult)){
					logger.error("修改账失败,记录日志失败:{},{},{}",userId,accountId,updateMoney);
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("修改账户余额验证异常:{}",userId,e);
			return false;
		}
	}
}
