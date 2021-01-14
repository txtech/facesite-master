package com.facesite.modules.game.xiao.entity;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/1/12 6:00 下午
 * @Version 1.0
 */
public class GameData {
    private String token;
    private Long score;
    private Long level;
    private String uid;
    private String gid;
    private String playId;
    // 完成等级
    private Long levelsCompleted;
    // 游戏分数
    private Long totalScore;
    // 游戏金币
    private Long gole;
    // 游戏道具
    private String boostersCount;
    // 游戏星级
    private String starsPerLevel;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public Long getLevelsCompleted() {
        return levelsCompleted;
    }

    public void setLevelsCompleted(Long levelsCompleted) {
        this.levelsCompleted = levelsCompleted;
    }

    public Long getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Long totalScore) {
        this.totalScore = totalScore;
    }

    public Long getGole() {
        return gole;
    }

    public void setGole(Long gole) {
        this.gole = gole;
    }

    public String getBoostersCount() {
        return boostersCount;
    }

    public void setBoostersCount(String boostersCount) {
        this.boostersCount = boostersCount;
    }

    public String getStarsPerLevel() {
        return starsPerLevel;
    }

    public void setStarsPerLevel(String starsPerLevel) {
        this.starsPerLevel = starsPerLevel;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }
}
