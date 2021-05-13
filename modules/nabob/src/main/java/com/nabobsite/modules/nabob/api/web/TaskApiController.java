/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.utils.UserUtils;
import com.nabobsite.modules.nabob.api.service.ProductApiService;
import com.nabobsite.modules.nabob.api.service.TaskApiService;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.ApiListing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 账户中心
 * @author nada
 * @version 2021-01-11
 */
@RestController
@RequestMapping(value = "${frontPath}/api/task")
@Api(tags = "新用户任务接口")
public class TaskApiController extends BaseController {

	@Autowired
	private TaskApiService taskApiService;

	@PostMapping(value = {"getTaskList"})
	@ApiOperation(value = "获取任务列表")
	public String getTaskList() {
		CommonResult<List<TaskInfo>> result = taskApiService.getTaskList(new TaskInfo());
		return renderResult(Global.TRUE,text("getTaskList"), result);
	}

	@PostMapping(value = {"getTaskInfo"})
	@ApiOperation(value = "获取任务详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "taskId", value = "任务ID", required = true, paramType="query", type="String"),})
	public String getTaskInfo(String taskId) {
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setId(taskId);
		CommonResult<TaskInfo> result = taskApiService.getTaskInfo(taskInfo);
		return renderResult(Global.TRUE,text("getTaskInfo"), result);
	}

	@ApiOperation(value = "新用户做任务")
	@RequestMapping(value = "doUserTask")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "taskId", value = "任务ID", required = true, paramType="query", type="String"),
			@ApiImplicitParam(name = "param_token",  value = "会话token", required = true),
	})
	public String doUserTask(String taskId,String param_token) {
		CommonResult<Boolean> result = taskApiService.doUserTask(taskId,param_token);
		return renderResult(Global.TRUE, text("userTask"),result);
	}
}
