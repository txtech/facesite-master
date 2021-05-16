package com.nabobsite.modules.nabob.interceptor;

import com.jeesite.common.lang.StringUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.I18nUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.management.monitor.StringMonitor;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.util.Locale;
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

    @Autowired
    private I18nUtils i18nUtils;

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
        if (I18nInterceptor.userThreadLocal != null && I18nInterceptor.userThreadLocal.get() !=null){
            Map<String,String> userLocal = I18nInterceptor.userThreadLocal.get();
            String lang = I18nUtils.getLangStandard(userLocal.get(CommonContact.LANG));
            String i8nCode = commonResult.getI8nCode();
            if(StringUtils.isNoneEmpty(i8nCode)){
                String msg = i18nUtils.getText(i8nCode,lang);
                commonResult.setLang(lang);
                if(StringUtils.isNoneEmpty(msg)){
                    commonResult.setMsg(msg);
                }
            }
        }
        return commonResult;
    }
}
