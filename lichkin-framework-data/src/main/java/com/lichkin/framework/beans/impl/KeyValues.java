package com.lichkin.framework.beans.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.exceptions.LKFrameworkException;

import lombok.NoArgsConstructor;

/**
 * 键值对封装对象
 * <pre>
 *   键为null或空将报错
 *   值为null或空将不实际存储值
 * </pre>
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor
public class KeyValues {

	/** 内置集合对象 */
	protected Map<String, Object> map = new HashMap<>();


	/**
	 * 构造方法
	 * @param key 键
	 * @param value 值
	 */
	public KeyValues(String key, String value) {
		putString(key, value);
	}


	/**
	 * 校验键
	 * @param key 键
	 * @return 键
	 */
	protected String checkKey(String key) {
		// 键不能为null或空
		if (StringUtils.isBlank(key)) {
			throw new LKFrameworkException("key can not be blank.");
		}

		// 校验通过
		return key;
	}


	/**
	 * 校验值
	 * @param value 值
	 * @return 值
	 */
	protected Object checkValue(Object value) {
		// 值不能为null或空
		if (value == null) {
			throw new LKFrameworkException("value can not be null.");
		}

		if ((value instanceof String) && StringUtils.isBlank((String) value)) {
			throw new LKFrameworkException("String value can not be blank.");
		}

		// 校验通过
		return value;
	}


	/**
	 * 设置值
	 * <pre>
	 *   没有存储信息，将存储信息。
	 *   有存储信息，将替换信息。
	 * </pre>
	 * @param key 键
	 * @param value 值
	 * @return 本对象
	 */
	public KeyValues putString(String key, String value) {
		// 校验键
		key = checkKey(key);

		// 没有存储信息，将存储信息。
		try {
			map.put(key, checkValue(value));
		} catch (Exception e) {
			// 校验值出错时，将不存储信息。
		}

		return this;
	}


	/**
	 * 追加值
	 * <pre>
	 *   没有存储信息，将存储信息。
	 *   有存储信息，将追加信息。标准分隔符追加。
	 * </pre>
	 * @param key 键
	 * @param value 值
	 * @return 本对象
	 */
	public KeyValues appendString(String key, String value) {
		// 校验键
		key = checkKey(key);

		// 没有存储信息，将存储信息。
		if (!map.containsKey(key)) {
			return putString(key, value);
		}

		String val = getString(key);
		// 未取到值，说明该值原本并非为字符串类型，不能进行追加。
		if (val == null) {
			throw new LKFrameworkException("saved value is not a String value, can not use append.");
		}
		putString(key, val + LKFrameworkStatics.SPLITOR + value);

		return this;
	}


	/**
	 * 获取值
	 * @param key 键
	 * @return 值
	 */
	public String getString(String key) {
		return getString(key, null, false);
	}


	/**
	 * 获取值
	 * @param key 键
	 * @param defaultValue 默认值。无存储信息时的默认值。
	 * @param convert 非字符串值是否做转换处理。true:转换;false:返回defaultValue;
	 * @return 值
	 */
	public String getString(String key, String defaultValue, boolean convert) {
		try {
			key = checkKey(key);
		} catch (Exception e) {
			return defaultValue;
		}

		Object value = map.get(key);

		// 无存储信息返回默认值
		if (value == null) {
			return defaultValue;
		}

		// 有存储信息，但非String类型
		if (!(value instanceof String)) {
			// 需要转换，返回转换值。
			if (convert) {
				return String.valueOf(value);
			}
			// 不需要转换，返回默认值。
			return defaultValue;
		}

		// 有存储信息返回值
		return (String) value;
	}


	/**
	 * 设置值
	 * <pre>
	 *   没有存储信息，将存储信息。
	 *   有存储信息，将替换信息。
	 * </pre>
	 * @param key 键
	 * @param value 值
	 * @return 本对象
	 */
	public KeyValues putObject(String key, Object value) {
		// 校验键
		key = checkKey(key);

		// 没有存储信息，将存储信息。
		try {
			map.put(key, checkValue(value));
		} catch (Exception e) {
			// 校验值出错时，将不存储信息。
		}

		return this;
	}


	/**
	 * 获取值
	 * @param <T> 待取得对象类型
	 * @param key 键
	 * @return 值
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObject(String key) {
		try {
			key = checkKey(key);
		} catch (Exception e) {
			return null;
		}

		Object value = map.get(key);
		if (value == null) {
			return null;
		}

		try {
			return (T) value;
		} catch (Exception e) {
			return null;
		}
	}


	/**
	 * 获取值
	 * @param key 键
	 * @return 值
	 */
	public Integer getInteger(String key) {
		return getInteger(key, null, false);
	}


	/**
	 * 获取值
	 * @param key 键
	 * @param defaultValue 默认值。无存储信息时的默认值。
	 * @param convert 非字符串值是否做转换处理。true:转换;false:返回defaultValue;
	 * @return 值
	 */
	public Integer getInteger(String key, Integer defaultValue, boolean convert) {
		try {
			key = checkKey(key);
		} catch (Exception e) {
			return defaultValue;
		}

		Object value = map.get(key);

		// 无存储信息返回默认值
		if (value == null) {
			return defaultValue;
		}

		// 有存储信息，但非Integer类型
		if (!(value instanceof Integer)) {
			// 需要转换，返回转换值。
			if (convert) {
				try {
					return Integer.parseInt(String.valueOf(value));
				} catch (Exception e) {
					return defaultValue;
				}
			}
			// 不需要转换，返回默认值。
			return defaultValue;
		}

		// 有存储信息返回值
		return (Integer) value;
	}


	/**
	 * 清空数据
	 * @return 本对象
	 */
	public KeyValues clear() {
		map.clear();
		return this;
	}

}
