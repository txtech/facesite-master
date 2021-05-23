/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.team.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.team.entity.TeamUserReward;
import com.nabobsite.modules.nabob.cms.team.dao.TeamUserRewardDao;

/**
 * 团队任务奖励Service
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class TeamUserRewardService extends CrudService<TeamUserRewardDao, TeamUserReward> {
	
	/**
	 * 获取单条数据
	 * @param teamUserReward
	 * @return
	 */
	@Override
	public TeamUserReward get(TeamUserReward teamUserReward) {
		return super.get(teamUserReward);
	}
	
	/**
	 * 查询分页数据
	 * @param teamUserReward 查询条件
	 * @param teamUserReward.page 分页对象
	 * @return
	 */
	@Override
	public Page<TeamUserReward> findPage(TeamUserReward teamUserReward) {
		return super.findPage(teamUserReward);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param teamUserReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TeamUserReward teamUserReward) {
		super.save(teamUserReward);
	}
	
	/**
	 * 更新状态
	 * @param teamUserReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TeamUserReward teamUserReward) {
		super.updateStatus(teamUserReward);
	}
	
	/**
	 * 删除数据
	 * @param teamUserReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TeamUserReward teamUserReward) {
		super.delete(teamUserReward);
	}
	
}