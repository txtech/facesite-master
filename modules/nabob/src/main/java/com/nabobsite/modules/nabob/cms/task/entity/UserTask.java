/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.task.entity;

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
 * 用户任务Entity
 * @author face
 * @version 2021-05-20
 */
@Table(name="t1_user_task", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="task_status", attrName="taskStatus", label="任务状态 1", comment="任务状态 1:未开始 2:进行中 3:完成"),
		@Column(name="task_initial_num", attrName="taskInitialNum", label="奖励金额"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="task_order_num", attrName="taskOrderNum", label="完成个数"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="task_start_day", attrName="taskStartDay", label="任务日前"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="task_end_day", attrName="taskEndDay", label="任务结束日期"),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class UserTask extends DataEntity<UserTask> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private Integer taskStatus;		// 任务状态 1:未开始 2:进行中 3:完成
	private BigDecimal taskInitialNum;		// 奖励金额
	private Date created;		// 创建时间
	private Integer taskOrderNum;		// 完成个数
	private Date updated;		// 更新时间
	private Date taskStartDay;		// 任务日前
	private Date taskEndDay;		// 任务结束日期
	private String delFlag;		// 删除标志
	
	public UserTask() {
		this(null);
	}

	public UserTask(String id){
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
	
	public BigDecimal getTaskInitialNum() {
		return taskInitialNum;
	}

	public void setTaskInitialNum(BigDecimal taskInitialNum) {
		this.taskInitialNum = taskInitialNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	@NotNull(message="完成个数不能为空")
	public Integer getTaskOrderNum() {
		return taskOrderNum;
	}

	public void setTaskOrderNum(Integer taskOrderNum) {
		this.taskOrderNum = taskOrderNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaskStartDay() {
		return taskStartDay;
	}

	public void setTaskStartDay(Date taskStartDay) {
		this.taskStartDay = taskStartDay;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaskEndDay() {
		return taskEndDay;
	}

	public void setTaskEndDay(Date taskEndDay) {
		this.taskEndDay = taskEndDay;
	}
	
	@Length(min=0, max=1, message="删除标志长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}