/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountTask;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountTaskDao;

/**
 * 用户奖励账户Service
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class UserAccountTaskService extends CrudService<UserAccountTaskDao, UserAccountTask> {
	
	/**
	 * 获取单条数据
	 * @param userAccountTask
	 * @return
	 */
	@Override
	public UserAccountTask get(UserAccountTask userAccountTask) {
		return super.get(userAccountTask);
	}
	
	/**
	 * 查询分页数据
	 * @param userAccountTask 查询条件
	 * @param userAccountTask.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserAccountTask> findPage(UserAccountTask userAccountTask) {
		return super.findPage(userAccountTask);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userAccountTask
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserAccountTask userAccountTask) {
		super.save(userAccountTask);
	}
	
	/**
	 * 更新状态
	 * @param userAccountTask
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserAccountTask userAccountTask) {
		super.updateStatus(userAccountTask);
	}
	
	/**
	 * 删除数据
	 * @param userAccountTask
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserAccountTask userAccountTask) {
		super.delete(userAccountTask);
	}
	
}