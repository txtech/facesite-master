package com.facesite.modules.game.xiao.utils;

import com.alibaba.fastjson.JSONObject;
import com.facesite.modules.game.xiao.entity.GameData;
import com.facesite.modules.game.xiao.entity.HgamePlayRecord;
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

    //类型：1:准备 2:开始 3:升级 4:完成 5:结束
    public static int PLAY_TYPE_1 = 1;
    public static int PLAY_TYPE_2 = 2;
    public static int PLAY_TYPE_3 = 3;
    public static int PLAY_TYPE_4 = 4;
    public static int PLAY_TYPE_5 = 5;
    public static int PLAY_TYPE_6 = 6;

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
        userInfo.setParentId(UUID.randomUUID().toString());
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
        result.put("uid",hgameUserRef.getUserId());
        result.put("type",userInfo.getType());
        result.put("hbeans",userInfo.getHbeans());
        result.put("levelsCompleted", hgameUserRef.getLevelsCompleted());
        result.put("totalScore",hgameUserRef.getTotalScore());
        result.put("gold",hgameUserRef.getGold());
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

    public static HgameUserRef initGameUserRef(String gameId,String userId){
        HgameUserRef userRef = new HgameUserRef();
        userRef.setGameId(gameId);
        userRef.setUserId(userId);
        userRef.setStatus("0");
        userRef.setLevelsCompleted(0L);
        userRef.setTotalScore(0L);
        userRef.setGold(0L);
        userRef.setBoostersCount("[0,0,0,0,0,0]"); // 骰子:200,定时器:200,闪电:150,丘比特:250,太阳:200
        userRef.setStarsPerLevel("[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]");
        return userRef;
    }

    public static HgameUserRef getGameUserRefUserId(String userId) {
        HgameUserRef userRef = new HgameUserRef();
        userRef.setUserId(userId);
        userRef.setStatus("0");
        return userRef;
    }

    public static HgamePlayRecord initRecord(GameData gameData,Integer type){
        HgamePlayRecord record = new HgamePlayRecord();
        record.setStart(0L);
        record.setSymbol(0);
        record.setLbeans(0L);
        record.setUpdateBeans(0L);
        record.setType(type);
        record.setGameId(gameData.getGid());
        record.setUserId(gameData.getUid());
        record.setPlayId(gameData.getPlayId());
        record.setHbeans(gameData.getGold());
        record.setLevel(gameData.getLevel());
        record.setScore(gameData.getScore());
        return record;
    }

    public static HgamePlayRecord getUniqueRecord(HgamePlayRecord hgamePlayRecord){
        HgamePlayRecord record = new HgamePlayRecord();
        record.setStatus("0");
        record.setType(hgamePlayRecord.getType());
        record.setUserId(hgamePlayRecord.getUserId());
        record.setPlayId(hgamePlayRecord.getPlayId());
        record.setLevel(hgamePlayRecord.getLevel());
        return record;
    }

    public static HgameUserRef updateGameUserRef(HgamePlayRecord hgamePlayRecord){
        HgameUserRef userRef = new HgameUserRef();
        userRef.setUserId(hgamePlayRecord.getUserId());
        userRef.setGameId(hgamePlayRecord.getGameId());
        userRef.setTotalScore(hgamePlayRecord.getScore());
        userRef.setLevelsCompleted(hgamePlayRecord.getLevel());
        userRef.setGold(hgamePlayRecord.getGold());
        return userRef;
    }
}
