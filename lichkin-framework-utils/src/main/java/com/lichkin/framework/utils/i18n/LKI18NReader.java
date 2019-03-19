package com.lichkin.framework.utils.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 国际化文件读取工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LKI18NReader {

	/** 缓存 */
	private final static Map<Locale, Map<String, Map<String, String>>> locales = new HashMap<>();


	/**
	 * 获取值
	 * @param locale 国际化类型
	 * @param folderName 文件夹名
	 * @param key 键
	 * @param params 替换参数
	 * @return 值
	 */
	protected static String read(Locale locale, String folderName, String key, Map<String, Object> params) {
		Map<String, Map<String, String>> files = locales.get(locale);
		String value = null;
		if (files == null) {
			value = LKI18NUtils.getString(locale, folderName, key);
			if (params != null) {
				value = replaceParams(value, params);
			} else {
				files = new HashMap<>();
				Map<String, String> props = new HashMap<>();
				props.put(key, value);
				files.put(folderName, props);
				locales.put(locale, files);
			}
		} else {
			Map<String, String> props = files.get(folderName);
			if (props == null) {
				value = LKI18NUtils.getString(locale, folderName, key);
				if (params != null) {
					value = replaceParams(value, params);
				} else {
					props = new HashMap<>();

					props.put(key, value);
					files.put(folderName, props);
				}
			} else {
				value = props.get(key);
				if (value == null) {
					value = LKI18NUtils.getString(locale, folderName, key);
					if (params != null) {
						value = replaceParams(value, params);
					} else {
						props.put(key, value);
					}
				} else {
					if (params != null) {
						value = replaceParams(value, params);
					}
				}
			}
		}
		return value;
	}


	/**
	 * 替换参数
	 * @param value 原值
	 * @param params 参数
	 * @return 替换后的值
	 */
	private static String replaceParams(String value, Map<String, Object> params) {
		for (Entry<String, Object> param : params.entrySet()) {
			value = value.replaceAll(param.getKey(), (param.getValue() == null) ? "" : param.getValue().toString());
		}
		return value;
	}

}
