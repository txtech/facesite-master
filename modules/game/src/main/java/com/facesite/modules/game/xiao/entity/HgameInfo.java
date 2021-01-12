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
 * 游戏信息Entity
 * @author nada
 * @version 2021-01-11
 */
@Table(name="h_game_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="游戏名称", queryType=QueryType.LIKE),
		@Column(name="status", attrName="status", label="游戏状态 1", comment="游戏状态 1:启用 2:停用", isUpdate=false),
		@Column(name="type", attrName="type", label="游戏类型：1", comment="游戏类型：1:新人福利场 2:欢乐场 3:竞技场"),
		@Column(name="version", attrName="version", label="游戏版本"),
		@Column(name="win", attrName="win", label="赢得游戏"),
		@Column(name="lose", attrName="lose", label="游戏输了"),
		@Column(name="config", attrName="config", label="游戏配置"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class HgameInfo extends DataEntity<HgameInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 游戏名称
	private Integer type;		// 游戏类型：1:新人福利场 2:欢乐场 3:竞技场
	private String version;		// 游戏版本
	private Long win;		// 赢得游戏
	private Long lose;		// 游戏输了
	private String config;		// 游戏配置
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public HgameInfo() {
		this(null);
	}

	public HgameInfo(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="游戏名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="游戏版本长度不能超过 255 个字符")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public Long getWin() {
		return win;
	}

	public void setWin(Long win) {
		this.win = win;
	}
	
	public Long getLose() {
		return lose;
	}

	public void setLose(Long lose) {
		this.lose = lose;
	}
	
	@Length(min=0, max=1024, message="游戏配置长度不能超过 1024 个字符")
	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
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