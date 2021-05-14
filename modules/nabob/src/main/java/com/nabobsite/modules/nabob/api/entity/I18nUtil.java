package com.nabobsite.modules.nabob.api.entity;

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
import java.util.Locale;
import java.util.Map;

@Service("messageSource")
public class I18nUtil extends AbstractMessageSource implements ResourceLoaderAware {

    ResourceLoader resourceLoader;

    @Autowired
    private HttpServletRequest request;

    public static String getMessage(String code) {
        return getMessage(code, null);
    }

    public static String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }

    public static String getMessage(String code, Object[] args, String defaultMessage) {
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

    /**
     * @desc 从缓存中取出国际化配置对应的数据 或者从父级获取
     * @author nada
     * @create 2021/5/14 9:40 下午
    */
    public String getSourceFromCache(String code, Locale locale) {
        String language = locale == null ? RequestContextUtils.getLocale(request).getLanguage() : locale.getLanguage();
        Map<String, String> props = NabobI18n.LOCAL_CACHE.get(language);
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
}
