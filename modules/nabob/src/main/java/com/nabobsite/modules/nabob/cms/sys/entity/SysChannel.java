/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.sys.entity;

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
 * 通道配置Entity
 * @author face
 * @version 2021-05-23
 */
@Table(name="t1_sys_channel", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="type", attrName="type", label="类型 1,卡支付 2,扫码支付"),
		@Column(name="name", attrName="name", label="通道名称", queryType=QueryType.LIKE),
		@Column(name="channel_source", attrName="channelSource", label="来源"),
		@Column(name="merch_no", attrName="merchNo", label="配置no"),
		@Column(name="merch_key", attrName="merchKey", label="配置key"),
		@Column(name="pay_rate", attrName="payRate", label="支付费率"),
		@Column(name="cash_rate", attrName="cashRate", label="出款费率"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class SysChannel extends DataEntity<SysChannel> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 类型 1,卡支付 2,扫码支付
	private String name;		// 通道名称
	private Integer channelSource;		// 来源
	private String merchNo;		// 配置no
	private String merchKey;		// 配置key
	private BigDecimal payRate;		// 支付费率
	private Integer cashRate;		// 出款费率
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public SysChannel() {
		this(null);
	}

	public SysChannel(String id){
		super(id);
	}
	
	@NotNull(message="类型 1,卡支付 2,扫码支付不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@NotBlank(message="通道名称不能为空")
	@Length(min=0, max=250, message="通道名称长度不能超过 250 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getChannelSource() {
		return channelSource;
	}

	public void setChannelSource(Integer channelSource) {
		this.channelSource = channelSource;
	}
	
	@NotBlank(message="配置no不能为空")
	@Length(min=0, max=250, message="配置no长度不能超过 250 个字符")
	public String getMerchNo() {
		return merchNo;
	}

	public void setMerchNo(String merchNo) {
		this.merchNo = merchNo;
	}
	
	@NotBlank(message="配置key不能为空")
	@Length(min=0, max=2048, message="配置key长度不能超过 2048 个字符")
	public String getMerchKey() {
		return merchKey;
	}

	public void setMerchKey(String merchKey) {
		this.merchKey = merchKey;
	}
	
	@NotNull(message="支付费率不能为空")
	public BigDecimal getPayRate() {
		return payRate;
	}

	public void setPayRate(BigDecimal payRate) {
		this.payRate = payRate;
	}
	
	@NotNull(message="出款费率不能为空")
	public Integer getCashRate() {
		return cashRate;
	}

	public void setCashRate(Integer cashRate) {
		this.cashRate = cashRate;
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