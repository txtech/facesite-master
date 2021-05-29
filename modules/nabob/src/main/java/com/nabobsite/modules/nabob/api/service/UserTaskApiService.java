/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.StringUtils;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.InstanceUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.service.simple.SimpleUserService;
import com.nabobsite.modules.nabob.cms.product.dao.ProductWarehouseProgressDao;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouseProgress;
import com.nabobsite.modules.nabob.cms.task.dao.TaskUserRewardDao;
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;
import com.nabobsite.modules.nabob.cms.task.entity.TaskUserReward;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountTask;
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
public class UserTaskApiService extends ProductApiService {

	@Autowired
	private UserAccountApiService userAccountApiService;

	/**
	 * @desc 任务提现接口
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> doWithDrawTaskReward(String token) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failedAuthorization(I18nCode.CODE_10001);
			}
			UserAccountTask oldUserAccountTask = this.getUserAccountTaskByUserId(userId);
			if(oldUserAccountTask == null){
				return ResultUtil.failed(I18nCode.CODE_10009);
			}
			String userTaskId = oldUserAccountTask.getId();
			BigDecimal taskInitialNum =	oldUserAccountTask.getTaskInitialNum();
			int taskOrderNum =	oldUserAccountTask.getTaskOrderNum();
			BigDecimal totalRewardMoney =	oldUserAccountTask.getTotalRewardMoney();
			if(ContactUtils.isLesserOrEqualZero(taskInitialNum) || ContactUtils.isLesserOrEqualZero(totalRewardMoney)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			if(ContactUtils.isLesser(taskInitialNum,totalRewardMoney)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}

			UserAccountTask userAccountTask = new UserAccountTask();
			userAccountTask.setUserId(userId);
			userAccountTask.setTaskInitialNum(taskInitialNum.negate());
			long dbResult = userAccountTaskDao.updateUserAccountTask(userAccountTask);
			if(ContactUtils.dbResult(dbResult)){
				return ResultUtil.failed(I18nCode.CODE_10100);
			}
			String title = "提取奖励";
			Boolean isOk = userAccountApiService.updateAccountRewardMoney(userId,taskInitialNum,userTaskId,title);
			if(isOk){
				return ResultUtil.success(true);
			}
			return ResultUtil.success(false);
		} catch (Exception e) {
			logger.error("任务提现接口异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

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
				return ResultUtil.failedAuthorization(I18nCode.CODE_10001);
			}
			TaskInfo taskInfo = this.getTaskInfoById(taskId);
			if(taskInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10019);
			}
			synchronized (userId) {
				//任务类型 1:分享好友 2:观看视频 3:邀请好友 4:定期投资
				int type = taskInfo.getType();
				int taskNum = taskInfo.getTaskNumber();
				BigDecimal rewardMoney = taskInfo.getRewardMoney();
				UserAccountTask userAccountTask = this.getUserAccountTaskByUserId(userId);
				if(userAccountTask == null){
					return ResultUtil.failed(I18nCode.CODE_10019);
				}

				String userTaskId = userAccountTask.getId();
				int taskStatus = userAccountTask.getTaskStatus();
				String taskFinishData = userAccountTask.getTaskFinishData();
				if(taskStatus == ContactUtils.USER_TASK_STATUS_2){
					logger.error("任务已经完成:{},{}",userId,taskId);
					return ResultUtil.failed(I18nCode.CODE_10102);
				}

				int taskFinishNumber = 0;
				JSONObject taskJson = ContactUtils.str2JSONObject(taskFinishData);
				if(taskJson !=null){
					taskFinishNumber = taskJson.containsKey(taskId)?taskJson.getInteger(taskId):0;
					if(taskFinishNumber >= taskNum){
						logger.error("任务已经完成:{},{}",userId,taskId);
						return ResultUtil.failed(I18nCode.CODE_10102);
					}
				}
				boolean isFishTask = false;
				if(taskFinishNumber+1 >= taskNum){
					isFishTask = true;
				}
				taskJson.put(taskId,taskFinishNumber+1);
				Boolean isOk = this.updateTaskFinishNumber(userTaskId,1,taskJson,isFishTask);
				if(isOk){
					isOk = this.sendReward(userId,taskId,type,rewardMoney,taskFinishNumber+1,taskNum);
					logger.error("任务奖励:{},{},{}",userId,taskId,isOk);
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
	 * 用户任务奖励
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean doUserTaskJob() {
		try {
			long dbResult = userAccountTaskDao.updateCleantAccountTaskJob();
			return ContactUtils.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("用户任务奖励异常",e);
			return false;
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
			TaskUserReward taskUserReward = InstanceUtils.initUserTaskReward(userId,taskId,type,title,finishNumber,taskNum,rewardMoney);
			taskUserReward.setUserId(userId);
			long dbResult = taskUserRewardDao.insert(taskUserReward);
			if(!ContactUtils.dbResult(dbResult)){
				return false;
			}
			UserAccountTask userAccountTask = new UserAccountTask();
			userAccountTask.setUserId(userId);
			userAccountTask.setTaskOrderNum(taskNum);
			userAccountTask.setTaskInitialNum(rewardMoney);
			dbResult = userAccountTaskDao.updateUserAccountTask(userAccountTask);
			if(ContactUtils.dbResult(dbResult)){
				return true;
			}
			return false;
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
	public Boolean updateTaskFinishNumber(String id,int finishNumber,JSONObject taskJson,boolean isFishTask){
		try {
			UserAccountTask userTaskPrams = new UserAccountTask();
			userTaskPrams.setId(id);
			userTaskPrams.setTaskOrderNum(finishNumber);
			userTaskPrams.setTaskFinishData(taskJson.toJSONString());
			if(isFishTask){
				userTaskPrams.setTaskStatus(ContactUtils.USER_TASK_STATUS_2);
			}else{
				userTaskPrams.setTaskStatus(ContactUtils.USER_TASK_STATUS_1);
			}
			long dbResult = userAccountTaskDao.update(userTaskPrams);
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
	public CommonResult<List<TaskUserReward>> getTaskRewardList(String token,TaskUserReward taskUserReward) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failedAuthorization(I18nCode.CODE_10001);
			}
			taskUserReward.setUserId(userId);
			List<TaskUserReward> userTaskRewardList = taskUserRewardDao.findList(taskUserReward);
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
	public CommonResult<List<ProductWarehouseProgress>> getCompletingsList(ProductWarehouseProgress productWarehouseProgress) {
		try {
			List<ProductWarehouseProgress> result = userTaskProgressDao.findList(productWarehouseProgress);
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
				ProductWarehouseProgress userTaskProgress = new ProductWarehouseProgress();
				userTaskProgress.setIsNewRecord(true);
				userTaskProgress.setInContent("टास्क पूरा हुआ，रूपया RS निकाले1000");
				userTaskProgress.setContent("mission completed, RS has been withdrawn1000");
				userTaskProgress.setPhone(phone);
				userTaskProgress.setNum(new BigDecimal(1000));
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
	public CommonResult<List<TaskInfo>> getTaskList(String token,TaskInfo taskInfo) {
		try {
			JSONObject taskJson = new JSONObject();
			if(StringUtils.isNotEmpty(token)){
				String userId  = this.getUserIdByToken(token);
				taskJson = this.getUserTaskNumJsonByUserId(userId);
			}
			List<TaskInfo> newList = new ArrayList<>();
			List<TaskInfo> taskInfoList = taskInfoDao.findList(taskInfo);
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
	public CommonResult<UserAccountTask> getUserTaskInfo(String token) {
		try {
			String userId  = this.getUserIdByToken(token);
			if(!ContactUtils.isOkUserId(userId)){
				return ResultUtil.failedAuthorization(I18nCode.CODE_10001);
			}
			UserAccountTask result = this.getUserAccountTaskByUserId(userId);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("获取用户任务详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
}
