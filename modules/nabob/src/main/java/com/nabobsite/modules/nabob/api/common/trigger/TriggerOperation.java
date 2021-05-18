package com.nabobsite.modules.nabob.api.common.trigger;

import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TriggerOperation implements TriggerThread {
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


	/**
	 * @desc 获取用户信息
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
			userInfo = userInfoDao.getByEntity(userInfo);
			if(userInfo == null){
				logger.error("获取用户信息为空,{}",userId);
				return null;
			}
			return userInfo;
		} catch (Exception e) {
			logger.error("获取用户信息异常",e);
			return null;
		}
	}

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
