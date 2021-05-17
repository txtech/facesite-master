package com.nabobsite.modules.nabob.api.model;

import com.jeesite.common.lang.ObjectUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @ClassName nada
 * @Date 2021/5/15 9:46 下午
 * @Version 1.0
 */
@ApiModel(value = "UserInfoModel",description = "用户信息")
public class UserInfoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("操作员上级")
    private String parentSysId;
    @ApiModelProperty("一级ID")
    private String parent1UserId;
    @ApiModelProperty("二级ID")
    private String parent2UserId;
    @ApiModelProperty("三级ID")
    private String parent3UserId;
    @ApiModelProperty("账号")
    private String accountNo;
    @ApiModelProperty("旧密码")
    private String oldPassword;
    @ApiModelProperty("新密码")
    private String password;
    @ApiModelProperty("邀请码")
    private String inviteCode;
    @ApiModelProperty("会话令牌")
    private String token;
    @ApiModelProperty("邀请秘文")
    private String inviteSecret;
    @ApiModelProperty("最喜欢的人")
    private String favorite;
    @ApiModelProperty("语言")
    private String lang;
    @ApiModelProperty("验证吗")
    private String smsCode;
    @ApiModelProperty("注册IP")
    private String registIp;
    @ApiModelProperty("登陆Ip")
    private String loginIp;



    public String getParentSysId() {
        return parentSysId;
    }

    public void setParentSysId(String parentSysId) {
        this.parentSysId = parentSysId;
    }

    public String getParent1UserId() {
        return parent1UserId;
    }

    public void setParent1UserId(String parent1UserId) {
        this.parent1UserId = parent1UserId;
    }

    public String getParent2UserId() {
        return parent2UserId;
    }

    public void setParent2UserId(String parent2UserId) {
        this.parent2UserId = parent2UserId;
    }

    public String getParent3UserId() {
        return parent3UserId;
    }

    public void setParent3UserId(String parent3UserId) {
        this.parent3UserId = parent3UserId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getInviteSecret() {
        return inviteSecret;
    }

    public void setInviteSecret(String inviteSecret) {
        this.inviteSecret = inviteSecret;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getRegistIp() {
        return registIp;
    }

    public void setRegistIp(String registIp) {
        this.registIp = registIp;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
