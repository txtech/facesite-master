package com.nabobsite.modules.nabob.api.common.response;


import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 前后端交互数据标准
 * @author nada
 * @Date 2018/03/24
 */
public class CommonResult<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * @desc 返回代码
     * @author nada
     * @create 2020/12/21 11:08 上午
     */
    private Integer code;

    /**
     * @desc 返回code
     * @author nada
     * @create 2020/12/21 11:08 上午
     */
    private String i18n;

    /**
     * @desc 语言
     * @author nada
     * @create 2020/12/21 11:08 上午
     */
    private String lang;

    /**
     * @desc 返回消息
     * @author nada
     * @create 2020/12/21 11:08 上午
    */
    private String message;

    /**
     * @desc 结果对象
     * @author nada
     * @create 2020/12/21 11:08 上午
    */
    private T result;

    public CommonResult() {
        this.code = ResultCode.FAILED.getCode();
    }

    public CommonResult(Integer code, T result) {
        this.message = "";
        this.code = code;
        this.result = result;
        this.i18n = String.valueOf(code);
    }

    public CommonResult(Integer code, String i8nCode) {
        this.message = "";
        this.code = code;
        this.result = null;
        this.i18n = i8nCode;
    }

    public CommonResult(Integer code, String i8nCode, String defaultValue) {
        this.message = defaultValue;
        this.code = code;
        this.result = null;
        this.i18n = i8nCode;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
