/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service.core;

import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.InstanceUtils;
import com.nabobsite.modules.nabob.api.service.UserAccountApiService;
import com.nabobsite.modules.nabob.api.service.simple.SimpleUserService;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfoMembership;
import com.nabobsite.modules.nabob.cms.user.entity.UserProfitDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 逻辑Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class LogicService extends SimpleUserService {

	@Autowired
	private UserAccountApiService userAccountApiService;

	/**
	 * @desc 会员分润
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean memberProfit(UserInfo userInfo,UserAccount userAccount,int  type,BigDecimal updateMoney) {
		String userId = userInfo.getId();
		String parent1UserId = userInfo.getParent1UserId();
		String parent2UserId = userInfo.getParent2UserId();
		String parent3UserId = userInfo.getParent3UserId();

		synchronized (userId) {
			if(ContactUtils.isOkUserId(parent1UserId)){
				UserInfo parent1UserInfo =  this.getUserInfoByUserId(parent1UserId);
				int level = parent1UserInfo.getLevel();
				UserInfoMembership userInfoMembership = this.getMemberShipByLevel(level);
				BigDecimal commissionRate1 = ContactUtils.parsePercentage(userInfoMembership.getCommissionRate1());
				if(!ContactUtils.isLesserOrEqualZero(commissionRate1)){
					BigDecimal prifitMoney = updateMoney.multiply(commissionRate1);
					UserProfitDetail userProfitDetail = InstanceUtils.initUserProfitDetail(userId,parent1UserId,parent2UserId,parent3UserId,commissionRate1,prifitMoney,updateMoney);
					long dbResult = userProfitDetailDao.insert(userProfitDetail);
					if(ContactUtils.dbResult(dbResult)){
						String profitId = userProfitDetail.getId();
						String title = "刷单佣金分成";
						userAccountApiService.updateAccountCommissionMoney(userId,prifitMoney,profitId,title);
					}
				}
			}
			if(ContactUtils.isOkUserId(parent2UserId)){
				UserInfo parent2UserInfo =  this.getUserInfoByUserId(parent2UserId);
				int level = parent2UserInfo.getLevel();
				UserInfoMembership userInfoMembership = this.getMemberShipByLevel(level);
				BigDecimal commissionRate2 = ContactUtils.parsePercentage(userInfoMembership.getCommissionRate2());
				if(!ContactUtils.isLesserOrEqualZero(commissionRate2)){
					BigDecimal prifitMoney = updateMoney.multiply(commissionRate2);
					UserProfitDetail userProfitDetail = InstanceUtils.initUserProfitDetail(userId,parent1UserId,parent2UserId,parent3UserId,commissionRate2,prifitMoney,updateMoney);
					long dbResult = userProfitDetailDao.insert(userProfitDetail);
					if(ContactUtils.dbResult(dbResult)){
						String profitId = userProfitDetail.getId();
						String title = "刷单佣金分成";
						userAccountApiService.updateAccountCommissionMoney(userId,prifitMoney,profitId,title);
					}
				}
			}
			if(ContactUtils.isOkUserId(parent3UserId)){
				UserInfo parent3UserInfo =  this.getUserInfoByUserId(parent3UserId);
				int level = parent3UserInfo.getLevel();
				UserInfoMembership userInfoMembership = this.getMemberShipByLevel(level);
				BigDecimal commissionRate3 = ContactUtils.parsePercentage(userInfoMembership.getCommissionRate3());
				if(!ContactUtils.isLesserOrEqualZero(commissionRate3)){
					BigDecimal prifitMoney = updateMoney.multiply(commissionRate3);
					UserProfitDetail userProfitDetail = InstanceUtils.initUserProfitDetail(userId,parent1UserId,parent2UserId,parent3UserId,commissionRate3,prifitMoney,updateMoney);
					long dbResult = userProfitDetailDao.insert(userProfitDetail);
					if(ContactUtils.dbResult(dbResult)){
						String profitId = userProfitDetail.getId();
						String title = "刷单佣金分成";
						userAccountApiService.updateAccountCommissionMoney(userId,prifitMoney,profitId,title);
					}
				}
			}
			return true;
		}
	}

	/**
	 * @desc 团队分润
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean teamProfit(UserInfo userInfo,UserAccount userAccount,int  type,BigDecimal updateMoney) {
		return true;
	}

	/**
	 * @desc 会员等级升级或解锁
	 * LV0、LV1不设锁，LV2~LV7为锁住状态，达到条件自动解锁LV0、LV1不设锁
	 * LV2~LV7为锁住状态，达到条件自动解锁，两种方式都可以解锁；1，一次性充值满等级对应的金额 2，直推低1等级用户累计5人
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean memberLevelUp(UserInfo userInfo,UserAccount userAccount,int  type,BigDecimal updateMoney) {
		try {
			String userId = userInfo.getId();
			synchronized (userId) {
				int num = 1;
				int currentLock = userInfo.getLock();
				int currentLevel = userInfo.getLevel();
				String parent1Id = userInfo.getParent1UserId();
				BigDecimal currentTotalMoney = userAccount.getTotalMoney();
				int maxLevel = this.getMemberShipMaxLevel(currentTotalMoney);
				if(currentLevel < maxLevel){
					int userLock = this.getUserLock(userId,maxLevel,updateMoney);
					int valid = ContactUtils.USER_VALID_1;
					Boolean isUpLevelOk = this.updateUpLevelAdnLock(userId,maxLevel,userLock,valid);
					logger.info("会员等级升级且解锁:{},{}",userId,isUpLevelOk);
					if(isUpLevelOk){
						boolean isOK = this.updateDirectTeamNum(userId,num);
						logger.info("充值修改团队数:{},{}",parent1Id,isOK);

						int teamNum = this.getTeamUserValidNum(parent1Id);
						if(teamNum >= ContactUtils.USER_LEVEL_UP_TEAM_NUM){
							isUpLevelOk = this.updateLock(parent1Id, ContactUtils.USER_LOCK_1);
							logger.info("上级用户不升级只解锁:{},{}",parent1Id,isUpLevelOk);
						}
					}
				}else{
					int userLock = this.getUserLock(userId,currentLevel,updateMoney);
					if(currentLock != ContactUtils.USER_LOCK_1 && userLock == ContactUtils.USER_LOCK_1){
						Boolean isUpLevelOk = this.updateLock(userId,userLock);
						logger.info("用户不升级只解锁:{},{}",userId,isUpLevelOk);
					}
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("会员等级升级异常",e);
			return false;
		}
	}

	/**
	 * @desc 修改三级团队人数
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public synchronized Boolean registerUpdateTeam(String parent1UserId,String parent2UserId,String parent3UserId) {
		try {
			int num = 1;
			if(ContactUtils.isOkUserId(parent1UserId)){
				boolean isOK = this.updateTeamNum(parent1UserId,num);
				logger.info("修改1级团队人数:{},{}",parent1UserId,isOK);
			}
			if(ContactUtils.isOkUserId(parent2UserId)){
				boolean isOK = this.updateTeamNum(parent2UserId,num);
				logger.info("修改2级团队人数:{},{}",parent2UserId,isOK);
			}
			if(ContactUtils.isOkUserId(parent3UserId)){
				boolean isOK = this.updateTeamNum(parent3UserId,num);
				logger.info("修改3级团队人数:{},{}",parent3UserId,isOK);
			}
			return true;
		} catch (Exception e) {
			logger.error("修改团队人数异常",e);
			return false;
		}
	}

}
