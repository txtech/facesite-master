/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.DbInstanceContact;
import com.nabobsite.modules.nabob.api.entity.RedisPrefixContant;
import com.nabobsite.modules.nabob.cms.task.dao.TaskInfoDao;
import com.nabobsite.modules.nabob.cms.task.dao.UserTaskDao;
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;
import com.nabobsite.modules.nabob.cms.task.entity.UserTask;
import com.nabobsite.modules.nabob.config.RedisOpsUtil;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
	private RedisOpsUtil redisOpsUtil;
	@Autowired
	private TaskInfoDao taskInfoDao;
	@Autowired
	private UserTaskDao userTaskDao;
	@Autowired
	private UserAccountApiService userAccountApiService;

	/**
	 * @desc 新用户做任务
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> doUserTask(String taskId,String token) {
		try {
			if(StringUtils.isEmpty(token)){
				return ResultUtil.failed("获取失败,获取令牌为空");
			}
			String userId = (String) redisOpsUtil.get(RedisPrefixContant.getTokenUserKey(token));
			if(StringUtils.isEmpty(userId)){
				return ResultUtil.failed("获取失败,登陆令牌失效");
			}
			TaskInfo taskInfo = this.getTaskInfoById(taskId);
			if(taskInfo == null){
				return ResultUtil.failed("获取失败,任务不存在");
			}
			synchronized (userId) {
				BigDecimal rewardMoney = taskInfo.getRewardMoney();
				UserTask userTask = this.getUserTaskByUserIdAndTaskId(userId,taskId);
				if(userTask == null){
					UserTask initUserTask = DbInstanceContact.initUserTask(userId,taskId,1);
					long dbResult = userTaskDao.insert(initUserTask);
					if(CommonContact.dbResult(dbResult)){
						logger.info("新用户第一次做任务成功:{},{}",userId,taskId);
						return ResultUtil.success(true);
					}
					logger.error("新用户第一次做任务失败:{},{}",userId,taskId);
					return ResultUtil.failed("Failed to do the task!");
				}

				int taskStatus = userTask.getTaskStatus();
				int taskFinishNumber = userTask.getFinishNumber();
				if(taskStatus == CommonContact.USER_TASK_STATUS_3){
					logger.error("新用户任务已经完毕:{},{}",userId,taskId);
					this.sendReward(userId,rewardMoney,taskId);
					return ResultUtil.failed("Failed to do the task!");
				}
				int taskNum = taskInfo.getTaskNumber();
				if(taskFinishNumber >= taskNum){
					logger.error("新用户任务已经完毕:{},{}",userId,taskId);
					return ResultUtil.failed("Failed to do the task!");
				}
				Boolean isOk = this.updateTaskFinishNumber(userTask.getId(),taskFinishNumber+1);
				if(isOk){
					logger.info("新用户做任务成功:{},{}",userId,taskId);
					this.sendReward(userId,rewardMoney,taskId);
					return ResultUtil.success(true);
				}
				logger.error("新用户做任务失败:{},{}",userId,taskId);
				return ResultUtil.failed("Failed to do the task!");
			}
		} catch (Exception e) {
			logger.error("Failed to do the task!",e);
			return ResultUtil.failed("Failed to do the task!");
		}
	}

	/**
	 * @desc 完成任务送奖励
	 * @author nada
	 * @create 2021/5/13 8:16 下午
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Boolean sendReward(String userId,BigDecimal rewardMoney,String taskId) {
		try {
			String title = CommonContact.USER_ACCOUNT_DETAIL_TITLE_3;
			return userAccountApiService.updateAccountRewardMoney(userId,rewardMoney,taskId,title);
		} catch (Exception e) {
			logger.error("完成任务送奖励异常",e);
			return false;
		}
	}

	/**
	 * @desc 修改任务完成数量
	 * @author nada
	 * @create 2021/5/13 8:03 下午
	*/
	public Boolean updateTaskFinishNumber(String id,int finishNumber){
		try {
			UserTask userTaskPrams = new UserTask();
			userTaskPrams.setId(id);
			userTaskPrams.setFinishNumber(finishNumber);
			long dbResult = userTaskDao.update(userTaskPrams);
			return CommonContact.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("修改任务完成数量异常",e);
			return false;
		}
	}

	/**
	 * @desc 获取用户任务
	 * @author nada
	 * @create 2021/5/13 7:32 下午
	*/
	public UserTask getUserTaskByUserIdAndTaskId(String userId,String taskId){
		try {
			UserTask userTaskPrams = new UserTask();
			userTaskPrams.setTaskId(taskId);
			userTaskPrams.setUserId(userId);
			UserTask userTask = userTaskDao.getByEntity(userTaskPrams);
			return userTask;
		} catch (Exception e) {
			logger.error("获取用户任务异常",e);
			return null;
		}
	}

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
	 * @desc 获取任务详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public TaskInfo getTaskInfoById(String id) {
		try {
			TaskInfo taskInfo = new TaskInfo();
			taskInfo.setId(id);
			return taskInfoDao.getByEntity(taskInfo);
		} catch (Exception e) {
			logger.error("Failed to get task list!",e);
			return null;
		}
	}
}
