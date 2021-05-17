package com.nabobsite.modules.nabob.api.model;

import com.jeesite.common.lang.ObjectUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName nada
 * @Date 2021/5/15 9:46 下午
 * @Version 1.0
 */
@ApiModel(value = "SmsModel",description = "短信信息")
public class SmsModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("电话号码")
    private String phoneNumber;

    @ApiModelProperty("验证码")
    private String smsCode;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
