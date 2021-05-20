/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.task.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.task.entity.UserTaskReward;
import com.nabobsite.modules.nabob.cms.task.dao.UserTaskRewardDao;

/**
 * 用户任务奖励Service
 * @author face
 * @version 2021-05-20
 */
@Service
@Transactional(readOnly=true)
public class UserTaskRewardService extends CrudService<UserTaskRewardDao, UserTaskReward> {
	
	/**
	 * 获取单条数据
	 * @param userTaskReward
	 * @return
	 */
	@Override
	public UserTaskReward get(UserTaskReward userTaskReward) {
		return super.get(userTaskReward);
	}
	
	/**
	 * 查询分页数据
	 * @param userTaskReward 查询条件
	 * @param userTaskReward.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserTaskReward> findPage(UserTaskReward userTaskReward) {
		return super.findPage(userTaskReward);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userTaskReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserTaskReward userTaskReward) {
		super.save(userTaskReward);
	}
	
	/**
	 * 更新状态
	 * @param userTaskReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserTaskReward userTaskReward) {
		super.updateStatus(userTaskReward);
	}
	
	/**
	 * 删除数据
	 * @param userTaskReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserTaskReward userTaskReward) {
		super.delete(userTaskReward);
	}
	
}