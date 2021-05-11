/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户产品仓库信息Entity
 * @author face
 * @version 2021-05-10
 */
@Table(name="t1_user_product_warehouse", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="warehouse_id", attrName="warehouseId", label="产品仓库ID"),
		@Column(name="asstes_held_money", attrName="asstesHeldMoney", label="持有资产"),
		@Column(name="accumulative_income_money", attrName="accumulativeIncomeMoney", label="累计收益"),
		@Column(name="personal_income_money", attrName="personalIncomeMoney", label="个人受益"),
		@Column(name="personal_accumulative_income_money", attrName="personalAccumulativeIncomeMoney", label="累计个人收入"),
		@Column(name="team_income_money", attrName="teamIncomeMoney", label="团队收益"),
		@Column(name="team_accumulative_income_money", attrName="teamAccumulativeIncomeMoney", label="团队累计收益"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class UserProductWarehouse extends DataEntity<UserProductWarehouse> {

	private static final long serialVersionUID = 1L;
	private Long userId;		// 用户ID
	private Long warehouseId;		// 产品仓库ID
	private Double asstesHeldMoney;		// 持有资产
	private Double accumulativeIncomeMoney;		// 累计收益
	private Double personalIncomeMoney;		// 个人受益
	private Double personalAccumulativeIncomeMoney;		// 累计个人收入
	private Double teamIncomeMoney;		// 团队收益
	private Double teamAccumulativeIncomeMoney;		// 团队累计收益
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志

	public UserProductWarehouse() {
		this(null);
	}

	public UserProductWarehouse(String id){
		super(id);
	}

	@NotNull(message="用户ID不能为空")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@NotNull(message="产品仓库ID不能为空")
	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	@NotNull(message="持有资产不能为空")
	public Double getAsstesHeldMoney() {
		return asstesHeldMoney;
	}

	public void setAsstesHeldMoney(Double asstesHeldMoney) {
		this.asstesHeldMoney = asstesHeldMoney;
	}

	@NotNull(message="累计收益不能为空")
	public Double getAccumulativeIncomeMoney() {
		return accumulativeIncomeMoney;
	}

	public void setAccumulativeIncomeMoney(Double accumulativeIncomeMoney) {
		this.accumulativeIncomeMoney = accumulativeIncomeMoney;
	}

	@NotNull(message="个人受益不能为空")
	public Double getPersonalIncomeMoney() {
		return personalIncomeMoney;
	}

	public void setPersonalIncomeMoney(Double personalIncomeMoney) {
		this.personalIncomeMoney = personalIncomeMoney;
	}

	@NotNull(message="累计个人收入不能为空")
	public Double getPersonalAccumulativeIncomeMoney() {
		return personalAccumulativeIncomeMoney;
	}

	public void setPersonalAccumulativeIncomeMoney(Double personalAccumulativeIncomeMoney) {
		this.personalAccumulativeIncomeMoney = personalAccumulativeIncomeMoney;
	}

	@NotNull(message="团队收益不能为空")
	public Double getTeamIncomeMoney() {
		return teamIncomeMoney;
	}

	public void setTeamIncomeMoney(Double teamIncomeMoney) {
		this.teamIncomeMoney = teamIncomeMoney;
	}

	@NotNull(message="团队累计收益不能为空")
	public Double getTeamAccumulativeIncomeMoney() {
		return teamAccumulativeIncomeMoney;
	}

	public void setTeamAccumulativeIncomeMoney(Double teamAccumulativeIncomeMoney) {
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
