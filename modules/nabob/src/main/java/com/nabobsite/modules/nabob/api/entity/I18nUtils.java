package com.nabobsite.modules.nabob.api.entity;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jeesite.common.lang.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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

    public static String getText(String code,String language) {
        Map<String, String> props = I18nUtils.LOCAL_CACHE.get(language);
        if (null != props && props.containsKey(code)) {
            String msg = props.get(code);
            if(StringUtils.isNotEmpty(msg)){
                return msg;
            }
        }
        return code;
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

    public static String getUserLang(String userId) {
        String lang = USER_LANG_CACHE.get(userId);
        if(StringUtils.isNotEmpty(lang)){
            return lang;
        }
        return LANG_IN;
    }

    public static Boolean setUserLang(String userId,String lang) {
        USER_LANG_CACHE.put(userId,lang);
        return true;
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
