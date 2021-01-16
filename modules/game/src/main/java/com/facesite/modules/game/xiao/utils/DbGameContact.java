package com.facesite.modules.game.xiao.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.facesite.modules.game.xiao.entity.*;
import com.jeesite.common.lang.StringUtils;

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
        if(hgameUserRef.getHgameUserInfo() != null){
            result.put("name",hgameUserRef.getHgameUserInfo().getUsername());
        }
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
    public static HgameUserInfo initVisitorUserInfo(Long seqId,String ip,String token){
        HgameUserInfo userInfo = new HgameUserInfo();
        userInfo.setParentId(UUID.randomUUID().toString());
        userInfo.setType(TYPE_VISITOR);
        userInfo.setToken(token);
        userInfo.setStatus("0");
        userInfo.setMobile("15188888888");
        userInfo.setNickname(ip);
        userInfo.setUsername("游客:"+seqId);
        userInfo.setHbeans(0L);
        userInfo.setLbeans(0L);
        userInfo.setPhoto("");
        userInfo.setSex("1");
        userInfo.setAge(1);
        userInfo.setRemarks(HttpBrowserTools.getAlibaba(ip));
        return userInfo;
    }
    public static HgameUserRef initGameUserRef(HgameInfo hgameInfo,String userId){
        HgameUserRef userRef = new HgameUserRef();
        userRef.setGameId(hgameInfo.getId());
        userRef.setUserId(userId);
        userRef.setStatus("0");
        userRef.setLevelsCompleted(0L);
        userRef.setTotalScore(0L);
        userRef.setGold(0L);
        // 骰子:200,定时器:200,闪电:150,丘比特:250,太阳:200
        if(StringUtils.isNotEmpty(hgameInfo.getBoosters())){
            userRef.setBoostersCount(hgameInfo.getBoosters());
        }else{
            userRef.setBoostersCount("[0,0,0,0,0,0]");
        }
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


    public static HgameUserRef updateGameUserRefGold(String userId,String gameId,Long gold){
        HgameUserRef userRef = new HgameUserRef();
        userRef.setUserId(userId);
        userRef.setGameId(gameId);
        userRef.setGold(gold);
        return userRef;
    }
    public static HgameUserRef updateGameUserRef(String userId,String gameId,Long level,Long score,Long start,String oldStarsPerLevel,Boolean isSync){
        HgameUserRef userRef = new HgameUserRef();
        userRef.setUserId(userId);
        userRef.setGameId(gameId);
        userRef.setLevelsCompleted(level);
        if(score > 0){
            userRef.setTotalScore(score);
        }
        if(level > 0 && start >0 && StringUtils.isNotBlank(oldStarsPerLevel)){
            JSONArray array = JSONArray.parseArray(oldStarsPerLevel);
            int index = level.intValue() - 1;
            array.add(index,start);
            String newStarsPerLevel = array.toString();
            userRef.setStarsPerLevel(newStarsPerLevel);
        }
        if(!isSync){
            userRef.setRemarks(level+":同步失败");
        }
        return userRef;
    }



    public static HgameInfo paramsGameInfo(Integer type) {
        HgameInfo hgameInfo = new HgameInfo();
        hgameInfo.setStatus("0");
        hgameInfo.setType(type);
        return hgameInfo;
    }
    public static HgameUserInfo paramsGameUserToken(String token) {
        HgameUserInfo userInfo = new HgameUserInfo();
        userInfo.setStatus("0");
        userInfo.setToken(token);
        return userInfo;
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
