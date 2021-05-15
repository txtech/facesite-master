/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户账户Entity
 * @author face
 * @version 2021-05-15
 */
@Table(name="t1_user_account", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="account_status", attrName="accountStatus", label="账户状态 1", comment="账户状态 1:可用 2:冻结"),
		@Column(name="total_money", attrName="totalMoney", label="总资金"),
		@Column(name="available_money", attrName="availableMoney", label="可用资金"),
		@Column(name="warehouse_money", attrName="warehouseMoney", label="仓库资金"),
		@Column(name="income_money", attrName="incomeMoney", label="收入资金"),
		@Column(name="ai_assets_money", attrName="aiAssetsMoney", label="云资产"),
		@Column(name="commission_money", attrName="commissionMoney", label="佣金账户"),
		@Column(name="increment_money", attrName="incrementMoney", label="增值账户"),
		@Column(name="claimable_money", attrName="claimableMoney", label="可提取账户"),
		@Column(name="reward_money", attrName="rewardMoney", label="奖励账户"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class UserAccount extends DataEntity<UserAccount> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private Integer accountStatus;		// 账户状态 1:可用 2:冻结
	private BigDecimal totalMoney;		// 总资金
	private BigDecimal availableMoney;		// 可用资金
	private BigDecimal warehouseMoney;		// 仓库资金
	private BigDecimal incomeMoney;		// 收入资金
	private BigDecimal aiAssetsMoney;		// 云资产
	private BigDecimal commissionMoney;		// 佣金账户
	private BigDecimal incrementMoney;		// 增值账户
	private BigDecimal claimableMoney;		// 可提取账户
	private BigDecimal rewardMoney;		// 奖励账户
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public UserAccount() {
		this(null);
	}

	public UserAccount(String id){
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
	
	@NotNull(message="账户状态 1不能为空")
	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	@NotNull(message="总资金不能为空")
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	@NotNull(message="可用资金不能为空")
	public BigDecimal getAvailableMoney() {
		return availableMoney;
	}

	public void setAvailableMoney(BigDecimal availableMoney) {
		this.availableMoney = availableMoney;
	}
	
	@NotNull(message="仓库资金不能为空")
	public BigDecimal getWarehouseMoney() {
		return warehouseMoney;
	}

	public void setWarehouseMoney(BigDecimal warehouseMoney) {
		this.warehouseMoney = warehouseMoney;
	}
	
	@NotNull(message="收入资金不能为空")
	public BigDecimal getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}
	
	@NotNull(message="云资产不能为空")
	public BigDecimal getAiAssetsMoney() {
		return aiAssetsMoney;
	}

	public void setAiAssetsMoney(BigDecimal aiAssetsMoney) {
		this.aiAssetsMoney = aiAssetsMoney;
	}
	
	@NotNull(message="佣金账户不能为空")
	public BigDecimal getCommissionMoney() {
		return commissionMoney;
	}

	public void setCommissionMoney(BigDecimal commissionMoney) {
		this.commissionMoney = commissionMoney;
	}
	
	@NotNull(message="增值账户不能为空")
	public BigDecimal getIncrementMoney() {
		return incrementMoney;
	}

	public void setIncrementMoney(BigDecimal incrementMoney) {
		this.incrementMoney = incrementMoney;
	}
	
	@NotNull(message="可提取账户不能为空")
	public BigDecimal getClaimableMoney() {
		return claimableMoney;
	}

	public void setClaimableMoney(BigDecimal claimableMoney) {
		this.claimableMoney = claimableMoney;
	}
	
	@NotNull(message="奖励账户不能为空")
	public BigDecimal getRewardMoney() {
		return rewardMoney;
	}

	public void setRewardMoney(BigDecimal rewardMoney) {
		this.rewardMoney = rewardMoney;
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