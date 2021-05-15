package com.nabobsite.modules.nabob.api.entity;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service("i18nUtils")
public class I18nUtils extends AbstractMessageSource implements ResourceLoaderAware {

    //英语(美国)
    public static final String LANG_EN = "en_US";
    //英语(印度)
    public static final String LANG_IN = "en_IN";
    //简体中文(中国)
    public static final String LANG_ZH = "zh_CN";

    public static final String LANG_MODULE = "common";

    public static final Map<String,String> USER_LANG_CACHE = new ConcurrentHashMap<>(256);
    public static final Map<String, Map<String, String>> LOCAL_CACHE = new ConcurrentHashMap<>(256);
    private static Cache<String, List> cache = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).build();

    ResourceLoader resourceLoader;
    @Autowired
    private HttpServletRequest request;

    public static String getText(String code) {
        return getText(code, null);
    }

    public static String getText(String code, Object[] args) {
        return getText(code, args, "");
    }

    public static String getText(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");
        String content;
        try{
            content = messageSource.getMessage(code, args, locale);
        }catch (Exception e){
            content = defaultMessage;
        }
        return content;
    }

    public static String getUserLang(String token) {
        return USER_LANG_CACHE.get(token);
    }

    /**
     * @desc 从缓存中取出国际化配置对应的数据 或者从父级获取
     * @author nada
     * @create 2021/5/14 9:40 下午
    */
    public String getSourceFromCache(String code, Locale locale) {
        String language = locale == null ? RequestContextUtils.getLocale(request).getLanguage() : locale.getLanguage();
        Map<String, String> props = I18nUtils.LOCAL_CACHE.get(language);
        if (null != props && props.containsKey(code)) {
            return props.get(code);
        }
        if (null != this.getParentMessageSource()) {
            return this.getParentMessageSource().getMessage(code, null, locale);
        }
        return code;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = (resourceLoader == null ? new DefaultResourceLoader() : resourceLoader);
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getSourceFromCache(code, locale);
        MessageFormat messageFormat = new MessageFormat(msg, locale);
        return messageFormat;
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getSourceFromCache(code, locale);
    }

    //    public Map<String, Map<Integer, CardInfoDO>> getAllCardInfo() {
//        List<Map<String, Map<Integer, CardInfoDO>>> allCardList = null;
//        try {
//            allCardList = cache.get(CARD_INFO,new Callable<List<Map<String, Map<Integer, CardInfoDO>>>>() {
//                                @Override
//                                public List<Map<String, Map<Integer, CardInfoDO>>> call()
//                                        throws Exception {
//                                    return buildCardInfo();
//                                }
//                            });
//        } catch (ExecutionException e) {
//            LOG.error("get card info exception ", e);
//            return null;
//        }
//        if (CollectionUtils.isEmpty(allCardList)) {
//            return null;
//        }
//        return allCardList.get(0);
//    }
}
