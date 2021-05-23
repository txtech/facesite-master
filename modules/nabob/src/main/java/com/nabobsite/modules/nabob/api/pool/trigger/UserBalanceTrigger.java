package com.nabobsite.modules.nabob.api.pool.trigger;

import com.nabobsite.modules.nabob.api.pool.manager.TriggerOperation;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.LogicStaticContact;
import com.nabobsite.modules.nabob.api.service.core.LogicService;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @desc 用户余额触发
 * @author nada
 * @create 2021/5/12 5:41 下午
*/
public class UserBalanceTrigger extends TriggerOperation {

	private int type;
	private BigDecimal updateMoney;
	private UserAccountDao userAccountDao;
	private LogicService logicService;

	public UserBalanceTrigger(String userId,int type,BigDecimal updateMoney, UserInfoDao userInfoDao, UserAccountDao userAccountDao,LogicService logicService) {
		super(userId,userInfoDao);
		this.type = type;
		this.userId = userId;
		this.updateMoney = updateMoney;
		this.logicService = logicService;
		this.userAccountDao = userAccountDao;
	}

	@Override
	public void execute() {
		logger.info("用户余额触发，userId:{},type:{},money:{}",userId,type,updateMoney);
		UserInfo userInfo = logicService.getUserInfoByUserId(userId);
		if(userInfo == null){
			return;
		}
		UserAccount userAccount = this.getUserAccountByUserId(userId);
		if(userAccount == null){
			return;
		}
		int currentLock = userInfo.getLock();
		int currentLevel = userInfo.getLevel();
		String parent1Id = userInfo.getParent1UserId();
		BigDecimal currentTotalMoney = userAccount.getTotalMoney();
		int maxLevel = LogicStaticContact.getMaxUserLevelByTotalBalance(currentTotalMoney);
		if(currentLevel < maxLevel){
			currentLevel = maxLevel;
			int userLock = this.getUserLock(userId,currentLevel,updateMoney);
			Boolean isUpLevelOk = this.updateUpLevel(userId,maxLevel,userLock);
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
	 * @desc 账号等级升级
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean updateUpLevel(String userId,int upLevel,int userLock) {
		try {
			if(!ContactUtils.isOkUserId(userId)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			userInfo.setLock(userLock);
			userInfo.setLevel(upLevel);
			long dbResult = userInfoDao.update(userInfo);
			return ContactUtils.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("根据账号ID升级异常",e);
			return null;
		}
	}
	/**
	 * @desc 当前用户解锁
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean updateLock(String userId,int userLock) {
		try {
			if(!ContactUtils.isOkUserId(userId)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			userInfo.setLock(userLock);
			long dbResult = userInfoDao.update(userInfo);
			return ContactUtils.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("根据账号ID升级异常",e);
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

	/**
	 * @desc 获取用户团队个数
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public int getLevelUpTeamNum(String userId){
		try {
			if(!ContactUtils.isOkUserId(userId)){
				return 0;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			return userInfoDao.getOkLevelTeam1Num(userInfo);
		} catch (Exception e) {
			logger.error("获取用户团队个数异常",e);
			return 0;
		}
	}

	/**
	 * @desc 获取账户信息
	 * @author nada
	 * @create 2021/5/17 10:58 上午
	*/
	public UserAccount getUserAccountByUserId(String userId) {
		try {
			if(!ContactUtils.isOkUserId(userId)){
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

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getUpdateMoney() {
		return updateMoney;
	}

	public void setUpdateMoney(BigDecimal updateMoney) {
		this.updateMoney = updateMoney;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
