package com.nabobsite.test;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.DesUtils;
import com.nabobsite.modules.nabob.api.entity.I18nUtils;
import io.swagger.annotations.ApiModelProperty;
import org.junit.Test;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 5:37 下午
 * @Version 1.0
 */
public class NabobTest {

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
        param.put("password", "123456");
        String result = HttpRequest.post(url)
                .body(param.toString())
                .header("Content-Type","application/json")
                .execute().body();
        System.out.println(result);
    }

    @Test
    public void getUserInfo(){
        String url = baseReqUrl + "user/getUserInfo";
        String result = HttpRequest.post(url)
                .header("Content-Type","application/json")
                .header("Authorization","98eb1dcbaf244419a0a08ba3f8524b98")
                .execute().body();
        System.out.println(result);
    }

    @Test
    public void switchLang(){
        String url = baseReqUrl + "user/switchLang/en_US";
        String result = HttpRequest.post(url)
                .header("Authorization","98eb1dcbaf244419a0a08ba3f8524b98")
                .execute().body();
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
