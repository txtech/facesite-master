package com.facesite.modules.game.xiao.utils;

import com.alibaba.fastjson.JSONObject;
import com.facesite.modules.game.xiao.entity.*;

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

    /**
     * @desc 响应游戏信息
     * @author nada
     * @create 2021/1/15 11:45 上午
    */
    public static JSONObject responseGameUserInfo(HgameInfo hgameInfo,HgameUserRef hgameUserRef){
        JSONObject result = new JSONObject();
        result.put("gid",hgameUserRef.getGameId());
        result.put("uid",hgameUserRef.getUserId());
        result.put("hbeans",hgameUserRef.getGold());
        result.put("gold",hgameUserRef.getGold());
        result.put("winGold",hgameInfo.getWin());
        result.put("loseGold",hgameInfo.getLose());
        result.put("totalScore",hgameUserRef.getTotalScore());
        result.put("levelsCompleted", hgameUserRef.getLevelsCompleted());
        result.put("boostersCount",hgameUserRef.getBoostersCount());
        result.put("starsPerLevel",hgameUserRef.getStarsPerLevel());
        return result;
    }
    /**
     * @desc 初始化会员玩家
     * @author nada
     * @create 2021/1/15 11:26 上午
     */
    public static HgameUserInfo initMemberUserInfo(String token, JSONObject resData, int type){
        HgameUserInfo userInfo = new HgameUserInfo();
        userInfo.setType(type);
        userInfo.setToken(token);
        userInfo.setStatus("0");
        userInfo.setAge(1);
        userInfo.setParentId(resData.getString("userInfo_ID"));
        userInfo.setMobile(resData.getString("userInfo_Mobile"));
        userInfo.setNickname(resData.getString("userInfo_NickName"));
        userInfo.setUsername(resData.getString("userInfo_NickName"));
        userInfo.setHbeans(resData.getLong("userInfo_HBeans"));
        userInfo.setLbeans(resData.getLong("userInfo_LBeans"));
        userInfo.setPhoto(resData.getString("userInfo_HeadImg"));
        userInfo.setSex(resData.getString("userInfo_Sex"));
        return userInfo;
    }
    /**
     * @desc 初始化游客玩家
     * @author nada
     * @create 2021/1/15 11:26 上午
    */
    public static HgameUserInfo initVisitorUserInfo(String token){
        HgameUserInfo userInfo = new HgameUserInfo();
        userInfo.setParentId(UUID.randomUUID().toString());
        userInfo.setType(TYPE_VISITOR);
        userInfo.setToken(token);
        userInfo.setStatus("0");
        userInfo.setMobile("123456");
        userInfo.setNickname("游客");
        userInfo.setUsername("游客");
        userInfo.setHbeans(0L);
        userInfo.setLbeans(0L);
        userInfo.setPhoto("");
        userInfo.setSex("1");
        userInfo.setAge(1);
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
        // 骰子:200,定时器:200,闪电:150,丘比特:250,太阳:200
        userRef.setBoostersCount("[0,0,0,0,0,0]");
        userRef.setStarsPerLevel("[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]");
        return userRef;
    }
    public static HgamePlayRecord initGamePlayRecord(String userId,String gameId,String playId,int type,Long level,Long gold,Long score,Long start,String tag){
        HgamePlayRecord record = new HgamePlayRecord();
        record.setStart(0L);
        record.setLbeans(0L);
        record.setType(type);
        record.setGameId(gameId);
        record.setUserId(userId);
        record.setPlayId(playId);
        record.setHbeans(gold);
        record.setGold(gold);
        record.setScore(score);
        record.setLevel(level);
        record.setStart(start);
        record.setRemarks(tag);
        return record;
    }




    public static HgameUserRef resetGameUserRef(String userId,String gameId,Long newScore){
        HgameUserRef userRef = new HgameUserRef();
        userRef.setUserId(userId);
        userRef.setGameId(gameId);
        if(newScore > 0){
            userRef.setTotalScore(newScore);
        }
        return userRef;
    }
    public static HgameUserRef updateGameUserRefGold(String userId,String gameId,Long gold){
        HgameUserRef userRef = new HgameUserRef();
        userRef.setUserId(userId);
        userRef.setGameId(gameId);
        userRef.setGold(gold);
        return userRef;
    }
    public static HgameUserRef updateGameUserRef(String userId,String gameId,Long level,Long score){
        HgameUserRef userRef = new HgameUserRef();
        userRef.setUserId(userId);
        userRef.setGameId(gameId);
        userRef.setLevelsCompleted(level);
        userRef.setTotalScore(score);
        return userRef;
    }



    public static HgameInfo paramsGameInfo(Integer type) {
        HgameInfo hgameInfo = new HgameInfo();
        hgameInfo.setStatus("0");
        hgameInfo.setType(type);
        return hgameInfo;
    }
    public static HgameUserInfo paramsGameUserInfo(String parentId) {
        HgameUserInfo userInfo = new HgameUserInfo();
        userInfo.setStatus("0");
        userInfo.setParentId(parentId);
        return userInfo;
    }
    public static HgamePlayRecord paramsGamePlayRecord(int type,String userId,String gameId,Long level){
        HgamePlayRecord record = new HgamePlayRecord();
        record.setStatus("0");
        record.setType(type);
        record.setUserId(userId);
        record.setGameId(gameId);
        record.setLevel(level);
        return record;
    }
    public static HgameUserRef paramsGameUserRef(String userId) {
        HgameUserRef userRef = new HgameUserRef();
        userRef.setUserId(userId);
        userRef.setStatus("0");
        return userRef;
    }
    public static HgameUserRef paramsGameUserRef(String userId,String gameId) {
        HgameUserRef userRef = new HgameUserRef();
        userRef.setGameId(gameId);
        userRef.setUserId(userId);
        userRef.setStatus("0");
        return userRef;
    }
}
