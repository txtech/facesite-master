/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.dao.ProductWarehouseDao;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.task.dao.TaskInfoDao;
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务列表Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class TaskApiService extends CrudService<TaskInfoDao, TaskInfo> {

	@Autowired
	private TaskInfoDao taskInfoDao;


	/**
	 * @desc 获取任务列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<List<TaskInfo>> getTaskList(TaskInfo taskInfo) {
		try {
			List<TaskInfo> result = taskInfoDao.findList(taskInfo);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("Failed to get task list!",e);
			return ResultUtil.failed("Failed to get task list!");
		}
	}


	/**
	 * @desc 获取任务详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<TaskInfo> getTaskInfo(TaskInfo taskInfo) {
		try {
			TaskInfo result = taskInfoDao.getByEntity(taskInfo);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("Failed to get task list!",e);
			return ResultUtil.failed("Failed to get task info!");
		}
	}

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
