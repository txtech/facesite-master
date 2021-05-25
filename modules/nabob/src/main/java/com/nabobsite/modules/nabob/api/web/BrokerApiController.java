/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
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
 * 经纪人接口
 * @author nada
 * @version 2021-01-11
 */
@RestController
@RequestMapping(value = "${frontPath}/api/broker")
@ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "经纪人接口(需要登陆)")
public class BrokerApiController extends BaseController {

	@RequestMapping(value = {"economicInfo"})
	@ApiOperation(value = "获取经纪人详情")
	public CommonResult<JSONObject> economicInfo(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		result.put("is_economic",2);
		return ResultUtil.success(result);
	}

	@RequestMapping(value = {"applyEconomic"})
	@ApiOperation(value = "申请经纪人")
	public CommonResult<JSONObject> applyEconomic(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		result.put("flag",false);
		return ResultUtil.success(result);
	}

	@RequestMapping(value = {"applyCondition"})
	@ApiOperation(value = "经纪人条件")
	public CommonResult<JSONObject> applyCondition(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		result.put("condition1","the number of people involved in the promotion300");
		result.put("condition2","the amount of input capitalRS100");
		return ResultUtil.success(result);
	}
}
