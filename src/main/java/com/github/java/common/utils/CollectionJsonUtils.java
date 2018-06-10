package com.github.java.common.utils;

import java.util.ArrayList;
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
 * @author wangzhifeng
 * @date 2018年2月9日 上午9:35:09
 */
public class CollectionJsonUtils {
    private final static Logger logger = LoggerFactory.getLogger(CollectionJsonUtils.class);

    /**
     * 字符串转为jsonToList
     * 
     * @param <T>
     * @param text
     * @return
     */
    public static <T> List<T> jsonToList(String text, Class<T> cls) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return JSON.parseObject(text, new TypeReference<ArrayList<T>>() {
        });
    }

    /**
     * 字符串转为jsonToSet
     * 
     * @param <T>
     * @param text
     * @return
     */
    public static <T> Set<T> jsonToSet(String text, Class<T> cls) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return JSON.parseObject(text, new TypeReference<HashSet<T>>() {
        });
    }

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
}
