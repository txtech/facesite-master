package com.nabobsite.test;

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
        testLogin();
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
