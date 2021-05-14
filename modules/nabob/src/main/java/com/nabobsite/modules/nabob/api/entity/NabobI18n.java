package com.nabobsite.modules.nabob.api.entity;

import com.jeesite.common.config.Global;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.task.entity.UserTask;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountDetail;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountLog;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 11:23 上午
 * @Version 1.0
 */
public class NabobI18n {

    //英文
    public static final String LANG_EN = "en";
    //中文
    public static final String LANG_ZH = "zh";
    //印度
    public static final String LANG_IN = "in";

    /**
     * @desc 获取信息
     * @author nada
     * @create 2021/5/14 7:37 下午
    */
    public static String getText(String key,String defaultVale){
        Global.getLang();
        return i18nMap.get(key);
    }

    public static volatile Map<String,Map<String,String>>  langMap = new HashMap<String,Map<String,String>>();
    public static volatile Map<String,String>  i18nMap = new HashMap<>();

    public static volatile Map<String,String>  userLang = new HashMap<>();
}
