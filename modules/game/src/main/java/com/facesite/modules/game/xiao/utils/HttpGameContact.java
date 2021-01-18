package com.facesite.modules.game.xiao.utils;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/1/12 6:00 下午
 * @Version 1.0
 */
public class HttpGameContact {

    private static final String BASE_DOMAIN = "http://test.hehelegou.com/Api";
    private static final String login_url = "http://test.hehelegou.com/Api/api/user/login";
    private static final String userinfo_url = "http://test.hehelegou.com/Api/api/happygame/getUserInfo";
    private static final String updatebean_url = "http://test.hehelegou.com/Api/api/happygame/updateAccount";

    public static void main(String[] args) {
        //postUserLogin();
       /* String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVc2VySWQiOiIxMzA3MTAwNy00NzQyLTRlOTQtOTc0OC1kMWEyOGRkOWZmZmMiLCJNb2JpbGUiOiIxODE2MTg1NjAzMCIsIlVzZXJUeXBlIjoiNmMwZmYyNDktM2RhOC00NDk5LThkODEtNGZhNTgwNzIyMGQ4IiwiZXhwIjoxNjEwOTIyMjg5LjB9.vH5aHYZtkxuMxbIMcoWYVerv8uu2zlACdHR33PVdSOw";
        Long hBeans = 0L;
        String tage = "赢得游戏";
        updateAppGold(token,hBeans,tage);
        getUserInfo(token);*/
       /* test();*/
        Long a = 1L;
        if( a != 0){
            System.out.println("1111：");
        }else{
            System.out.println("22222：");
        }
    }

    public static void test(){
        String a = "[0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]";
        JSONArray array = JSONArray.parseArray(a);
        System.out.println("原始数组："+array);
        Long value = array.getLongValue(6);
        System.out.println("修改数组："+value);
        array.remove(6);
        array.add(6,value+1);
        System.out.println("修改数组："+array);
    }

    /**
     * @desc 用户登陆
     * @author nada
     * @create 2021/1/11 11:10 下午
     */
    public static void postUserLogin(){
        JSONObject retJson = new JSONObject();
        retJson.put("Phone", "18161856030");
        retJson.put("Pwd", "123456");
        retJson.put("Code", "123456");
        String result = HttpRequest.post(login_url).header("Content-Type", "application/json").body(retJson.toJSONString()).execute().body();
        Console.log(result);
        //{"Message":"","Status":1,"Result":{"hasBind":0,"userTypeName":"普通会员","orderSnap":{"allOrders":0,"waitGoods":0,"refund":0,"waiPay":0},"userInfo_ID":"5eb8e7af-8fdb-4568-b5ad-c2ab3f28f92c","UserInfo_Mobile":"13607123717","auto_ID":10556,"userInfo_NickName":"用户3717","userInfo_HeadImg":"/Resource/HeadImage/default.png?v=637460033504049109",
        // "userInfo_HBeans":1000000.0000,"userInfo_LBeans":0.0000,"userInfo_FansNumber":0,"customerPhone":"027-87256044","onlineTalkUrl":"http://192.168.0.6:8085//HomeWX/Talk","checkSwitch":"0","centerMenus":[{"Menu_ID":"4ac29036-6f3c-46f8-9519-572f82d36a73","Menu_Name":"兑换商城","Menu_Icon":"/Resource/PhotoFile/9d0bcf74-7094-48d5-a892-95b24e4dc5a3.png","Menu_Type":1,"Menu_Url":"/hedouMall","Menu_Sort":1,"Menu_IsShow":1},{"Menu_ID":"83b4d39e-253b-432c-b781-b6e34d86d73a","Menu_Name":"我的呵豆","Menu_Icon":"/Resource/PhotoFile/cd6c61dd-191d-41ee-a4c6-56a863178d99.png","Menu_Type":1,"Menu_Url":"/hedou","Menu_Sort":2,"Menu_IsShow":1},{"Menu_ID":"c9d02935-53ff-4ffe-8380-50776a329e40","Menu_Name":"幸运水果","Menu_Icon":"/Resource/PhotoFile/0c3f4516-ee2f-4624-98f8-ef6697428757.png","Menu_Type":1,"Menu_Url":"/fruitGames","Menu_Sort":3,"Menu_IsShow":1},{"Menu_ID":"adc18759-645a-4e56-bf8e-8906c25f5de9","Menu_Name":"分享好友","Menu_Icon":"/Resource/PhotoFile/ae000b27-0fc6-4645-af3d-812a80ccd27c.png","Menu_Type":1,"Menu_Url":"/share","Menu_Sort":4,"Menu_IsShow":1},{"Menu_ID":"eee70c0f-eb9a-4858-a484-7c6226cdd87e","Menu_Name":"收货地址","Menu_Icon":"/Resource/PhotoFile/da29303e-3499-4e79-b54b-75052765f0c3.png","Menu_Type":1,"Menu_Url":"/addressList","Menu_Sort":5,"Menu_IsShow":1},{"Menu_ID":"1c99f4e4-5624-4b5a-a5f6-2dbf5d364030","Menu_Name":"联系客服","Menu_Icon":"/Resource/PhotoFile/4a11d850-bb45-4206-99c3-5bd719d1d64b.png","Menu_Type":1,"Menu_Url":"/callMe","Menu_Sort":6,"Menu_IsShow":1},{"Menu_ID":"0bdaa988-a065-4233-a490-2d4d5fc18459","Menu_Name":"乐豆商城","Menu_Icon":"/Resource/PhotoFile/61991c28-9832-468f-b9e9-78a09ba078fd.png","Menu_Type":1,"Menu_Url":"/ledouMall","Menu_Sort":7,"Menu_IsShow":1},{"Menu_ID":"110fd0bc-e00e-47f6-99be-99652183e375","Menu_Name":"农场","Menu_Icon":"/Resource/PhotoFile/0e07dea1-87dd-4322-9188-92961afaaabd.png","Menu_Type":2,"Menu_Url":"https://fanyi.qq.com/","Menu_Sort":8,"Menu_IsShow":1}],"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVc2VySWQiOiI1ZWI4ZTdhZi04ZmRiLTQ1NjgtYjVhZC1jMmFiM2YyOGY5MmMiLCJNb2JpbGUiOiIxMzYwNzEyMzcxNyIsIlVzZXJUeXBlIjoiNmMwZmYyNDktM2RhOC00NDk5LThkODEtNGZhNTgwNzIyMGQ4IiwiZXhwIjoxNjEwNDkyOTUwLjB9.vrdkMHVjZ54Xcf7XBMfeatSdCc6fAXO5AKQ4EHPUlUU"},"ServerTime":"2021-01-11 23:09:09"}
    }

