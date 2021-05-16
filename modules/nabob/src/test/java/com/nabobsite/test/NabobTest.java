package com.nabobsite.test;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.DesUtils;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 5:37 下午
 * @Version 1.0
 */
public class NabobTest {

    public static void main(String[] args) {
        testGetUserInfoHttp();
    }

    public static void testGetUserInfoHttp(){
        String url = "http://localhost:9998/nabob/f/api/user/getUserInfo";
        String result = HttpRequest.post(url)
                .header("Content-Type","application/json")
                .header("Authorization","07ea3eda0cfc40debf69ca98ca2b9e1a")
                .execute()
                .body();
        System.out.println(result);
    }

    public static void testLoginHttp(){
        String url = "http://192.168.1.100:9998/nabob/f/api/open/login";
        JSONObject param = new JSONObject();
        param.put("accountNo", "15118135523");
        param.put("password", "123456");
        String result = HttpRequest.post(url)
                .body(param.toString())
                .header("Content-Type","application/json")
                .execute()
                .body();
        System.out.println(result);
    }

    public static void getSid(){
        String pidAndSid = "{pid:123456,sid:234234}";
        JSONObject jsonObject = JSONObject.parseObject(pidAndSid);

        System.out.println( jsonObject.getString("pid"));
        System.out.println( jsonObject.getString("sid"));
    }

    public static void testLogin(){
        String secretKey = "NabobBase64";
        String username = DesUtils.encode("15118135523", secretKey);
        String password = DesUtils.encode("123456", secretKey);
        System.out.println("&username=" + username + "&password=" + password);

        String username1 = DesUtils.decode("424A6C6BCB75439B5206D0A6F88A90C1", secretKey);
        String password2 = DesUtils.decode("BC7CE71528D97E942E10D117E6EAFFCC", secretKey);
        System.out.println("&username=" + username1 + "&password=" + password2);
    }
}
