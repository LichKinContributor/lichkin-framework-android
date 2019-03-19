package com.lichkin.framework.defines.beans.impl;

import com.lichkin.framework.defines.beans.LKPageable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LKPageBean implements LKPageable {

	/** 页码 */
	private Integer pageNumber;

	/** 每页数据量 */
	private Integer pageSize;


	/**
	 * 构造方法
	 */
	public LKPageBean() {
		this(0, 25);
	}


	/**
	 * 构造方法
	 * @param pageSize 每页数据量
	 */
	public LKPageBean(Integer pageSize) {
		this(0, pageSize);
	}


	/**
	 * 构造方法
	 * @param pageNumber 页码
	 * @param pageSize 每页数据量
	 */
	public LKPageBean(Integer pageNumber, Integer pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

}
