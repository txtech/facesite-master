package com.nabobsite.modules.nabob.i18n;
import com.jeesite.common.shiro.realms.ud;
import org.springframework.context.i18n.LocaleContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/15 4:58 下午
 * @Version 1.0
 */
public class I18nLocaleResolver extends ud {
    private static Boolean enabled;

    @Override
    public Locale determineDefaultLocale(HttpServletRequest request) {
        return super.determineDefaultLocale(request);
    }

    public I18nLocaleResolver() {
    }

    @Override
    public void setLocaleContext(HttpServletRequest request, HttpServletResponse response, LocaleContext localeContext) {
        super.setLocaleContext(request, response, localeContext);
    }

    public static boolean enabled() {
        if (enabled == null) {
            boolean var10002 = true;
            enabled = true;
        }
        return enabled;
    }

    @Override
    public LocaleContext resolveLocaleContext(HttpServletRequest request) {
        return super.resolveLocaleContext(request);
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        return super.resolveLocale(request);
    }
}
