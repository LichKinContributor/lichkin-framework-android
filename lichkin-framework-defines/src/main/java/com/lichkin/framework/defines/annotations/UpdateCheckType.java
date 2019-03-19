package com.lichkin.framework.defines.annotations;

/**
 * 新增校验类型枚举
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public enum UpdateCheckType {

	/**
	 * 不校验冲突数据。
	 * <pre>
	 * 只走更新逻辑
	 * </pre>
	 */
	UNCHECK,

	/**
	 * 校验冲突。
	 * <pre>
	 * 1、无冲突数据时，走更新逻辑。
	 * 2、有冲突数据时，抛异常。
	 * </pre>
	 */
	CHECK,

	/**
	 * 校验冲突。
	 * <pre>
	 * 自定义实现校验逻辑
	 * </pre>
	 */
	BUS_CHECK,

}
