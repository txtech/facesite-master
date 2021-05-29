/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.entity;

import javax.validation.constraints.NotBlank;

import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户账户仓库信息Entity
 * @author face
 * @version 2021-05-25
 */
@Table(name="t1_user_account_warehouse", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="asstes_held_money", attrName="asstesHeldMoney", label="仓库持有资产"),
		@Column(name="accumulative_income_money", attrName="accumulativeIncomeMoney", label="累计收益"),
		@Column(name="personal_income_money", attrName="personalIncomeMoney", label="个人受益"),
		@Column(name="personal_accumulative_income_money", attrName="personalAccumulativeIncomeMoney", label="个人累计收益"),
		@Column(name="team_income_money", attrName="teamIncomeMoney", label="团队收益"),
		@Column(name="team_accumulative_income_money", attrName="teamAccumulativeIncomeMoney", label="团队累计收益"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="pserson_update_time", attrName="psersonUpdateTime", label="个人动态收益更新时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="team_update_time", attrName="teamUpdateTime", label="团队动态收益更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
		@Column(name="income_money", attrName="incomeMoney", label="当前总收益")
	},
		joinTable={
				@JoinTable(type=Type.LEFT_JOIN, entity= UserInfo.class, alias="b",
						on="a.user_id = b.id", attrName="userInfo",
						columns={@Column(includeEntity=UserInfo.class)}),
		},
		orderBy="a.id DESC"
)
public class UserAccountWarehouse extends DataEntity<UserAccountWarehouse> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private BigDecimal asstesHeldMoney;		// 仓库持有资产
	private BigDecimal accumulativeIncomeMoney;		// 累计收益
	private BigDecimal personalIncomeMoney;		// 个人受益
	private BigDecimal personalAccumulativeIncomeMoney;		// 个人累计收益
	private BigDecimal teamIncomeMoney;		// 团队收益
	private BigDecimal teamAccumulativeIncomeMoney;		// 团队累计收益
	private Date created;		// 创建时间
	private Date psersonUpdateTime;		// 个人动态收益更新时间
	private Date updated;		// 更新时间
	private Date teamUpdateTime;		// 团队动态收益更新时间
	private String delFlag;		// 删除标志
	private BigDecimal incomeMoney;		// 当前总收益
	private UserInfo userInfo;

	public UserAccountWarehouse() {
		this(null);
	}

	public UserAccountWarehouse(String id){
		super(id);
	}
	
	@NotBlank(message="用户id不能为空")
	@Length(min=0, max=30, message="用户id长度不能超过 30 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@NotNull(message="仓库持有资产不能为空")
	public BigDecimal getAsstesHeldMoney() {
		return asstesHeldMoney;
	}

	public void setAsstesHeldMoney(BigDecimal asstesHeldMoney) {
		this.asstesHeldMoney = asstesHeldMoney;
	}
	
	@NotNull(message="累计收益不能为空")
	public BigDecimal getAccumulativeIncomeMoney() {
		return accumulativeIncomeMoney;
	}

	public void setAccumulativeIncomeMoney(BigDecimal accumulativeIncomeMoney) {
		this.accumulativeIncomeMoney = accumulativeIncomeMoney;
	}
	
	@NotNull(message="个人受益不能为空")
	public BigDecimal getPersonalIncomeMoney() {
		return personalIncomeMoney;
	}

	public void setPersonalIncomeMoney(BigDecimal personalIncomeMoney) {
		this.personalIncomeMoney = personalIncomeMoney;
	}
	
	@NotNull(message="个人累计收益不能为空")
	public BigDecimal getPersonalAccumulativeIncomeMoney() {
		return personalAccumulativeIncomeMoney;
	}

	public void setPersonalAccumulativeIncomeMoney(BigDecimal personalAccumulativeIncomeMoney) {
		this.personalAccumulativeIncomeMoney = personalAccumulativeIncomeMoney;
	}
	
	@NotNull(message="团队收益不能为空")
	public BigDecimal getTeamIncomeMoney() {
		return teamIncomeMoney;
	}

	public void setTeamIncomeMoney(BigDecimal teamIncomeMoney) {
		this.teamIncomeMoney = teamIncomeMoney;
	}
	
	@NotNull(message="团队累计收益不能为空")
	public BigDecimal getTeamAccumulativeIncomeMoney() {
		return teamAccumulativeIncomeMoney;
	}

	public void setTeamAccumulativeIncomeMoney(BigDecimal teamAccumulativeIncomeMoney) {
		this.teamAccumulativeIncomeMoney = teamAccumulativeIncomeMoney;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="个人动态收益更新时间不能为空")
	public Date getPsersonUpdateTime() {
		return psersonUpdateTime;
	}

	public void setPsersonUpdateTime(Date psersonUpdateTime) {
		this.psersonUpdateTime = psersonUpdateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="团队动态收益更新时间不能为空")
	public Date getTeamUpdateTime() {
		return teamUpdateTime;
	}

	public void setTeamUpdateTime(Date teamUpdateTime) {
		this.teamUpdateTime = teamUpdateTime;
	}
	
	@Length(min=0, max=1, message="删除标志长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	@NotNull(message="当前总收益不能为空")
	public BigDecimal getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}