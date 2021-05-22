package com.nabobsite.modules.nabob.pay.hander;

import com.alibaba.fastjson.JSONObject;
import com.nabobsite.modules.nabob.api.service.simple.SimpleUserService;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.sys.entity.SysChannel;
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
    public JSONObject doRestPay(Order order,SysChannel channel) {
        String orderNo = order.getOrderNo();
        try {
            int source = channel.getChannelSource();
            switch (source) {
                case 1:
                    JSONObject reqData = new JSONObject();
                    return india1OrderService.payOrder(reqData,this.ResultListener(order));
                default:
                    return ContactUtils.failedMsg("未知的支付通道:" + source);
            }
        } catch (Exception e) {
            logger.error ("支付订单分发异常{}",orderNo, e);
            return ContactUtils.failedMsg("支付订单分发异常");
        }
    }

    /**
     * @描述:获取监听
     * @时间:2017年12月19日 下午3:42:31
     */
    public ResultListener ResultListener(Order order) {
        return new ResultListener(){
            @Override
            public JSONObject successHandler (JSONObject resultData) {
                logger.info ("获取监听successHandler结果:{}", resultData);
                if (resultData == null || resultData.isEmpty ()) {
                    return ContactUtils.failedMsg ("下单响应为空,请稍后重试");
                }
                String orderNo = order.getOrderNo();
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
