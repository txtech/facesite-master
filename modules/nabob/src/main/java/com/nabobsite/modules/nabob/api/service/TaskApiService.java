/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.StringUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.service.simple.SimpleUserService;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.InstanceContact;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductWarehouse;
import com.nabobsite.modules.nabob.cms.task.dao.TaskInfoDao;
import com.nabobsite.modules.nabob.cms.task.dao.UserTaskDao;
import com.nabobsite.modules.nabob.cms.task.dao.UserTaskRewardDao;
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;
import com.nabobsite.modules.nabob.cms.task.entity.UserTask;
import com.nabobsite.modules.nabob.cms.task.entity.UserTaskReward;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
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
public class TaskApiService extends SimpleUserService {

	@Autowired
	private UserTaskRewardDao userTaskRewardDao;
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
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			TaskInfo taskInfo = this.getTaskInfoById(taskId);
			if(taskInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10019);
			}
			synchronized (userId) {
				int type = taskInfo.getType();
				BigDecimal rewardMoney = taskInfo.getRewardMoney();
				UserTask userTask = this.getUserTaskByUserId(userId);
				if(userTask == null){
					UserTask initUserTask = InstanceContact.initUserTask(userId,taskId,type,1);
					long dbResult = userTaskDao.insert(initUserTask);
					if(!CommonContact.dbResult(dbResult)){
						logger.error("初始化任务失败:{},{}",userId,taskId);
						return ResultUtil.failed(I18nCode.CODE_10004);
					}
					this.sendReward(userId,taskId,type,rewardMoney,1);
					return ResultUtil.successToBoolean(true);
				}

				String userTaskId = userTask.getId();
				int taskStatus = userTask.getTaskStatus();
				if(taskStatus == CommonContact.USER_TASK_STATUS_3){
					logger.error("任务已经完成:{},{}",userId,taskId);
					return ResultUtil.failed(I18nCode.CODE_10008);
				}
				int taskNum = taskInfo.getTaskNumber();
				int taskFinishNumber = userTask.getTaskOrderNum();
				/*if(taskFinishNumber >= taskNum){
					logger.error("任务已经完成:{},{}",userId,taskId);
					return ResultUtil.failed(I18nCode.CODE_10008);
				}*/
				Boolean isOk = this.updateTaskFinishNumber(userTaskId,taskFinishNumber+1);
				if(isOk){
					this.sendReward(userId,taskId,type,rewardMoney,taskFinishNumber+1);
					return ResultUtil.successToBoolean(true);
				}
				return ResultUtil.failed(I18nCode.CODE_10004);
			}
		} catch (Exception e) {
			logger.error("新用户做任务异常",taskId,e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 完成任务送奖励
	 * @author nada
	 * @create 2021/5/13 8:16 下午
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Boolean sendReward(String userId,String taskId,int type,BigDecimal rewardMoney,int finishNumber) {
		try {
			String title = CommonContact.USER_ACCOUNT_DETAIL_TITLE_3;
			if(type == CommonContact.USER_ACCOUNT_REWARD_TYPE_1){
				title = CommonContact.USER_ACCOUNT_REWARD_TITLE_1;
			}else if(type == CommonContact.USER_ACCOUNT_REWARD_TYPE_2){
				title = CommonContact.USER_ACCOUNT_REWARD_TITLE_2;
			}else if(type == CommonContact.USER_ACCOUNT_REWARD_TYPE_3){
				title = CommonContact.USER_ACCOUNT_REWARD_TITLE_3;
			}else if(type == CommonContact.USER_ACCOUNT_REWARD_TYPE_4){
				title = CommonContact.USER_ACCOUNT_REWARD_TITLE_4;
			}
			UserTaskReward userTaskReward = InstanceContact.initUserTaskReward(userId,taskId,type,title,finishNumber,rewardMoney);
			userTaskReward.setUserId(userId);
			long dbResult = userTaskRewardDao.insert(userTaskReward);
			if(!CommonContact.dbResult(dbResult)){
				return false;
			}
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
			userTaskPrams.setTaskOrderNum(finishNumber);
			long dbResult = userTaskDao.update(userTaskPrams);
			return CommonContact.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("修改任务完成数量异常",e);
			return false;
		}
	}

	/**
	 * @desc 获取任务奖励列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONArray> getTaskRewardList(String token) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			String userId = userInfo.getId();
			UserTaskReward userTaskReward = new UserTaskReward();
			userTaskReward.setUserId(userId);
			List<UserTaskReward> userTaskRewardList = userTaskRewardDao.findList(userTaskReward);
			JSONArray result = new JSONArray();
			for (UserTaskReward entity : userTaskRewardList) {
				result.add(CommonContact.toJSONObject(entity));
			}
			return ResultUtil.successToJsonArray(result);
		} catch (Exception e) {
			logger.error("获取任务奖励列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取任务列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONArray> getTaskList(String token) {
		try {
			String userId = "";
			if(StringUtils.isNotEmpty(token)){
				UserInfo userInfo = this.getUserInfoByToken(token);
				if(userInfo !=null){
					userId  = userInfo.getId();
				}
			}
			List<TaskInfo> taskInfoList = taskInfoDao.findList(new TaskInfo());
			JSONArray result = new JSONArray();
			for (TaskInfo entity : taskInfoList) {
				JSONObject date = CommonContact.toJSONObject(entity);
				String entityId = entity.getId();
				int finishNum = 0;
				if(CommonContact.isOkUserId(userId)){
					UserTaskReward userTaskReward = this.getUserTaskRewardByUserId(userId,entityId);
					if(userTaskReward != null){
						finishNum = userTaskReward.getFinishNum();
					}
				}
				date.put("finishNum",finishNum);
				result.add(date);
			}
			return ResultUtil.successToJsonArray(result);
		} catch (Exception e) {
			logger.error("获取任务列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
	/**
	 * @desc 获取任务详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONObject> getTaskInfo(TaskInfo taskInfo) {
		try {
			TaskInfo result = taskInfoDao.getByEntity(taskInfo);
			return ResultUtil.successToJson(result);
		} catch (Exception e) {
			logger.error("获取任务详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取用户任务详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONObject> getUserTaskInfo(String token) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserTask result = this.getUserTaskByUserId(userInfo.getId());
			return ResultUtil.successToJson(result);
		} catch (Exception e) {
			logger.error("获取用户任务详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
}
