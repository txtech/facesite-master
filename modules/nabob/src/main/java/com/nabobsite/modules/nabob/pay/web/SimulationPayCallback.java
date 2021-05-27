package com.nabobsite.modules.nabob.pay.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.pay.service.CommonCallbackService;
import com.nabobsite.modules.nabob.utils.HttpRequestTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/12 6:34 下午
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "${frontPath}/api/open/payCallBack")
@ConditionalOnProperty(name="web.swagger.pay.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "模拟支付回调接口")
public class SimulationPayCallback extends BaseController {

    @Autowired
    private CommonCallbackService commonCallbackService;

    @PostMapping(value = {"SimulationPayCallback"})
    @ApiOperation(value = "模拟支付回调")
    public String customCallback(HttpServletRequest request, HttpServletResponse response) {
        ContactUtils.initHttpServletRequest (request, response);
        JSONObject reqData = HttpRequestTools.getRequestJson (request);
        logger.info("异步通知接口收数据:{}", reqData);
        if (reqData == null || reqData.isEmpty()) {
            return "ERROR";
        }
        String status = reqData.containsKey("status")?reqData.getString("status"):"";
        String orderNo = reqData.containsKey("orderNo")?reqData.getString("orderNo"):"";
        String pOrderNo = reqData.containsKey("pOrderNo")?reqData.getString("pOrderNo"):"";
        if(StringUtils.isAnyEmpty(orderNo,status)){
            logger.error("回调接口失败,自定义支付参数为空");
            return "ERROR";
        }
        int backStatus = Integer.valueOf(status);
        Boolean result = commonCallbackService.callBack(orderNo,pOrderNo,backStatus,"支付回调");
        if(result){
            return "OK";
        }
        return "ERROR";
    }
}
