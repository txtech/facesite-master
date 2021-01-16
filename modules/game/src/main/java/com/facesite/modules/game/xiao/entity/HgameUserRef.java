/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.entity;

import javax.validation.constraints.NotNull;
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
 * @version 2021-01-12
 */
@Table(name="h_game_user_ref", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="game_id", attrName="gameId", label="父ID", isUpdate=false),
		@Column(name="user_id", attrName="userId", label="用户ID", isUpdate=false),
		@Column(name="status", attrName="status", label="游戏状态", comment="游戏状态 1:离线 2:在线 3:战局中"),
		@Column(name="levels_completed", attrName="levelsCompleted", label="完成等级"),
		@Column(name="total_score", attrName="totalScore", label="游戏分数"),
		@Column(name="gold", attrName="gold", label="游戏金币"),
		@Column(name="boosters_count", attrName="boostersCount", label="游戏道具"),
		@Column(name="stars_per_level", attrName="starsPerLevel", label="游戏道具"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="start_time", attrName="startTime", label="创建时间"),
		@Column(name="end_time", attrName="endTime", label="更新时间"),
		@Column(name="create_by", attrName="createBy", label="创建人", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志", isQuery=false),
	},
	joinTable={
			@JoinTable(type=Type.LEFT_JOIN, entity=HgameInfo.class, alias="g",
					on="g.id = a.game_id", attrName="hgameInfo",
					columns={@Column(includeEntity=HgameInfo.class)}),
			@JoinTable(type=Type.LEFT_JOIN, entity=HgameUserInfo.class, alias="u",
					on="u.id = a.user_id", attrName="hgameUserInfo",
					columns={@Column(includeEntity=HgameUserInfo.class)}),
	},
	orderBy="a.created DESC"
)
public class HgameUserRef extends DataEntity<HgameUserRef> {

	private static final long serialVersionUID = 1L;
	private HgameInfo hgameInfo;
	private HgameUserInfo hgameUserInfo;

	private String gameId;		// 父ID
	private String userId;		// 用户ID
	private Long levelsCompleted;		// 完成等级
	private Long totalScore;		// 游戏分数
	private Long gold;		// 游戏金币
	private String boostersCount;		// 游戏道具
	private String starsPerLevel;		// 游戏道具
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private Date startTime;		// 创建时间
	private Date endTime;		// 更新时间
	private String delFlag;		// 删除标志


	public HgameUserRef() {
		this(null);
	}

	public HgameUserRef(String id){
		super(id);
	}

	@NotNull(message="父ID不能为空")
	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	@NotNull(message="用户ID不能为空")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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

	public Long getGold() {
		return gold;
	}

	public void setGold(Long gold) {
		this.gold = gold;
	}

	public HgameInfo getHgameInfo() {
		return hgameInfo;
	}

	public void setHgameInfo(HgameInfo hgameInfo) {
		this.hgameInfo = hgameInfo;
	}

	public HgameUserInfo getHgameUserInfo() {
		return hgameUserInfo;
	}

	public void setHgameUserInfo(HgameUserInfo hgameUserInfo) {
		this.hgameUserInfo = hgameUserInfo;
	}

	public Date getCreated_gte() {
		return sqlMap.getWhere().getValue("created", QueryType.GTE);
	}

	public void setCreated_gte(Date created) {
		sqlMap.getWhere().and("created", QueryType.GTE, created);
	}

	public Date getCreated_lte() {
		return sqlMap.getWhere().getValue("created", QueryType.LTE);
	}

	public void setCreated_lte(Date created) {
		sqlMap.getWhere().and("created", QueryType.LTE, created);
	}

}
