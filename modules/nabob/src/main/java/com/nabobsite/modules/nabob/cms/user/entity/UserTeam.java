/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 会员用户Entity
 * @author face
 * @version 2021-05-23
 */
@Table(name="t1_user_team", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="valid_num", attrName="validNum", label="有效直推个数"),
		@Column(name="team_num", attrName="teamNum", label="团队总人数"),
		@Column(name="team1_num", attrName="team1Num", label="一级团队人数"),
		@Column(name="team2_num", attrName="team2Num", label="二级团队人数"),
		@Column(name="team3_num", attrName="team3Num", label="三级团队人数"),
		@Column(name="link_data", attrName="linkData", label="团队链"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class UserTeam extends DataEntity<UserTeam> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private Integer validNum;		// 有效直推个数
	private Integer teamNum;		// 团队总人数
	private Integer team1Num;		// 一级团队人数
	private Integer team2Num;		// 二级团队人数
	private Integer team3Num;		// 三级团队人数
	private String linkData;		// 团队链
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public UserTeam() {
		this(null);
	}

	public UserTeam(String id){
		super(id);
	}
	
	@NotBlank(message="用户ID不能为空")
	@Length(min=0, max=30, message="用户ID长度不能超过 30 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@NotNull(message="有效直推个数不能为空")
	public Integer getValidNum() {
		return validNum;
	}

	public void setValidNum(Integer validNum) {
		this.validNum = validNum;
	}
	
	@NotNull(message="团队总人数不能为空")
	public Integer getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(Integer teamNum) {
		this.teamNum = teamNum;
	}
	
	@NotNull(message="一级团队人数不能为空")
	public Integer getTeam1Num() {
		return team1Num;
	}

	public void setTeam1Num(Integer team1Num) {
		this.team1Num = team1Num;
	}
	
	@NotNull(message="二级团队人数不能为空")
	public Integer getTeam2Num() {
		return team2Num;
	}

	public void setTeam2Num(Integer team2Num) {
		this.team2Num = team2Num;
	}
	
	@NotNull(message="三级团队人数不能为空")
	public Integer getTeam3Num() {
		return team3Num;
	}

	public void setTeam3Num(Integer team3Num) {
		this.team3Num = team3Num;
	}
	
	@NotBlank(message="团队链不能为空")
	public String getLinkData() {
		return linkData;
	}

	public void setLinkData(String linkData) {
		this.linkData = linkData;
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