package com.nabobsite.modules.nabob.api.entity;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jeesite.common.config.Global;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.task.entity.UserTask;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountDetail;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountLog;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 11:23 上午
 * @Version 1.0
 */
public class NabobI18n {
    //英语(美国)
    public static final String LANG_EN = "en_US";
    //英语(印度)
    public static final String LANG_IN = "en_IN";
    //简体中文(中国)
    public static final String LANG_ZH = "zh_CN";

    public static final Map<String, Map<String, String>> LOCAL_CACHE = new ConcurrentHashMap<>(256);

    private static Cache<String, List> cache = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).build();

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
