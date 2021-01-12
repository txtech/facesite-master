/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.entity;

import com.alibaba.fastjson.JSONObject;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.UUID;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.xml.crypto.Data;

/**
 * 用户信息Entity
 * @author nada
 * @version 2021-01-11
 */
@Table(name="h_game_user_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="parent_id", attrName="parentId", label="父ID"),
		@Column(name="status", attrName="status", label="用户状态", isUpdate=false),
		@Column(name="type", attrName="type", label="用户类型"),
		@Column(name="mobile", attrName="mobile", label="手机号码"),
		@Column(name="nickname", attrName="nickname", label="用户昵称"),
		@Column(name="username", attrName="username", label="用户名称"),
		@Column(name="hbeans", attrName="hbeans", label="用户呵豆"),
		@Column(name="lbeans", attrName="lbeans", label="用户乐豆"),
		@Column(name="token", attrName="token", label="登陆token"),
		@Column(name="photo", attrName="photo", label="用户头像"),
		@Column(name="sex", attrName="sex", label="用户性别"),
		@Column(name="age", attrName="age", label="用户年龄"),
		@Column(name="created", attrName="created", label="创建时间"),
		@Column(name="updated", attrName="updated", label="更新时间"),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标志"),
	}, orderBy="a.id DESC"
)
public class HgameUserInfo extends DataEntity<HgameUserInfo> {

	private static final long serialVersionUID = 1L;
	private String parentId;		// 父ID
	private Integer type;		// 用户类型 1：游客 2:会员
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

	public static int TYPE_VISITOR  = 1;
	public static int TYPE_MEMBER = 2;

	public static HgameUserInfo saveUserInfo(String token,JSONObject resData,int type){
		HgameUserInfo userInfo = new HgameUserInfo();
		userInfo.setParentId(resData.getString("userInfo_ID"));
		userInfo.setType(type);
		userInfo.setToken(token);
		userInfo.setStatus("1");
		userInfo.setMobile(resData.getString("userInfo_Mobile"));
		userInfo.setNickname(resData.getString("userInfo_NickName"));
		userInfo.setUsername(resData.getString("userInfo_NickName"));
		userInfo.setHbeans(resData.getLong("userInfo_HBeans"));
		userInfo.setLbeans(resData.getLong("userInfo_LBeans"));
		userInfo.setPhoto(resData.getString("userInfo_HeadImg"));
		userInfo.setSex(resData.getString("userInfo_Sex"));
		userInfo.setAge(1);
		return userInfo;
	}

	public static HgameUserInfo initVisitorUserInfo(String token){
		HgameUserInfo userInfo = new HgameUserInfo();
		userInfo.setParentId(token);
		userInfo.setType(TYPE_VISITOR);
		userInfo.setToken(token);
		userInfo.setStatus("1");
		userInfo.setMobile("");
		userInfo.setNickname("");
		userInfo.setUsername("");
		userInfo.setHbeans(0L);
		userInfo.setLbeans(0L);
		userInfo.setPhoto("");
		userInfo.setSex("1");
		userInfo.setAge(1);
		return userInfo;
	}

	public static JSONObject getGameUserInfo(HgameUserInfo userInfo,HgameUserRef hgameUserRef){
		JSONObject result = new JSONObject();
		result.put("gid",hgameUserRef.getGameId());
		result.put("uid",userInfo.getParentId());
		result.put("type",userInfo.getType());
		result.put("hbeans",userInfo.getHbeans());
		result.put("levelsCompleted", hgameUserRef.getLevelsCompleted());
		result.put("totalScore",hgameUserRef.getTotalScore());
		result.put("gole",hgameUserRef.getGole());
		result.put("boostersCount",hgameUserRef.getBoostersCount());
		result.put("starsPerLevel",hgameUserRef.getStarsPerLevel());
		return result;
	}

	public static JSONObject getVisitorGameUserInfo(){
		JSONObject result = new JSONObject();
		result.put("gid",1);
		result.put("uid", UUID.randomUUID());
		result.put("type",TYPE_MEMBER);
		result.put("hbeans",0);
		result.put("levelsCompleted",0L);
		result.put("totalScore",0L);
		result.put("gole",0L);
		result.put("boostersCount","[0,0,0,0,0,0]");// 骰子:200,定时器:200,闪电:150,丘比特:250,太阳:200
		result.put("starsPerLevel","[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]");
		return result;
	}

	public HgameUserInfo() {
		this(null);
	}

	public static HgameUserInfo getUserInfoParent(String parentId) {
		HgameUserInfo userInfo = new HgameUserInfo();
		userInfo.setParentId(parentId);
		return userInfo;
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

}
