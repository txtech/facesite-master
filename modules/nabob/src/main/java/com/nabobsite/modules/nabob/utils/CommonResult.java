package com.nabobsite.modules.nabob.utils;


import java.io.Serializable;

/**
 * 前后端交互数据标准
 * @author nada
 * @Date 2018/03/24
 */
public class CommonResult<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * @desc 失败消息
     * @author nada
     * @create 2020/12/21 11:08 上午
    */
    private String message;
    /**
     * @desc 返回代码
     * @author nada
     * @create 2020/12/21 11:08 上午
    */
    private Integer code;
    /**
     * @desc 结果对象
     * @author nada
     * @create 2020/12/21 11:08 上午
    */
    private T result;
    /**
     * @desc 时间戳
     * @author nada
     * @create 2020/12/21 11:11 上午
    */
    private long timestamp = System.currentTimeMillis();

    public CommonResult() {
        this.code = ResultCode.FAILED.getCode();
    }

    public CommonResult(Integer code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
