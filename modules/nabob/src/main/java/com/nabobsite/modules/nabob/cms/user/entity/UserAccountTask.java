/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.entity;

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
 * 用户奖励账户Entity
 * @author face
 * @version 2021-05-23
 */
@Table(name="t1_user_account_task", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="task_status", attrName="taskStatus", label="任务状态 1", comment="任务状态 1:未开始 2:进行中 3:完成"),
		@Column(name="task_initial_num", attrName="taskInitialNum", label="奖励金额"),
		@Column(name="task_order_num", attrName="taskOrderNum", label="完成个数"),
		@Column(name="task_start_day", attrName="taskStartDay", label="任务开始日期"),
		@Column(name="task_end_day", attrName="taskEndDay", label="任务结束日期"),
		@Column(name="task_finish_data", attrName="taskFinishData", label="每个任务完成情况"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class UserAccountTask extends DataEntity<UserAccountTask> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private Integer taskStatus;		// 任务状态 1:未开始 2:进行中 3:完成
	private BigDecimal taskInitialNum;		// 奖励金额
	private Integer taskOrderNum;		// 完成个数
	private Date taskStartDay;		// 任务开始日期
	private Date taskEndDay;		// 任务结束日期
	private String taskFinishData;		// 每个任务完成情况
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public UserAccountTask() {
		this(null);
	}

	public UserAccountTask(String id){
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
	
	@NotNull(message="任务状态 1不能为空")
	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	@NotNull(message="奖励金额不能为空")
	public BigDecimal getTaskInitialNum() {
		return taskInitialNum;
	}

	public void setTaskInitialNum(BigDecimal taskInitialNum) {
		this.taskInitialNum = taskInitialNum;
	}
	
	@NotNull(message="完成个数不能为空")
	public Integer getTaskOrderNum() {
		return taskOrderNum;
	}

	public void setTaskOrderNum(Integer taskOrderNum) {
		this.taskOrderNum = taskOrderNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="任务开始日期不能为空")
	public Date getTaskStartDay() {
		return taskStartDay;
	}

	public void setTaskStartDay(Date taskStartDay) {
		this.taskStartDay = taskStartDay;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="任务结束日期不能为空")
	public Date getTaskEndDay() {
		return taskEndDay;
	}

	public void setTaskEndDay(Date taskEndDay) {
		this.taskEndDay = taskEndDay;
	}
	
	@NotBlank(message="每个任务完成情况不能为空")
	@Length(min=0, max=3200, message="每个任务完成情况长度不能超过 3200 个字符")
	public String getTaskFinishData() {
		return taskFinishData;
	}

	public void setTaskFinishData(String taskFinishData) {
		this.taskFinishData = taskFinishData;
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