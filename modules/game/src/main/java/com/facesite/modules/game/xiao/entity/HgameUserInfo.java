/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户信息Entity
 * @author nada
 * @version 2021-01-12
 */
@Table(name="h_game_user_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="parent_id", attrName="parentId", label="父ID", isQuery=false),
		@Column(name="status", attrName="status", label="用户状态"),
		@Column(name="type", attrName="type", label="用户类型"),
		@Column(name="mobile", attrName="mobile", label="手机号码", queryType=QueryType.LIKE),
		@Column(name="nickname", attrName="nickname", label="用户昵称", queryType=QueryType.LIKE),
		@Column(name="username", attrName="username", label="用户名称", queryType=QueryType.LIKE),
		@Column(name="hbeans", attrName="hbeans", label="用户呵豆"),
		@Column(name="lbeans", attrName="lbeans", label="用户乐豆"),
		@Column(name="token", attrName="token", label="登陆token", isQuery=false),
		@Column(name="photo", attrName="photo", label="用户头像", isQuery=false),
		@Column(name="sex", attrName="sex", label="用户性别"),
		@Column(name="age", attrName="age", label="用户年龄"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间", isQuery=false),
		@Column(name="remarks", attrName="remarks", label="备注信息", isQuery=false),
		@Column(name="create_by", attrName="createBy", label="创建人", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志", isQuery=false),
	}, orderBy="a.id DESC"
)
public class HgameUserInfo extends DataEntity<HgameUserInfo> {
	
	private static final long serialVersionUID = 1L;
	private String parentId;		// 父ID
	private Integer type;		// 用户类型
	private String mobile;		// 手机号码
	private String nickname;		// 用户昵称
	private String username;		// 用户名称
	private Long hbeans;		// 用户呵豆
	private Long lbeans;		// 用户乐豆
	private String token;		// 登陆token
	private String photo;		// 用户头像
	private String sex;		// 用户性别
	private Integer age;		// 用户年龄
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	
	public HgameUserInfo() {
		this(null);
	}

	public HgameUserInfo(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="父ID长度不能超过 255 个字符")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@NotNull(message="用户类型不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Length(min=0, max=50, message="手机号码长度不能超过 50 个字符")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=50, message="用户昵称长度不能超过 50 个字符")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Length(min=0, max=50, message="用户名称长度不能超过 50 个字符")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Long getHbeans() {
		return hbeans;
	}

	public void setHbeans(Long hbeans) {
		this.hbeans = hbeans;
	}
	
	public Long getLbeans() {
		return lbeans;
	}

	public void setLbeans(Long lbeans) {
		this.lbeans = lbeans;
	}
	
	@Length(min=0, max=255, message="登陆token长度不能超过 255 个字符")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Length(min=0, max=255, message="用户头像长度不能超过 255 个字符")
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@Length(min=0, max=2, message="用户性别长度不能超过 2 个字符")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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
	
	public Date getCreated_gte() {
		return sqlMap.getWhere().getValue("created", QueryType.GTE);
	}

	public void setCreated_gte(Date created) {
		sqlMap.getWhere().and("created", QueryType.GTE, created);
	}
	
	public Date getCreated_lte() {
		return sqlMap.getWhere().getValue("created", QueryType.LTE);
	}

	public void setCreated_lte(Date created) {
		sqlMap.getWhere().and("created", QueryType.LTE, created);
	}
	
}