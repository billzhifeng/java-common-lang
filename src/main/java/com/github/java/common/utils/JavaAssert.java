package com.github.java.common.utils;

import java.util.Collection;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.java.common.base.BaseEnum;
import com.github.java.common.base.BaseException;
import com.github.java.common.base.ReturnCodeEnum;

/**
 */
public class JavaAssert<E extends BaseException> {
    private static final Logger log = LoggerFactory.getLogger(JavaAssert.class);

    /**
     * 不为空
     * 
     * @param object
     * @param returnCode
     * @param message
     * @param exceptionCls
     */
    public static <E extends BaseException> void notNull(Object object, BaseEnum returnCode, String message,
                                                         Class<E> exceptionCls) {
        if (object == null) {
            log.warn(message);

            throwException(returnCode, message, exceptionCls);
        }
    }

    /**
     * 判断
     * 
     * @param expression M必填
     * @param returnCode M必填
     * @param message M必填
     * @param arg 表达式所在的请求参数, 非必填
     */
    public static <E extends BaseException> void isTrue(boolean expression, BaseEnum returnCode, String message,
                                                        Object arg, Class<E> exceptionCls) {
        if (!expression) {
            log.warn("expression is false,{},args:{}", message, arg);

            throwException(returnCode, message, exceptionCls);
        }
    }

    /**
     * 判断
     * 
     * @param expression M必填
     * @param returnCode M必填
     * @param message M必填
     * @param arg 表达式所在的请求参数, 非必填
     */
    public static <E extends BaseException> void isTrue(boolean expression, BaseEnum returnCode, String message,
                                                        Class<E> exceptionCls) {
        if (!expression) {
            log.warn("{}", message);

            throwException(returnCode, message, exceptionCls);
        }
    }

    /**
     * 有文本
     * 
     * @param text
     * @param returnCode
     * @param message
     * @param exceptionCls
     */
    public static <E extends BaseException> void hasText(String text, BaseEnum returnCode, String message,
                                                         Class<E> exceptionCls) {
        if (StringUtils.isBlank(text)) {
            log.warn("{},args:{}", message, text);

            throwException(returnCode, message, exceptionCls);
        }
    }

    /**
     * 不为空
     * 
     * @param collection
     * @param returnCode
     * @param message
     * @param exceptionCls
     */
    public static <E extends BaseException> void notEmpty(Collection<?> collection, BaseEnum returnCode, String message,
                                                          Class<E> exceptionCls) {
        if (null == collection || 0 == collection.size()) {
            log.warn("{},args:{}", message, JSON.toJSONString(collection));

            throwException(returnCode, message, exceptionCls);
        }
    }

    /**
     * 为正整数
     * 
     * @param text
     * @param message
     * @param class1
     */
    public static <E extends BaseException> void isNum(String text, String message, Class<E> exceptionCls) {
        boolean isNum = Pattern.compile("^[0-9]*$").matcher(text).find();
        if (!isNum) {
            log.warn("{},args:{}", message, text);

            throwException(ReturnCodeEnum.PARAM_VALIDATE_ERROR, message, exceptionCls);
        }
    }

    /**
     * 抛异常工具
     * 
     * @param returnCode
     * @param message
     * @param exceptionCls
     */
    private static <E extends BaseException> void throwException(BaseEnum returnCode, String message,
                                                                 Class<E> exceptionCls) {
        if (null == exceptionCls) {
            throw new BaseException(returnCode.getCode(), message);
        }

        BaseException cls = null;
        try {
            cls = exceptionCls.newInstance();
        } catch (InstantiationException e1) {
            log.warn("构建异常失败,Class:{}", exceptionCls, e1.getMessage());
        } catch (IllegalAccessException e1) {
            log.warn("构建异常失败,Class:{}", exceptionCls, e1.getMessage());
        }

        cls.setReturnCode(returnCode.getCode());
        cls.setReturnMsg(message);
        throw cls;
    }
}
