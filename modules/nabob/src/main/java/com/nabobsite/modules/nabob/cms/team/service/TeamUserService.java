/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.team.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.team.entity.TeamUser;
import com.nabobsite.modules.nabob.cms.team.dao.TeamUserDao;

/**
 * 用户团队Service
 * @author face
 * @version 2021-05-24
 */
@Service
@Transactional(readOnly=true)
public class TeamUserService extends CrudService<TeamUserDao, TeamUser> {
	
	/**
	 * 获取单条数据
	 * @param teamUser
	 * @return
	 */
	@Override
	public TeamUser get(TeamUser teamUser) {
		return super.get(teamUser);
	}
	
	/**
	 * 查询分页数据
	 * @param teamUser 查询条件
	 * @param teamUser.page 分页对象
	 * @return
	 */
	@Override
	public Page<TeamUser> findPage(TeamUser teamUser) {
		return super.findPage(teamUser);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param teamUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TeamUser teamUser) {
		super.save(teamUser);
	}
	
	/**
	 * 更新状态
	 * @param teamUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TeamUser teamUser) {
		super.updateStatus(teamUser);
	}
	
	/**
	 * 删除数据
	 * @param teamUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TeamUser teamUser) {
		super.delete(teamUser);
	}
	
}