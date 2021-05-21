/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.task.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.task.entity.UserTask;
import com.nabobsite.modules.nabob.cms.task.dao.UserTaskDao;

/**
 * 用户任务Service
 * @author face
 * @version 2021-05-21
 */
@Service
@Transactional(readOnly=true)
public class UserTaskService extends CrudService<UserTaskDao, UserTask> {
	
	/**
	 * 获取单条数据
	 * @param userTask
	 * @return
	 */
	@Override
	public UserTask get(UserTask userTask) {
		return super.get(userTask);
	}
	
	/**
	 * 查询分页数据
	 * @param userTask 查询条件
	 * @param userTask.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserTask> findPage(UserTask userTask) {
		return super.findPage(userTask);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userTask
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserTask userTask) {
		super.save(userTask);
	}
	
	/**
	 * 更新状态
	 * @param userTask
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserTask userTask) {
		super.updateStatus(userTask);
	}
	
	/**
	 * 删除数据
	 * @param userTask
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserTask userTask) {
		super.delete(userTask);
	}
	
}