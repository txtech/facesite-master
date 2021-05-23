/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
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
 * 产品仓库Entity
 * @author face
 * @version 2021-05-23
 */
@Table(name="t1_product_warehouse", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="seq", attrName="seq", label="排序"),
		@Column(name="type", attrName="type", label="类型 1", comment="类型 1:实时存取 2:定投"),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(name="in_name", attrName="inName", label="印度名称", queryType=QueryType.LIKE),
		@Column(name="daily_interest_rate", attrName="dailyInterestRate", label="日收益比例"),
		@Column(name="limit_price", attrName="limitPrice", label="最低限额"),
		@Column(name="days", attrName="days", label="限制日"),
		@Column(name="product_url", attrName="productUrl", label="图片地址"),
		@Column(name="desc", attrName="desc", label="产品描述"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class ProductWarehouse extends DataEntity<ProductWarehouse> {
	
	private static final long serialVersionUID = 1L;
	private Integer seq;		// 排序
	private Integer type;		// 类型 1:实时存取 2:定投
	private String name;		// 名称
	private String inName;		// 印度名称
	private BigDecimal dailyInterestRate;		// 日收益比例
	private BigDecimal limitPrice;		// 最低限额
	private Integer days;		// 限制日
	private String productUrl;		// 图片地址
	private String desc;		// 产品描述
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	private BigDecimal asstesHeldMoney;
	
	public ProductWarehouse() {
		this(null);
	}

	public ProductWarehouse(String id){
		super(id);
	}
	
	@NotNull(message="排序不能为空")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@NotNull(message="类型 1不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@NotBlank(message="名称不能为空")
	@Length(min=0, max=1024, message="名称长度不能超过 1024 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank(message="印度名称不能为空")
	@Length(min=0, max=3200, message="印度名称长度不能超过 3200 个字符")
	public String getInName() {
		return inName;
	}

	public void setInName(String inName) {
		this.inName = inName;
	}
	
	@NotNull(message="日收益比例不能为空")
	public BigDecimal getDailyInterestRate() {
		return dailyInterestRate;
	}

	public void setDailyInterestRate(BigDecimal dailyInterestRate) {
		this.dailyInterestRate = dailyInterestRate;
	}
	
	@NotNull(message="最低限额不能为空")
	public BigDecimal getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(BigDecimal limitPrice) {
		this.limitPrice = limitPrice;
	}
	
	@NotNull(message="限制日不能为空")
	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}
	
	@NotBlank(message="图片地址不能为空")
	@Length(min=0, max=1024, message="图片地址长度不能超过 1024 个字符")
	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	
	@Length(min=0, max=2048, message="产品描述长度不能超过 2048 个字符")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public BigDecimal getAsstesHeldMoney() {
		return asstesHeldMoney;
	}

	public void setAsstesHeldMoney(BigDecimal asstesHeldMoney) {
		this.asstesHeldMoney = asstesHeldMoney;
	}
}