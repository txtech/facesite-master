/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.entity;

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
 * 用户产品机器人信息Entity
 * @author face
 * @version 2021-05-17
 */
@Table(name="t1_user_product_bot", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="bot_id", attrName="botId", label="产品ID"),
		@Column(name="today_orders", attrName="todayOrders", label="今日订单数"),
		@Column(name="today_income_money", attrName="todayIncomeMoney", label="今天收入"),
		@Column(name="today_team_income", attrName="todayTeamIncome", label="今天团队收入"),
		@Column(name="yesterday_income_money", attrName="yesterdayIncomeMoney", label="昨日收入"),
		@Column(name="yesterday_team_income_money", attrName="yesterdayTeamIncomeMoney", label="昨日团队收入"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class UserProductBot extends DataEntity<UserProductBot> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String botId;		// 产品ID
	private Integer todayOrders;		// 今日订单数
	private BigDecimal todayIncomeMoney;		// 今天收入
	private BigDecimal todayTeamIncome;		// 今天团队收入
	private BigDecimal yesterdayIncomeMoney;		// 昨日收入
	private BigDecimal yesterdayTeamIncomeMoney;		// 昨日团队收入
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public UserProductBot() {
		this(null);
	}

	public UserProductBot(String id){
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
	
	@NotNull(message="今日订单数不能为空")
	public Integer getTodayOrders() {
		return todayOrders;
	}

	public void setTodayOrders(Integer todayOrders) {
		this.todayOrders = todayOrders;
	}
	
	@NotNull(message="今天收入不能为空")
	public BigDecimal getTodayIncomeMoney() {
		return todayIncomeMoney;
	}

	public void setTodayIncomeMoney(BigDecimal todayIncomeMoney) {
		this.todayIncomeMoney = todayIncomeMoney;
	}
	
	@NotNull(message="今天团队收入不能为空")
	public BigDecimal getTodayTeamIncome() {
		return todayTeamIncome;
	}

	public void setTodayTeamIncome(BigDecimal todayTeamIncome) {
		this.todayTeamIncome = todayTeamIncome;
	}
	
	@NotNull(message="昨日收入不能为空")
	public BigDecimal getYesterdayIncomeMoney() {
		return yesterdayIncomeMoney;
	}

	public void setYesterdayIncomeMoney(BigDecimal yesterdayIncomeMoney) {
		this.yesterdayIncomeMoney = yesterdayIncomeMoney;
	}
	
	@NotNull(message="昨日团队收入不能为空")
	public BigDecimal getYesterdayTeamIncomeMoney() {
		return yesterdayTeamIncomeMoney;
	}

	public void setYesterdayTeamIncomeMoney(BigDecimal yesterdayTeamIncomeMoney) {
		this.yesterdayTeamIncomeMoney = yesterdayTeamIncomeMoney;
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