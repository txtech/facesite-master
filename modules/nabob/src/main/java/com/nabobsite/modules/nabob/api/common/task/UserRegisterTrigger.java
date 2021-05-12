package com.nabobsite.modules.nabob.api.common.task;

import com.nabobsite.modules.nabob.api.common.trigger.TriggerOperation;

/**
 * @desc 用户注册触发器
 * @author nada
 * @create 2021/5/12 5:40 下午
*/
public class UserRegisterTrigger extends TriggerOperation {

	public UserRegisterTrigger(String userId) {
		super(userId);
	}

	@Override
	public void execute() {
		LOG.info("用户注册触发器，userId:{}",userId);
	}
}
