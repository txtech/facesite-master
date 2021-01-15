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
    private String uid;
    private String gid;
    private String playId;

    private Long gold;
    private Long score;
    private Long level;
    private Long start;
    private String boosters;

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

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public String getBoosters() {
        return boosters;
    }

    public void setBoosters(String boosters) {
        this.boosters = boosters;
    }
}
