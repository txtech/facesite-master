/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.entity;

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
 * 消消乐游戏日志Entity
 * @author nada
 * @version 2021-01-16
 */
@Table(name="h_game_play_log", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="game_id", attrName="gameId", label="父ID"),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="type", attrName="type", label="记录类型 1", comment="记录类型 1:同步日志 2:金币日志 3:道具日志"),
		@Column(name="level", attrName="level", label="游戏等级"),
		@Column(name="gole", attrName="gole", label="游戏金币"),
		@Column(name="score", attrName="score", label="游戏分数"),
		@Column(name="start", attrName="start", label="游戏星级"),
		@Column(name="boosters", attrName="boosters", label="游戏道具"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	},
	joinTable={
	@JoinTable(type=Type.LEFT_JOIN, entity=HgameInfo.class, alias="g",
			on="g.id = a.game_id", attrName="hgameInfo",
			columns={@Column(includeEntity=HgameInfo.class)}),
	@JoinTable(type=Type.LEFT_JOIN, entity=HgameUserInfo.class, alias="u",
			on="u.id = a.user_id", attrName="hgameUserInfo",
			columns={@Column(includeEntity=HgameUserInfo.class)}),
	},
	orderBy="a.id DESC"
)
public class HgamePlayLog extends DataEntity<HgamePlayLog> {

	private static final long serialVersionUID = 1L;
	private HgameInfo hgameInfo;
	private HgameUserInfo hgameUserInfo;
	private String gameId;		// 父ID
	private String userId;		// 用户ID
	private Integer type;		// 记录类型 1:同步日志 2:金币日志 3:道具日志
	private Long level;		// 游戏等级
	private Long gole;		// 游戏金币
	private Long score;		// 游戏分数
	private Long start;		// 游戏星级
	private Long boosters;		// 游戏道具
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志

	public HgamePlayLog() {
		this(null);
	}

	public HgamePlayLog(String id){
		super(id);
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Long getGole() {
		return gole;
	}

	public void setGole(Long gole) {
		this.gole = gole;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
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

	@Length(min=0, max=1, message="删除标志长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public HgameUserInfo getHgameUserInfo() {
		return hgameUserInfo;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getBoosters() {
		return boosters;
	}

	public void setBoosters(Long boosters) {
		this.boosters = boosters;
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

	public HgameInfo getHgameInfo() {
		return hgameInfo;
	}

	public void setHgameInfo(HgameInfo hgameInfo) {
		this.hgameInfo = hgameInfo;
	}
}
