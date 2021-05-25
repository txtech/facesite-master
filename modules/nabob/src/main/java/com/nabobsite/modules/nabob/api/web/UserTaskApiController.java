/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.service.TaskApiService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouseProgress;
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;
import com.nabobsite.modules.nabob.cms.task.entity.TaskUserReward;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 账户中心
 * @author nada
 * @version 2021-01-11
 */
@RestController
@RequestMapping(value = "${frontPath}/api/task")
@ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "用户任务接口(需要登陆)")
public class UserTaskApiController extends BaseController {

	@Autowired
	private TaskApiService taskApiService;

	@ApiOperation(value = "用户任务")
	@PostMapping(value = "doUserTask/{taskId}")
	public CommonResult<Boolean> doUserTask(@PathVariable String taskId,HttpServletRequest request) {
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return taskApiService.doUserTask(taskId,token);
	}

	@RequestMapping(value = {"getTaskInfo/{taskId}"})
	@ApiOperation(value = "获取任务详情")
	public CommonResult<TaskInfo> getTaskInfo(@PathVariable String taskId, HttpServletRequest request) {
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setId(taskId);
		return taskApiService.getTaskInfo(taskInfo);
	}

	@RequestMapping(value = {"getUserTaskInfo"})
	@ApiOperation(value = "获取用户任务信息")
	public CommonResult<UserAccountTask> getTaskInfo(HttpServletRequest request) {
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return taskApiService.getUserTaskInfo(token);
	}

	@RequestMapping(value = {"getTaskRewardList"})
	@ApiOperation(value = "获取任务奖励列表")
	public CommonResult<List<TaskUserReward>> getTaskRewardList(HttpServletRequest request) {
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return taskApiService.getTaskRewardList(token);
	}

	@RequestMapping(value = {"getCompletings"})
	@ApiOperation(value = "获取任务进行中列表")
	public CommonResult<List<ProductWarehouseProgress>> getCompletings(HttpServletRequest request) {
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return taskApiService.getCompletings(token);
	}

	@RequestMapping(value = {"doWithDrawTaskReward"})
	@ApiOperation(value = "任务提现接口")
	public CommonResult<Boolean> doWithDrawTaskReward(HttpServletRequest request) {
		String token = request.getHeader(ContactUtils.AUTHORIZATION);
		return taskApiService.doWithDrawTaskReward(token);
	}
}
