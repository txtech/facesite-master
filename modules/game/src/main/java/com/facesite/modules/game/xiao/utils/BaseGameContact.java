package com.facesite.modules.game.xiao.utils;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.facesite.modules.game.xiao.service.HgameInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/1/12 6:00 下午
 * @Version 1.0
 */
public class BaseGameContact {

    public static Long getLong(Long v){
        if(v == null){
            return 0L;
        }
        return v;
    }

    public static JSONObject success(Object result){
        JSONObject response = new JSONObject();
        response.put("ok",true);
        response.put("code",1);
        response.put("result",result);
        return response;
    }

    public static JSONObject failed(String msg){
        JSONObject response = new JSONObject();
        response.put("ok",false);
        response.put("code",3);
        response.put("msg",msg);
        return response;
    }

    public static Boolean isOk(JSONObject resData){
        try {
            if(resData == null || resData.isEmpty()){
                return false;
            }
            Boolean isok = resData.getBoolean("ok");
            return isok;
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean isOkDb(Long index){
        try {
            if(index == null){
                return false;
            }
            if(index > 0){
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean isOkRemote(JSONObject resData){
        try {
            if(resData == null || resData.isEmpty()){
                return false;
            }
            String status = resData.getString("Status");
            if("1".equalsIgnoreCase(status)){
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
