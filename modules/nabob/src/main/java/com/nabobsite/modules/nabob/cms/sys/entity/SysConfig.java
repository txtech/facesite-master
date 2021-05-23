/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.sys.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
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
 * 系统配置Entity
 * @author face
 * @version 2021-05-23
 */
@Table(name="t1_sys_config", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="type", attrName="type", label="配置类型 1", comment="配置类型 1:时间控制"),
		@Column(name="status", attrName="status", label="配置状态 1", comment="配置状态 1:启用 2:禁用", isUpdate=false),
		@Column(name="key", attrName="key", label="配置key"),
		@Column(name="value", attrName="value", label="配置value"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class SysConfig extends DataEntity<SysConfig> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 配置类型 1:时间控制
	private String key;		// 配置key
	private String value;		// 配置value
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public SysConfig() {
		this(null);
	}

	public SysConfig(String id){
		super(id);
	}
	
	@NotNull(message="配置类型 1不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@NotBlank(message="配置key不能为空")
	@Length(min=0, max=250, message="配置key长度不能超过 250 个字符")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@NotBlank(message="配置value不能为空")
	@Length(min=0, max=2048, message="配置value长度不能超过 2048 个字符")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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