package com.nabobsite.modules.nabob.pay.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.pay.service.CommonCallbackService;
import com.nabobsite.modules.nabob.utils.HttpRequestTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/12 6:34 下午
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "${frontPath}/api/payCallBack")
@ConditionalOnProperty(name="web.swagger.pay.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "印度1支付支付回调接口")
public class India1PayCallback extends BaseController {

    @Autowired
    private CommonCallbackService commonCallbackService;

    @ApiOperation(value = "印度1支付回调")
    @RequestMapping(value = "India1PayCallback", produces = "text/html;charset=UTF-8")
    public String customCallback(HttpServletRequest request, HttpServletResponse response) {
        try {
            CommonContact.initHttpServletRequest (request, response);
            JSONObject reqData = HttpRequestTools.getRequestJson (request);
            logger.info("异步通知接口收数据:{}", reqData);
            if (reqData == null || reqData.isEmpty()) {
                return "ERROR";
            }
            String amount = reqData.containsKey("amount")?reqData.getString("amount"):"";
            String orderNo = reqData.containsKey("orderid")?reqData.getString("orderid"):"";
            String returncode = reqData.containsKey("returncode")?reqData.getString("returncode"):"";
            String pOrderNo = reqData.containsKey("transaction_id")?reqData.getString("transaction_id"):"";
            if(StringUtils.isAnyEmpty(orderNo,returncode,pOrderNo,amount)){
                logger.error("回调接口失败,自定义支付参数为空");
                return "ERROR";
            }
            //1: 待支付 2:支付中 3:支付失败 4:支付成功 9:退款
            int backStatus = 2;
            if("00".equalsIgnoreCase(returncode)){
                backStatus = 4;
            }
            Boolean result = commonCallbackService.callBack(orderNo,pOrderNo,backStatus,"支付回调");
            if(result){
                return "OK";
            }
            return "ERROR";
        } catch (Exception e) {
            logger.error("异步通知接口收数据异常", e);
            return "ERROR";
        }
    }
}
