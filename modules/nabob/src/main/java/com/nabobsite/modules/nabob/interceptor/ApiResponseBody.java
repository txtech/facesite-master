package com.nabobsite.modules.nabob.interceptor;

import com.jeesite.common.lang.StringUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.I18nUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/16 12:24 下午
 * @Version 1.0
 */
@ControllerAdvice
public class ApiResponseBody  implements ResponseBodyAdvice<CommonResult> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        Method method = methodParameter.getMethod();
        Class type = method.getReturnType();
        if(type.getName().equalsIgnoreCase(CommonResult.class.getName())){
            return true;
        }
        return false;
    }

    /**
     * @desc 调用限制拦截器进入
     * @author nada
     * @create 2021/5/16 12:33 下午
    */
    @Override
    public CommonResult beforeBodyWrite(CommonResult commonResult, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(commonResult == null){
            return commonResult;
        }
        String lang = I18nUtils.LANG_EN;
        if (I18nInterceptor.userThreadLocal != null && I18nInterceptor.userThreadLocal.get() !=null){
            Map<String,String> userLocal = I18nInterceptor.userThreadLocal.get();
            lang = I18nUtils.getLangStandard(userLocal.get(ContactUtils.LANG));
        }
        commonResult.setLang(lang);
        String i8nCode = commonResult.getI18n();
        if(StringUtils.isNotEmpty(i8nCode)){
            commonResult.setMessage(I18nUtils.getText(i8nCode,lang));
        }
        return commonResult;
    }
}
