/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.task.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.task.entity.TaskUserReward;
import com.nabobsite.modules.nabob.cms.task.dao.TaskUserRewardDao;

/**
 * 用户任务奖励Service
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class TaskUserRewardService extends CrudService<TaskUserRewardDao, TaskUserReward> {
	
	/**
	 * 获取单条数据
	 * @param taskUserReward
	 * @return
	 */
	@Override
	public TaskUserReward get(TaskUserReward taskUserReward) {
		return super.get(taskUserReward);
	}
	
	/**
	 * 查询分页数据
	 * @param taskUserReward 查询条件
	 * @param taskUserReward.page 分页对象
	 * @return
	 */
	@Override
	public Page<TaskUserReward> findPage(TaskUserReward taskUserReward) {
		return super.findPage(taskUserReward);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param taskUserReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TaskUserReward taskUserReward) {
		super.save(taskUserReward);
	}
	
	/**
	 * 更新状态
	 * @param taskUserReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TaskUserReward taskUserReward) {
		super.updateStatus(taskUserReward);
	}
	
	/**
	 * 删除数据
	 * @param taskUserReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TaskUserReward taskUserReward) {
		super.delete(taskUserReward);
	}
	
}