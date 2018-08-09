package com.github.java.common.base;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 */
@Setter
@Getter
public class PageResp<T> extends BaseResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2742432240143380351L;
    /**
     * 分页内容
     */
    private Page<T>           page;
}
