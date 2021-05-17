/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.task.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.task.entity.TeamReward;
import com.nabobsite.modules.nabob.cms.task.dao.TeamRewardDao;

/**
 * 团队任务Service
 * @author face
 * @version 2021-05-17
 */
@Service
@Transactional(readOnly=true)
public class TeamRewardService extends CrudService<TeamRewardDao, TeamReward> {
	
	/**
	 * 获取单条数据
	 * @param teamReward
	 * @return
	 */
	@Override
	public TeamReward get(TeamReward teamReward) {
		return super.get(teamReward);
	}
	
	/**
	 * 查询分页数据
	 * @param teamReward 查询条件
	 * @param teamReward.page 分页对象
	 * @return
	 */
	@Override
	public Page<TeamReward> findPage(TeamReward teamReward) {
		return super.findPage(teamReward);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param teamReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TeamReward teamReward) {
		super.save(teamReward);
	}
	
	/**
	 * 更新状态
	 * @param teamReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TeamReward teamReward) {
		super.updateStatus(teamReward);
	}
	
	/**
	 * 删除数据
	 * @param teamReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TeamReward teamReward) {
		super.delete(teamReward);
	}
	
}