package com.nabobsite.modules.nabob.api.common.response;

/**
 * @author nada
 * @date 2018/03/24
 */
public class ResultUtil<T> {

    private CommonResult<T> result;

    public ResultUtil(CommonResult<T> result){
            this.result = result;
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> success(T data) {
        int code = ResultCode.SUCCESS.getCode();
        return new CommonResult<T>(code,data);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed(String i18nCode) {
        int code = ResultCode.SUCCESS.getCode();
        return new CommonResult<T>(code, i18nCode);
    }
}
