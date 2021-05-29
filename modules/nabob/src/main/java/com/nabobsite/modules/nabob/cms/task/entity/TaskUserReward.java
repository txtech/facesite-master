/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.task.entity;

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
 * 用户任务奖励Entity
 * @author face
 * @version 2021-05-23
 */
@Table(name="t1_task_user_reward", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="task_id", attrName="taskId", label="任务ID"),
		@Column(name="type", attrName="type", label="奖励类型 1", comment="奖励类型 1:分享好友 2:观看视频 3:邀请好友 4:定期投资"),
		@Column(name="title", attrName="title", label="标题", queryType=QueryType.LIKE),
		@Column(name="finish_num", attrName="finishNum", label="完成个数"),
		@Column(name="task_number", attrName="taskNumber", label="任务个数"),
		@Column(name="reward_money", attrName="rewardMoney", label="奖励金额"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	},
		joinTable={
				@JoinTable(type=Type.LEFT_JOIN, entity= UserInfo.class, alias="b",
						on="a.user_id = b.id", attrName="userInfo",
						columns={@Column(includeEntity=UserInfo.class)}),
				@JoinTable(type=Type.LEFT_JOIN, entity= TaskInfo.class, alias="c",
						on="a.task_id = c.id", attrName="taskInfo",
						columns={@Column(includeEntity=TaskInfo.class)}),
		},
		orderBy="a.id DESC"
)
public class TaskUserReward extends DataEntity<TaskUserReward> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String taskId;		// 任务ID
	private Integer type;		// 奖励类型 1:分享好友 2:观看视频 3:邀请好友 4:定期投资
	private String title;		// 标题
	private Integer finishNum;		// 完成个数
	private Integer taskNumber;		// 任务个数
	private BigDecimal rewardMoney;		// 奖励金额
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	private UserInfo userInfo;
	private TaskInfo taskInfo;

	public TaskUserReward() {
		this(null);
	}

	public TaskUserReward(String id){
		super(id);
	}
	
	@NotBlank(message="用户id不能为空")
	@Length(min=0, max=30, message="用户id长度不能超过 30 个字符")
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
	
	@NotNull(message="奖励类型 1不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@NotBlank(message="标题不能为空")
	@Length(min=0, max=520, message="标题长度不能超过 520 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotNull(message="完成个数不能为空")
	public Integer getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(Integer finishNum) {
		this.finishNum = finishNum;
	}
	
	@NotNull(message="任务个数不能为空")
	public Integer getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(Integer taskNumber) {
		this.taskNumber = taskNumber;
	}
	
	@NotNull(message="奖励金额不能为空")
	public BigDecimal getRewardMoney() {
		return rewardMoney;
	}

	public void setRewardMoney(BigDecimal rewardMoney) {
		this.rewardMoney = rewardMoney;
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

	public TaskInfo getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(TaskInfo taskInfo) {
		this.taskInfo = taskInfo;
	}
}