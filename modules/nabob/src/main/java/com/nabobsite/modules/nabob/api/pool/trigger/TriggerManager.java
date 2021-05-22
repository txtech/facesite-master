package com.nabobsite.modules.nabob.api.pool.trigger;

public interface TriggerManager {

	/**
	 * 提交定时任务
	 * @param callback
	 */
	public boolean submit(TriggerThread callback);

	/**
	 * 根据用户Id取消关联的定时任务
	 */
	public boolean cancel(String userId);

}
