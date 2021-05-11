/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户产品机器人信息Entity
 * @author face
 * @version 2021-05-10
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
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class UserProductBot extends DataEntity<UserProductBot> {

	private static final long serialVersionUID = 1L;
	private Long userId;		// 用户ID
	private Long botId;		// 产品ID
	private Long todayOrders;		// 今日订单数
	private Double todayIncomeMoney;		// 今天收入
	private Double todayTeamIncome;		// 今天团队收入
	private Double yesterdayIncomeMoney;		// 昨日收入
	private Double yesterdayTeamIncomeMoney;		// 昨日团队收入
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志

	public UserProductBot() {
		this(null);
	}

	public UserProductBot(String id){
		super(id);
	}

	@NotNull(message="用户ID不能为空")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@NotNull(message="产品ID不能为空")
	public Long getBotId() {
		return botId;
	}

	public void setBotId(Long botId) {
		this.botId = botId;
	}

	@NotNull(message="今日订单数不能为空")
	public Long getTodayOrders() {
		return todayOrders;
	}

	public void setTodayOrders(Long todayOrders) {
		this.todayOrders = todayOrders;
	}

	@NotNull(message="今天收入不能为空")
	public Double getTodayIncomeMoney() {
		return todayIncomeMoney;
	}

	public void setTodayIncomeMoney(Double todayIncomeMoney) {
		this.todayIncomeMoney = todayIncomeMoney;
	}

	@NotNull(message="今天团队收入不能为空")
	public Double getTodayTeamIncome() {
		return todayTeamIncome;
	}

	public void setTodayTeamIncome(Double todayTeamIncome) {
		this.todayTeamIncome = todayTeamIncome;
	}

	@NotNull(message="昨日收入不能为空")
	public Double getYesterdayIncomeMoney() {
		return yesterdayIncomeMoney;
	}

	public void setYesterdayIncomeMoney(Double yesterdayIncomeMoney) {
		this.yesterdayIncomeMoney = yesterdayIncomeMoney;
	}

	@NotNull(message="昨日团队收入不能为空")
	public Double getYesterdayTeamIncomeMoney() {
		return yesterdayTeamIncomeMoney;
	}

	public void setYesterdayTeamIncomeMoney(Double yesterdayTeamIncomeMoney) {
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
