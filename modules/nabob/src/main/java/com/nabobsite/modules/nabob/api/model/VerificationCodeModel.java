package com.nabobsite.modules.nabob.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @ClassName nada
 * @Date 2021/5/15 9:46 下午
 * @Version 1.0
 */
@ApiModel(value = "SmsModel",description = "短信信息")
public class VerificationCodeModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("验证码key")
    private String codeKey;

    @ApiModelProperty("验证码")
    private String code;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
