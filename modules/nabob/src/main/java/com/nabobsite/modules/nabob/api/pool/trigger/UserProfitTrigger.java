package com.nabobsite.modules.nabob.api.pool.trigger;

import com.nabobsite.modules.nabob.api.pool.manager.TriggerOperation;
import com.nabobsite.modules.nabob.api.pool.manager.TriggerPoolManagerImpl;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;

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
		super(userId,userInfoDao);
		this.userId = userId;
		this.userInfoDao = userInfoDao;
		this.userAccountDao = userAccountDao;
		this.triggerPoolManager = triggerPoolManager;
	}

	@Override
	public void execute() {
		logger.info("用户注册触发器，userId:{}",userId);
	}

	public TriggerPoolManagerImpl getTriggerPoolManager() {
		return triggerPoolManager;
	}

	public void setTriggerPoolManager(TriggerPoolManagerImpl triggerPoolManager) {
		this.triggerPoolManager = triggerPoolManager;
	}
}
