package com.nabobsite.modules.nabob.api.pool.manager;

import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TriggerOperation implements TriggerThread{
	protected static final Logger logger = LoggerFactory .getLogger(TriggerOperation.class);
	protected UserInfoDao userInfoDao;
	protected String userId;
	protected long timeout;

	public TriggerOperation(String userId,UserInfoDao userInfoDao) {
		this.userId = userId;
		this.userInfoDao = userInfoDao;
		this.timeout = 15;
	}

	@Override
	public void run() {
		try {
			execute();
		} catch (Exception e) {
			logger.warn("执行超时回调函数失败！", e);
		}
	}

	public abstract void execute();

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public long getTimeOut() {
		return timeout;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
}
