package com.github.test;

import com.github.java.common.base.BaseEnum;

/**
 * @author wanzhifeng
 * @date 2017/11/2916:47
 */
public enum DemoReturnCodeEnum implements BaseEnum {
    //成功返回码
    SUCCESS(1, "成功"),
    FAIL(9, "失败"),

    //业务校验
    REQUEST_ARGUMENTS_ILLEGAL(100, "请求参数异常"),
    DATA_NOT_EXIST(101, "数据不存在"),
    PARAM_VALIDATE_ERROR(102, "参数格式校验错误"),

    //系统异常 3开头 
    SYSTEM_EXCEPTION(300, "系统执行异常"),;

    private int    code;

    private String desc;

    private DemoReturnCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
