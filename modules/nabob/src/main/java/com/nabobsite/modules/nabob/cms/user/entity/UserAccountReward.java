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
 * 用户账户明细Entity
 * @author face
 * @version 2021-05-15
 */
@Table(name="t1_user_account_reward", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="account_id", attrName="accountId", label="账户ID"),
		@Column(name="title", attrName="title", label="标题", queryType=QueryType.LIKE),
		@Column(name="type", attrName="type", label="类型 1", comment="类型 1:总资金 20:佣金账户 30:仓库资金 40:奖励账户"),
		@Column(name="unique", attrName="unique", label="唯一标识"),
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
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class UserAccountReward extends DataEntity<UserAccountReward> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String accountId;		// 账户ID
	private String title;		// 标题
	private Integer type;		// 类型 1:总资金 20:佣金账户 30:仓库资金 40:奖励账户
	private String unique;		// 唯一标识
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
	
	public UserAccountReward() {
		this(null);
	}

	public UserAccountReward(String id){
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
	
	@NotBlank(message="账户ID不能为空")
	@Length(min=0, max=50, message="账户ID长度不能超过 50 个字符")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@NotBlank(message="标题不能为空")
	@Length(min=0, max=520, message="标题长度不能超过 520 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotNull(message="类型 1不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@NotBlank(message="唯一标识不能为空")
	@Length(min=0, max=128, message="唯一标识长度不能超过 128 个字符")
	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
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