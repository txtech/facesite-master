/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.task.dao.TaskInfoDao;
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 任务列表Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class TaskApiService extends CrudService<TaskInfoDao, TaskInfo> {

	/**
	 * 获取单条数据
	 * @param taskInfo
	 * @return
	 */
	@Override
	public TaskInfo get(TaskInfo taskInfo) {
		return super.get(taskInfo);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param taskInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TaskInfo taskInfo) {
		super.save(taskInfo);
	}

	/**
	 * 更新状态
	 * @param taskInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TaskInfo taskInfo) {
		super.updateStatus(taskInfo);
	}

	/**
	 * 删除数据
	 * @param taskInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TaskInfo taskInfo) {
		super.delete(taskInfo);
	}

}
