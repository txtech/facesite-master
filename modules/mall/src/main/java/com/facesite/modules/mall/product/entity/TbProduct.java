/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.mall.product.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 商品表Entity
 * @author nada
 * @version 2021-01-07
 */
@Table(name="tb_product", alias="a", columns={
		@Column(name="id", attrName="id", label="商品id，同时也是商品编号", isPK=true),
		@Column(name="title", attrName="title", label="商品标题", queryType=QueryType.LIKE),
		@Column(name="sell_point", attrName="sellPoint", label="英文标题"),
		@Column(name="price", attrName="price", label="商品价格"),
		@Column(name="num", attrName="num", label="库存数量"),
		@Column(name="limit_num", attrName="limitNum", label="售卖数量限制"),
		@Column(name="image", attrName="image", label="商品图片"),
		@Column(name="cid", attrName="cid", label="所属分类"),
		@Column(name="status", attrName="status", label="商品状态 1正常 0下架", isUpdate=false),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
		@Column(name="en_title", attrName="enTitle", label="商品名称", comment="商品名称(英文)", queryType=QueryType.LIKE),
		@Column(name="en_sell_point", attrName="enSellPoint", label="商品买点", comment="商品买点(英文)"),
	}, orderBy="a.id DESC"
)
public class TbProduct extends DataEntity<TbProduct> {

	private static final long serialVersionUID = 1L;
	private String title;		// 商品标题
	private String sellPoint;		// 英文标题
	private Double price;		// 商品价格
	private Long num;		// 库存数量
	private Long limitNum;		// 售卖数量限制
	private String image;		// 商品图片
	private Long cid;		// 所属分类
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	private String enTitle;		// 商品名称(英文)
	private String enSellPoint;		// 商品买点(英文)

	public TbProduct() {
		this(null);
	}

	public TbProduct(String id){
		super(id);
	}

	@Length(min=0, max=100, message="商品标题长度不能超过 100 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Length(min=0, max=100, message="英文标题长度不能超过 100 个字符")
	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Long getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Long limitNum) {
		this.limitNum = limitNum;
	}

	@Length(min=0, max=2000, message="商品图片长度不能超过 2000 个字符")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
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

	@Length(min=0, max=255, message="商品名称长度不能超过 255 个字符")
	public String getEnTitle() {
		return enTitle;
	}

	public void setEnTitle(String enTitle) {
		this.enTitle = enTitle;
	}

	@Length(min=0, max=255, message="商品买点长度不能超过 255 个字符")
	public String getEnSellPoint() {
		return enSellPoint;
	}

	public void setEnSellPoint(String enSellPoint) {
		this.enSellPoint = enSellPoint;
	}

}
