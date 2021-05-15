package com.nabobsite.modules.nabob.i18n;

import com.jeesite.common.codec.AesUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.datasource.DataSourceHolder;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.i18n.I18nLocaleResolver;
import com.jeesite.common.io.FileUtils;
import com.jeesite.common.io.PropertiesUtils;
import com.jeesite.common.lang.ObjectUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.mybatis.mapper.query.QueryTable;
import com.jeesite.common.service.ServiceException;
import com.jeesite.common.shiro.realms.Ra;
import com.jeesite.common.utils.SpringUtils;
import com.jeesite.common.web.http.ServletUtils;
import com.jeesite.modules.sys.utils.ConfigUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/15 5:03 下午
 * @Version 1.0
 */
public class Global {
    public static final String YES = "1";
    public static final String NO = "0";
    public static final String FALSE = "false";
    public static Logger logger = LoggerFactory.getLogger(com.jeesite.common.config.Global.class);
    public static final String SHOW = "1";
    public static final String OP_EDIT = "edit";
    public static final String OP_ADD = "add";
    private static final com.jeesite.common.config.Global INSTANCE = new com.jeesite.common.config.Global();
    public static final String USERFILES_BASE_URL = "/userfiles/";
    public static final String HIDE = "0";
    public static final String OP_AUTH = "auth";
    public static final String TRUE = "true";
    private static Map<String, String> props = MapUtils.newHashMap();

    public Global() {
    }

   /* public static void setLang(String lang, HttpServletRequest request, HttpServletResponse response) {
        Locale a = Locale.CHINA;
        if (com.jeesite.common.i18n.I18nLocaleResolver.enabled()) {
            try {
                a = LocaleUtils.toLocale(lang);
            } catch (IllegalArgumentException var5) {
            }
        }

        F.null().setLocale(request, response, a);
    }

    public static String getLang() {
        if (I18nLocaleResolver.enabled()) {
            Locale a = null;
            HttpServletRequest a;
            if ((a = ServletUtils.getRequest()) != null) {
                a = F.null().resolveLocale(a);
            }

            if (a != null) {
                return a.toString();
            }
        }

        return Locale.CHINA.toString();
    }


    public static String getText(String code, String... params) {
        if (StringUtils.isBlank(code)) {
            return code;
        } else {
            Locale a = Locale.CHINA;

            try {
                a = LocaleUtils.toLocale(getLang());
            } catch (IllegalArgumentException var4) {
            }

            try {
                return F.null().getMessage(code, params, a);
            } catch (NoSuchMessageException var5) {
                if (StringUtils.isBlank(code)) {
                    code = "";
                }

                if (StringUtils.startsWith(code, Ra.null("8"))) {
                    code = StringUtils.substringAfter(code, QueryTable.null("f"));
                }

                return params != null && params.length > 0 ? (new MessageFormat(code, a)).format(params) : code;
            }
        }
    }*/
}
