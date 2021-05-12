package com.nabobsite.modules.nabob.pay.web;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.nabobsite.modules.nabob.pay.service.CommonCallbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/12 6:34 下午
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "${frontPath}/api/payCallBack")
@ConditionalOnProperty(name="web.swagger.pay.enabled", havingValue="true", matchIfMissing=true)
@Api(tags = "自定义支付回调接口")
public class CustomPayCallback extends BaseController {

    @Autowired
    private CommonCallbackService commonCallbackService;

    @PostMapping(value = {"custom"})
    @ApiOperation(value = "自定义回调通道")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "支付订单", required = true, paramType="query", type="String"),
            @ApiImplicitParam(name = "pOrderNo",value = "上游订单号"),
            @ApiImplicitParam(name = "status",value = "支付状态"),
    })
    public String customCallback(String orderNo,String status,String pOrderNo) {
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