    /**
     * @desc 获取用户信息
     * @author nada
     * @create 2021/1/11 10:47 下午
     * //{"Message":"恭喜您领取成功","Status":1,"Result":
     * {"userTypeName":"普通会员","userInfo_ID":"5eb8e7af-8fdb-4568-b5ad-c2ab3f28f92c","userInfo_Mobile":"13607123717","auto_ID":10556,
     * "userInfo_NickName":"用户3717","userInfo_HeadImg":"http://192.168.0.6:8085//Resource/HeadImage/default.png?v=637460040756392859",
     * "userInfo_HBeans":1000021.0000,"userInfo_LBeans":0.0000,"userInfo_Sex":"1"
     * },
     * "ServerTime":"2021-01-11 23:21:15"}
     */
    public static JSONObject getUserInfo(String token){
        try {
            String result = HttpRequest.get(userinfo_url).header("Content-Type", "application/json").header("token",token).execute().body();
            Console.log(result);
            JSONObject resData = JSONObject.parseObject(result);
            if(resData == null || resData.isEmpty()){
                return BaseGameContact.failed("get userinfo empty!");
            }
            if(!BaseGameContact.isOkRemote(resData)){
                return BaseGameContact.failed("get user status is faile!");
            }
            return BaseGameContact.success(resData.getJSONObject("Result"));
        } catch (HttpException e) {
            Console.log("获取用户信息异常",e);
            return BaseGameContact.failed("get userinfo error!");
        }
    }
    /**
     * @desc 修改用户信息
     * @author nada
     * {"Message":"","Status":1,"Result":"","ServerTime":"2021-01-11 23:21:15"}
     * @create 2021/1/11 10:47 下午
     */
    public static JSONObject updateAppGold(String token,Long hBeans,String tage){
        try {
            JSONObject retJson = new JSONObject();
            retJson.put("HBeans",hBeans);
            retJson.put("Stage",tage);
            String result = HttpRequest.post(updatebean_url).header("Content-Type", "application/json").header("token",token).body(retJson.toJSONString()).execute().body();
            Console.log(result);
            JSONObject resData = JSONObject.parseObject(result);
            if(resData == null || resData.isEmpty()){
                return BaseGameContact.failed("get userinfo empty!");
            }
            if(!BaseGameContact.isOkRemote(resData)){
                Console.log("修改用户信息:"+resData);
                return BaseGameContact.failed("get user status is faile!");
            }
            return BaseGameContact.success(JSONObject.parseObject(result));
        } catch (HttpException e) {
            Console.log("修改用户信息异常",e);
            return BaseGameContact.failed("update userinfo error!");
        }
    }
}
