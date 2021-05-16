package com.nabobsite.modules.nabob.interceptor;

import com.jeesite.common.lang.StringUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.I18nUtils;
import com.nabobsite.modules.nabob.api.service.RedisOpsUtil;
import com.nabobsite.modules.nabob.api.entity.RedisPrefixContant;
import com.nabobsite.modules.nabob.utils.HttpBrowserTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
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
@Component
public class I18nInterceptor implements HandlerInterceptor {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RedisOpsUtil redisOpsUtil;

    public static final ThreadLocal<Map<String,String>> userThreadLocal = new NamedThreadLocal<Map<String,String>>("I18nInterceptor Lang");

    public I18nInterceptor(RedisOpsUtil redisOpsUtil) {
        this.redisOpsUtil = redisOpsUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        try {
            String ip = HttpBrowserTools.getIpAddr(request);
            String reqUrl = request.getRequestURI();
            String token = request.getHeader("Authorization");
            if(StringUtils.isEmpty(token)){
                logger.error("请求被拦截，获取授权信息为空:{},{},{}",token,ip,reqUrl);
                return false;
            }
            String newTokenKey = RedisPrefixContant.getTokenUserKey(token);
            String userId = (String) redisOpsUtil.get(newTokenKey);
            if(StringUtils.isEmpty(userId)){
                logger.error("请求被拦截，获取授权用户为空:{},{}",token,ip);
                return false;
            }
            String lang = I18nUtils.getUserLang(userId);
            Map<String,String> userLocal = new HashMap<>();
            userLocal.put(CommonContact.TOKEN,token);
            userLocal.put(CommonContact.USERID,userId);
            userLocal.put(CommonContact.LANG,lang);
            userThreadLocal.set(userLocal);
            return true;
        } catch (Exception e) {
           logger.error("拦截器准备发生异常",e);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        try {
            if (userThreadLocal != null){
                userThreadLocal.remove();
            }
        } catch (Exception e1) {
            logger.error("拦截器结束时发生异常",e1);
        }
    }

    public RedisOpsUtil getRedisOpsUtil() {
        return redisOpsUtil;
    }
}
