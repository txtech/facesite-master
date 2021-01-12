package com.facesite.modules.game.xiao.utils;

import com.alibaba.fastjson.JSONObject;
import com.facesite.modules.game.xiao.entity.HgameUserInfo;
import com.facesite.modules.game.xiao.entity.HgameUserRef;

import java.util.UUID;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/1/12 8:36 下午
 * @Version 1.0
 */
public class DbGameContact {
    public static int TYPE_VISITOR  = 1;
    public static int TYPE_MEMBER = 2;

    public static HgameUserInfo saveUserInfo(String token, JSONObject resData, int type){
        HgameUserInfo userInfo = new HgameUserInfo();
        userInfo.setParentId(resData.getString("userInfo_ID"));
        userInfo.setType(type);
        userInfo.setToken(token);
        userInfo.setStatus("0");
        userInfo.setMobile(resData.getString("userInfo_Mobile"));
        userInfo.setNickname(resData.getString("userInfo_NickName"));
        userInfo.setUsername(resData.getString("userInfo_NickName"));
        userInfo.setHbeans(resData.getLong("userInfo_HBeans"));
        userInfo.setLbeans(resData.getLong("userInfo_LBeans"));
        userInfo.setPhoto(resData.getString("userInfo_HeadImg"));
        userInfo.setSex(resData.getString("userInfo_Sex"));
        userInfo.setAge(1);
        return userInfo;
    }

    public static HgameUserInfo initVisitorUserInfo(String token){
        HgameUserInfo userInfo = new HgameUserInfo();
        userInfo.setParentId(token);
        userInfo.setType(TYPE_VISITOR);
        userInfo.setToken(token);
        userInfo.setStatus("0");
        userInfo.setMobile("");
        userInfo.setNickname("");
        userInfo.setUsername("");
        userInfo.setHbeans(0L);
        userInfo.setLbeans(0L);
        userInfo.setPhoto("");
        userInfo.setSex("1");
        userInfo.setAge(1);
        return userInfo;
    }

    public static JSONObject getGameUserInfo(HgameUserInfo userInfo, HgameUserRef hgameUserRef){
        JSONObject result = new JSONObject();
        result.put("gid",hgameUserRef.getGameId());
        result.put("uid",userInfo.getParentId());
        result.put("type",userInfo.getType());
        result.put("hbeans",userInfo.getHbeans());
        result.put("levelsCompleted", hgameUserRef.getLevelsCompleted());
        result.put("totalScore",hgameUserRef.getTotalScore());
        result.put("gold",hgameUserRef.getGole());
        result.put("boostersCount",hgameUserRef.getBoostersCount());
        result.put("starsPerLevel",hgameUserRef.getStarsPerLevel());
        return result;
    }

    public static HgameUserInfo getUserInfoParent(String parentId) {
        HgameUserInfo userInfo = new HgameUserInfo();
        userInfo.setStatus("0");
        userInfo.setParentId(parentId);
        return userInfo;
    }

    public static HgameUserRef initGameUserRef(Long gameId,Long userId){
        HgameUserRef userRef = new HgameUserRef();
        userRef.setGameId(gameId);
        userRef.setUserId(userId);
        userRef.setStatus("0");
        userRef.setLevelsCompleted(0L);
        userRef.setTotalScore(0L);
        userRef.setGole(0L);
        userRef.setBoostersCount("[0,0,0,0,0,0]"); // 骰子:200,定时器:200,闪电:150,丘比特:250,太阳:200
        userRef.setStarsPerLevel("[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]");
        return userRef;
    }

    public static HgameUserRef getGameUserRefUserId(Long userId) {
        HgameUserRef userRef = new HgameUserRef();
        userRef.setUserId(userId);
        userRef.setStatus("0");
        return userRef;
    }
}
