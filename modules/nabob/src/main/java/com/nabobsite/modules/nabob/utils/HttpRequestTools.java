package com.nabobsite.modules.nabob.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @描述: request请求处理工具类
 * @作者:nada
 * @时间:2018/12/20
 **/
public class HttpRequestTools {

    private static Logger logger = LoggerFactory.getLogger (HttpRequestTools.class);

    public static String CHARTSET_UTF_8 = "UTF-8";

    /**
     * @描述: 兼容解析请求报文
     * @时间:2017年11月15日 下午3:21:34
     */
    public static JSONObject getRequestJson (HttpServletRequest request) {
        try {
            request.setCharacterEncoding (CHARTSET_UTF_8);
            //1,From表单数据流解析
            String content = getFormDataRequest (request);
            if(StringUtils.isNotEmpty (content)){
                if(!content.contains("pushHeartbeat")){
                    logger.info ("请求内容:{}",content);
                }
                try {
                    return JSONObject.parseObject (content);
                } catch (Exception e) {
                    return StringToJson (content);
                }
            }

            //2,内置参数对象解析
            Map<String, String> reqPrmsMaps = getParameterMap (request);
            if (null != reqPrmsMaps && !reqPrmsMaps.isEmpty () && reqPrmsMaps.size () > 1) {
                logger.info ("内置对象解析:{}", reqPrmsMaps);
                return JSONObject.parseObject(JSON.toJSONString(reqPrmsMaps));
            }

            //3,根据get请求解析
            if ("GET".equalsIgnoreCase (request.getMethod())) {
                String getparamsStr = URLDecoder.decode (request.getQueryString () == null ? "" : request.getQueryString (), "utf-8");
                return str2JsonUtil (getparamsStr);
            }
            return null;
        } catch (Exception e) {
            logger.error ("兼容解析请求报文异常", e);
            return null;
        }
    }

    /**
     * @描述:通过内置对象解析报文
     * @作者:nada
     * @时间:2018/12/20
     **/
    public static Map<String, String> getParameterMap (HttpServletRequest request) {
        Map requestMap = request.getParameterMap ();
        Map<String, String> returnMap = new TreeMap ();
        Iterator entries = requestMap.entrySet ().iterator ();
        while (entries.hasNext ()) {
            Map.Entry entry = (Map.Entry) entries.next ();
            String key = (String) entry.getKey ();
            Object valueObj = entry.getValue ();
            if (StringUtils.isEmpty (key)) {
                continue;
            }
            if (null == valueObj) {
                returnMap.put (key, "");
                continue;
            }
            String value = "";
            if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring (0, value.length () - 1);
            } else {
                value = valueObj.toString ();
            }
            returnMap.put (key, value.trim ());
        }
        return returnMap;
    }

    /**
     * @描述:通过数据流解析报文
     * @时间:2017年12月5日 下午2:34:01
     */
    public static String getFormDataRequest (HttpServletRequest request) {
        BufferedReader br = null;
        try {
            request.setCharacterEncoding ("UTF-8");
            br = new BufferedReader (new InputStreamReader (request.getInputStream (), StandardCharsets.UTF_8));
            StringBuffer resContent = new StringBuffer ();
            String temp = null;
            while ((temp = br.readLine ()) != null) {
                resContent.append (URLDecoder.decode (temp, "UTF-8"));
            }
            br.close ();
            String line = resContent.toString ();
            return line;
        } catch (Exception e) {
            logger.error ("通过数据流解析报文异常", e);
            return null;
        }finally {
            if (br != null) {
                try {
                    br.close ();
                } catch (IOException e) {
                    logger.error ("兼容解析请求报文关闭流异常", e);
                }
            }
        }
    }

    /**
     * @描述:获取参数字符转为json
     * @时间:2018年3月5日 下午5:05:43
     */
    public static JSONObject str2JsonUtil (String str) {
        try {
            JSONObject notifyJson = new JSONObject ();
            if (StringUtils.isBlank (str)) {
                return null;
            }
            String[] param = str.split ("&");
            for (String content : param) {
                if (content.indexOf ("=") > 0) {
                    String key = content.substring (0, content.indexOf ("="));
                    String value = content.substring (content.indexOf ("=") + 1);
                    notifyJson.put (StringUtils.deleteWhitespace (key), StringUtils.deleteWhitespace (value).replace ("\"", ""));
                } else if (content.indexOf (":") > 0) {
                    String key = content.substring (0, content.indexOf (":"));
                    String value = content.substring (content.indexOf (":") + 1);
                    notifyJson.put (StringUtils.deleteWhitespace (key), StringUtils.deleteWhitespace (value).replace ("\"", ""));
                }
            }
            return notifyJson;
        } catch (Exception e) {
            logger.error ("获取参数字符转为json异常", e);
            return null;
        }
    }

    /**
     * @描述:请求&拼接字符串转为JSONObject格式
     */
    public static JSONObject StringToJson(String str){
        JSONObject notifyJson = new JSONObject();
        String[] param = str.split("&");
        for (String content : param) {
            if (content.indexOf("=") > 0) {
                String key = content.substring(0, content.indexOf("="));
                String value = content.substring(content.indexOf("=") + 1);
                notifyJson.put(StringUtils.deleteWhitespace(key), StringUtils.deleteWhitespace(value).replace("\"", ""));
            }else if (content.indexOf(":") > 0) {
                String key = content.substring(0, content.indexOf(":"));
                String value = content.substring(content.indexOf(":") + 1);
                notifyJson.put(StringUtils.deleteWhitespace(key),StringUtils.deleteWhitespace(value).replace("\"", ""));
            }
        }
        return notifyJson;
    }
}
