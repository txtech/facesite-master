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
 * 出款Entity
 * @author face
 * @version 2021-05-12
 */
@Table(name="t1_cash", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="type", attrName="type", label="类型 1", comment="类型 1:充值订单"),
		@Column(name="cash_status", attrName="cashStatus", label="状态 1", comment="状态 1:发起 2:出款中 3:出款失败 4:出款成功 9:退回"),
		@Column(name="cash_money", attrName="cashMoney", label="提现金额"),
		@Column(name="service_charge", attrName="serviceCharge", label="提现手续费"),
		@Column(name="ipaddress", attrName="ipaddress", label="提现地址"),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(name="email", attrName="email", label="邮箱"),
		@Column(name="phone_number", attrName="phoneNumber", label="手机号码"),
		@Column(name="account_no", attrName="accountNo", label="提款账户"),
		@Column(name="cash_message", attrName="cashMessage", label="提款豹纹"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class Cash extends DataEntity<Cash> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private Integer type;		// 类型 1:充值订单
	private Integer cashStatus;		// 状态 1:发起 2:出款中 3:出款失败 4:出款成功 9:退回
	private BigDecimal cashMoney;		// 提现金额
	private BigDecimal serviceCharge;		// 提现手续费
	private String ipaddress;		// 提现地址
	private String name;		// 名称
	private String email;		// 邮箱
	private String phoneNumber;		// 手机号码
	private String accountNo;		// 提款账户
	private String cashMessage;		// 提款豹纹
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public Cash() {
		this(null);
	}

	public Cash(String id){
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
	
	@Length(min=0, max=255, message="提现地址长度不能超过 255 个字符")
	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	
	@Length(min=0, max=255, message="名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	@Length(min=0, max=30, message="提款账户长度不能超过 30 个字符")
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	@Length(min=0, max=3200, message="提款豹纹长度不能超过 3200 个字符")
	public String getCashMessage() {
		return cashMessage;
	}

	public void setCashMessage(String cashMessage) {
		this.cashMessage = cashMessage;
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