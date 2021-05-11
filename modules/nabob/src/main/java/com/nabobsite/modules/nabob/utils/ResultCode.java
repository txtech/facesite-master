package com.nabobsite.modules.nabob.utils;

/**
 * @desc 枚举了一些常用API操作码
 * @author nada
 * @create 2020/12/21 11:47 上午
*/
public enum ResultCode {
    //操作成功
    SUCCESS(200, "操作成功"),
    //操作失败
    FAILED(500, "操作失败");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
