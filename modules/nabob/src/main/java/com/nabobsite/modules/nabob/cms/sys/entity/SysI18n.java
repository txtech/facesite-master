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
 * 用户任务Entity
 * @author face
 * @version 2021-05-15
 */
@Table(name="t1_sys_i18n", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="type", attrName="type", label="类型 1", comment="类型 1:通用 2:错误 3:响应 4:日志"),
		@Column(name="module", attrName="module", label="模块"),
		@Column(name="key", attrName="key", label="标志"),
		@Column(name="zh_value", attrName="zhValue", label="中文"),
		@Column(name="en_value", attrName="enValue", label="英文"),
		@Column(name="in_value", attrName="inValue", label="印度语言"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class SysI18n extends DataEntity<SysI18n> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 类型 1:通用 2:错误 3:响应 4:日志
	private String module;		// 模块
	private String key;		// 标志
	private String zhValue;		// 中文
	private String enValue;		// 英文
	private String inValue;		// 印度语言
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public SysI18n() {
		this(null);
	}

	public SysI18n(String id){
		super(id);
	}
	
	@NotNull(message="类型 1不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@NotBlank(message="模块不能为空")
	@Length(min=0, max=250, message="模块长度不能超过 250 个字符")
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	@NotBlank(message="标志不能为空")
	@Length(min=0, max=250, message="标志长度不能超过 250 个字符")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@NotBlank(message="中文不能为空")
	@Length(min=0, max=2028, message="中文长度不能超过 2028 个字符")
	public String getZhValue() {
		return zhValue;
	}

	public void setZhValue(String zhValue) {
		this.zhValue = zhValue;
	}
	
	@NotBlank(message="英文不能为空")
	@Length(min=0, max=2048, message="英文长度不能超过 2048 个字符")
	public String getEnValue() {
		return enValue;
	}

	public void setEnValue(String enValue) {
		this.enValue = enValue;
	}
	
	@NotBlank(message="印度语言不能为空")
	@Length(min=0, max=2018, message="印度语言长度不能超过 2018 个字符")
	public String getInValue() {
		return inValue;
	}

	public void setInValue(String inValue) {
		this.inValue = inValue;
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