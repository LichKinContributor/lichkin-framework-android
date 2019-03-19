package com.lichkin.framework.defines.beans.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 树形结构对象
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class LKTreeBean {

	/** 主键 */
	private final String id;

	/** 名字 */
	private final String name;

	/** 编码 */
	private final String code;

	/** 上级编码 */
	private final String parentCode;

	/** 参数 */
	private Map<String, Object> params = new HashMap<>();

	/** 下级 */
	private List<LKTreeBean> children = new ArrayList<>();


	/**
	 * 增加参数
	 * @param key 键
	 * @param value 值
	 */
	public void addParam(String key, Object value) {
		params.put(key, value);
	}


	/**
	 * 增加下级
	 * @param bean 对象
	 */
	public void addChild(LKTreeBean bean) {
		children.add(bean);
	}

}
