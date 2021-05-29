/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.entity;

import javax.validation.constraints.NotBlank;

import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
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
 * 用户产品仓库信息Entity
 * @author face
 * @version 2021-05-25
 */
@Table(name="t1_product_user_warehouse", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="warehouse_id", attrName="warehouseId", label="产品仓库ID"),
		@Column(name="asstes_held_money", attrName="asstesHeldMoney", label="持有理财资产"),
		@Column(name="accumulative_income_money", attrName="accumulativeIncomeMoney", label="累计动态收益"),
		@Column(name="personal_income_money", attrName="personalIncomeMoney", label="个人动态受益"),
		@Column(name="personal_accumulative_income_money", attrName="personalAccumulativeIncomeMoney", label="累计个人受益"),
		@Column(name="team_income_money", attrName="teamIncomeMoney", label="团队动态收益"),
		@Column(name="team_accumulative_income_money", attrName="teamAccumulativeIncomeMoney", label="团队累计动态收益"),
		@Column(name="pserson_update_time", attrName="psersonUpdateTime", label="个人动态收益更新时间"),
		@Column(name="team_update_time", attrName="teamUpdateTime", label="团队动态收益更新时间"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
		@Column(name="income_money", attrName="incomeMoney", label="当前总收益"),
		@Column(name="ai_assets_money", attrName="aiAssetsMoney", label="云资产"),
	},
		extWhereKeys="dsfOffice",
		joinTable={
				@JoinTable(type=Type.LEFT_JOIN, entity= UserInfo.class, alias="b",
						on="a.user_id = b.id", attrName="userInfo",
						columns={@Column(includeEntity=UserInfo.class)}),
				@JoinTable(type=Type.LEFT_JOIN, entity= ProductWarehouse.class, alias="c",
						on="a.warehouse_id = c.id", attrName="productWarehouse",
						columns={@Column(includeEntity=ProductWarehouse.class)}),
		},
		orderBy="a.id DESC"
)
public class ProductUserWarehouse extends DataEntity<ProductUserWarehouse> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String warehouseId;		// 产品仓库ID
	private BigDecimal asstesHeldMoney;		// 持有理财资产
	private BigDecimal accumulativeIncomeMoney;		// 累计动态收益
	private BigDecimal personalIncomeMoney;		// 个人动态受益
	private BigDecimal personalAccumulativeIncomeMoney;		// 累计个人受益
	private BigDecimal teamIncomeMoney;		// 团队动态收益
	private BigDecimal teamAccumulativeIncomeMoney;		// 团队累计动态收益
	private Date psersonUpdateTime;		// 个人动态收益更新时间
	private Date teamUpdateTime;		// 团队动态收益更新时间
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	private BigDecimal incomeMoney;		// 当前总收益
	private BigDecimal aiAssetsMoney;		// 云资产
	private UserInfo userInfo;
	private ProductWarehouse productWarehouse;

	
	public ProductUserWarehouse() {
		this(null);
	}

	public ProductUserWarehouse(String id){
		super(id);
	}
	
	@NotBlank(message="用户ID不能为空")
	@Length(min=0, max=50, message="用户ID长度不能超过 50 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@NotBlank(message="产品仓库ID不能为空")
	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	@NotNull(message="持有理财资产不能为空")
	public BigDecimal getAsstesHeldMoney() {
		return asstesHeldMoney;
	}

	public void setAsstesHeldMoney(BigDecimal asstesHeldMoney) {
		this.asstesHeldMoney = asstesHeldMoney;
	}
	
	@NotNull(message="累计动态收益不能为空")
	public BigDecimal getAccumulativeIncomeMoney() {
		return accumulativeIncomeMoney;
	}

	public void setAccumulativeIncomeMoney(BigDecimal accumulativeIncomeMoney) {
		this.accumulativeIncomeMoney = accumulativeIncomeMoney;
	}
	
	@NotNull(message="个人动态受益不能为空")
	public BigDecimal getPersonalIncomeMoney() {
		return personalIncomeMoney;
	}

	public void setPersonalIncomeMoney(BigDecimal personalIncomeMoney) {
		this.personalIncomeMoney = personalIncomeMoney;
	}
	
	@NotNull(message="累计个人受益不能为空")
	public BigDecimal getPersonalAccumulativeIncomeMoney() {
		return personalAccumulativeIncomeMoney;
	}

	public void setPersonalAccumulativeIncomeMoney(BigDecimal personalAccumulativeIncomeMoney) {
		this.personalAccumulativeIncomeMoney = personalAccumulativeIncomeMoney;
	}
	
	@NotNull(message="团队动态收益不能为空")
	public BigDecimal getTeamIncomeMoney() {
		return teamIncomeMoney;
	}

	public void setTeamIncomeMoney(BigDecimal teamIncomeMoney) {
		this.teamIncomeMoney = teamIncomeMoney;
	}
	
	@NotNull(message="团队累计动态收益不能为空")
	public BigDecimal getTeamAccumulativeIncomeMoney() {
		return teamAccumulativeIncomeMoney;
	}

	public void setTeamAccumulativeIncomeMoney(BigDecimal teamAccumulativeIncomeMoney) {
		this.teamAccumulativeIncomeMoney = teamAccumulativeIncomeMoney;
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
	@NotNull(message="团队动态收益更新时间不能为空")
	public Date getTeamUpdateTime() {
		return teamUpdateTime;
	}

	public void setTeamUpdateTime(Date teamUpdateTime) {
		this.teamUpdateTime = teamUpdateTime;
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
	
	@NotNull(message="当前总收益不能为空")
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

	public ProductWarehouse getProductWarehouse() {
		return productWarehouse;
	}

	public void setProductWarehouse(ProductWarehouse productWarehouse) {
		this.productWarehouse = productWarehouse;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}