package com.lichkin.framework.defines.enums.impl;

import com.lichkin.framework.defines.enums.LKEnum;

/**
 * 日期时间类型枚举
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public enum LKDateTimeTypeEnum implements LKEnum {

	/** SSS */
	MILLISECOND,

	/** ss */
	SECOND,

	/** mm */
	MINUTE,

	/** HH */
	HOUR,

	/** dd */
	DAY,

	/** MM */
	MONTH,

	/** yyyy */
	YEAR,

	/** yyyy-MM-dd HH:mm:ss.SSS */
	TIMESTAMP,

	/** yyyyMMddHHmmssSSS */
	TIMESTAMP_MIN,

	/** yyyy-MM-dd HH:mm:ss */
	STANDARD,

	/** yyyyMMddHHmmss */
	STANDARD_MIN,

	/** yyyy-MM-dd HH:mm */
	yyyyMMddHHmm,

	/** yyyyMMddHHmm */
	yyyyMMddHHmm_MIN,

	/** yyyy-MM-dd HH */
	yyyyMMddHH,

	/** yyyyMMddHH */
	yyyyMMddHH_MIN,

	/** yyyy-MM-dd */
	DATE_ONLY,

	/** yyyyMMdd */
	DATE_ONLY_MIN,

	/** yyyy-MM */
	yyyyMM,

	/** yyyyMM */
	yyyyMM_MIN,

	/** HH:mm:ss.SSS */
	TIME_MILLISECOND,

	/** HHmmssSSS */
	TIME_MILLISECOND_MIN,

	/** HH:mm:ss */
	TIME_ONLY,

	/** HHmmss */
	TIME_ONLY_MIN,

	/** HH:mm */
	HHmm,

	/** HHmm */
	HHmm_MIN;

}
