package com.nabobsite.modules.nabob.api.common.response;

/**
 * @desc 枚举了一些常用API操作码
 * @author nada
 * @create 2020/12/21 11:47 上午
*/
public enum ResultCode {
    SUCCESS(10000, "operation success"),
    ERROR(10001, "operation error"),
    EXCEPTION(10002, "operation exception"),
    FAILED(10003, "operation failed");

    private Integer code;
    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
