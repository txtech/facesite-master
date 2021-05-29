/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.order.entity;

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
 * 出款订单Entity
 * @author face
 * @version 2021-05-25
 */
@Table(name="t1_order_cash", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="channel_id", attrName="channelId", label="通道ID"),
		@Column(name="order_no", attrName="orderNo", label="订单号"),
		@Column(name="p_order_no", attrName="porderNo", label="上游订单号"),
		@Column(name="type", attrName="type", label="类型 1", comment="类型 1:用户提款"),
		@Column(name="cash_status", attrName="cashStatus", label="状态 1", comment="状态 1:发起 2:出款中 3:出款失败 4:出款成功 9:退回"),
		@Column(name="cash_money", attrName="cashMoney", label="提现金额"),
		@Column(name="service_charge", attrName="serviceCharge", label="提现手续费"),
		@Column(name="ipaddress", attrName="ipaddress", label="提现地址"),
		@Column(name="cash_rate", attrName="cashRate", label="出款费率"),
		@Column(name="account_name", attrName="accountName", label="账户名称", queryType=QueryType.LIKE),
		@Column(name="account_card", attrName="accountCard", label="账户卡号"),
		@Column(name="email", attrName="email", label="邮箱"),
		@Column(name="phone_number", attrName="phoneNumber", label="手机号码"),
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
		},
		orderBy="a.id DESC"
)
public class OrderCash extends DataEntity<OrderCash> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String channelId;		// 通道ID
	private String orderNo;		// 订单号
	private String porderNo;		// 上游订单号
	private Integer type;		// 类型 1:用户提款
	private Integer cashStatus;		// 状态 1:发起 2:出款中 3:出款失败 4:出款成功 9:退回
	private BigDecimal cashMoney;		// 提现金额
	private BigDecimal serviceCharge;		// 提现手续费
	private String ipaddress;		// 提现地址
	private BigDecimal cashRate;		// 出款费率
	private String accountName;		// 账户名称
	private String accountCard;		// 账户卡号
	private String email;		// 邮箱
	private String phoneNumber;		// 手机号码
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	private UserInfo userInfo;
	
	public OrderCash() {
		this(null);
	}

	public OrderCash(String id){
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
	
	@NotBlank(message="通道ID不能为空")
	@Length(min=0, max=50, message="通道ID长度不能超过 50 个字符")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	@NotBlank(message="订单号不能为空")
	@Length(min=0, max=250, message="订单号长度不能超过 250 个字符")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@NotBlank(message="上游订单号不能为空")
	public String getPorderNo() {
		return porderNo;
	}

	public void setPorderNo(String porderNo) {
		this.porderNo = porderNo;
	}
	
	@NotNull(message="类型 1不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@NotNull(message="状态 1不能为空")
	public Integer getCashStatus() {
		return cashStatus;
	}

	public void setCashStatus(Integer cashStatus) {
		this.cashStatus = cashStatus;
	}
	
	@NotNull(message="提现金额不能为空")
	public BigDecimal getCashMoney() {
		return cashMoney;
	}

	public void setCashMoney(BigDecimal cashMoney) {
		this.cashMoney = cashMoney;
	}
	
	@NotNull(message="提现手续费不能为空")
	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	
	@NotBlank(message="提现地址不能为空")
	@Length(min=0, max=255, message="提现地址长度不能超过 255 个字符")
	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	
	@NotNull(message="出款费率不能为空")
	public BigDecimal getCashRate() {
		return cashRate;
	}

	public void setCashRate(BigDecimal cashRate) {
		this.cashRate = cashRate;
	}
	
	@NotBlank(message="账户名称不能为空")
	@Length(min=0, max=255, message="账户名称长度不能超过 255 个字符")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@NotBlank(message="账户卡号不能为空")
	public String getAccountCard() {
		return accountCard;
	}

	public void setAccountCard(String accountCard) {
		this.accountCard = accountCard;
	}
	
	@Length(min=0, max=255, message="邮箱长度不能超过 255 个字符")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=20, message="手机号码长度不能超过 20 个字符")
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
}