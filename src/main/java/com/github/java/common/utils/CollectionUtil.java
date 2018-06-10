package com.github.java.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @Description: 集合工具类
 */
public final class CollectionUtil {
    private final static Logger logger = LoggerFactory.getLogger(CollectionUtil.class);

    /**
     * 字符串转为HashMap
     * 
     * @param text
     * @return
     */
    public static Map<String, String> jsonToMap(String text) {
        try {
            if (StringUtils.isBlank(text)) {
                return null;
            }
            return JSON.parseObject(text, new TypeReference<HashMap<String, String>>() {
            });
        } catch (Throwable e) {
            logger.error("convert str to map error, str:{}", text);
            return new HashMap<String, String>();
        }
    }

    /**
     * 字符串转为HashMap
     * 
     * @param text
     * @return
     */
    public static Map<String, String[]> jsonToParameterMap(String text) {
        try {
            if (StringUtils.isBlank(text)) {
                return null;
            }
            return JSON.parseObject(text, new TypeReference<HashMap<String, String[]>>() {
            });
        } catch (Throwable e) {
            logger.error("convert str to map error, str:{}", text);
            return new HashMap<String, String[]>();
        }
    }

    public static <T> T getParameter(String key, Map<String, String[]> parameterMap, Class<T> t) {
        if (null == parameterMap) {
            return null;
        }
        String[] value = parameterMap.get(key);
        if (null == value) {
            return null;
        }
        if (t == String.class) {
            if (value.length > 0) {
                return (T) value[0];
            }
        } else if (t == String[].class) {
            return (T) value;
        }
        return null;
    }

    /**
     * 通过对象的属性包装Map
     * 
     * @param o
     * @return
     */
    public static Map<String, String> createMapByObjField(Object o) {
        Map<String, String> map = new HashMap<String, String>();
        // 记录，老的JAVA，
        // getFields() 获取所有public和protected字段,包括父类字段, 1.8经测试只能是获取public
        // getDeclaredFields()
        // 获取所有字段,public和protected和private,但是不包括父类字段,经测试static final等会影响使用
        Field[] fields = o.getClass().getFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(o);
                if (obj != null) {
                    map.put(field.getName(), (String) obj);
                } else {
                    map.put(field.getName(), "");
                }
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage());
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
            }
        }
        logger.debug("createMapByObjField key set:{}", map.keySet());
        return map;
    }

    /**
     * 除去数组中的空值和指定key
     * 
     * @param map 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> emptyValueFilter(Map<String, String> map, Set<String> filterKeySet) {
        if (filterKeySet == null) {
            filterKeySet = new HashSet<String>();
        }

        Map<String, String> result = new HashMap<String, String>();
        if (map == null || map.size() <= 0) {
            return result;
        }

        for (String key : map.keySet()) {
            String value = map.get(key);
            if (value == null || value.equals("") || filterKeySet.contains(key)) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 把所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * 
     * @param map 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createAndLinkString(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key);

            stringBuilder.append(key);
            stringBuilder.append("=");
            stringBuilder.append(value);

            if (i < keys.size() - 1) {// 拼接时，不包括最后一个&字符
                stringBuilder.append("&");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 把所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * 
     * @param map 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createQueryString(@SuppressWarnings("rawtypes") Map map) {
        @SuppressWarnings("unchecked")
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = map.get(key);

            stringBuilder.append(key);
            stringBuilder.append("=");
            stringBuilder.append(value);

            if (i < keys.size() - 1) {// 拼接时，不包括最后一个&字符
                stringBuilder.append("&");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 将ServletRequest的ParameterMap转换为QueryString
     * 
     * @param map 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createQueryStringByParameterMap(Map<String, String[]> map) {
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String[] value = map.get(key);

            stringBuilder.append(key);
            stringBuilder.append("=");
            if (value != null && value.length > 0) {
                for (int j = 0; j < value.length; j++) {
                    if (j >= value.length - 1)
                        stringBuilder.append(value[j]);
                    else
                        stringBuilder.append(value[j] + ",");
                }
            }

            if (i < keys.size() - 1) {// 拼接时，不包括最后一个&字符
                stringBuilder.append("&");
            }
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {

    }

}
