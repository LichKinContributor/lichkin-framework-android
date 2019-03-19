package com.lichkin.framework.defines.beans;

/**
 * 分页接口
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface LKPageable {

	public Integer getPageNumber();


	public void setPageNumber(Integer pageNumber);


	public Integer getPageSize();


	public void setPageSize(Integer pageSize);

}
