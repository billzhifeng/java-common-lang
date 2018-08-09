package com.github.java.common.base;

/**
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 4540164817117468586L;

    private int               returnCode;
    private String            returnMsg;

    public BaseException() {
    }

    public BaseException(int code, String msg) {
        this.returnCode = code;
        this.returnMsg = msg;
    }

    public BaseException(BaseEnum code, String msg) {
        this.returnCode = code.getCode();
        this.returnMsg = msg;
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
}
