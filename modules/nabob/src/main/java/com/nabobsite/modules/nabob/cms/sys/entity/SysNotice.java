/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 系统通知Entity
 * @author face
 * @version 2021-05-23
 */
@Table(name="t1_sys_notice", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="title", attrName="title", label="title", queryType=QueryType.LIKE),
		@Column(name="content", attrName="content", label="content"),
		@Column(name="add_time", attrName="addTime", label="add_time"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class SysNotice extends DataEntity<SysNotice> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// title
	private String content;		// content
	private Date addTime;		// add_time
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public SysNotice() {
		this(null);
	}

	public SysNotice(String id){
		super(id);
	}
	
	@Length(min=0, max=50, message="title长度不能超过 50 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=3200, message="content长度不能超过 3200 个字符")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
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