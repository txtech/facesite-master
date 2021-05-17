package com.nabobsite.test;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/17 1:43 下午
 * @Version 1.0
 */
public class SmsTest {
    private static final String smsUrl = "http://api.wftqm.com/api/sms/mtsend";

    @Test
    public void forgetPwd(){
        String phone = "8615118135523";
        String code = "123456";
        sendSmsCode(phone,code);
    }

    public void sendSmsCode(String phone,String code){
        try {
            String content = "动态验证码:"+code+",您正在办理修改手机号业务,请输入六位动态验证码完成手机号码验证。如非本人操作，请忽略此短信。";
            JSONObject param = new JSONObject();
            param.put("appkey", "4nHf5Pdp");
            param.put("secretkey", "yfJxfYDN");
            param.put("phone", phone);
            param.put("content", URLEncoder.encode(content,"UTF-8"));//短信内容,必须做urlencode(UFT-8)
            String result = HttpRequest.post(smsUrl)
                    .form(param)
                    .execute().body();
            System.out.println(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
