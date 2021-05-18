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
 * 产品机器人Entity
 * @author face
 * @version 2021-05-12
 */
@Table(name="t1_product_bot", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="seq", attrName="seq", label="排序"),
		@Column(name="name", attrName="name", label="产品名称", queryType=QueryType.LIKE),
		@Column(name="level", attrName="level", label="限制等级"),
		@Column(name="price", attrName="price", label="价格"),
		@Column(name="commission_rate", attrName="commissionRate", label="佣金比例"),
		@Column(name="commission_rate_other", attrName="commissionRateOther", label="额外佣金比例"),
		@Column(name="product_url", attrName="productUrl", label="产品图片"),
		@Column(name="desc", attrName="desc", label="产品描述"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class ProductBot extends DataEntity<ProductBot> {

	private static final long serialVersionUID = 1L;
	private Integer seq;		// 排序
	private String name;		// 产品名称
	private Integer level;		// 限制等级
	private BigDecimal price;		// 价格
	private BigDecimal commissionRate;		// 佣金比例
	private BigDecimal commissionRateOther;		// 额外佣金比例
	private String productUrl;		// 产品图片
	private String desc;		// 产品描述
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	private String orderNo;

	public ProductBot() {
		this(null);
	}

	public ProductBot(String id){
		super(id);
	}

	@NotNull(message="排序不能为空")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@NotBlank(message="产品名称不能为空")
	@Length(min=0, max=520, message="产品名称长度不能超过 520 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull(message="限制等级不能为空")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@NotNull(message="佣金比例不能为空")
	public BigDecimal getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}

	@NotNull(message="额外佣金比例不能为空")
	public BigDecimal getCommissionRateOther() {
		return commissionRateOther;
	}

	public void setCommissionRateOther(BigDecimal commissionRateOther) {
		this.commissionRateOther = commissionRateOther;
	}

	@NotBlank(message="产品图片不能为空")
	@Length(min=0, max=1024, message="产品图片长度不能超过 1024 个字符")
	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	@Length(min=0, max=2024, message="产品描述长度不能超过 2024 个字符")
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
