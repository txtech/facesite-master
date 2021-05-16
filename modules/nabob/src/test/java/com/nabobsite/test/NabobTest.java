package com.nabobsite.test;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.DesUtils;
import com.nabobsite.modules.nabob.api.entity.I18nUtils;
import org.junit.Test;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 5:37 下午
 * @Version 1.0
 */
public class NabobTest {

    private final static String Authorization = "26440d47baf4474cbe39890bb7112d8f";
    private final static String baseReqUrl = "http://localhost:9998/nabob/f/api/";

    @Test
    public void userRegister(){
        String url = baseReqUrl + "open/register";
        JSONObject param = new JSONObject();
        param.put("accountNo", "15118135528");
        param.put("password", "123456");
        //param.put("inviteCode", "100015");
        param.put("inviteSecret", "4182927c1c3600e49f1553ba9ab50bc1aef9531d36b9e1918e74d14baaa769dc");
        param.put("favorite", "Elon Musk");
        param.put("lang", I18nUtils.LANG_IN);
        String result = HttpRequest.post(url)
                .body(param.toString())
                .header("Content-Type","application/json")
                .execute().body();
        System.out.println(result);
    }

    @Test
    public void userLogin(){
        String url = baseReqUrl + "open/login";
        JSONObject param = new JSONObject();
        param.put("accountNo", "15118135523");
        param.put("password", "654321");
        String result = HttpRequest.post(url)
                .body(param.toString())
                .header("Content-Type","application/json")
                .execute().body();
        System.out.println(result);
    }

    @Test
    public void updatePwd(){
        String url = baseReqUrl + "user/updatePwd";
        JSONObject param = new JSONObject();
        param.put("accountNo", "15118135523");
        param.put("password", "654321");
        param.put("oldPassword", "123456");
        String result = HttpRequest.post(url)
                .body(param.toString())
                .header("Content-Type","application/json")
                .header("Authorization",Authorization)
                .execute().body();
        System.out.println(result);
    }

    @Test
    public void logout(){
        String url = baseReqUrl + "user/logout";
        String result = HttpRequest.post(url)
                .header("Authorization",Authorization)
                .execute().body();
        System.out.println(result);
    }

    @Test
    public void getUserInfo(){
        String url = baseReqUrl + "user/getUserInfo";
        String result = HttpRequest.post(url)
                .header("Content-Type","application/json")
                .header("Authorization",Authorization)
                .execute().body();
        System.out.println(result);
    }

    @Test
    public void shareFriends(){
        String url = baseReqUrl + "user/shareFriends";
        String result = HttpRequest.post(url)
                .header("Content-Type","application/json")
                .header("Authorization",Authorization)
                .execute().body();
        System.out.println(result);
    }


    @Test
    public void testEntryPassward(){
        String pidAndSid = "{pid:123456,sid:234234}";
        JSONObject jsonObject = JSONObject.parseObject(pidAndSid);

        System.out.println( jsonObject.getString("pid"));
        System.out.println( jsonObject.getString("sid"));

        String secretKey = "NabobBase64";
        String username = DesUtils.encode("15118135523", secretKey);
        String password = DesUtils.encode("123456", secretKey);
        System.out.println("&username=" + username + "&password=" + password);

        String username1 = DesUtils.decode("424A6C6BCB75439B5206D0A6F88A90C1", secretKey);
        String password2 = DesUtils.decode("BC7CE71528D97E942E10D117E6EAFFCC", secretKey);
        System.out.println("&username=" + username1 + "&password=" + password2);
    }
}
