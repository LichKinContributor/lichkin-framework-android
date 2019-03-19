package com.lichkin.framework.defines.annotations;

/**
 * 新增校验类型枚举
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public enum InsertCheckType {

	/**
	 * 不校验冲突数据。
	 * <pre>
	 * 只走新增逻辑
	 * </pre>
	 */
	UNCHECK,

	/**
	 * 校验冲突。
	 * <pre>
	 * 1、无冲突数据时，走新增逻辑。
	 * 2、有多条数据冲突时，抛异常。
	 * 3、有且仅有一条满足条件的数据冲突时，走还原逻辑。
	 * </pre>
	 */
	CHECK_RESTORE,

	/**
	 * 校验冲突。
	 * <pre>
	 * 1、无冲突数据时，走新增逻辑。
	 * 2、有冲突数据时，抛异常。
	 * </pre>
	 */
	FORCE_CHECK,

}
