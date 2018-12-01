package com.github.java.common.base;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseResp<T> extends Printable implements Serializable {

    private static final long   serialVersionUID = -6333627171476575637L;
    private static final Logger log              = LoggerFactory.getLogger(BaseResp.class);

    /**
     * 成功
     */
    private static final int    successCode      = 10000;

    /**
     * 失败
     */
    private static final int    failCode         = 99999;

    /**
     * 返回code
     */
    private Integer             returnCode       = failCode;                               //默认失败

    /**
     * 返回code 描述
     */
    private String              returnMsg;

    private T                   data;

    public static <E extends BaseResp> E buildFailResp(String msg, Class<E> clazz) {

        return buildBaseResp(failCode, msg, clazz);
    }

    public static <E extends BaseResp> E buildFailResp(BaseEnum code, String msg, Class<E> clazz) {

        return buildBaseResp(code.getCode(), String.format("%s,%s", code.getDesc(), msg), clazz);
    }

    /**
     * 已废弃 by wangzhifeng 2018 -7- 12
     */
    //    public static <E extends BaseResp> E buildFailResp(ReturnCodeEnum code, String msg, Class<E> clazz) {
    //
    //        return buildBaseResp(code.getCode(), String.format("%s,%s", code.getDesc(), msg), clazz);
    //    }

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

    public void fail(Throwable t) {
        if (t instanceof BaseException) {
            BaseException e = (BaseException) t;
            this.setReturnCode(e.getReturnCode());
            this.setReturnMsg(e.getReturnMsg());
        } else {
            this.setReturnCode(failCode);
            this.setReturnMsg(t.getMessage());
        }
    }

    /**
     * 构建成功结果
     *
     * @return
     */
    public static <E extends BaseResp> E buildSuccessResp(Class<E> clazz) {
        return buildBaseResp(successCode, "成功", clazz);
    }
    

    /**
     * 构建成功结果
     *
     * @return
     */
    public static BaseResp buildSuccessBaseResp() {
        BaseResp resp = new BaseResp();
        resp.setReturnCode(successCode);
        resp.setReturnMsg("成功");
        return resp;
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
            // 废弃  e = buildFailResp(ReturnCodeEnum.FAIL, "系统异常", clazz);
            e = buildBaseResp(failCode, "系统异常", clazz);
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
        return successCode == returnCode;
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
