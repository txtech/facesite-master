/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
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
 * 会员资格Entity
 * @author face
 * @version 2021-05-23
 */
@Table(name="t1_user_info_membership", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="seq", attrName="seq", label="排序"),
		@Column(name="level", attrName="level", label="等级"),
		@Column(name="grade_name", attrName="gradeName", label="等级名称", queryType=QueryType.LIKE),
		@Column(name="grade_money", attrName="gradeMoney", label="等级要求"),
		@Column(name="order_num", attrName="orderNum", label="刷单数量"),
		@Column(name="commission_rate", attrName="commissionRate", label="订单佣金比例"),
		@Column(name="commission_rate1", attrName="commissionRate1", label="一级佣金比例"),
		@Column(name="commission_rate2", attrName="commissionRate2", label="二级佣金比例"),
		@Column(name="commission_rate3", attrName="commissionRate3", label="三级佣金比例"),
		@Column(name="withdraw_max", attrName="withdrawMax", label="最大出款金额"),
		@Column(name="withdraw_min", attrName="withdrawMin", label="最小出款金额"),
		@Column(name="withdraw_num", attrName="withdrawNum", label="最大出款次数"),
		@Column(name="logo", attrName="logo", label="图标地址"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class UserInfoMembership extends DataEntity<UserInfoMembership> {
	
	private static final long serialVersionUID = 1L;
	private Integer seq;		// 排序
	private Integer level;		// 等级
	private String gradeName;		// 等级名称
	private BigDecimal gradeMoney;		// 等级要求
	private Integer orderNum;		// 刷单数量
	private String commissionRate;		// 订单佣金比例
	private String commissionRate1;		// 一级佣金比例
	private String commissionRate2;		// 二级佣金比例
	private String commissionRate3;		// 三级佣金比例
	private BigDecimal withdrawMax;		// 最大出款金额
	private BigDecimal withdrawMin;		// 最小出款金额
	private Integer withdrawNum;		// 最大出款次数
	private String logo;		// 图标地址
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public UserInfoMembership() {
		this(null);
	}

	public UserInfoMembership(String id){
		super(id);
	}
	
	@NotNull(message="排序不能为空")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@NotNull(message="等级不能为空")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@NotBlank(message="等级名称不能为空")
	@Length(min=0, max=50, message="等级名称长度不能超过 50 个字符")
	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	@NotNull(message="等级要求不能为空")
	public BigDecimal getGradeMoney() {
		return gradeMoney;
	}

	public void setGradeMoney(BigDecimal gradeMoney) {
		this.gradeMoney = gradeMoney;
	}
	
	@NotNull(message="刷单数量不能为空")
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	@NotBlank(message="订单佣金比例不能为空")
	@Length(min=0, max=50, message="订单佣金比例长度不能超过 50 个字符")
	public String getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}
	
	@NotBlank(message="一级佣金比例不能为空")
	@Length(min=0, max=50, message="一级佣金比例长度不能超过 50 个字符")
	public String getCommissionRate1() {
		return commissionRate1;
	}

	public void setCommissionRate1(String commissionRate1) {
		this.commissionRate1 = commissionRate1;
	}
	
	@NotBlank(message="二级佣金比例不能为空")
	@Length(min=0, max=50, message="二级佣金比例长度不能超过 50 个字符")
	public String getCommissionRate2() {
		return commissionRate2;
	}

	public void setCommissionRate2(String commissionRate2) {
		this.commissionRate2 = commissionRate2;
	}
	
	@NotBlank(message="三级佣金比例不能为空")
	@Length(min=0, max=50, message="三级佣金比例长度不能超过 50 个字符")
	public String getCommissionRate3() {
		return commissionRate3;
	}

	public void setCommissionRate3(String commissionRate3) {
		this.commissionRate3 = commissionRate3;
	}
	
	@NotNull(message="最大出款金额不能为空")
	public BigDecimal getWithdrawMax() {
		return withdrawMax;
	}

	public void setWithdrawMax(BigDecimal withdrawMax) {
		this.withdrawMax = withdrawMax;
	}
	
	@NotNull(message="最小出款金额不能为空")
	public BigDecimal getWithdrawMin() {
		return withdrawMin;
	}

	public void setWithdrawMin(BigDecimal withdrawMin) {
		this.withdrawMin = withdrawMin;
	}
	
	@NotNull(message="最大出款次数不能为空")
	public Integer getWithdrawNum() {
		return withdrawNum;
	}

	public void setWithdrawNum(Integer withdrawNum) {
		this.withdrawNum = withdrawNum;
	}
	
	@NotBlank(message="图标地址不能为空")
	@Length(min=0, max=1024, message="图标地址长度不能超过 1024 个字符")
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
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