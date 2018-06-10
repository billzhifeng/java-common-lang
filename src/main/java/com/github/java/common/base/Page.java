package com.github.java.common.base;

import java.io.Serializable;

/**
 * 分页信息
 */
public abstract class Page extends Printable implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页面,请求参数
     */
    private int               currentPageNo    = 1;

    /**
     * 每页条数,请求参数
     */
    private int               pageSize         = 10;

    /**
     * 总条数 返回结果
     */
    private int               totalCountNum;

    /**
     * 总页数 返回结果
     */
    private int               totalPageNum;

    public int getStartIndex() {
        currentPageNo = currentPageNo > 0 ? currentPageNo : 1;
        int startIndex = (currentPageNo - 1) * pageSize;
        return startIndex;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCountNum() {
        return totalCountNum;
    }

    public void setTotalCountNum(int totalCountNum) {
        this.totalCountNum = totalCountNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

}
