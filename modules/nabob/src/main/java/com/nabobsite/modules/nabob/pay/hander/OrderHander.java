package com.nabobsite.modules.nabob.pay.hander;

import com.alibaba.fastjson.JSONObject;
import com.nabobsite.modules.nabob.api.service.simple.SimpleUserService;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.pay.common.ResultListener;
import com.nabobsite.modules.nabob.pay.order.payorder.India1PayOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/17 2:35 下午
 * @Version 1.0
 */
@Service
@Transactional(readOnly=true)
public class OrderHander extends SimpleUserService {

    @Autowired
    private India1PayOrderServiceImpl india1OrderService;

    /**
     * @描述:支付订单分发
     * @时间:2017年12月28日 上午11:19:37
     */
    public JSONObject doRestPay(JSONObject reqData, Order order) {
        try {
            int source = reqData.getIntValue("channelSource");
            switch (source) {
                case 50:
                    //return  customCardPayService.payOrder(reqData,tradeCmd.tradResultListener (reqData));
                case 51:
                    //return  customAlipayPayService.payOrder(reqData,tradeCmd.tradResultListener (reqData));
                default:
                    return CommonContact.failedMsg ("未知的支付通道:" + source);
            }
        } catch (Exception e) {
            logger.error ("支付订单分发异常{}", reqData, e);
            return CommonContact.failedMsg ("支付订单分发异常");
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
                    return CommonContact.failedMsg ("下单响应为空,请稍后重试");
                }
                String code = reqData.getString ("code");
                String orderNo = reqData.getString ("orderNo");
                if (!resultData.containsKey ("code")) {
                    String msg = resultData.containsKey ("msg")?resultData.getString ("msg"):"下单失败";
                    return CommonContact.failedMsg (msg);
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
                    return CommonContact.failedMsg ("下单响应为空,请稍后重试");
                }
                String message = resultData.containsKey ("msg") ? resultData.getString ("msg") : "下单失败,请稍后重试";
                //updateBaseOrderStatus(reqData.getString ("vcOrderNo"), 2, message,"","","");
                return CommonContact.failedMsg ( message);
            }
        };
    }

}
