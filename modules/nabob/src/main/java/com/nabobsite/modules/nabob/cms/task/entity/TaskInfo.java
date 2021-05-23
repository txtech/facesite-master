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
 * 任务管理Entity
 * @author face
 * @version 2021-05-23
 */
@Table(name="t1_task_info", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="name", attrName="name", label="任务名称", queryType=QueryType.LIKE),
		@Column(name="seq", attrName="seq", label="seq"),
		@Column(name="content", attrName="content", label="content"),
		@Column(name="button_name", attrName="buttonName", label="button_name", queryType=QueryType.LIKE),
		@Column(name="in_name", attrName="inName", label="in_name", queryType=QueryType.LIKE),
		@Column(name="in_content", attrName="inContent", label="in_content"),
		@Column(name="in_button_name", attrName="inButtonName", label="in_button_name", queryType=QueryType.LIKE),
		@Column(name="type", attrName="type", label="任务类型 1", comment="任务类型 1:分享好友 2:观看视频 3:邀请好友 4:定期投资"),
		@Column(name="task_number", attrName="taskNumber", label="总任务个数"),
		@Column(name="reward_money", attrName="rewardMoney", label="奖励金额"),
		@Column(name="icon_url", attrName="iconUrl", label="图标地址"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class TaskInfo extends DataEntity<TaskInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 任务名称
	private Integer seq;		// seq
	private String content;		// content
	private String buttonName;		// button_name
	private String inName;		// in_name
	private String inContent;		// in_content
	private String inButtonName;		// in_button_name
	private Integer type;		// 任务类型 1:分享好友 2:观看视频 3:邀请好友 4:定期投资
	private Integer taskNumber;		// 总任务个数
	private BigDecimal rewardMoney;		// 奖励金额
	private String iconUrl;		// 图标地址
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	private int FinishNum;
	
	public TaskInfo() {
		this(null);
	}

	public TaskInfo(String id){
		super(id);
	}
	
	@NotBlank(message="任务名称不能为空")
	@Length(min=0, max=255, message="任务名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="seq不能为空")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@NotBlank(message="content不能为空")
	@Length(min=0, max=3200, message="content长度不能超过 3200 个字符")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@NotBlank(message="button_name不能为空")
	@Length(min=0, max=520, message="button_name长度不能超过 520 个字符")
	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}
	
	@NotBlank(message="in_name不能为空")
	@Length(min=0, max=520, message="in_name长度不能超过 520 个字符")
	public String getInName() {
		return inName;
	}

	public void setInName(String inName) {
		this.inName = inName;
	}
	
	@NotBlank(message="in_content不能为空")
	@Length(min=0, max=3200, message="in_content长度不能超过 3200 个字符")
	public String getInContent() {
		return inContent;
	}

	public void setInContent(String inContent) {
		this.inContent = inContent;
	}
	
	@NotBlank(message="in_button_name不能为空")
	@Length(min=0, max=520, message="in_button_name长度不能超过 520 个字符")
	public String getInButtonName() {
		return inButtonName;
	}

	public void setInButtonName(String inButtonName) {
		this.inButtonName = inButtonName;
	}
	
	@NotNull(message="任务类型 1不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@NotNull(message="总任务个数不能为空")
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
	
	@NotBlank(message="图标地址不能为空")
	@Length(min=0, max=1024, message="图标地址长度不能超过 1024 个字符")
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
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

	public int getFinishNum() {
		return FinishNum;
	}

	public void setFinishNum(int finishNum) {
		FinishNum = finishNum;
	}
}