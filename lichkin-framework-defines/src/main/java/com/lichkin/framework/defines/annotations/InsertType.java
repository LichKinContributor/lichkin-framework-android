package com.lichkin.framework.defines.annotations;

/**
 * 新增类型枚举
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public enum InsertType {

	/*
	 * 取默认值的，入参中将忽略该字段。
	 */
	/** 不需要入参。新增时：取默认值；还原时：取默认值； */
	DEFAULT_DEFAULT,

	/** 不需要入参。新增时：取默认值；还原时：保留原值； */
	DEFAULT_RETAIN,

	/*
	 * 从入参复制的，入参中将加入该字段。
	 */
	/** 需要入参。新增时：从入参复制；还原时：从入参复制； */
	COPY_COPY,

	/** 需要入参。新增时：从入参复制；还原时：保留原值； */
	COPY_RETAIN,

	/** 需要入参。新增时：从入参复制；还原时：入参与原值不同抛异常； */
	COPY_ERROR,

	/*
	 * 转换参数的，入参中将加入该字段。
	 */
	/** 需要入参。新增时：转换参数；还原时：保留原值； */
	CHANGE_RETAIN,

	/** 需要入参。新增时：转换参数；还原时：特殊处理； */
	CHANGE_HANDLE,

	/*
	 * 特殊处理的，入参中将忽略该字段。
	 */
	/** 不需要入参。新增时：特殊处理；还原时：保留原值； */
	HANDLE_RETAIN,

	/** 不需要入参。新增时：特殊处理；还原时：特殊处理； */
	HANDLE_HANDLE,

}
