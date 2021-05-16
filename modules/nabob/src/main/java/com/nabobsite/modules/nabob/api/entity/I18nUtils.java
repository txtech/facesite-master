package com.nabobsite.modules.nabob.api.entity;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jeesite.common.lang.StringUtils;
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

@Service
public class I18nUtils {

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

    public String getText(String code,String language) {
        Map<String, String> props = I18nUtils.LOCAL_CACHE.get(language);
        if (null != props && props.containsKey(code)) {
            return props.get(code);
        }
        return "";
    }

    public static Locale getLocale(String lang) {
        if(I18nUtils.LANG_EN.equalsIgnoreCase(lang)){
            return Locale.US;
        }else if(I18nUtils.LANG_IN.equalsIgnoreCase(lang)){
            return new Locale("en","IN");
        }else if(I18nUtils.LANG_ZH.equalsIgnoreCase(lang)){
            return Locale.SIMPLIFIED_CHINESE;
        }else{
            return Locale.US;
        }
    }

    public static String getUserLang(String token) {
        String lang = USER_LANG_CACHE.get(token);
        if(StringUtils.isEmpty(lang)){
            //lang = LANG_EN;
            lang = LANG_IN;
        }
        return lang;
    }

    /**
     * @desc 格式化语言
     * @author nada
     * @create 2021/5/16 2:10 下午
    */
    public static String getLangStandard(String lang) {
        if(StringUtils.isEmpty(lang)){
            return I18nUtils.LANG_EN;
        }
        if(I18nUtils.LANG_EN.equalsIgnoreCase(lang)){
            return I18nUtils.LANG_EN;
        }else if(I18nUtils.LANG_IN.equalsIgnoreCase(lang)){
            return I18nUtils.LANG_IN;
        }else if(I18nUtils.LANG_ZH.equalsIgnoreCase(lang)){
            return I18nUtils.LANG_ZH;
        }else{
            return I18nUtils.LANG_EN;
        }
    }
}
