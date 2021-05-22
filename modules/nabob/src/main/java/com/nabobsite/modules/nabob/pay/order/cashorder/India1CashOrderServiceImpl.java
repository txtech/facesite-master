package com.nabobsite.modules.nabob.pay.order.cashorder;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.DateUtils;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.pay.common.ResultListener;
import com.nabobsite.modules.nabob.utils.Md5CoreUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/17 2:35 下午
 * @Version 1.0
 */
@Service
public class India1CashOrderServiceImpl {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String merchNo = "10026";
    private final static String merchKey = "p9fmt5ydl4oq9iwkkdqj02j0uif8gszi";

    private final static String testCashUrl = "http://www.cbwpay.in/Payment_Withdraw_singleOrder.html";
    private final static String productCashUrl = "http://www.cbwwpay.in/Payment_Withdraw_singleOrder.html";

    private final static String testCashQueryUrl = "http://www.cbwpay.in/Payment_Withdraw_query.html";
    private final static String productCashQueryUrl = "http://www.cbwwpay.in/Payment_Withdraw_query.html";


    private final static String returnUrl = "http://www.cbwwpay.in";

    /**
     * @desc 印度1支付接口
     * @author nada
     * @create 2021/5/17 3:47 下午
    */
    public JSONObject cashOrder(JSONObject reqData, ResultListener listener){
        try {
            String orderNo = reqData.containsKey("orderNo")?reqData.getString("orderNo"):"";
            String amount = reqData.containsKey ("payAmount")?reqData.getString ("payAmount"):"";

            String payApplydate = DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
            JSONObject param = new JSONObject();
            param.put("pay_memberid",merchNo);
            param.put("out_trade_no",orderNo);
            param.put("money",amount);
            param.put("bankname","929");//银行编号
            param.put("accountname","929");//收款人姓名
            param.put("cardnumber","929");//银行卡号
            param.put("province","IFSC");//省份
            param.put("notifyurl",returnUrl);//回调地址
            param.put("pay_md5sign", Md5CoreUtil.getMd5Sign(param,merchKey).toUpperCase());
            String response = HttpRequest.post(testCashUrl).body(param.toString()).execute().body();
            logger.info("印度1支付接口响应:{}",response);
            if(StringUtils.isEmpty(response)){
                JSONObject result = ContactUtils.failedMsg("下单响应为空");
                return listener.paddingHandler(result);
            }
            JSONObject resData = JSONObject.parseObject(response);
            if(resData == null || resData.isEmpty()){
                JSONObject result = ContactUtils.failedMsg("下单解析为空");
                return listener.paddingHandler(result);
            }
            String msg = resData.containsKey("msg")?resData.getString("msg"):"";
            String status = resData.containsKey("status")?resData.getString("status"):"";
            if(!"success".equalsIgnoreCase(status)){
                JSONObject result = ContactUtils.failedMsg("下单失败:"+msg);
                return listener.failedHandler(result);
            }
            String refCode = resData.containsKey("refCode")?resData.getString("refCode"):"";
            String pOrderNo = resData.containsKey("transaction_id")?resData.getString("transaction_id"):"";
            if("1".equalsIgnoreCase(refCode)){
                JSONObject result = ContactUtils.successMsg("成功");
                result.put("pOrderNo",pOrderNo);
                return listener.successHandler(result);
            }else if("2".equalsIgnoreCase(refCode) || "5".equalsIgnoreCase(refCode)){
                JSONObject result = ContactUtils.successMsg("失败："+msg);
                result.put("pOrderNo",pOrderNo);
                return listener.failedHandler(result);
            }else {
                JSONObject result = ContactUtils.failedMsg("处理中:"+msg);
                return listener.paddingHandler(result);
            }
        } catch (Exception e) {
            logger.error("印度1支付接口异常",e);
            return listener.paddingHandler(ContactUtils.failedMsg("下单接口超时"));
        }
    }

    /**
     * @desc 印度1出款查询接口
     * @author nada
     * @create 2021/5/17 4:22 下午
    */
    public JSONObject cashOrderQuery(JSONObject reqData, ResultListener listener){
        try {
            String orderNo = reqData.containsKey("orderNo")?reqData.getString("orderNo"):"";
            JSONObject param = new JSONObject();
            param.put("pay_memberid",merchNo);
            param.put("out_trade_no",orderNo);
            param.put("pay_md5sign", Md5CoreUtil.getMd5Sign(param,merchKey).toUpperCase());
            String response = HttpRequest.post(testCashQueryUrl).body(param.toString()).execute().body();
            logger.info("印度1出款查询接口响应:{}",response);
            if(StringUtils.isEmpty(response)){
                JSONObject result = ContactUtils.failedMsg("响应为空");
                return listener.paddingHandler(result);
            }
            JSONObject resData = JSONObject.parseObject(response);
            if(resData == null || resData.isEmpty()){
                JSONObject result = ContactUtils.failedMsg("解析为空");
                return listener.paddingHandler(result);
            }
            String refMsg = resData.containsKey("refMsg")?resData.getString("refMsg"):"";
            String pOrderNo = resData.containsKey("transaction_id")?resData.getString("transaction_id"):"";
            String msg = resData.containsKey("msg")?resData.getString("msg"):"";
            String status = resData.containsKey("status")?resData.getString("status"):"";
            String refCode = resData.containsKey("refCode")?resData.getString("refCode"):"";
            if(!"success".equalsIgnoreCase(status)){
                JSONObject result = ContactUtils.failedMsg("失败:"+msg);
                return listener.paddingHandler(result);
            }
            if("1".equalsIgnoreCase(refCode)){
                JSONObject result = ContactUtils.successMsg("成功");
                result.put("pOrderNo",pOrderNo);
                return listener.successHandler(result);
            }else if("2".equalsIgnoreCase(refCode) || "5".equalsIgnoreCase(refCode)){
                JSONObject result = ContactUtils.successMsg("失败："+refMsg);
                result.put("pOrderNo",pOrderNo);
                return listener.failedHandler(result);
            }else {
                JSONObject result = ContactUtils.failedMsg("处理中:"+refMsg);
                return listener.paddingHandler(result);
            }
        } catch (Exception e) {
            logger.error("印度1出款查询接口异常",e);
            return listener.paddingHandler(ContactUtils.failedMsg("印度1出款查询接口超时"));
        }
    }
}
