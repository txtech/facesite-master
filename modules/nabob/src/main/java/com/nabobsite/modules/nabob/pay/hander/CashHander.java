package com.nabobsite.modules.nabob.pay.hander;

import com.alibaba.fastjson.JSONObject;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.service.simple.SimpleUserService;
import com.nabobsite.modules.nabob.cms.order.entity.OrderCash;
import com.nabobsite.modules.nabob.pay.common.ResultListener;
import com.nabobsite.modules.nabob.pay.order.payorder.India1PayOrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @类名称:ReplaceServiceImpl.java
 * @时间:2017年12月19日下午5:31:01
 * @版权:公司 Copyright (c) 2017
 */
@Service
public class CashHander extends SimpleUserService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private India1PayOrderServiceImpl india1OrderService;

    /**
     * @描述:第三方接口代付提现接口
     * @时间:2017年12月14日 下午4:29:05
     */
    public JSONObject doRestReplace(JSONObject reqData, OrderCash cash){
        try {
            logger.info("路由完成开始执行代付==> {}",reqData);
            int route = reqData.getIntValue("channelSource");
            switch (route) {
                case 1:
                    //return insteadReplaceService.replaceOrder(reqData,transferCmd.transResultListener(reqData));
                case 3:
                    //return alipayCustomerReplaceService.replaceOrder(reqData,transferCmd.transResultListener(reqData));
	            default:
                    //return insteadReplaceService.replaceOrder(reqData,transferCmd.transResultListener(reqData));
            }
            return ContactUtils.failedMsg("代付提现接口失败");
        } catch (Exception e) {
            logger.error("代入提现接口异常{}",reqData, e);
            return ContactUtils.failedMsg("代付提现接口异常");
        }
    }

    /**
     * @描述:获取监听
     * @时间:2017年12月19日 下午3:42:31
     */
    public ResultListener ResultListener(JSONObject reqData) {
        return new ResultListener(){
            @Override
            public JSONObject successHandler (JSONObject resultData) {
                logger.info ("获取监听successHandler结果:{}", resultData);
                if (resultData == null || resultData.isEmpty ()) {
                    return ContactUtils.failedMsg ("下单响应为空,请稍后重试");
                }
                String code = reqData.getString ("code");
                String orderNo = reqData.getString ("orderNo");
                if (!resultData.containsKey ("code")) {
                    String msg = resultData.containsKey ("msg")?resultData.getString ("msg"):"下单失败";
                    return ContactUtils.failedMsg (msg);
                }
                //updateBaseOrderStatus (vcOrderNo, 1, "下单成功",pOrder,"","");
                return resultData;
            }
            @Override
            public JSONObject paddingHandler (JSONObject resultData) {
                logger.info ("监听paddingHandler结果:{}", resultData);
                return resultData;
            }

            @Override
            public JSONObject failedHandler (JSONObject resultData) {
                logger.info ("监听failedHandler结果:{}", resultData);
                if (resultData == null || resultData.isEmpty ()) {
                    return ContactUtils.failedMsg ("下单响应为空,请稍后重试");
                }
                String message = resultData.containsKey ("msg") ? resultData.getString ("msg") : "下单失败,请稍后重试";
                //updateBaseOrderStatus(reqData.getString ("vcOrderNo"), 2, message,"","","");
                return ContactUtils.failedMsg ( message);
            }
        };
    }
}
