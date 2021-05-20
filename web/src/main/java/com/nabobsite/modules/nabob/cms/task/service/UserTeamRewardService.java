/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.task.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.task.entity.UserTeamReward;
import com.nabobsite.modules.nabob.cms.task.dao.UserTeamRewardDao;

/**
 * 团队任务奖励Service
 * @author face
 * @version 2021-05-17
 */
@Service
@Transactional(readOnly=true)
public class UserTeamRewardService extends CrudService<UserTeamRewardDao, UserTeamReward> {
	
	/**
	 * 获取单条数据
	 * @param userTeamReward
	 * @return
	 */
	@Override
	public UserTeamReward get(UserTeamReward userTeamReward) {
		return super.get(userTeamReward);
	}
	
	/**
	 * 查询分页数据
	 * @param userTeamReward 查询条件
	 * @param userTeamReward.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserTeamReward> findPage(UserTeamReward userTeamReward) {
		return super.findPage(userTeamReward);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userTeamReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserTeamReward userTeamReward) {
		super.save(userTeamReward);
	}
	
	/**
	 * 更新状态
	 * @param userTeamReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserTeamReward userTeamReward) {
		super.updateStatus(userTeamReward);
	}
	
	/**
	 * 删除数据
	 * @param userTeamReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserTeamReward userTeamReward) {
		super.delete(userTeamReward);
	}
	
}