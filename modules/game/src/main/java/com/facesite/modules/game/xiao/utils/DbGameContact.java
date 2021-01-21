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

    // 1:同步呵豆日志 2:金币日志 3:道具日志
    public static int LOG_TYPE_1 = 1;
    public static int LOG_TYPE_2 = 2;
    public static int LOG_TYPE_3 = 3;

    /**
     * @desc 响应游戏信息
     * @author nada
     * @create 2021/1/15 11:45 上午
    */
    public static JSONObject responseGameUserInfo(HgameInfo hgameInfo,HgameUserRef hgameUserRef){
        JSONObject result = new JSONObject();
        result.put("gid",hgameUserRef.getGameId());
        result.put("uid",hgameUserRef.getUserId());
        result.put("gold",hgameUserRef.getGold());
        result.put("winGold",hgameInfo.getWin());
        result.put("loseGold",hgameInfo.getLose());
        result.put("totalScore",hgameUserRef.getTotalScore());
        result.put("levelsCompleted", hgameUserRef.getLevelsCompleted());
        result.put("boostersCount",hgameUserRef.getBoostersCount());
        result.put("boostersGold",hgameInfo.getBoostersGold());
        result.put("starsPerLevel",hgameUserRef.getStarsPerLevel());
        result.put("type",hgameUserRef.getHgameInfo() != null ? hgameUserRef.getHgameInfo().getType() : 1);
        result.put("gname",hgameUserRef.getHgameInfo() != null ? hgameUserRef.getHgameInfo().getName() : "");
        result.put("name",hgameUserRef.getHgameUserInfo() != null ? hgameUserRef.getHgameUserInfo().getUsername() : "");
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
    /**
     * @desc 更新用户呵豆
     * @author nada
     * @create 2021/1/19 10:00 下午
    */
    public static HgameUserInfo updateGameUserInfo(String userId,Long hBeans){
        HgameUserInfo hgameUserInfo = new HgameUserInfo();
        hgameUserInfo.setId(userId);
        hgameUserInfo.setHbeans(hBeans);
        return hgameUserInfo;
    }
    /**
     * @desc 初始化玩家游戏
     * @author nada
     * @create 2021/1/18 12:22 下午
    */
    public static HgameUserRef initGameUserRef(HgameInfo hgameInfo,String userId,Long gold){
        String gameId = hgameInfo.getId();
        HgameUserRef userRef = new HgameUserRef();
        userRef.setGameId(gameId);
        userRef.setUserId(userId);
        userRef.setStatus("0");
        userRef.setLevelsCompleted(0L);
        userRef.setTotalScore(0L);
        userRef.setGold(gold);
        // 骰子:200,定时器:200,闪电:150,丘比特:250,太阳:200
        if(StringUtils.isNotEmpty(hgameInfo.getBoosters())){
            userRef.setBoostersCount(hgameInfo.getBoosters());
        }else{
            userRef.setBoostersCount("[0,0,0,0,0,0]");
        }
        userRef.setStarsPerLevel("[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]");
        return userRef;
    }
    /**
     * @desc 初始化玩家游戏记录
     * @author nada
     * @create 2021/1/18 12:22 下午
     */
    public static HgamePlayRecord initGamePlayRecord(String userId,String gameId,String playId,int type,Long level,Long gold,Long score,Long start,String remarks){
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
        record.setRemarks(remarks);
        return record;
    }
    /**
     * @desc 保存游戏日志
     * @author nada
     * @create 2021/1/18 12:22 下午
    */
    public static HgamePlayLog saveLog(Integer type,String userId,String gameId,Long level,Long gole,Long score,Long boosters,String remarks){
        HgamePlayLog hgamePlayLog = new HgamePlayLog();
        hgamePlayLog.setUserId(userId);
        hgamePlayLog.setGameId(gameId);
        hgamePlayLog.setType(type);
        hgamePlayLog.setLevel(level);
        hgamePlayLog.setGole(gole);
        hgamePlayLog.setScore(score);
        hgamePlayLog.setRemarks(remarks);
        hgamePlayLog.setBoosters(boosters);
        return hgamePlayLog;
    }
    /**
     * @desc 同步游戏金币
     * @author nada
     * @create 2021/1/18 12:23 下午
    */
    public static HgameUserRef updateGameUserRefGold(String userId,String gameId,Long gold){
        HgameUserRef userRef = new HgameUserRef();
        userRef.setUserId(userId);
        userRef.setGameId(gameId);
        userRef.setGold(gold);
        return userRef;
    }

    public static HgameUserRef updateGameUserRef(String userId,String gameId,Long level,Long score,Long start,String oldStarsPerLevel,Boolean isLevelUp){
        HgameUserRef userRef = new HgameUserRef();
        userRef.setUserId(userId);
        userRef.setGameId(gameId);
        if(isLevelUp){
            userRef.setLevelsCompleted(level);
        }
        if(score > 0){
            userRef.setTotalScore(score);
        }
        if(level > 0 && start >0 && StringUtils.isNotBlank(oldStarsPerLevel)){
            JSONArray array = JSONArray.parseArray(oldStarsPerLevel);
            int index = level.intValue() - 1;
            array.remove(index);
            array.add(index,start);
            String newStarsPerLevel = array.toString();
            userRef.setStarsPerLevel(newStarsPerLevel);
        }
        if(isLevelUp){
            userRef.setRemarks("打赢升级:"+level);
        }
        return userRef;
    }
    /**
     * @desc 使用道具
     * @author nada
     * @create 2021/1/18 12:24 下午
    */
    public static HgameUserRef spendGameUserRefboosters(String userId,String gameId,Long bootserId,String olBboostersCount){
        HgameUserRef userRef = new HgameUserRef();
        userRef.setUserId(userId);
        userRef.setGameId(gameId);
        if(bootserId >=0 && StringUtils.isNotBlank(olBboostersCount)){
            JSONArray array = JSONArray.parseArray(olBboostersCount);
            int index = bootserId.intValue();
            Long value = array.getLongValue(index);
            if(value > 0){
                array.remove(index);
                array.add(index,value-1);
                String newBoostersCount = array.toString();
                userRef.setBoostersCount(newBoostersCount);
            }
        }
        return userRef;
    }
    /**
     * @desc 购买道具
     * @author nada
     * @create 2021/1/18 12:24 下午
    */
    public static HgameUserRef addGameUserRefboosters(String userId,String gameId,Long bootserId,String olBboostersCount,Boolean isSync){
        HgameUserRef userRef = new HgameUserRef();
        userRef.setUserId(userId);
        userRef.setGameId(gameId);
        if(bootserId >=0 && StringUtils.isNotBlank(olBboostersCount)){
            JSONArray array = JSONArray.parseArray(olBboostersCount);
            int index = bootserId.intValue();
            Long value = array.getLongValue(index);
            array.remove(index);
            array.add(index,value+1);
            String newBoostersCount = array.toString();
            userRef.setBoostersCount(newBoostersCount);
        }
        if(!isSync){
            userRef.setRemarks(bootserId+":道具更新失败");
        }
        return userRef;
    }


    public static HgameInfo paramsGameInfo(String id) {
        HgameInfo hgameInfo = new HgameInfo();
        hgameInfo.setStatus("0");
        hgameInfo.setId(id);
        return hgameInfo;
    }
    public static HgameInfo paramsGameInfo(Integer type) {
        HgameInfo hgameInfo = new HgameInfo();
        hgameInfo.setStatus("0");
        hgameInfo.setType(type);
        return hgameInfo;
    }
    public static HgameUserInfo paramsGameUserId(String id) {
        HgameUserInfo userInfo = new HgameUserInfo();
        userInfo.setStatus("0");
        userInfo.setId(id);
        return userInfo;
    }
    public static HgameUserInfo paramsGameUserInfo(String parentId) {
        HgameUserInfo userInfo = new HgameUserInfo();
        userInfo.setStatus("0");
        userInfo.setParentId(parentId);
        return userInfo;
    }
    public static HgameUserInfo paramsGameUserInfoUpdate(String userId,String token,Long hBeans) {
        HgameUserInfo userInfo = new HgameUserInfo();
        userInfo.setId(userId);
        userInfo.setToken(token);
        userInfo.setHbeans(hBeans);
        return userInfo;
    }
    public static HgamePlayRecord paramsGamePlayRecord(int type,String userId,String gameId,Long level){
        HgamePlayRecord record = new HgamePlayRecord();
        record.setUserId(userId);
        record.setGameId(gameId);
        record.setStatus("0");
        record.setType(type);
        record.setLevel(level);
        return record;
    }
    public static HgameUserRef paramsGameUserRef(String userId,String gameId) {
        HgameUserRef userRef = new HgameUserRef();
        userRef.setGameId(gameId);
        userRef.setUserId(userId);
        userRef.setStatus("0");
        return userRef;
    }
}
