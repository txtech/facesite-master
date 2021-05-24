/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.entity;

import javax.validation.constraints.NotBlank;
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
 * 会员用户Entity
 * @author face
 * @version 2021-05-23
 */
@Table(name="t1_user_info", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="parent_sys_id", attrName="parentSysId", label="操作员上级"),
		@Column(name="parent1_user_id", attrName="parent1UserId", label="一级ID"),
		@Column(name="parent2_user_id", attrName="parent2UserId", label="二级ID"),
		@Column(name="parent3_user_id", attrName="parent3UserId", label="三级ID"),
		@Column(name="user_status", attrName="userStatus", label="状态 1", comment="状态 1:正常 2:禁用"),
		@Column(name="level", attrName="level", label="级别"),
		@Column(name="is_valid", attrName="isValid", label="是否有效"),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(name="lock", attrName="lock", label="解锁状态：1", comment="解锁状态：1:解锁 2:锁定"),
		@Column(name="account_no", attrName="accountNo", label="账号"),
		@Column(name="password", attrName="password", label="密码"),
		@Column(name="phone_number", attrName="phoneNumber", label="电话号码"),
		@Column(name="invite_code", attrName="inviteCode", label="邀请码"),
		@Column(name="app_version", attrName="appVersion", label="使用版本"),
		@Column(name="invite_secret", attrName="inviteSecret", label="邀请秘文"),
		@Column(name="login_ip", attrName="loginIp", label="登陆IP"),
		@Column(name="regist_ip", attrName="registIp", label="注册IP"),
		@Column(name="REMARKS", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="CREATE_BY", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="UPDATE_BY", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="DEL_FLAG", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class UserInfo extends DataEntity<UserInfo> {
	
	private static final long serialVersionUID = 1L;
	private String parentSysId;		// 操作员上级
	private String parent1UserId;		// 一级ID
	private String parent2UserId;		// 二级ID
	private String parent3UserId;		// 三级ID
	private Integer userStatus;		// 状态 1:正常 2:禁用
	private Integer level;		// 级别
	private String name;		// 名称
	private Integer lock;		// 解锁状态：1:解锁 2:锁定
	private String accountNo;		// 账号
	private String password;		// 密码
	private String phoneNumber;		// 电话号码
	private String inviteCode;		// 邀请码
	private String appVersion;		// 使用版本
	private String inviteSecret;		// 邀请秘文
	private String loginIp;		// 登陆IP
	private String registIp;		// 注册IP
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	private String smsCode; //短信验证码
    private String codeKey; //图片验证码key
    private String imgCode; //图片验证码code
    private String oldPassword;
    private Integer isValid;
	
	public UserInfo() {
		this(null);
	}

	public UserInfo(String id){
		super(id);
	}
	
	@NotBlank(message="操作员上级不能为空")
	@Length(min=0, max=30, message="操作员上级长度不能超过 30 个字符")
	public String getParentSysId() {
		return parentSysId;
	}

	public void setParentSysId(String parentSysId) {
		this.parentSysId = parentSysId;
	}
	
	@NotBlank(message="一级ID不能为空")
	@Length(min=0, max=30, message="一级ID长度不能超过 30 个字符")
	public String getParent1UserId() {
		return parent1UserId;
	}

	public void setParent1UserId(String parent1UserId) {
		this.parent1UserId = parent1UserId;
	}
	
	@NotBlank(message="二级ID不能为空")
	@Length(min=0, max=50, message="二级ID长度不能超过 50 个字符")
	public String getParent2UserId() {
		return parent2UserId;
	}

	public void setParent2UserId(String parent2UserId) {
		this.parent2UserId = parent2UserId;
	}
	
	@NotBlank(message="三级ID不能为空")
	@Length(min=0, max=50, message="三级ID长度不能超过 50 个字符")
	public String getParent3UserId() {
		return parent3UserId;
	}

	public void setParent3UserId(String parent3UserId) {
		this.parent3UserId = parent3UserId;
	}
	
	@NotNull(message="状态 1不能为空")
	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	
	@NotNull(message="级别不能为空")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@NotBlank(message="名称不能为空")
	@Length(min=0, max=520, message="名称长度不能超过 520 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="解锁状态：1不能为空")
	public Integer getLock() {
		return lock;
	}

	public void setLock(Integer lock) {
		this.lock = lock;
	}
	
	@NotBlank(message="账号不能为空")
	@Length(min=0, max=250, message="账号长度不能超过 250 个字符")
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	@NotBlank(message="密码不能为空")
	@Length(min=0, max=250, message="密码长度不能超过 250 个字符")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@NotBlank(message="电话号码不能为空")
	@Length(min=0, max=20, message="电话号码长度不能超过 20 个字符")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@NotBlank(message="邀请码不能为空")
	@Length(min=0, max=20, message="邀请码长度不能超过 20 个字符")
	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	
	@Length(min=0, max=128, message="使用版本长度不能超过 128 个字符")
	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
	@Length(min=0, max=250, message="邀请秘文长度不能超过 250 个字符")
	public String getInviteSecret() {
		return inviteSecret;
	}

	public void setInviteSecret(String inviteSecret) {
		this.inviteSecret = inviteSecret;
	}
	
	@Length(min=0, max=50, message="登陆IP长度不能超过 50 个字符")
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	@Length(min=0, max=520, message="注册IP长度不能超过 520 个字符")
	public String getRegistIp() {
		return registIp;
	}

	public void setRegistIp(String registIp) {
		this.registIp = registIp;
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


	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getCodeKey() {
		return codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
}