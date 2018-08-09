package com.github.java.common.base;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页信息
 */
@Setter
@Getter
public class Page<T> extends Printable implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2811019915783107625L;

    /**
     * 请求开始
     */
    private int               offSet           = 1;

    /**
     * 请求条数
     */
    private int               length           = 10;

    /**
     * 总条数 返回结果
     */
    private int               totalCountNum;

    private List<T>           data;

}
