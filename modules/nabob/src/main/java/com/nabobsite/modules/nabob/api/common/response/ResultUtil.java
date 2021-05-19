package com.nabobsite.modules.nabob.api.common.response;

import com.alibaba.fastjson.JSONObject;

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
    public static <T> CommonResult<T> successJson(T data) {
        int code = ResultCode.SUCCESS.getCode();
        return new CommonResult<T>(code,data);
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> successToBoolean(T data) {
        int code = ResultCode.SUCCESS.getCode();
        return new CommonResult<T>(code,data);
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> successToJsonArray(T data) {
        int code = ResultCode.SUCCESS.getCode();
        if (data instanceof JSONObject) {
            JSONObject result = filterResult((JSONObject)data);
            return new CommonResult<T>(code,(T)result);
        }
        return new CommonResult<T>(code,data);
    }

    /**
     * @desc 响应结果
     * @author nada
     * @create 2021/5/18 5:41 下午
    */
    public static <T> CommonResult<JSONObject> successToJson(T data) {
        try {
            JSONObject reqData = (JSONObject)JSONObject.toJSON(data);
            JSONObject result = filterResult(reqData);
            int code = ResultCode.SUCCESS.getCode();
            return new CommonResult<JSONObject>(code,result);
        } catch (Exception e) {
            e.printStackTrace();
            return failed(I18nCode.CODE_10004);
        }
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed(String i18nCode) {
        int code = ResultCode.FAILED.getCode();
        return new CommonResult<T>(code, i18nCode);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed(String i18nCode,String defaultValue) {
        int code = ResultCode.FAILED.getCode();
        return new CommonResult<T>(code, i18nCode,defaultValue);
    }

    public static JSONObject filterResult(JSONObject object){
        if(object == null || object.isEmpty()){
            return new JSONObject();
        }
        if(object.containsKey("isNewRecord")){
            object.remove("isNewRecord");
        }
        if(object.containsKey("delFlag")){
            object.remove("delFlag");
        }
        if(object.containsKey("createBy")){
            object.remove("createBy");
        }
        if(object.containsKey("updateBy")){
            object.remove("updateBy");
        }
        if(object.containsKey("updated")){
            object.remove("updated");
        }
        if(object.containsKey("created")){
            object.remove("created");
        }
        if(object.containsKey("password")){
            object.remove("password");
        }
        return object;
    }
}
