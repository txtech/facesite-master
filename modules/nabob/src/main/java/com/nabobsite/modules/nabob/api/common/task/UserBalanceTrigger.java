package com.nabobsite.modules.nabob.api.common.task;

import com.nabobsite.modules.nabob.api.common.trigger.TriggerOperation;
import com.nabobsite.modules.nabob.api.entity.CommonStaticContact;
import com.nabobsite.modules.nabob.api.entity.LogicStaticContact;
import com.nabobsite.modules.nabob.api.service.UserAccountApiService;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

/**
 * @desc 用户支付触发
 * @author nada
 * @create 2021/5/12 5:41 下午
*/
public class UserBalanceTrigger extends TriggerOperation {

	private BigDecimal payMoney;
	private UserInfoDao userInfoDao;
	private UserAccountApiService userAccountApiService;

	public UserBalanceTrigger(String userId,BigDecimal payMoney, UserInfoDao userInfoDao, UserAccountApiService userAccountApiService) {
		super(userId);
		this.userId = userId;
		this.payMoney = payMoney;
		this.userInfoDao = userInfoDao;
		this.userAccountApiService = userAccountApiService;
	}

	@Override
	public void execute() {
		LOG.info("用户支付触发，userId:{},money:{}",userId,payMoney);
		UserInfo userInfo = this.getUserInfoByUserId(userId);
		if(userInfo == null){
			LOG.error("用户支付触发,用户为空:{}",userId);
			return;
		}
		UserAccount userAccount = userAccountApiService.getUserAccountByUserId(userId);
		if(userInfo == null){
			LOG.error("用户支付触发,用户账户为空:{}",userId);
			return;
		}
		int oldLock = userInfo.getLock();
		int currentLevel = userInfo.getLevel();
		String parent1Id = userInfo.getParentUserId();
		BigDecimal currentTotalMoney = userAccount.getTotalMoney();
		int maxLevel = LogicStaticContact.getMaxUserLevelByTotalBalance(currentTotalMoney);
		if(currentLevel < maxLevel){
			currentLevel = maxLevel;
			int userLock = this.getUserLock(userId,currentLevel,payMoney);
			Boolean isUpLevelOk = this.updateUpLevel(userId,maxLevel,userLock);
			if(isUpLevelOk){
				LOG.info("用户支付触发,用户升级且解锁:{},{}",userId,isUpLevelOk);
				isUpLevelOk = this.updateParentUpLock(parent1Id);
				LOG.info("用户支付触发,上级用户解锁:{},{}",parent1Id,isUpLevelOk);
			}
		}else{
			int userLock = this.getUserLock(userId,currentLevel,payMoney);
			if(oldLock != CommonStaticContact.USER_LOCK_1 && userLock == CommonStaticContact.USER_LOCK_1){
				Boolean isUpLevelOk = this.updateLock(userId,userLock);
				LOG.info("用户支付触发,用户不升级只解锁:{},{}",userId,isUpLevelOk);
			}
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
			if(StringUtils.isEmpty(parent1Id) || "0".equalsIgnoreCase(parent1Id)){
				return false;
			}
			int teamNum = this.getLevelUpTeamNum(parent1Id);
			if(teamNum >= LogicStaticContact.USER_LEVEL_UP_TEAM_NUM){
				Boolean isUpLevelOk = this.updateLock(parent1Id,CommonStaticContact.USER_LOCK_1);
				LOG.info("用户支付触发,上级用户解锁:{},{}",parent1Id,isUpLevelOk);
			}
			return true;
		} catch (Exception e) {
			LOG.error("上级用户解锁异常",e);
			return false;
		}
	}
	/**
	 * @desc 根据账号ID升级
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean updateUpLevel(String userId,int upLevel,int userLock) {
		try {
			if(StringUtils.isEmpty(userId)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			userInfo.setLock(userLock);
			userInfo.setLevel(upLevel);
			long dbResult = userInfoDao.update(userInfo);
			return CommonStaticContact.dbResult(dbResult);
		} catch (Exception e) {
			LOG.error("根据账号ID升级异常",e);
			return null;
		}
	}
	/**
	 * @desc 根据账号ID解锁
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean updateLock(String userId,int userLock) {
		try {
			if(StringUtils.isEmpty(userId)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			userInfo.setLock(userLock);
			long dbResult = userInfoDao.update(userInfo);
			return CommonStaticContact.dbResult(dbResult);
		} catch (Exception e) {
			LOG.error("根据账号ID升级异常",e);
			return null;
		}
	}

	/**
	 * @desc 获取用户解锁状态：1：解锁 2：锁定
	 * @author nada
	 * @create 2021/5/12 11:22 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public int getUserLock(String userId,int currentLevel,BigDecimal payMoney){
		if(currentLevel == LogicStaticContact.USER_LEVEL_0 || currentLevel == LogicStaticContact.USER_LEVEL_1){
			return 1;
		}
		BigDecimal mustBalance = LogicStaticContact.LEVEL_BALANCE_MIN_BALANCE.get(currentLevel);
		if(CommonStaticContact.isBiggerOrEqual(payMoney,mustBalance)){
			return 1;
		}else{
			int teamNum = this.getLevelUpTeamNum(userId);
			if(teamNum >= LogicStaticContact.USER_LEVEL_UP_TEAM_NUM){
				return 1;
			}
		}
		return 2;
	}

	/**
	 * @desc 根据账号ID获取用户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public UserInfo getUserInfoByUserId(String userId) {
		try {
			if(StringUtils.isEmpty(userId) || "0".equalsIgnoreCase(userId)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			return userInfoDao.getByEntity(userInfo);
		} catch (Exception e) {
			LOG.error("根据账号ID获取用户信息异常",e);
			return null;
		}
	}
	/**
	 * @desc 根据账号ID和等级获取用户团队个数
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public int getLevelUpTeamNum(String userId){
		try {
			if(StringUtils.isEmpty(userId) || "0".equalsIgnoreCase(userId)){
				return 0;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			return userInfoDao.getOkLevelTeamNum1(userInfo);
		} catch (Exception e) {
			LOG.error("根据账号ID和等级获取用户团队个数异常",e);
			return 0;
		}
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public UserAccountApiService getUserAccountApiService() {
		return userAccountApiService;
	}

	public void setUserAccountApiService(UserAccountApiService userAccountApiService) {
		this.userAccountApiService = userAccountApiService;
	}
}
