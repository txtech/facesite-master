package com.nabobsite.modules.nabob.api.common.task;

import com.nabobsite.modules.nabob.api.common.trigger.TriggerOperation;
import com.nabobsite.modules.nabob.api.entity.DbInstanceEntity;
import com.nabobsite.modules.nabob.api.service.UserAccountApiService;

/**
 * @desc 用户注册触发器
 * @author nada
 * @create 2021/5/12 5:40 下午
*/
public class UserRegisterTrigger extends TriggerOperation {

	private UserAccountApiService userAccountApiService;

	public UserRegisterTrigger(String userId,UserAccountApiService userAccountApiService) {
		super(userId);
		this.userAccountApiService = userAccountApiService;
	}

	@Override
	public void execute() {
		LOG.info("用户注册触发器，userId:{}",userId);
		//注册成功，插入账户信息
		userAccountApiService.save(DbInstanceEntity.initUserAccount(userId));
	}

	public UserAccountApiService getUserAccountApiService() {
		return userAccountApiService;
	}

	public void setUserAccountApiService(UserAccountApiService userAccountApiService) {
		this.userAccountApiService = userAccountApiService;
	}
}
