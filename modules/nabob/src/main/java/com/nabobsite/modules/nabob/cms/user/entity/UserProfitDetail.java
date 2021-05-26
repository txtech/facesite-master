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
 * 用户分润Entity
 * @author face
 * @version 2021-05-26
 */
@Table(name="t1_user_profit_detail", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="parent1_user_id", attrName="parent1UserId", label="一级用户ID"),
		@Column(name="parent2_user_id", attrName="parent2UserId", label="二级用户ID"),
		@Column(name="parent3_user_id", attrName="parent3UserId", label="三级用户ID"),
		@Column(name="title", attrName="title", label="日志标题", queryType=QueryType.LIKE),
		@Column(name="type", attrName="type", label="账户状态 1", comment="账户状态 1:佣金分润 2:充值分润 3:云仓库分润"),
		@Column(name="money", attrName="money", label="交易金额"),
		@Column(name="profit_rate", attrName="profitRate", label="分润费率"),
		@Column(name="profit_money", attrName="profitMoney", label="分润金额"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class UserProfitDetail extends DataEntity<UserProfitDetail> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String parent1UserId;		// 一级用户ID
	private String parent2UserId;		// 二级用户ID
	private String parent3UserId;		// 三级用户ID
	private String title;		// 日志标题
	private Integer type;		// 账户状态 1:佣金分润 2:充值分润 3:云仓库分润
	private BigDecimal money;		// 交易金额
	private BigDecimal profitRate;		// 分润费率
	private BigDecimal profitMoney;		// 分润金额
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public UserProfitDetail() {
		this(null);
	}

	public UserProfitDetail(String id){
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
	
	@NotBlank(message="一级用户ID不能为空")
	@Length(min=0, max=30, message="一级用户ID长度不能超过 30 个字符")
	public String getParent1UserId() {
		return parent1UserId;
	}

	public void setParent1UserId(String parent1UserId) {
		this.parent1UserId = parent1UserId;
	}
	
	@NotBlank(message="二级用户ID不能为空")
	@Length(min=0, max=30, message="二级用户ID长度不能超过 30 个字符")
	public String getParent2UserId() {
		return parent2UserId;
	}

	public void setParent2UserId(String parent2UserId) {
		this.parent2UserId = parent2UserId;
	}
	
	@NotBlank(message="三级用户ID不能为空")
	public String getParent3UserId() {
		return parent3UserId;
	}

	public void setParent3UserId(String parent3UserId) {
		this.parent3UserId = parent3UserId;
	}
	
	@NotBlank(message="日志标题不能为空")
	@Length(min=0, max=520, message="日志标题长度不能超过 520 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotNull(message="账户状态 1不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@NotNull(message="交易金额不能为空")
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	@NotNull(message="分润费率不能为空")
	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}
	
	@NotNull(message="分润金额不能为空")
	public BigDecimal getProfitMoney() {
		return profitMoney;
	}

	public void setProfitMoney(BigDecimal profitMoney) {
		this.profitMoney = profitMoney;
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