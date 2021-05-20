/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.order.entity;

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
 * 订单Entity
 * @author face
 * @version 2021-05-20
 */
@Table(name="t1_order", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="type", attrName="type", label="订单类型"),
		@Column(name="channel_id", attrName="channelId", label="通道ID"),
		@Column(name="order_status", attrName="orderStatus", label="1", comment="1: 待支付 2:支付中 3:支付失败 4:支付成功 9:退款"),
		@Column(name="order_no", attrName="orderNo", label="订单号"),
		@Column(name="pay_type", attrName="payType", label="支付类型"),
		@Column(name="p_order_no", attrName="porderNo", label="上游订单号"),
		@Column(name="pay_money", attrName="payMoney", label="支付金额"),
		@Column(name="actual_money", attrName="actualMoney", label="真实到账金额"),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(name="email", attrName="email", label="邮箱"),
		@Column(name="ipaddress", attrName="ipaddress", label="订单IP"),
		@Column(name="phone_number", attrName="phoneNumber", label="电话号码"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="pay_rate", attrName="payRate", label="支付费率"),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class Order extends DataEntity<Order> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private Integer type;		// 订单类型
	private String channelId;		// 通道ID
	private Integer orderStatus;		// 1: 待支付 2:支付中 3:支付失败 4:支付成功 9:退款
	private String orderNo;		// 订单号
	private Integer payType;		// 支付类型
	private String porderNo;		// 上游订单号
	private BigDecimal payMoney;		// 支付金额
	private BigDecimal actualMoney;		// 真实到账金额
	private String name;		// 名称
	private String email;		// 邮箱
	private String ipaddress;		// 订单IP
	private String phoneNumber;		// 电话号码
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private BigDecimal payRate;		// 支付费率
	private String delFlag;		// 删除标志
	
	public Order() {
		this(null);
	}

	public Order(String id){
		super(id);
	}
	
	@NotBlank(message="用户ID不能为空")
	@Length(min=0, max=30, message="用户ID长度不能超过 30 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@NotNull(message="订单类型不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@NotBlank(message="通道ID不能为空")
	@Length(min=0, max=30, message="通道ID长度不能超过 30 个字符")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	@NotNull(message="1不能为空")
	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	@NotBlank(message="订单号不能为空")
	@Length(min=0, max=50, message="订单号长度不能超过 50 个字符")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@NotNull(message="支付类型不能为空")
	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=50, message="上游订单号长度不能超过 50 个字符")
	public String getPorderNo() {
		return porderNo;
	}

	public void setPorderNo(String porderNo) {
		this.porderNo = porderNo;
	}
	
	@NotNull(message="支付金额不能为空")
	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}
	
	@NotNull(message="真实到账金额不能为空")
	public BigDecimal getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(BigDecimal actualMoney) {
		this.actualMoney = actualMoney;
	}
	
	@NotBlank(message="名称不能为空")
	@Length(min=0, max=255, message="名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank(message="邮箱不能为空")
	@Length(min=0, max=255, message="邮箱长度不能超过 255 个字符")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@NotBlank(message="订单IP不能为空")
	@Length(min=0, max=255, message="订单IP长度不能超过 255 个字符")
	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	
	@NotBlank(message="电话号码不能为空")
	@Length(min=0, max=255, message="电话号码长度不能超过 255 个字符")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	
	@NotNull(message="支付费率不能为空")
	public BigDecimal getPayRate() {
		return payRate;
	}

	public void setPayRate(BigDecimal payRate) {
		this.payRate = payRate;
	}
	
	@Length(min=0, max=1, message="删除标志长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}