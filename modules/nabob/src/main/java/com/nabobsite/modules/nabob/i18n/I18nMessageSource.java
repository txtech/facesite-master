package com.nabobsite.modules.nabob.i18n;

import com.jeesite.common.shiro.realms.xd;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/15 4:58 下午
 * @Version 1.0
 */
public class I18nMessageSource extends xd {

    @Override
    public String resolveCodeWithoutArguments(String code, Locale locale) {
        return super.resolveCodeWithoutArguments(code, locale);
    }

    @Override
    public MessageFormat resolveCode(String code, Locale locale) {
        return super.resolveCode(code, locale);
    }

    public I18nMessageSource() {
    }

    @Override
    public void clearCache() {
        super.clearCache();
    }
}
