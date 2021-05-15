/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountReward;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountRewardDao;

/**
 * 用户账户明细Service
 * @author face
 * @version 2021-05-15
 */
@Service
@Transactional(readOnly=true)
public class UserAccountRewardService extends CrudService<UserAccountRewardDao, UserAccountReward> {
	
	/**
	 * 获取单条数据
	 * @param userAccountReward
	 * @return
	 */
	@Override
	public UserAccountReward get(UserAccountReward userAccountReward) {
		return super.get(userAccountReward);
	}
	
	/**
	 * 查询分页数据
	 * @param userAccountReward 查询条件
	 * @param userAccountReward.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserAccountReward> findPage(UserAccountReward userAccountReward) {
		return super.findPage(userAccountReward);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userAccountReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserAccountReward userAccountReward) {
		super.save(userAccountReward);
	}
	
	/**
	 * 更新状态
	 * @param userAccountReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserAccountReward userAccountReward) {
		super.updateStatus(userAccountReward);
	}
	
	/**
	 * 删除数据
	 * @param userAccountReward
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserAccountReward userAccountReward) {
		super.delete(userAccountReward);
	}
	
}