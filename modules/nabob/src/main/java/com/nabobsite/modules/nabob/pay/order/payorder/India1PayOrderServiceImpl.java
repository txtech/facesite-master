package com.nabobsite.modules.nabob.pay.order.payorder;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.DateUtils;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
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
public class India1PayOrderServiceImpl {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String merchNo = "10026";
    private final static String merchKey = "p9fmt5ydl4oq9iwkkdqj02j0uif8gszi";

    private final static String testUrl = "http://www.cbwpay.in";
    private final static String productUrl = "http://www.cbwwpay.in";
    private final static String returnUrl = "http://www.cbwwpay.in";

    /**
     * @desc 印度1支付接口
     * @author nada
     * @create 2021/5/17 3:47 下午
    */
    public JSONObject payOrder(JSONObject reqData, ResultListener listener){
        try {
            if(true){
                JSONObject result = CommonContact.successMsg("下单成功");
                result.put("pOrderNo","123456");
                return listener.successHandler(result);
            }
            String payUrl = testUrl + "/Pay_Index.html";
            String orderNo = reqData.containsKey("orderNo")?reqData.getString("orderNo"):"";
            String amount = reqData.containsKey ("payAmount")?reqData.getString ("payAmount"):"";

            String payApplydate = DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
            JSONObject param = new JSONObject();
            param.put("pay_memberid",merchNo);
            param.put("pay_orderid",orderNo);
            param.put("pay_amount",amount);
            param.put("pay_applydate",payApplydate);
            param.put("pay_bankcode","929");
            param.put("pay_notifyurl",returnUrl);//服务端通知
            param.put("pay_callbackurl",returnUrl);//页面跳转通知
            param.put("origin_bank_code","");//目标银行编号 //银行编码	银行名称//BAY Bank of Ayudhya //KTB	Krung Thai Bank //BBL	BANGKOK BANK //KBANK	Kasikornbank
            param.put("bank_card_number","");//银行卡号	泰国网银直连必须	泰国网银直连参与
            param.put("pay_md5sign", Md5CoreUtil.getMd5Sign(param,merchKey).toUpperCase());//MD5签名
            String response = HttpRequest.post(payUrl).body(param.toString()).execute().body();
            logger.info("印度1支付接口响应:{}",response);
            if(StringUtils.isEmpty(response)){
                JSONObject result = CommonContact.failedMsg("下单响应为空");
                return listener.paddingHandler(result);
            }
            JSONObject resData = JSONObject.parseObject(response);
            if(resData == null || resData.isEmpty()){
                JSONObject result = CommonContact.failedMsg("下单解析为空");
                return listener.paddingHandler(result);
            }
            String msg = resData.containsKey("msg")?resData.getString("msg"):"";
            String status = resData.containsKey("status")?resData.getString("status"):"";
            if(!"success".equalsIgnoreCase(status)){
                JSONObject result = CommonContact.failedMsg("下单失败:"+msg);
                return listener.failedHandler(result);
            }
            JSONObject dataJson = resData.containsKey("data")?resData.getJSONObject("data"):null;
            if(dataJson == null){
                JSONObject result = CommonContact.failedMsg("dataJson解析为空");
                return listener.paddingHandler(result);
            }
            String pOrderNo = dataJson.containsKey("out_order_no")?dataJson.getString("out_order_no"):"";
            JSONObject result = CommonContact.successMsg("下单成功");
            result.put("pOrderNo",pOrderNo);
            return listener.successHandler(result);
        } catch (Exception e) {
            logger.error("印度1支付接口异常",e);
            return listener.paddingHandler(CommonContact.failedMsg("下单接口超时"));
        }
    }



    /**
     * @desc 印度1支付查询接口
     * @author nada
     * @create 2021/5/17 3:47 下午
     */
    public JSONObject payOrderQuery(JSONObject reqData, ResultListener listener){
        try {
            String payUrl = testUrl + "/Pay_Trade_query.html";
            String orderNo = reqData.containsKey("orderNo")?reqData.getString("orderNo"):"";
            JSONObject param = new JSONObject();
            param.put("pay_memberid",merchNo);
            param.put("pay_orderid",orderNo);
            param.put("pay_md5sign", Md5CoreUtil.getMd5Sign(param,merchKey).toUpperCase());
            String response = HttpRequest.post(payUrl).body(param.toString()).execute().body();
            logger.info("印度1支付接口响应:{}",response);
            if(StringUtils.isEmpty(response)){
                JSONObject result = CommonContact.failedMsg("下单响应为空");
                return listener.paddingHandler(result);
            }
            JSONObject resData = JSONObject.parseObject(response);
            if(resData == null || resData.isEmpty()){
                JSONObject result = CommonContact.failedMsg("下单解析为空");
                return listener.paddingHandler(result);
            }
            String msg = resData.containsKey("msg")?resData.getString("msg"):"";
            String orderid = resData.containsKey("orderid")?resData.getString("orderid"):"";
            String returncode = resData.containsKey("returncode")?resData.getString("returncode"):"";
            String pOrderNo = resData.containsKey("transaction_id")?resData.getString("transaction_id"):"";
            String status = resData.containsKey("trade_state")?resData.getString("trade_state"):"";
            if("success".equalsIgnoreCase(status) && "00".equalsIgnoreCase(returncode)){
                JSONObject result = CommonContact.successMsg("下单成功");
                result.put("pOrderNo",pOrderNo);
                return listener.successHandler(result);
            }
            JSONObject result = CommonContact.successMsg("未支付");
            result.put("pOrderNo",pOrderNo);
            return listener.paddingHandler(result);
        } catch (Exception e) {
            logger.error("印度1支付接口异常",e);
            return listener.paddingHandler(CommonContact.failedMsg("下单接口超时"));
        }
    }
}
