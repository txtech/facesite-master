/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.entity;

import com.alibaba.fastjson.JSONObject;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户游戏记录Entity
 * @author nada
 * @version 2021-01-11
 */
@Table(name="h_game_user_ref", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="game_id", attrName="gameId", label="父ID"),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="status", attrName="status", label="游戏状态 1", comment="游戏状态 1:离线 2:在线 3:战局中", isUpdate=false),
		@Column(name="levels_completed", attrName="levelsCompleted", label="完成等级"),
		@Column(name="total_score", attrName="totalScore", label="游戏分数"),
		@Column(name="gole", attrName="gole", label="游戏金币"),
		@Column(name="boosters_count", attrName="boostersCount", label="游戏道具"),
		@Column(name="stars_per_level", attrName="starsPerLevel", label="游戏道具"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="start_time", attrName="startTime", label="创建时间"),
		@Column(name="end_time", attrName="endTime", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class HgameUserRef extends DataEntity<HgameUserRef> {

	private static final long serialVersionUID = 1L;
	private Long gameId;		// 父ID
	private Long userId;		// 用户ID
	private Long levelsCompleted;		// 完成等级
	private Long totalScore;		// 游戏分数
	private Long gole;		// 游戏金币
	private String boostersCount;		// 游戏道具
	private String starsPerLevel;		// 游戏道具
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private Date startTime;		// 创建时间
	private Date endTime;		// 更新时间
	private String delFlag;		// 删除标志

	public static HgameUserRef saveGameUserRef(JSONObject resData, int type){
		HgameUserRef userRef = new HgameUserRef();
		return userRef;
	}

	public static HgameUserRef initGameUserRef(Long gameId,Long userId){
		HgameUserRef userRef = new HgameUserRef();
		userRef.setGameId(gameId);
		userRef.setUserId(userId);
		userRef.setStatus("1");
		userRef.setLevelsCompleted(0L);
		userRef.setTotalScore(0L);
		userRef.setGole(0L);
		userRef.setBoostersCount("[0,0,0,0,0,0]"); // 骰子:200,定时器:200,闪电:150,丘比特:250,太阳:200
		userRef.setStarsPerLevel("[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]");
		return userRef;
	}

	public HgameUserRef() {
		this(null);
	}

	public static HgameUserRef getGameUserRefUserId(Long userId) {
		HgameUserRef userRef = new HgameUserRef();
		userRef.setUserId(userId);
		return userRef;
	}

	public HgameUserRef(String id){
		super(id);
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	@Length(min=0, max=255, message="游戏道具长度不能超过 255 个字符")
	public String getBoostersCount() {
		return boostersCount;
	}

	public void setBoostersCount(String boostersCount) {
		this.boostersCount = boostersCount;
	}

	@Length(min=0, max=255, message="游戏道具长度不能超过 255 个字符")
	public String getStarsPerLevel() {
		return starsPerLevel;
	}

	public void setStarsPerLevel(String starsPerLevel) {
		this.starsPerLevel = starsPerLevel;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Length(min=0, max=1, message="删除标志长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}