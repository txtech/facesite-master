package com.nabobsite.modules.nabob.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.StringUtils;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.I18nUtils;
import com.nabobsite.modules.nabob.api.entity.RedisPrefixContant;
import com.nabobsite.modules.nabob.api.service.RedisOpsUtil;
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

    public static String CHART_UTF ="UTF-8";

    private final RedisOpsUtil redisOpsUtil;

    public static final ThreadLocal<Map<String,String>> userThreadLocal = new NamedThreadLocal<Map<String,String>>("I18nInterceptor Lang");

    public I18nInterceptor(RedisOpsUtil redisOpsUtil) {
        this.redisOpsUtil = redisOpsUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        try {
            String userId = "";
            String reqUrl = request.getRequestURI();
            String ip = HttpBrowserTools.getIpAddr(request);
            String lang = request.getHeader("lang");
            String token = request.getHeader("Authorization");
            Boolean isOpenApi = Boolean.FALSE;
            if(StringUtils.isNotEmpty(reqUrl) && reqUrl.contains("/api/open/")){
                isOpenApi = Boolean.TRUE;
            }
            if(StringUtils.isEmpty(token) && !isOpenApi){
                logger.error("请求被拦截，获取授权信息为空:{},{},{}",token,ip,reqUrl);
                this.writeResponse(response,ResultUtil.failed(I18nCode.CODE_10001,"Failed to request,User not authorized"));
                return false;
            }
            if(StringUtils.isNotEmpty(token)){
                String newTokenKey = RedisPrefixContant.getTokenUserKey(token);
                userId = (String) redisOpsUtil.get(newTokenKey);
                if(StringUtils.isEmpty(userId)){
                    logger.error("请求被拦截，获取授权用户为空:{},{}",token,ip);
                    this.writeResponse(response,ResultUtil.failed(I18nCode.CODE_10001,"Failed to request,User authorization expired！"));
                    return false;
                }
            }
            lang = I18nUtils.getLangStandard(lang);
            Map<String,String> userLocal = new HashMap<>();
            userLocal.put(CommonContact.TOKEN,token);
            userLocal.put(CommonContact.USERID,userId);
            userLocal.put(CommonContact.LANG,lang);
            userThreadLocal.set(userLocal);
            return true;
        } catch (Exception e) {
           logger.error("拦截器准备发生异常",e);
            this.writeResponse(response,ResultUtil.failed(I18nCode.CODE_10002,"Failed to request,Authorization error！"));
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

    /**
     * @描述:网关响应报文
     * @作者:nada
     * @时间:2019/3/15
     **/
    public ModelAndView writeResponse(HttpServletResponse response, CommonResult result) {
        try {
            this.initHttpServletRequest (null, response);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSONObject.toJSONString(result));
        } catch (Exception e) {
            logger.error("Failed to write response exception", e);
        }
        return null;
    }

    /**
     * @描述:初始化设置报文请求响应编码格式
     * @时间:2017年12月18日 下午5:45:02
     */
    public void initHttpServletRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request != null) {
                request.setCharacterEncoding(CHART_UTF);
            }
            if (response != null) {
                response.setCharacterEncoding(CHART_UTF);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
