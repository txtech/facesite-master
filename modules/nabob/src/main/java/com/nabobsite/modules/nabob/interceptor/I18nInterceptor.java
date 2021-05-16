package com.nabobsite.modules.nabob.interceptor;

import com.jeesite.common.lang.StringUtils;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.I18nUtils;
import com.nabobsite.modules.nabob.config.RedisOpsUtil;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/15 5:13 下午
 * @Version 1.0
 */
public class I18nInterceptor implements HandlerInterceptor {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisOpsUtil redisOpsUtil;


    private static final ThreadLocal<Map<String,String>> LangThreadLocal = new NamedThreadLocal<Map<String,String>>("I18nInterceptor Lang");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        try {
            String ip = HttpBrowserTools.getIpAddr(request);
            String token = request.getHeader("Authorization");
            if(StringUtils.isEmpty(token)){
                logger.error("请求被拦截，获取授权信息为空:{},{}",token,ip);
                return false;
            }
            String userId = (String) redisOpsUtil.get(token);
            if(StringUtils.isEmpty(userId)){
                logger.error("请求被拦截，获取授权用户为空:{},{}",token,ip);
                return false;
            }
            String lang = I18nUtils.getUserLang(userId);
            Map<String,String> threadLocal = new HashMap<>();
            threadLocal.put(CommonContact.TOKEN,token);
            threadLocal.put(CommonContact.USERID,userId);
            threadLocal.put(CommonContact.LANG,lang);
            LangThreadLocal.set(threadLocal);
            return true;
        } catch (Exception e) {
           logger.error("拦截器准备发生异常",e);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        try {
            if (LangThreadLocal != null){
                Map<String,String> threadLocal = LangThreadLocal.get();
                I18nUtils.getText(threadLocal.get(CommonContact.LANG));
                LangThreadLocal.remove();
            }
        } catch (Exception e) {
            logger.error("拦截器结束时发生异常",e);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
