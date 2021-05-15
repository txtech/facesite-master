/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 产品仓库Entity
 * @author face
 * @version 2021-05-10
 */
@Table(name="t1_product_warehouse", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(name="daily_interest_rate", attrName="dailyInterestRate", label="日收益比例"),
		@Column(name="days", attrName="days", label="限制日"),
		@Column(name="product_url", attrName="productUrl", label="图片地址"),
		@Column(name="desc", attrName="desc", label="描述"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class ProductWarehouse extends DataEntity<ProductWarehouse> {

	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private Double dailyInterestRate;		// 日收益比例
	private Long days;		// 限制日
	private String productUrl;		// 图片地址
	private String desc;		// 描述
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志

	public ProductWarehouse() {
		this(null);
	}

	public ProductWarehouse(String id){
		super(id);
	}

	@NotBlank(message="名称不能为空")
	@Length(min=0, max=1024, message="名称长度不能超过 1024 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull(message="日收益比例不能为空")
	public Double getDailyInterestRate() {
		return dailyInterestRate;
	}

	public void setDailyInterestRate(Double dailyInterestRate) {
		this.dailyInterestRate = dailyInterestRate;
	}

	@NotNull(message="限制日不能为空")
	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
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

	@NotBlank(message="描述不能为空")
	@Length(min=0, max=2048, message="描述长度不能超过 2048 个字符")
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

}