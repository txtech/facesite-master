/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户任务进行中Entity
 * @author face
 * @version 2021-05-23
 */
@Table(name="t1_product_warehouse_progress", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="in_content", attrName="inContent", label="任务内容"),
		@Column(name="content", attrName="content", label="任务内容"),
		@Column(name="phone", attrName="phone", label="手机号码"),
		@Column(name="num", attrName="num", label="奖励金额"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class ProductWarehouseProgress extends DataEntity<ProductWarehouseProgress> {
	
	private static final long serialVersionUID = 1L;
	private String inContent;		// 任务内容
	private String content;		// 任务内容
	private String phone;		// 手机号码
	private BigDecimal num;		// 奖励金额
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public ProductWarehouseProgress() {
		this(null);
	}

	public ProductWarehouseProgress(String id){
		super(id);
	}
	
	@NotBlank(message="任务内容不能为空")
	@Length(min=0, max=250, message="任务内容长度不能超过 250 个字符")
	public String getInContent() {
		return inContent;
	}

	public void setInContent(String inContent) {
		this.inContent = inContent;
	}
	
	@NotBlank(message="任务内容不能为空")
	@Length(min=0, max=250, message="任务内容长度不能超过 250 个字符")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@NotBlank(message="手机号码不能为空")
	@Length(min=0, max=30, message="手机号码长度不能超过 30 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@NotNull(message="奖励金额不能为空")
	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
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