package com.github.java.common.base;

import java.io.Serializable;


/**
 * 分页查询条件
 * 
 * @date 2018年11月9日 下午3:11:50
 */
public class BasePageRequest extends Printable implements Serializable {

	private static final long serialVersionUID = -6155583542128238331L;

	/**
     * 开始条数（0开始）
     */
    private Integer           offSet;

    /**
     * 本次查询条数
     */
    private Integer           length;

    public Integer getOffSet() {
        return offSet;
    }

    public void setOffSet(Integer offSet) {
        this.offSet = offSet;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

}
