/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service.core;

import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.LogicStaticContact;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.service.simple.SimpleUserService;
import com.nabobsite.modules.nabob.cms.order.dao.CashDao;
import com.nabobsite.modules.nabob.cms.order.entity.Cash;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 逻辑Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class LogicService extends SimpleUserService {

	/**
	 * @desc 会员等级升级
	 * LV0、LV1不设锁，LV2~LV7为锁住状态，达到条件自动解锁LV0、LV1不设锁
	 * LV2~LV7为锁住状态，达到条件自动解锁，两种方式都可以解锁；1，一次性充值满等级对应的金额 2，直推低1等级用户累计5人
	 * @author nada
	 * @create 2021/5/12 1:10 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean lenvelUp(String token, Cash cash) {
		try {
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
			if(teamNum >= LogicStaticContact.USER_LEVEL_UP_TEAM_NUM){
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
	 * @desc 获取用户解锁状态：1：解锁 2：锁定
	 * @author nada
	 * @create 2021/5/12 11:22 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public int getUserLock(String userId, int currentLevel, BigDecimal payMoney){
		if(currentLevel == LogicStaticContact.USER_LEVEL_0 || currentLevel == LogicStaticContact.USER_LEVEL_1){
			return 1;
		}
		BigDecimal mustBalance = LogicStaticContact.LEVEL_BALANCE_MIN_BALANCE.get(currentLevel);
		if(ContactUtils.isBiggerOrEqual(payMoney,mustBalance)){
			return 1;
		}else{
			int teamNum = this.getLevelUpTeamNum(userId);
			if(teamNum >= LogicStaticContact.USER_LEVEL_UP_TEAM_NUM){
				return 1;
			}
		}
		return 2;
	}
}
