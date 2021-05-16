package com.nabobsite.modules.nabob.api.common.response;


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
    private String i8nCode;

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
    private String msg;

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
        this.code = code;
        this.result = result;
        this.i8nCode = String.valueOf(code);
    }

    public CommonResult(Integer code, String i8nCode) {
        this.code = code;
        this.i8nCode = i8nCode;
    }

    public CommonResult(Integer code, String i8nCode, T result) {
        this.code = code;
        this.i8nCode = i8nCode;
        this.result = result;
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

    public String getI8nCode() {
        return i8nCode;
    }

    public void setI8nCode(String i8nCode) {
        this.i8nCode = i8nCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}