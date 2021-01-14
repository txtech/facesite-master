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
 * 玩游戏记录Entity
 * @author nada
 * @version 2021-01-12
 */
@Table(name="h_game_play_record", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="play_id", attrName="playId", label="玩局ID"),
		@Column(name="game_id", attrName="gameId", label="父ID"),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="status", attrName="status", label="游戏状态", comment="游戏状态 1:输 2:赢", isUpdate=false),
		@Column(name="type", attrName="type", label="记录类型", comment="记录类型", isUpdate=false),
		@Column(name="symbol", attrName="symbol", label="游戏运算符", comment="游戏运算符：1+，2-，3*，4/"),
		@Column(name="update_beans", attrName="updateBeans", label="修改豆值"),
		@Column(name="hbeans", attrName="hbeans", label="用户呵豆"),
		@Column(name="lbeans", attrName="lbeans", label="用户乐豆"),
		@Column(name="gold", attrName="gold", label="游戏金币"),
		@Column(name="level", attrName="level", label="游戏等级"),
		@Column(name="score", attrName="score", label="游戏分数"),
		@Column(name="start", attrName="start", label="游戏星级"),
		@Column(name="boosters", attrName="boosters", label="游戏道具"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
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
public class HgamePlayRecord extends DataEntity<HgamePlayRecord> {

	private static final long serialVersionUID = 1L;
	private HgameInfo hgameInfo;
	private HgameUserInfo hgameUserInfo;
	private String gameId;		// 父ID
	private String userId;		// 用户ID
	private Integer symbol;		// 游戏运算符：0 1+，2-，3*，4/
	private Long updateBeans;		// 修改豆值
	private Long hbeans;		// 用户呵豆
	private Long lbeans;		// 用户乐豆
	private Long gold;		// 游戏金币
	private Long level;		// 游戏等级
	private Long score;		// 游戏分数
	private Long start;		// 游戏星级
	private String playId;		// 游戏时间
	private Integer type; //类型：1:准备 2:开始 3:升级 4:完成 5:结束
	private String boosters;		// 游戏道具
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志


	public HgamePlayRecord() {
		this(null);
	}

	public HgamePlayRecord(String id){
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

	public Integer getSymbol() {
		return symbol;
	}

	public void setSymbol(Integer symbol) {
		this.symbol = symbol;
	}

	public Long getUpdateBeans() {
		return updateBeans;
	}

	public void setUpdateBeans(Long updateBeans) {
		this.updateBeans = updateBeans;
	}

	public Long getHbeans() {
		return hbeans;
	}

	public void setHbeans(Long hbeans) {
		this.hbeans = hbeans;
	}

	public Long getLbeans() {
		return lbeans;
	}

	public void setLbeans(Long lbeans) {
		this.lbeans = lbeans;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
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

	@Length(min=0, max=255, message="游戏道具长度不能超过 255 个字符")
	public String getBoosters() {
		return boosters;
	}

	public void setBoosters(String boosters) {
		this.boosters = boosters;
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

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getGold() {
		return gold;
	}

	public void setGold(Long gold) {
		this.gold = gold;
	}
}
