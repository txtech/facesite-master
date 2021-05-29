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
 * 用户产品机器人智能任务Entity
 * @author face
 * @version 2021-05-27
 */
@Table(name="t1_product_user_bot_aistart", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="bot_id", attrName="botId", label="产品ID"),
		@Column(name="level", attrName="level", label="当前等级"),
		@Column(name="ai_day", attrName="aiDay", label="任务天数"),
		@Column(name="daily_num", attrName="dailyNum", label="每日执行次数"),
		@Column(name="ai_rate", attrName="aiRate", label="ai费率"),
		@Column(name="ai_money", attrName="aiMoney", label="ai收入"),
		@Column(name="ai_rate_other", attrName="aiRateOther", label="ai增值费率"),
		@Column(name="start_date", attrName="startDate", label="开始时间"),
		@Column(name="end_date", attrName="endDate", label="结束时间"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
		@Column(name="ai_status", attrName="aiStatus", label="ai状态 1", comment="ai状态 1:已经启动 2:已结束"),
	}, orderBy="a.id DESC"
)
public class ProductUserBotAistart extends DataEntity<ProductUserBotAistart> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String botId;		// 产品ID
	private Integer level;		// 当前等级
	private Integer aiDay;		// 任务天数
	private Integer dailyNum;		// 每日执行次数
	private BigDecimal aiRate;		// ai费率
	private BigDecimal aiMoney;		// ai收入
	private BigDecimal aiRateOther;		// ai增值费率
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	private Integer aiStatus;		// ai状态 1:已经启动 2:已结束
	
	public ProductUserBotAistart() {
		this(null);
	}

	public ProductUserBotAistart(String id){
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
	
	@NotNull(message="当前等级不能为空")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@NotNull(message="任务天数不能为空")
	public Integer getAiDay() {
		return aiDay;
	}

	public void setAiDay(Integer aiDay) {
		this.aiDay = aiDay;
	}
	
	@NotNull(message="每日执行次数不能为空")
	public Integer getDailyNum() {
		return dailyNum;
	}

	public void setDailyNum(Integer dailyNum) {
		this.dailyNum = dailyNum;
	}
	
	@NotNull(message="ai费率不能为空")
	public BigDecimal getAiRate() {
		return aiRate;
	}

	public void setAiRate(BigDecimal aiRate) {
		this.aiRate = aiRate;
	}
	
	@NotNull(message="ai收入不能为空")
	public BigDecimal getAiMoney() {
		return aiMoney;
	}

	public void setAiMoney(BigDecimal aiMoney) {
		this.aiMoney = aiMoney;
	}
	
	@NotNull(message="ai增值费率不能为空")
	public BigDecimal getAiRateOther() {
		return aiRateOther;
	}

	public void setAiRateOther(BigDecimal aiRateOther) {
		this.aiRateOther = aiRateOther;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开始时间不能为空")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结束时间不能为空")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	
	@NotNull(message="ai状态 1不能为空")
	public Integer getAiStatus() {
		return aiStatus;
	}

	public void setAiStatus(Integer aiStatus) {
		this.aiStatus = aiStatus;
	}
	
}