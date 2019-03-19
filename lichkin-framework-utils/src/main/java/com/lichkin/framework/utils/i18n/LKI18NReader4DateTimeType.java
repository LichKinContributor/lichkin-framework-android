package com.lichkin.framework.utils.i18n;

import java.util.Locale;

import com.lichkin.framework.defines.enums.impl.LKDateTimeTypeEnum;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 日期时间类型读取工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKI18NReader4DateTimeType extends LKI18NReader {

	/**
	 * 读取配置值
	 * @param dateTimeType 日期时间类型
	 * @param locale 国际化类型
	 * @return 配置值
	 */
	private static String read(LKDateTimeTypeEnum dateTimeType, Locale locale) {
		return read(locale, "dateTimeType", dateTimeType.toString(), null);
	}


	/**
	 * 读取配置值
	 * @param locale 国际化类型
	 * @param dateTimeType 日期时间类型
	 * @return 配置值
	 */
	public static String read(Locale locale, LKDateTimeTypeEnum dateTimeType) {
		return read(dateTimeType, locale);
	}

}
