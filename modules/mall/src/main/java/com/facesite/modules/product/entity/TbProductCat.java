/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.product.entity;

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
 * 商品分类Entity
 * @author nada
 * @version 2021-01-07
 */
@Table(name="tb_product_cat", alias="a", columns={
		@Column(name="id", attrName="id", label="商品分类ID", isPK=true),
		@Column(name="parent_id", attrName="parentId", label="分类上级ID"),
		@Column(name="name", attrName="name", label="分类名称", queryType=QueryType.LIKE),
		@Column(name="en_name", attrName="enName", label="英文名称", queryType=QueryType.LIKE),
		@Column(name="status", attrName="status", label="分类状态 1启用 0禁用", isUpdate=false),
		@Column(name="sort_order", attrName="sortOrder", label="排列序号"),
		@Column(name="is_parent", attrName="isParent", label="是否父类 1", comment="是否父类 1:true 0:false"),
		@Column(name="icon", attrName="icon", label="图标"),
		@Column(name="remark", attrName="remark", label="备注"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class TbProductCat extends DataEntity<TbProductCat> {
	
	private static final long serialVersionUID = 1L;
	private Long parentId;		// 分类上级ID
	private String name;		// 分类名称
	private String enName;		// 英文名称
	private Integer sortOrder;		// 排列序号
	private Integer isParent;		// 是否父类 1:true 0:false
	private String icon;		// 图标
	private String remark;		// 备注
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public TbProductCat() {
		this(null);
	}

	public TbProductCat(String id){
		super(id);
	}
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	@Length(min=0, max=50, message="分类名称长度不能超过 50 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="英文名称长度不能超过 255 个字符")
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}
	
	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}
	
	@Length(min=0, max=255, message="图标长度不能超过 255 个字符")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Length(min=0, max=255, message="备注长度不能超过 255 个字符")
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