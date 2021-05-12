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
 * @version 2021-05-11
 */
@Table(name="t1_user_info", alias="a", columns={
		@Column(name="id", attrName="id", label="主键ID", isPK=true),
		@Column(name="parent_user_id", attrName="parentUserId", label="上级ID", queryType=QueryType.EQ),
		@Column(name="parent_sys_id", attrName="parentSysId", label="操作员上级", queryType=QueryType.EQ),
		@Column(name="user_status", attrName="userStatus", label="状态 1", comment="状态 1:正常 2:禁用", queryType=QueryType.EQ),
		@Column(name="level", attrName="level", label="级别", queryType=QueryType.EQ),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(name="account_no", attrName="accountNo", label="账号", queryType=QueryType.EQ),
		@Column(name="password", attrName="password", label="密码", queryType=QueryType.EQ),
		@Column(name="phone_number", attrName="phoneNumber", label="电话号码"),
		@Column(name="invite_code", attrName="inviteCode", label="邀请码", queryType=QueryType.EQ),
		@Column(name="favorite", attrName="favorite", label="最喜欢的人", queryType=QueryType.EQ),
		@Column(name="ipaddress", attrName="ipaddress", label="注册IP"),
		@Column(name="team_num", attrName="teamNum", label="团队总人数"),
		@Column(name="team_num_1", attrName="teamNum1", label="一级团队人数"),
		@Column(name="team_num_2", attrName="teamNum2", label="二级团队人数"),
		@Column(name="team_num_3", attrName="teamNum3", label="三级团队人数"),
		@Column(name="task_end_time", attrName="taskEndTime", label="任务结束时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="created", attrName="created", label="创建时间",isUpdate=false),
		@Column(name="updated", attrName="updated", label="更新时间", isQuery=false),
		@Column(name="create_by", attrName="createBy", label="创建人",isUpdate=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志", isQuery=false),
	}, orderBy="a.id DESC"
)
public class UserInfo extends DataEntity<UserInfo> {

	private static final long serialVersionUID = 1L;
	private String parentUserId;		// 上级ID
	private String parentSysId;		// 操作员上级
	private Integer userStatus;		// 状态 1:正常 2:禁用
	private Integer level;		// 级别
	private String name;		// 名称
	private String accountNo;		// 账号
	private String password;		// 密码
	private String phoneNumber;		// 电话号码
	private String inviteCode;		// 邀请码
	private String favorite;		// 最喜欢的人
	private String ipaddress;		// 注册IP
	private Integer teamNum;		// 团队总人数
	private Integer teamNum1;		// 一级团队人数
	private Integer teamNum2;		// 二级团队人数
	private Integer teamNum3;		// 三级团队人数
	private Date taskEndTime;		// 任务结束时间
	private Date created;		// 创建时间
	private Date updated;		// 更新时间
	private String delFlag;		// 删除标志
	private String token;

	public UserInfo() {
		this(null);
	}

	public UserInfo(String id){
		super(id);
	}

	@NotBlank(message="上级ID不能为空")
	@Length(min=0, max=30, message="上级ID长度不能超过 30 个字符")
	public String getParentUserId() {
		return parentUserId;
	}

	public void setParentUserId(String parentUserId) {
		this.parentUserId = parentUserId;
	}

	@NotBlank(message="操作员上级不能为空")
	@Length(min=0, max=30, message="操作员上级长度不能超过 30 个字符")
	public String getParentSysId() {
		return parentSysId;
	}

	public void setParentSysId(String parentSysId) {
		this.parentSysId = parentSysId;
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

	@Length(min=0, max=520, message="最喜欢的人长度不能超过 520 个字符")
	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}

	@Length(min=0, max=520, message="注册IP长度不能超过 520 个字符")
	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	@NotNull(message="团队总人数不能为空")
	public Integer getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(Integer teamNum) {
		this.teamNum = teamNum;
	}

	@NotNull(message="一级团队人数不能为空")
	public Integer getTeamNum1() {
		return teamNum1;
	}

	public void setTeamNum1(Integer teamNum1) {
		this.teamNum1 = teamNum1;
	}

	@NotNull(message="二级团队人数不能为空")
	public Integer getTeamNum2() {
		return teamNum2;
	}

	public void setTeamNum2(Integer teamNum2) {
		this.teamNum2 = teamNum2;
	}

	@NotNull(message="三级团队人数不能为空")
	public Integer getTeamNum3() {
		return teamNum3;
	}

	public void setTeamNum3(Integer teamNum3) {
		this.teamNum3 = teamNum3;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
