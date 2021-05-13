/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.task.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户任务Entity
 * @author face
 * @version 2021-05-13
 */
@Table(name="t1_user_task", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="task_id", attrName="taskId", label="任务ID"),
		@Column(name="task_status", attrName="taskStatus", label="任务状态 1", comment="任务状态 1:未开始 2:进行中 3:完成"),
		@Column(name="finish_number", attrName="finishNumber", label="完成个数"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class UserTask extends DataEntity<UserTask> {

	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String taskId;		// 任务ID
	private Integer taskStatus;		// 任务状态 1:未开始 2:进行中 3:完成
	private Integer finishNumber;		// 完成个数
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
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

	@NotBlank(message="任务ID不能为空")
	@Length(min=0, max=30, message="任务ID长度不能超过 30 个字符")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@NotNull(message="任务状态 1不能为空")
	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	@NotNull(message="完成个数不能为空")
	public Integer getFinishNumber() {
		return finishNumber;
	}

	public void setFinishNumber(Integer finishNumber) {
		this.finishNumber = finishNumber;
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
