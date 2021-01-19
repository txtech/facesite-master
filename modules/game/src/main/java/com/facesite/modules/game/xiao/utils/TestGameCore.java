package com.facesite.modules.game.xiao.utils;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/1/18 11:21 下午
 * @Version 1.0
 */
public class TestGameCore {

    public static void main(String[] args) throws Exception {
        String source = TestGameCore.source();
        source = bianLi(new M5Y8y(),source);
        System.out.println(source);
    }

    private static String bianLi(Object obj,String source)throws Exception{
        Field[] fields = obj.getClass().getDeclaredFields();
        String newSource= "";
        for(int i = 0 , len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            String key = "M5Y8y." + varName;
            Object value = fields[i].get(obj);
            if(StringUtils.isNotEmpty(newSource)){
                source = newSource;
            }
            String strValue = String.valueOf(value);
            if(StringUtils.isNotEmpty(strValue) && !strValue.equalsIgnoreCase("null")){
                Integer intValue = Integer.valueOf(strValue);
                if(intValue > 0){
                    newSource = source.replaceAll(key,strValue);
                }else{
                    newSource = source.replaceAll(key," ");
                }
            }
        }
        return newSource;
    }

    public static String source(){
        String source = "\"form\": [\n" +
                "                [M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U],\n" +
                "                [M5Y8y.s6U, M5Y8y.s6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.s6U, M5Y8y.s6U],\n" +
                "                [M5Y8y.s6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.s6U],\n" +
                "                [M5Y8y.s6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.s6U],\n" +
                "                [M5Y8y.s6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.s6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.s6U],\n" +
                "                [M5Y8y.s6U, M5Y8y.s6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.s6U, M5Y8y.s6U],\n" +
                "                [M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U],\n" +
                "                [M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.j6U, M5Y8y.j6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U]\n" +
                "            ],\n" +
                "            \"dirt\": M5Y8y.L7U,\n" +
                "            \"chips\": [\n" +
                "                [M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U],\n" +
                "                [M5Y8y.s6U, M5Y8y.s6U, M5Y8y.q6U, M5Y8y.D6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.q6U, M5Y8y.D6U, M5Y8y.s6U, M5Y8y.s6U],\n" +
                "                [M5Y8y.s6U, M5Y8y.q6U, M5Y8y.D6U, M5Y8y.q6U, M5Y8y.j6U, M5Y8y.D6U, M5Y8y.P6U, M5Y8y.q6U, M5Y8y.P6U, M5Y8y.s6U],\n" +
                "                [M5Y8y.s6U, M5Y8y.D6U, M5Y8y.D6U, M5Y8y.q6U, M5Y8y.q6U, M5Y8y.D6U, M5Y8y.q6U, M5Y8y.P6U, M5Y8y.q6U, M5Y8y.s6U],\n" +
                "                [M5Y8y.s6U, M5Y8y.q6U, M5Y8y.P6U, M5Y8y.j6U, M5Y8y.P6U, M5Y8y.P6U, M5Y8y.j6U, M5Y8y.P6U, M5Y8y.q6U, M5Y8y.s6U],\n" +
                "                [M5Y8y.s6U, M5Y8y.s6U, M5Y8y.q6U, M5Y8y.q6U, M5Y8y.j6U, M5Y8y.D6U, M5Y8y.D6U, M5Y8y.q6U, M5Y8y.s6U, M5Y8y.s6U],\n" +
                "                [M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.q6U, M5Y8y.q6U, M5Y8y.P6U, M5Y8y.q6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U],\n" +
                "                [M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.j6U, M5Y8y.q6U, M5Y8y.q6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U, M5Y8y.s6U]\n" +
                "            ],";
        return source;
    }
}
