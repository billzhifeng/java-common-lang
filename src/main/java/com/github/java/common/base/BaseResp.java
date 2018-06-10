package com.github.java.common.base;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class BaseResp<T> extends Page implements Serializable {

    private static final long   serialVersionUID = -6333627171476575637L;
    private static final Logger log              = LoggerFactory.getLogger(BaseResp.class);
    /**
     * 返回code
     */
    private Integer             returnCode;

    /**
     * 返回code 描述
     */
    private String              returnMsg;

    private T                   data;

    public static <E extends BaseResp> E buildFailResp(String msg, Class<E> clazz) {

        return buildBaseResp(ReturnCodeEnum.FAIL.getCode(), msg, clazz);
    }

    public static <E extends BaseResp> E buildFailResp(BaseEnum code, String msg, Class<E> clazz) {

        return buildBaseResp(code.getCode(), String.format("%s,%s", code.getDesc(), msg), clazz);
    }

    public static <E extends BaseResp> E buildFailResp(ReturnCodeEnum code, String msg, Class<E> clazz) {

        return buildBaseResp(code.getCode(), String.format("%s,%s", code.getDesc(), msg), clazz);
    }

    public static <E extends BaseResp> E buildFailResp(BaseException t, Class<E> clazz) {

        return buildBaseResp(t.getReturnCode(), t.getReturnMsg(), clazz);
    }

    public static <E extends BaseResp> E buildBaseResp(int returnCode, String returnMsg, Class<E> clazz) {
        E e = null;
        try {
            e = clazz.newInstance();
        } catch (InstantiationException e1) {
            log.warn("", e1.getMessage());
        } catch (IllegalAccessException e1) {
            log.warn("", e1.getMessage());
        }
        e.setReturnCode(returnCode);
        e.setReturnMsg(returnMsg);
        return e;
    }

    /**
     * 构建成功结果
     *
     * @return
     */
    public static <E extends BaseResp> E buildSuccessResp(Class<E> clazz) {
        return buildBaseResp(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getDesc(), clazz);
    }

    /**
     * 构建执行失败结果
     *
     * @param t
     * @param funcName
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E extends BaseResp> E buildFailResp(Throwable t, String funcName, Class<E> clazz) {
        E e = null;

        if (t instanceof IllegalArgumentException) {
            e = buildFailResp(ReturnCodeEnum.REQUEST_ARGUMENTS_ILLEGAL, t.getMessage(), clazz);
        } else if (t instanceof BaseException) {

            e = buildFailResp((BaseException) t, clazz);
        } else {

            e = buildFailResp(String.format("%s,%s", funcName, t.getMessage()), clazz);
        }
        return e;
    }

    /**
     * 执行是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return ReturnCodeEnum.SUCCESS.getCode() == returnCode;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public T getData() {
        return data;
    }

    public BaseResp setData(T data) {
        this.data = data;
        return this;
    }

}
