package com.nabobsite.modules.nabob.api.common.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jeesite.common.entity.DataEntity;

import java.util.List;

/**
 * @author nada
 * @date 2018/03/24
 */
public class ResultUtil<T> {

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> success(T data) {
        int code = ResultCode.SUCCESS.getCode();
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        if(data instanceof DataEntity){
            filter.getExcludes().add("isNewRecord");
            filter.getExcludes().add("delFlag");
            filter.getExcludes().add("createBy");
            filter.getExcludes().add("updateBy");
            filter.getExcludes().add("updated");
            filter.getExcludes().add("created");
            filter.getExcludes().add("password");
            String filterData = JSON.toJSONString(data,filter);
            data = (T)JSON.parseObject(filterData);
            return new CommonResult<T>(code,data);
        }
        if(data instanceof List){
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(data));
            String filterData = JSON.toJSONString(array,filter);
            data = (T)JSON.parseArray(filterData);
            return new CommonResult<T>(code,data);
        }
        return new CommonResult<T>(code,data);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed(String i18nCode) {
        int code = ResultCode.FAILED.getCode();
        return new CommonResult<T>(code, i18nCode);
    }

    /**
     * 授权失败返回结果
     */
    public static <T> CommonResult<T> failedAuthorization(String i18nCode,String defaultValue) {
        int code = ResultCode.ERROR_AUTHOR.getCode();
        return new CommonResult<T>(code, i18nCode,defaultValue);
    }
}
