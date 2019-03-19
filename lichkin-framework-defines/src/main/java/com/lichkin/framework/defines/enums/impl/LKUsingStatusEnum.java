package com.lichkin.framework.defines.enums.impl;

import com.lichkin.framework.defines.enums.LKEnum;

/**
 * 在用状态枚举
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public enum LKUsingStatusEnum implements LKEnum {

	/** 删除 */
	DEPRECATED,

	/** 待用 */
	STAND_BY,

	/** 在用 */
	USING,

	/** 停用 */
	DISABLE,

	/** 锁定 */
	LOCKED;

}
