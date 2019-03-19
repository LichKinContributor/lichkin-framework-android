package com.lichkin.framework.utils;

import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.LKPairEnum;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 枚举工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKEnumUtils {

	/**
	 * 获取枚举
	 * @param <T> 枚举类型的泛型
	 * @param clazz 枚举类型
	 * @param value 枚举值
	 * @return 枚举
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getEnum(final Class<T> clazz, final String value) {
		final Object[] enumValues = clazz.getEnumConstants();
		for (final Object enumValue : enumValues) {
			if ((enumValue != null) && enumValue.toString().equals(value)) {
				return (T) enumValue;
			}
		}
		return null;
	}


	/**
	 * 获取枚举
	 * @param <T> 枚举类型的泛型
	 * @param clazz 枚举类型
	 * @param code 枚举编码值
	 * @return 枚举
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getEnumByCode(final Class<? extends LKCodeEnum> clazz, final Integer code) {
		final Object[] enumValues = clazz.getEnumConstants();
		for (final Object enumValue : enumValues) {
			if ((enumValue != null) && ((LKCodeEnum) enumValue).getCode().equals(code)) {
				return (T) enumValue;
			}
		}
		return null;
	}


	/**
	 * 获取枚举
	 * @param <T> 枚举类型的泛型
	 * @param clazz 枚举类型
	 * @param name 枚举名称值
	 * @return 枚举
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getEnumByName(final Class<? extends LKPairEnum> clazz, final String name) {
		final Object[] enumValues = clazz.getEnumConstants();
		for (final Object enumValue : enumValues) {
			if ((enumValue != null) && ((LKPairEnum) enumValue).getName().equals(name)) {
				return (T) enumValue;
			}
		}
		return null;
	}


	/**
	 * 获取枚举编码
	 * @param clazz 枚举类型
	 * @param value 枚举值
	 * @return 枚举编码
	 */
	public static Integer getEnumCode(final Class<? extends LKCodeEnum> clazz, final String value) {
		final LKCodeEnum e = getEnum(clazz, value);
		if (e != null) {
			return e.getCode();
		}
		return null;
	}


	/**
	 * 获取枚举中文名称
	 * @param clazz 枚举类型
	 * @param value 枚举值
	 * @return 枚举中文名称
	 */
	public static String getEnumName(final Class<? extends LKPairEnum> clazz, final String value) {
		final LKPairEnum e = getEnum(clazz, value);
		if (e != null) {
			return e.getName();
		}
		return null;
	}

}
