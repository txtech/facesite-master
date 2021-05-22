/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.StringUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.service.simple.SimpleUserService;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.InstanceUtils;
import com.nabobsite.modules.nabob.cms.task.dao.UserTaskProgressDao;
import com.nabobsite.modules.nabob.cms.task.dao.UserTaskRewardDao;
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;
import com.nabobsite.modules.nabob.cms.task.entity.UserTask;
import com.nabobsite.modules.nabob.cms.task.entity.UserTaskProgress;
import com.nabobsite.modules.nabob.cms.task.entity.UserTaskReward;
import com.nabobsite.modules.nabob.utils.SnowFlakeIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	private UserTaskProgressDao userTaskProgressDao;
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
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			TaskInfo taskInfo = this.getTaskInfoById(taskId);
			if(taskInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10019);
			}
			synchronized (userId) {
				int type = taskInfo.getType();
				int taskNum = taskInfo.getTaskNumber();
				BigDecimal rewardMoney = taskInfo.getRewardMoney();
				UserTask userTask = this.getUserTaskByUserId(userId);
				if(userTask == null){
					return ResultUtil.failed(I18nCode.CODE_10019);
				}

				int taskStatus = userTask.getTaskStatus();
				if(taskStatus == ContactUtils.USER_TASK_STATUS_3){
					logger.error("任务已经完成:{},{}",userId,taskId);
					return ResultUtil.failed(I18nCode.CODE_10102);
				}
				int taskFinishNumber = 0;
				JSONObject taskJson = ContactUtils.str2JSONObject(userTask.getTaskFinishData());
				if(taskJson !=null){
					taskFinishNumber = taskJson.containsKey(taskId)?taskJson.getInteger(taskId):0;
					if(taskFinishNumber >= taskNum){
						logger.error("任务已经完成:{},{}",userId,taskId);
						return ResultUtil.failed(I18nCode.CODE_10102);
					}
				}else{
					taskJson  = new JSONObject();
				}
				String userTaskId = userTask.getId();
				taskJson.put(taskId,taskFinishNumber+1);
				Boolean isOk = this.updateTaskFinishNumber(userTaskId,1,taskJson);
				if(isOk){
					this.sendReward(userId,taskId,type,rewardMoney,taskFinishNumber+1,taskNum);
					return ResultUtil.success(true);
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
	public Boolean sendReward(String userId,String taskId,int type,BigDecimal rewardMoney,int finishNumber,int taskNum) {
		try {
			String title = ContactUtils.USER_ACCOUNT_DETAIL_TITLE_3;
			if(type == ContactUtils.USER_ACCOUNT_REWARD_TYPE_1){
				title = ContactUtils.USER_ACCOUNT_REWARD_TITLE_1;
			}else if(type == ContactUtils.USER_ACCOUNT_REWARD_TYPE_2){
				title = ContactUtils.USER_ACCOUNT_REWARD_TITLE_2;
			}else if(type == ContactUtils.USER_ACCOUNT_REWARD_TYPE_3){
				title = ContactUtils.USER_ACCOUNT_REWARD_TITLE_3;
			}else if(type == ContactUtils.USER_ACCOUNT_REWARD_TYPE_4){
				title = ContactUtils.USER_ACCOUNT_REWARD_TITLE_4;
			}
			UserTaskReward userTaskReward = InstanceUtils.initUserTaskReward(userId,taskId,type,title,finishNumber,taskNum,rewardMoney);
			userTaskReward.setUserId(userId);
			long dbResult = userTaskRewardDao.insert(userTaskReward);
			if(!ContactUtils.dbResult(dbResult)){
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
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean updateTaskFinishNumber(String id,int finishNumber,JSONObject taskJson){
		try {
			UserTask userTaskPrams = new UserTask();
			userTaskPrams.setId(id);
			userTaskPrams.setTaskOrderNum(finishNumber);
			userTaskPrams.setTaskFinishData(taskJson.toJSONString());
			long dbResult = userTaskDao.update(userTaskPrams);
			return ContactUtils.dbResult(dbResult);
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
	public CommonResult<List<UserTaskReward>> getTaskRewardList(String token) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserTaskReward userTaskReward = new UserTaskReward();
			userTaskReward.setUserId(userId);
			List<UserTaskReward> userTaskRewardList = userTaskRewardDao.findList(userTaskReward);
			return ResultUtil.success(userTaskRewardList,true);
		} catch (Exception e) {
			logger.error("获取任务奖励列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取任务奖励列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<List<UserTaskProgress>> getCompletings(String token) {
		try {
			List<UserTaskProgress> result = userTaskProgressDao.findList(new UserTaskProgress());
			if(result == null || result.size()<100){
				this.autoInitUserTaskProgress(5);
			}else{
				this.autoInitUserTaskProgress(1);
			}
			return ResultUtil.success(result,true);
		} catch (Exception e) {
			logger.error("获取任务奖励列表异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
	/**
	 * @desc 保存任务奖励列表
	 * @author nada
	 * @create 2021/5/21 3:39 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean autoInitUserTaskProgress(int num) {
		try {
			for (int i = 0; i < num ; i++) {
				ArrayList<String> list = new ArrayList(ContactUtils.PHONE_NUM_AREA_CODE);
				int randomIndex = new Random().nextInt(list.size());
				String areaCode = list.get(randomIndex);
				int code = SnowFlakeIDGenerator.getRandom4();
				String phone = areaCode+"****"+code;
				UserTaskProgress userTaskProgress = new UserTaskProgress();
				userTaskProgress.setIsNewRecord(true);
				userTaskProgress.setInContent("टास्क पूरा हुआ，रूपया RS निकाले1000");
				userTaskProgress.setContent("mission completed, RS has been withdrawn1000");
				userTaskProgress.setPhone(phone);
				userTaskProgress.setNum(1000);
				userTaskProgressDao.insert(userTaskProgress);
			}
			return true;
		} catch (Exception e) {
			logger.error("获取任务奖励列表异常",e);
			return false;
		}
	}

	/**
	 * @desc 获取任务列表
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	public CommonResult<List<TaskInfo>> getTaskList(String token) {
		try {
			JSONObject taskJson = new JSONObject();
			if(StringUtils.isNotEmpty(token)){
				String userId  = this.getUserIdByToken(token);
				taskJson = this.getUserTaskNumJsonByUserId(userId);
			}
			List<TaskInfo> newList = new ArrayList<>();
			List<TaskInfo> taskInfoList = taskInfoDao.findList(new TaskInfo());
			for (TaskInfo entity : taskInfoList) {
				int	finishNum = 0;
				if(taskJson !=null){
					String taskId = entity.getId();
					finishNum = taskJson.containsKey(taskId)?taskJson.getInteger(taskId):0;
				}
				entity.setFinishNum(finishNum);
				newList.add(entity);
			}
			return ResultUtil.success(newList);
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
	public CommonResult<TaskInfo> getTaskInfo(TaskInfo taskInfo) {
		try {
			TaskInfo result = taskInfoDao.getByEntity(taskInfo);
			return ResultUtil.success(result);
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
	public CommonResult<UserTask> getUserTaskInfo(String token) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserTask result = this.getUserTaskByUserId(userId);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("获取用户任务详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
}
