package com.facesite.modules.game.xiao.utils;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.http.HttpClientUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述: 浏览器解析识别工具类
 * @作者:nada
 * @时间:2018/12/20
 **/
public class HttpBrowserTools {

    public static void main(String[] args) {
        String ip = "11.186.122.90";
        String address = getAlibaba(ip);
        System.out.println(address);
    }

    /**
     * @param ip ip地址
     * @return java.lang.String
     * @version 1.0
     */
    public static String getAlibaba(String ip) {
        try {
            if(StringUtils.isEmpty(ip)){
                return "";
            }
            Map map = new HashMap();
            map.put("ip", ip);
            map.put("accessKey", "alibaba-inc");
            String result = HttpClientUtils.post("http://ip.taobao.com/outGetIpInfo", map);
            Map valueMap = JSONObject.parseObject(result, Map.class);
            if ("query success".equals(valueMap.get("msg"))) {
                Map<String, String> dataMap = (Map<String, String>) valueMap.get("data");
                String country = dataMap.get("country");
                String region = dataMap.get("region");
                String city = dataMap.get("city");
                return country + region + city;
            }
            return "";
        } catch (Exception e) {
            Console.log(e);
            return "";
        }
    }

    /***
     * @描述:获取客户端Ip地址
     * @作者:nada
     * @时间:2018/12/6
     **/
    public static String getIpAddr (HttpServletRequest request) {
        try {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            // 如果是多级代理，那么取第一个IP为客户端IP
            if (ip != null && ip.indexOf(",") != -1) {
                ip = ip.substring(0, ip.indexOf(",")).trim();
            }
            return ip;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @描述:判断是否微信进入
     * @时间:2018/6/5 17:58
     */
    public static boolean isWeChat (HttpServletRequest request) {
        String userAgent = request.getHeader ("user-agent").toLowerCase ();
        return userAgent.contains ("micromessenger");
    }

    /**
     * @描述:判断是否支付宝打开
     * @时间:2018/6/26 14:54
     */
    public static boolean isAlipay (HttpServletRequest request) {
        String userAgent = request.getHeader ("user-agent").toLowerCase ();
        return userAgent.contains ("alipay");
    }

    /**
     * @描述:判断是否IPhone打开
     * @时间:2018/6/26 14:54
     */
    public static boolean isIOS (HttpServletRequest request) {
        String userAgent = request.getHeader ("user-agent") == null ? "" : request.getHeader ("user-agent").toLowerCase ();
        return userAgent.contains ("iphone");
    }

    /**
     * @描述:判断浏览器Referer
     * @作者:nada
     * @时间:2018/12/20
     **/
    public static String getRefer (HttpServletRequest request) {
        try {
            String reffer = "";
            String[] list = {"referer", "Referer", "referrer", "REFERER"};
            for (String reKey : list) {
                if (StringUtils.isNotBlank (request.getHeader (reKey))) {
                    reffer = request.getHeader (reKey);
                    break;
                }
            }
            reffer = reffer.toLowerCase ();
            if (!reffer.contains ("http://") && !reffer.contains ("https://")) {
                return "";
            }
            reffer = reffer.substring (reffer.indexOf ("://") + 3);
            if (!reffer.contains ("/")) {
                return reffer;
            }
            reffer = reffer.substring (0, reffer.indexOf ("/"));
            if (getSubCountStr (reffer, ".") == 2) {
                reffer = reffer.substring (reffer.indexOf ("."));
            }
            if (!reffer.startsWith (".")) {
                reffer = "." + reffer;
            }
            return reffer;
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return "";
    }

    /**
     * 统计字符串出现的次数
     * @param str
     * @param key
     * @return
     */
    public static int getSubCountStr(String str, String key) {
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(key, index)) != -1) {
            index = index + key.length();
            count++;
        }
        return count;
    }
}
