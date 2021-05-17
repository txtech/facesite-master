package com.nabobsite.modules.nabob.api.common.task;

import com.nabobsite.modules.nabob.api.common.trigger.TriggerOperation;
import com.nabobsite.modules.nabob.api.common.trigger.TriggerPoolManagerImpl;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * @desc 用户注册触发器
 * @author nada
 * @create 2021/5/12 5:40 下午
*/
public class UserProfitTrigger extends TriggerOperation {

	private UserInfoDao userInfoDao;
	private UserAccountDao userAccountDao;
	private TriggerPoolManagerImpl triggerPoolManager;

	public UserProfitTrigger(String userId, UserInfoDao userInfoDao, UserAccountDao userAccountDao, TriggerPoolManagerImpl triggerPoolManager) {
		super(userId);
		this.userId = userId;
		this.userInfoDao = userInfoDao;
		this.userAccountDao = userAccountDao;
		this.triggerPoolManager = triggerPoolManager;
	}

	@Override
	public void execute() {
		LOG.info("用户注册触发器，userId:{}",userId);
		UserInfo curUserInfo = this.getUserInfoByUserId(userId);
		if(curUserInfo == null){
			LOG.error("用户注册触发器,用户为空:{}",userId);
			return;
		}
		//修改团队人数
		String parent1UserId = curUserInfo.getParent1UserId();
		String parent2UserId = curUserInfo.getParent2UserId();
		String parent3UserId = curUserInfo.getParent3UserId();
		boolean isok = this.updateTeam(parent1UserId,parent2UserId,parent3UserId,1);
		if(!isok){
			LOG.error("用户注册触发器,修改团队人数失败:{}",userId);
			return;
		}
	}

	/**
	 * @desc 修改三级团队人数
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Boolean updateTeam(String parent1UserId,String parent2UserId,String parent3UserId,int num) {
		try {
			if(CommonContact.isOkUserId(parent1UserId)){
				UserInfo userInfo = new UserInfo();
				userInfo.setId(parent1UserId);
				userInfo.setTeam1Num(num);
				long dbResult = userInfoDao.updateTeamNum(userInfo);
				if(CommonContact.dbResult(dbResult)){
					LOG.info("修改1级团队人数:{}",parent1UserId);
				}
			}
			if(CommonContact.isOkUserId(parent2UserId)){
				UserInfo userInfo = new UserInfo();
				userInfo.setId(parent2UserId);
				userInfo.setTeam2Num(num);
				long dbResult = userInfoDao.updateTeamNum(userInfo);
				if(CommonContact.dbResult(dbResult)){
					LOG.info("修改2级团队人数:{}",parent2UserId);
				}
			}
			if(CommonContact.isOkUserId(parent3UserId)){
				UserInfo userInfo = new UserInfo();
				userInfo.setId(parent3UserId);
				userInfo.setTeam3Num(num);
				long dbResult = userInfoDao.updateTeamNum(userInfo);
				if(CommonContact.dbResult(dbResult)){
					LOG.info("修改3级团队人数:{}",parent3UserId);
				}
			}
			return true;
		} catch (Exception e) {
			LOG.error("修改团队人数异常",e);
			return false;
		}
	}

	/**
	 * @desc 根据账号ID获取用户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public UserInfo getUserInfoByUserId(String userId) {
		try {
			if(!CommonContact.isOkUserId(userId)){
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

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public TriggerPoolManagerImpl getTriggerPoolManager() {
		return triggerPoolManager;
	}

	public void setTriggerPoolManager(TriggerPoolManagerImpl triggerPoolManager) {
		this.triggerPoolManager = triggerPoolManager;
	}
}