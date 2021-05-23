/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service.core;

import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.service.simple.SimpleUserService;
import com.nabobsite.modules.nabob.cms.team.entity.TeamUser;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
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

	/**
	 * @desc 会员分润
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean memberProfit(UserInfo userInfo,UserAccount userAccount,int  type,BigDecimal updateMoney) {
		return true;
	}

	/**
	 * @desc 团队分润
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean teamProfit(UserInfo userInfo,UserAccount userAccount,int  type,BigDecimal updateMoney) {
		return true;
	}

	/**
	 * @desc 会员等级升级
	 * LV0、LV1不设锁，LV2~LV7为锁住状态，达到条件自动解锁LV0、LV1不设锁
	 * LV2~LV7为锁住状态，达到条件自动解锁，两种方式都可以解锁；1，一次性充值满等级对应的金额 2，直推低1等级用户累计5人
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean memberLevelUp(UserInfo userInfo,UserAccount userAccount,int  type,BigDecimal updateMoney) {
		try {
			if(type != ContactUtils.ORDER_TYPE_RECHANGE){
				return false;
			}
			String userId = userInfo.getId();
			int currentLock = userInfo.getLock();
			int currentLevel = userInfo.getLevel();
			String parent1Id = userInfo.getParent1UserId();
			BigDecimal currentTotalMoney = userAccount.getTotalMoney();
			int maxLevel = this.getMemberShipMaxLevel(currentTotalMoney);
			if(currentLevel < maxLevel){
				currentLevel = maxLevel;
				int userLock = this.getUserLock(userId,currentLevel,updateMoney);
				Boolean isUpLevelOk = this.updateUpLevelAdnLock(userId,maxLevel,userLock);
				if(isUpLevelOk){
					logger.info("用户余额触发,用户升级且解锁:{},{}",userId,isUpLevelOk);
					isUpLevelOk = this.updateParentUpLock(parent1Id);
					logger.info("用户余额触发,上级用户解锁:{},{}",parent1Id,isUpLevelOk);
				}
			}else{
				int userLock = this.getUserLock(userId,currentLevel,updateMoney);
				if(currentLock != ContactUtils.USER_LOCK_1 && userLock == ContactUtils.USER_LOCK_1){
					Boolean isUpLevelOk = this.updateLock(userId,userLock);
					logger.info("用户余额触发,用户不升级只解锁:{},{}",userId,isUpLevelOk);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("会员等级升级异常",e);
			return false;
		}
	}

	/**
	 * @desc 上级用户解锁
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean updateParentUpLock(String parent1Id) {
		try {
			if(!ContactUtils.isOkUserId(parent1Id)){
				return false;
			}
			int teamNum = this.getLevelUpTeamNum(parent1Id);
			if(teamNum >= ContactUtils.USER_LEVEL_UP_TEAM_NUM){
				Boolean isUpLevelOk = this.updateLock(parent1Id, ContactUtils.USER_LOCK_1);
				logger.info("用户余额触发,上级用户解锁:{},{}",parent1Id,isUpLevelOk);
			}
			return true;
		} catch (Exception e) {
			logger.error("上级用户解锁异常",e);
			return false;
		}
	}


	/**
	 * @desc 修改三级团队人数
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Boolean updateTeam(String parent1UserId,String parent2UserId,String parent3UserId) {
		try {
			//增加1人
			int num = 1;
			if(ContactUtils.isOkUserId(parent1UserId)){
				TeamUser userTeam = new TeamUser();
				userTeam.setId(parent1UserId);
				userTeam.setTeam1Num(num);
				long dbResult = teamUserDao.updateTeamNum(userTeam);
				if(ContactUtils.dbResult(dbResult)){
					logger.info("修改1级团队人数:{}",parent1UserId);
				}
			}
			if(ContactUtils.isOkUserId(parent2UserId)){
				TeamUser userTeam = new TeamUser();
				userTeam.setId(parent2UserId);
				userTeam.setTeam2Num(num);
				long dbResult = teamUserDao.updateTeamNum(userTeam);
				if(ContactUtils.dbResult(dbResult)){
					logger.info("修改2级团队人数:{}",parent2UserId);
				}
			}
			if(ContactUtils.isOkUserId(parent3UserId)){
				TeamUser userTeam = new TeamUser();
				userTeam.setId(parent3UserId);
				userTeam.setTeam3Num(num);
				long dbResult = teamUserDao.updateTeamNum(userTeam);
				if(ContactUtils.dbResult(dbResult)){
					logger.info("修改3级团队人数:{}",parent3UserId);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("修改团队人数异常",e);
			return false;
		}
	}

}
