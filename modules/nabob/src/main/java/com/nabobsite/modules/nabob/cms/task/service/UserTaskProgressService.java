/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.task.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.task.entity.UserTaskProgress;
import com.nabobsite.modules.nabob.cms.task.dao.UserTaskProgressDao;

/**
 * 用户任务奖励Service
 * @author face
 * @version 2021-05-21
 */
@Service
@Transactional(readOnly=true)
public class UserTaskProgressService extends CrudService<UserTaskProgressDao, UserTaskProgress> {
	
	/**
	 * 获取单条数据
	 * @param userTaskProgress
	 * @return
	 */
	@Override
	public UserTaskProgress get(UserTaskProgress userTaskProgress) {
		return super.get(userTaskProgress);
	}
	
	/**
	 * 查询分页数据
	 * @param userTaskProgress 查询条件
	 * @param userTaskProgress.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserTaskProgress> findPage(UserTaskProgress userTaskProgress) {
		return super.findPage(userTaskProgress);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userTaskProgress
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserTaskProgress userTaskProgress) {
		super.save(userTaskProgress);
	}
	
	/**
	 * 更新状态
	 * @param userTaskProgress
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserTaskProgress userTaskProgress) {
		super.updateStatus(userTaskProgress);
	}
	
	/**
	 * 删除数据
	 * @param userTaskProgress
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserTaskProgress userTaskProgress) {
		super.delete(userTaskProgress);
	}
	
}