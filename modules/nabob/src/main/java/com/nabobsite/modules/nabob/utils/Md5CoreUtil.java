package com.nabobsite.modules.nabob.utils;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;
import java.util.TreeMap;

public class Md5CoreUtil {

	private static Logger logger = LoggerFactory.getLogger(Md5CoreUtil.class);

    /**
     * @desc 验证支付中心签名
     * @author nada
     * @create 2019/7/6 13:47
    */
    public static boolean verifyPaySign(JSONObject reqData, String md5Key,boolean isCheck) {
        if(!isCheck){
            return true;
        }
        String sourceSign = (String)reqData.get("sign");
        reqData.remove("sign");
        String  checkSign = getMd5Sign(reqData, md5Key);
        if (!checkSign.equalsIgnoreCase(sourceSign)) {
            return false;
        }
        return true;
    }

    /**
     * @desc 获取md5的字符串签名
     * @author nada
     * @create 2019/7/6 13:45
    */
    public static String getMd5Sign(JSONObject jsonData,String md5Key){
        SortedMap<String, Object> sortedMap = new TreeMap<String, Object>();
        for (Object key : jsonData.keySet()) {
            sortedMap.put(key.toString(), jsonData.get(key));
        }
        StringBuilder buffer = new StringBuilder();
        for (String key : sortedMap.keySet()){
            if(!sortedMap.containsKey(key) || sortedMap.get(key) == null || "".equals(sortedMap.get(key))){
                continue;
            }
            if(StringUtils.isNotEmpty(key) && null != sortedMap.get(key) && !"sign".equalsIgnoreCase(key)){
                buffer.append(key).append("=").append(sortedMap.get(key).toString()).append("&");
            }
        }
        buffer.append("key=").append(md5Key);
        String result = buffer.toString();
        logger.info("Sign Before MD5:{}",result);
        result = Md5Utils.md5(result);
        logger.debug("Sign Result:{}",result);
        return result;
    }

    public static String getSignStr(JSONObject jsonData){
        String signStr = "";
        try {
            SortedMap<String, Object> sortedMap = new TreeMap<String, Object>();
            for (Object key : jsonData.keySet()) {
                sortedMap.put(key.toString(), jsonData.get(key));
            }
            return getSignStr(sortedMap);
        } catch (Exception e) {
            logger.error("根据字母排序验签异常",e);
            return signStr;
        }
    }

    public static String getSignStr(SortedMap<String, Object> sortedMap){
        String signStr = "";
        try {
            StringBuilder buffer = new StringBuilder();
            for (String key : sortedMap.keySet()){
                if(!sortedMap.containsKey(key) || sortedMap.get(key) == null || "".equals(sortedMap.get(key))){
                    continue;
                }
                if("sign".equalsIgnoreCase(key) || "signature".equalsIgnoreCase(key) || "signData".equalsIgnoreCase(key)){
                    continue;
                }
                buffer.append(key).append("=").append(sortedMap.get(key).toString()).append("&");
            }
            signStr = buffer.toString();
            return signStr.substring(0,signStr.length()-1);
        } catch (Exception e) {
            logger.error("根据字母排序验签异常",e);
            return signStr;
        }
    }
}
