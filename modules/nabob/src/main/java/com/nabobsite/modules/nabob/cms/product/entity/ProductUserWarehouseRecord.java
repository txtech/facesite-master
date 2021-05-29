/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.entity;

import javax.validation.constraints.NotBlank;

import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
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
 * 用户云仓库操作记录Entity
 * @author face
 * @version 2021-05-23
 */
@Table(name="t1_product_user_warehouse_record", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="warehouse_id", attrName="warehouseId", label="产品仓库ID"),
		@Column(name="type", attrName="type", label="类型 1", comment="类型 1:存款 2:撤资"),
		@Column(name="money", attrName="money", label="持有资产"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
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
public class ProductUserWarehouseRecord extends DataEntity<ProductUserWarehouseRecord> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String warehouseId;		// 产品仓库ID
	private Integer type;		// 类型 1:存款 2:撤资
	private BigDecimal money;		// 持有资产
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	private UserInfo userInfo;
	private ProductWarehouse productWarehouse;
	
	public ProductUserWarehouseRecord() {
		this(null);
	}

	public ProductUserWarehouseRecord(String id){
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
	
	@NotNull(message="产品仓库ID不能为空")
	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	@NotNull(message="类型 1不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@NotNull(message="持有资产不能为空")
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
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

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public ProductWarehouse getProductWarehouse() {
		return productWarehouse;
	}

	public void setProductWarehouse(ProductWarehouse productWarehouse) {
		this.productWarehouse = productWarehouse;
	}
}