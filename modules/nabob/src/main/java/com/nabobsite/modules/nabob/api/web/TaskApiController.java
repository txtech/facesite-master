/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.utils.UserUtils;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "用户任务接口(需要登陆)")
public class TaskApiController extends BaseController {

	@Autowired
	private TaskApiService taskApiService;

	@ApiOperation(value = "用户任务")
	@PostMapping(value = "doUserTask/{taskId}")
	public CommonResult<Boolean> doUserTask(@PathVariable String taskId,HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		return taskApiService.doUserTask(taskId,token);
	}

	@RequestMapping(value = {"getTaskInfo/{taskId}"})
	@ApiOperation(value = "获取任务详情")
	public CommonResult<TaskInfo> getTaskInfo(@PathVariable String taskId,HttpServletRequest request) {
		String token = request.getHeader(CommonContact.AUTHORIZATION);
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setId(taskId);
		return taskApiService.getTaskInfo(taskInfo,token);
	}
}
