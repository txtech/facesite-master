/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.task.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;
import com.nabobsite.modules.nabob.cms.task.dao.TaskInfoDao;

/**
 * 任务列表Service
 * @author face
 * @version 2021-05-20
 */
@Service
@Transactional(readOnly=true)
public class TaskInfoService extends CrudService<TaskInfoDao, TaskInfo> {
	
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
	 * 查询分页数据
	 * @param taskInfo 查询条件
	 * @param taskInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<TaskInfo> findPage(TaskInfo taskInfo) {
		return super.findPage(taskInfo);
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