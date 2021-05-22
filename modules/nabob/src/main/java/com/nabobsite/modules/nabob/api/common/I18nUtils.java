package com.nabobsite.modules.nabob.api.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jeesite.common.lang.StringUtils;

import java.util.List;
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
