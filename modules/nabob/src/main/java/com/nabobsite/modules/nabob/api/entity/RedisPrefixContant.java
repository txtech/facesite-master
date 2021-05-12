package com.nabobsite.modules.nabob.api.entity;

/**
 * @author ：杨过
 * @date ：Created in 2020/2/17
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description:
 **/
public class RedisPrefixContant {

    /**
     * @desc 缓存1分钟
     * @author nada
     * @create 2020/12/23 7:10 下午
    */
    public final static long CACHE_ONE_SECONDS = 60000;

    /**
     * @desc 缓存30分钟
     * @author nada
     * @create 2020/12/23 7:10 下午
     */
    public final static long CACHE_HALF_HOUR = 1800000;

    /**
     * @desc 缓存1小时
     * @author nada
     * @create 2020/12/23 7:10 下午
     */
    public final static long CACHE_ONE_HOURS = 3600000;

    /**
     * 商城用户缓存+ID
     */
    public final static String FRONT_USER_INFO_CACHE = "front:user:info:id:";
    /**
     * 商城用户缓存+ID
     */
    public final static String FRONT_USER_TOKEN_INFO_CACHE = "front:member:info:token:";

    /**
     * 商城产品详情缓存+ID
     */
    public final static String FRONT_PRODUCT_INFO_CACHE = "front:product:info:id:";
    /**
     * 商城面板结构缓存
     */
    public final static String FRONT_HOME_PANEL_CACHE = "front:home:panel:position:";
    /**
     * 商城面板和产品缓存
     */
    public final static String FRONT_PRODUCT_PANEL_CACHE = "front:product:panel:position:";
    /**
     * 商城专题导航栏缓存
     */
    public final static String FRONT_PRODUCT_PANEL_SPECIAL_CACHE = "front:product:panel:panelId:";
    /**
     * 商城产品二级所有分类+cid
     */
    public final static String FRONT_PRODUCT_CATEGORY_SUB_CACHE = "front:product:category:sub:cid:";
    /**
     * 商城产品所有分类+parentId + cid
     */
    public final static String FRONT_PRODUCT_CATEGORY_CACHE = "front:product:category:parentid:cid:";
    /**
     * 商城系统设置缓存
     */
    public final static String FRONT_HOME_SYSTEM_CACHE = "front:home:system:list";
    /**
     * 商城系统设置缓存
     */
    public final static String FRONT_HOME_SYSTEM_INFO_CACHE = "front:home:system:info:id";

    /**
     * 运营后台产品详情缓存+ID
     */
    public final static String CMS_PANEL_NODE_CACHE = "cms:panel:node:position:";
    /**
     * 运营后台会员缓存+ID
     */
    public final static String CMS_MEMBER_INFO_CACHE = "cms:member:info:id:";

}
