/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户账户机器人信息Entity
 * @author face
 * @version 2021-05-10
 */
@Table(name="t1_user_account_bot", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="appreciation_rate", attrName="appreciationRate", label="增值率"),
		@Column(name="increment_money", attrName="incrementMoney", label="收益增长"),
		@Column(name="claimable_money", attrName="claimableMoney", label="可提现"),
		@Column(name="personal_income_money", attrName="personalIncomeMoney", label="个人受益"),
		@Column(name="team_income_money", attrName="teamIncomeMoney", label="团队收益"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class UserAccountBot extends DataEntity<UserAccountBot> {

	private static final long serialVersionUID = 1L;
	private Long userId;		// 用户id
	private Double appreciationRate;		// 增值率
	private Double incrementMoney;		// 收益增长
	private Double claimableMoney;		// 可提现
	private Double personalIncomeMoney;		// 个人受益
	private Double teamIncomeMoney;		// 团队收益
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志

	public UserAccountBot() {
		this(null);
	}

	public UserAccountBot(String id){
		super(id);
	}

	@NotNull(message="用户id不能为空")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@NotNull(message="增值率不能为空")
	public Double getAppreciationRate() {
		return appreciationRate;
	}

	public void setAppreciationRate(Double appreciationRate) {
		this.appreciationRate = appreciationRate;
	}

	@NotNull(message="收益增长不能为空")
	public Double getIncrementMoney() {
		return incrementMoney;
	}

	public void setIncrementMoney(Double incrementMoney) {
		this.incrementMoney = incrementMoney;
	}

	@NotNull(message="可提现不能为空")
	public Double getClaimableMoney() {
		return claimableMoney;
	}

	public void setClaimableMoney(Double claimableMoney) {
		this.claimableMoney = claimableMoney;
	}

	@NotNull(message="个人受益不能为空")
	public Double getPersonalIncomeMoney() {
		return personalIncomeMoney;
	}

	public void setPersonalIncomeMoney(Double personalIncomeMoney) {
		this.personalIncomeMoney = personalIncomeMoney;
	}

	@NotNull(message="团队收益不能为空")
	public Double getTeamIncomeMoney() {
		return teamIncomeMoney;
	}

	public void setTeamIncomeMoney(Double teamIncomeMoney) {
		this.teamIncomeMoney = teamIncomeMoney;
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
