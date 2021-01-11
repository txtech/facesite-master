/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.product.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 商品描述Entity
 * @author nada
 * @version 2021-01-07
 */
@Table(name="tb_product_desc", alias="a", columns={
		@Column(name="item_id", attrName="itemId", label="商品ID"),
		@Column(name="item_desc", attrName="itemDesc", label="商品描述"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
		@Column(name="comment_count", attrName="commentCount", label="产品品论数"),
		@Column(name="sale_count", attrName="saleCount", label="产品销量"),
		@Column(name="view_count", attrName="viewCount", label="浏览量"),
		@Column(name="id", attrName="id", label="id", isPK=true),
	}, orderBy="a.id DESC"
)
public class TbProductDesc extends DataEntity<TbProductDesc> {
	
	private static final long serialVersionUID = 1L;
	private Long itemId;		// 商品ID
	private String itemDesc;		// 商品描述
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	private Long commentCount;		// 产品品论数
	private Long saleCount;		// 产品销量
	private Long viewCount;		// 浏览量
	
	public TbProductDesc() {
		this(null);
	}

	public TbProductDesc(String id){
		super(id);
	}
	
	@NotNull(message="商品ID不能为空")
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
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
	
	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}
	
	public Long getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(Long saleCount) {
		this.saleCount = saleCount;
	}
	
	public Long getViewCount() {
		return viewCount;
	}

	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}
	
}