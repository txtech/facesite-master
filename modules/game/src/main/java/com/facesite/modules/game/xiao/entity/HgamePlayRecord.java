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
 * 玩游戏记录Entity
 * @author nada
 * @version 2021-01-11
 */
@Table(name="h_game_play_record", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="game_id", attrName="gameId", label="父ID"),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="status", attrName="status", label="游戏状态 1", comment="游戏状态 1:输 2:赢", isUpdate=false),
		@Column(name="symbol", attrName="symbol", label="游戏运算符", comment="游戏运算符：1+，2-，3*，4/"),
		@Column(name="update_beans", attrName="updateBeans", label="修改豆值"),
		@Column(name="hbeans", attrName="hbeans", label="用户呵豆"),
		@Column(name="lbeans", attrName="lbeans", label="用户乐豆"),
		@Column(name="gole", attrName="gole", label="游戏金币"),
		@Column(name="level", attrName="level", label="游戏等级"),
		@Column(name="score", attrName="score", label="游戏分数"),
		@Column(name="start", attrName="start", label="游戏星级"),
		@Column(name="time", attrName="time", label="游戏时间"),
		@Column(name="boosters", attrName="boosters", label="游戏道具"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class HgamePlayRecord extends DataEntity<HgamePlayRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long gameId;		// 父ID
	private Long userId;		// 用户ID
	private Integer symbol;		// 游戏运算符：1+，2-，3*，4/
	private Long updateBeans;		// 修改豆值
	private Long hbeans;		// 用户呵豆
	private Long lbeans;		// 用户乐豆
	private Long gole;		// 游戏金币
	private Long level;		// 游戏等级
	private Long score;		// 游戏分数
	private Long start;		// 游戏星级
	private Long time;		// 游戏时间
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
	
	public Long getGole() {
		return gole;
	}

	public void setGole(Long gole) {
		this.gole = gole;
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
	
	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
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
	
}