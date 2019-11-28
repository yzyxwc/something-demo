package com.example.redisdemo.util;

import com.alibaba.fastjson.JSON;
import com.example.redisdemo.annocation.DesensitizedAnnotation;
import com.example.redisdemo.enums.TypeEnum;
import com.example.redisdemo.selocal.desensitized.BaseInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * DesensitizedAnnotation 脱敏工具类
 * @author wc
 * @date 2019/10/29 09:50
 * @version 1.0
 */
public class DesensitizedUtils {

    private static final Logger logger = LoggerFactory.getLogger(DesensitizedUtils.class);

    private static final Map<String, TypeEnum> annotationMaps = new HashMap<>();

    /**
     * 类加载时装配待脱敏字段
     */
    static {
        try {
            Class<?> clazz = Class.forName(BaseInfo.class.getName());
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                DesensitizedAnnotation annotation = fields[i].getAnnotation(DesensitizedAnnotation.class);
                if (annotation != null) {
                    TypeEnum type = annotation.type();
                    String name = fields[i].getName();
                    //name为注解字段名称，value为注解类型。方便后续根据注解类型扩展
                    annotationMaps.put(name, type);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("类加载时装配待脱敏字段异常，异常信息:[{}]", new Object[]{e});
        }
    }


    /**
     * 脱敏处理方法
     *
     * @param object
     * @return
     */
    public static String getConverent(Map<String,Object> object) {
        try {
            // 1.处理Map数据类型
            if (object instanceof Map) {
                HashMap<String, Object> reqMap = (HashMap) object;
                Iterator<String> iterator = annotationMaps.keySet().iterator();
                iterator.forEachRemaining(annotationName -> {
                    if (reqMap.keySet().contains(annotationName)) {
                        doconverentForMap(reqMap, annotationName);
                    }
                });
                return JSON.toJSONString(reqMap);
            }
            return JSON.toJSONString(object);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("日志脱敏处理失败，回滚，详细信息:[{}]", new Object[]{e});
            return JSON.toJSONString(object);
        }
    }

    /**
     * 脱敏数据源为Map时处理方式
     *
     * @param reqMap
     * @param annotationName
     * @return
     */
    private static void doconverentForMap(HashMap<String, Object> reqMap, String annotationName) {
        String value = String.valueOf(reqMap.get(annotationName));
        if (StringUtils.isNotEmpty(value)) {
            value = doConverentByType(value, annotationName);
        }
        reqMap.put(annotationName, value);
    }
    /**
     * 脱敏处理方法
     *
     * @param object
     * @return
     */
    public static String getConverent(Object object) {

        String objClassName = object.getClass().getName();

        try {
            // 1.处理Map数据类型
            if (object instanceof Map) {
                HashMap<String, Object> reqMap = (HashMap) object;
                Iterator<String> iterator = annotationMaps.keySet().iterator();
                iterator.forEachRemaining(annotationName -> {
                    if (reqMap.keySet().contains(annotationName)) {
                        doconverentForMap(reqMap, annotationName);
                    }
                });
                return JSON.toJSONString(reqMap);
            }
            // 2.处理Object数据类型
            Object val = new Object();
            Class<?> objClazz = Class.forName(objClassName);
            Field[] declaredFields = objClazz.getDeclaredFields();
            for (int j = 0; j < declaredFields.length; j++) {
                Iterator<String> iterator = annotationMaps.keySet().iterator();
                while (iterator.hasNext()) {
                    String annotationName = iterator.next();
                    if (declaredFields[j].getName().equals(annotationName)) {
                        declaredFields[j].setAccessible(true);
                        val = declaredFields[j].get(object);
                        //获取属性后现在默认处理的是String类型，其他类型数据可扩展
                        String value = doconverentForObject(val, annotationName);
                        declaredFields[j].set(object, value);
                    }
                }
            }
            return JSON.toJSONString(object);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("日志脱敏处理失败，回滚，详细信息:[{}]", new Object[]{e});
            return JSON.toJSONString(object);
        }
    }

    /**
     * 脱敏数据源为Object时处理方式
     *
     * @param val
     * @param annotationName
     * @return
     */
    private static String doconverentForObject(Object val, String annotationName) {

        String value = String.valueOf(val);
        if (StringUtils.isNotEmpty(value)) {
            value = doConverentByType(value, annotationName);
        }
        return value;
    }


    /**
     * 根据不同注解类型处理不同字段
     *
     * @param value
     * @param annotationName
     * @return
     */
    private static String doConverentByType(String value, String annotationName) {
        TypeEnum typeEnum = annotationMaps.get(annotationName);
        switch (typeEnum) {
            case PERSON_NAME:
                value = getStringByLength(value);
                break;
            case PERSON_CERT_NO:
                value = getStringByLength(value);
            default:
                value = getStringByLength(value);
        }
        return value;
    }

    /**
     * 根据value长度取值(切分)
     *
     * @param value
     * @return
     */
    private static String getStringByLength(String value) {
        int length = value.length();
        if (length == 2){
            value = value.substring(1, 2) + "*";
        }else if (length == 3){
            value = value.substring(0,1) + "*" + value.substring(length -1);
        }else if (length > 11){
            value = value.substring(0,1) + "*****" + value.substring(length -1);
        }
        return value;
    }

}
