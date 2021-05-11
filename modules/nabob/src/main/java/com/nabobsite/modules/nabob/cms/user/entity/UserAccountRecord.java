/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 账户账务明显Entity
 * @author face
 * @version 2021-05-10
 */
@Table(name="t1_user_account_record", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="type", attrName="type", label="类型"),
		@Column(name="account_id", attrName="accountId", label="账户ID"),
		@Column(name="total_money", attrName="totalMoney", label="总金额"),
		@Column(name="change_money", attrName="changeMoney", label="变化金额"),
		@Column(name="remark", attrName="remark", label="备注"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class UserAccountRecord extends DataEntity<UserAccountRecord> {

	private static final long serialVersionUID = 1L;
	private Long type;		// 类型
	private Long accountId;		// 账户ID
	private Double totalMoney;		// 总金额
	private Double changeMoney;		// 变化金额
	private String remark;		// 备注
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志

	public UserAccountRecord() {
		this(null);
	}

	public UserAccountRecord(String id){
		super(id);
	}

	@NotNull(message="类型不能为空")
	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	@NotNull(message="账户ID不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	@NotNull(message="总金额不能为空")
	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	@NotNull(message="变化金额不能为空")
	public Double getChangeMoney() {
		return changeMoney;
	}

	public void setChangeMoney(Double changeMoney) {
		this.changeMoney = changeMoney;
	}

	@NotBlank(message="备注不能为空")
	@Length(min=0, max=520, message="备注长度不能超过 520 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
