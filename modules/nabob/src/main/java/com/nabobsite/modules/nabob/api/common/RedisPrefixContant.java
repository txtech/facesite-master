package com.nabobsite.modules.nabob.api.common;

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


    //系统配置key
    public final static String SYS_KEY_COUNTDOWN_TIME  = "sys.key.countdown.time";
    public final static String SYS_KEY_CURRENT_VERSION  = "sys.key.app.current.version";
    public final static String SYS_KEY_UPDATE_VERSION  = "sys.key.app.update.version";
    public final static String SYS_KEY_APP_DOWNLOAD_URL  = "sys.key.app.download.url";


    //随机码缓存
    public final static String FRONT_USER_RANDOM_CODE_CACHE = "front:user:random:code:";
    //发送短信
    public final static String FRONT_USER_SMS_CODE_CACHE = "front:user:sms:code:";
    //用户token
    public final static String FRONT_USER_TOKEN_INFO_CACHE = "front:member:info:token:";


    public static String getTokenUserKey(String token){
        return RedisPrefixContant.FRONT_USER_TOKEN_INFO_CACHE+token;
    }

    public static String getUserTokenKey(String userId){
        return RedisPrefixContant.FRONT_USER_TOKEN_INFO_CACHE+userId;
    }
}
