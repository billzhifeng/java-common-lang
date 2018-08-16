package com.github.java.common.base;

import java.io.Serializable;

/**
 */
public class BaseReq extends Printable implements Serializable {
    /**
     * 请求开始 不等于-1及启用分页
     */
    private int offSet = -1;

    /**
     * 请求条数
     */
    private int length = 10;

    public int getOffSet() {
        return offSet;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
