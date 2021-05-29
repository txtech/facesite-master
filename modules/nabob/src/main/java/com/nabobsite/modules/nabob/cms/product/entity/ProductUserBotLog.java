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
 * 用户产品机器人记录Entity
 * @author face
 * @version 2021-05-23
 */
@Table(name="t1_product_user_bot_log", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="bot_id", attrName="botId", label="产品ID"),
		@Column(name="order_no", attrName="orderNo", label="订单号"),
		@Column(name="order_amount", attrName="orderAmount", label="刷单金额"),
		@Column(name="income_money", attrName="incomeMoney", label="收入金额"),
		@Column(name="income_rate", attrName="incomeRate", label="收益比例"),
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
				@JoinTable(type=Type.LEFT_JOIN, entity= ProductBot.class, alias="c",
						on="a.bot_id = c.id", attrName="productBot",
						columns={@Column(includeEntity=ProductBot.class)}),
		},
		orderBy="a.id DESC"
)
public class ProductUserBotLog extends DataEntity<ProductUserBotLog> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String botId;		// 产品ID
	private String orderNo;		// 订单号
	private BigDecimal orderAmount;		// 刷单金额
	private BigDecimal incomeMoney;		// 收入金额
	private BigDecimal incomeRate;		// 收益比例
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	private UserInfo userInfo;
	private ProductBot productBot;

	public ProductUserBotLog() {
		this(null);
	}

	public ProductUserBotLog(String id){
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
	
	@NotBlank(message="产品ID不能为空")
	@Length(min=0, max=50, message="产品ID长度不能超过 50 个字符")
	public String getBotId() {
		return botId;
	}

	public void setBotId(String botId) {
		this.botId = botId;
	}
	
	@NotBlank(message="订单号不能为空")
	@Length(min=0, max=50, message="订单号长度不能超过 50 个字符")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@NotNull(message="刷单金额不能为空")
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	@NotNull(message="收入金额不能为空")
	public BigDecimal getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}
	
	@NotNull(message="收益比例不能为空")
	public BigDecimal getIncomeRate() {
		return incomeRate;
	}

	public void setIncomeRate(BigDecimal incomeRate) {
		this.incomeRate = incomeRate;
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

	public ProductBot getProductBot() {
		return productBot;
	}

	public void setProductBot(ProductBot productBot) {
		this.productBot = productBot;
	}
}