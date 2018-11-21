package com.github.java.common.base;


/**
 * 分页查询返回
 * @date 2018年11月9日 下午3:18:41
 */
public class BasePageResponse extends BaseResp {

	private static final long serialVersionUID = -196999743657420138L;

	/**
     * 开始条数（原样返回）
     */
    private Integer           offSet;

    /**
     * 本次查询条数（原样返回）
     */
    private Integer           length;
    
    /**
     * 总数据条数
     */
    private Integer           totalRowNum;

    public Integer getTotalRowNum() {
        return totalRowNum;
    }

    public void setTotalRowNum(Integer totalRowNum) {
        this.totalRowNum = totalRowNum;
    }

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
