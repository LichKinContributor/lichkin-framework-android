package com.lichkin.framework.utils;

import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.lichkin.framework.defines.enums.impl.LKDateTimeTypeEnum;
import com.lichkin.framework.utils.i18n.LKI18NReader4DateTimeType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 日期时间工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKDateTimeUtils {

	/**
	 * 获取当前时间字符串
	 * @param locale 国际化类型
	 * @param dateTimeType 日期时间类型
	 * @return 字符串
	 */
	public static String now(Locale locale, LKDateTimeTypeEnum dateTimeType) {
		return DateTime.now().toString(LKI18NReader4DateTimeType.read(locale, dateTimeType));
	}


	/**
	 * 获取当前时间字符串
	 * @param dateTimeType 日期时间类型
	 * @return 字符串
	 */
	public static String now(LKDateTimeTypeEnum dateTimeType) {
		return now(Locale.ENGLISH, dateTimeType);
	}


	/**
	 * 获取当前时间字符串
	 * @param locale 国际化类型
	 * @return 字符串
	 */
	public static String now(Locale locale) {
		return now(locale, LKDateTimeTypeEnum.TIMESTAMP_MIN);
	}


	/**
	 * 获取当前时间字符串
	 * @return 字符串
	 */
	public static String now() {
		return now(Locale.ENGLISH, LKDateTimeTypeEnum.TIMESTAMP_MIN);
	}


	/**
	 * 获取时间字符串
	 * @param dateTime 日期时间
	 * @param locale 国际化类型
	 * @param dateTimeType 日期时间类型
	 * @return 字符串
	 */
	public static String toString(DateTime dateTime, Locale locale, LKDateTimeTypeEnum dateTimeType) {
		return dateTime.toString(LKI18NReader4DateTimeType.read(locale, dateTimeType));
	}


	/**
	 * 获取时间字符串
	 * @param dateTime 日期时间
	 * @param dateTimeType 日期时间类型
	 * @return 字符串
	 */
	public static String toString(DateTime dateTime, LKDateTimeTypeEnum dateTimeType) {
		return toString(dateTime, Locale.ENGLISH, dateTimeType);
	}


	/**
	 * 获取时间字符串
	 * @param dateTime 日期时间
	 * @param locale 国际化类型
	 * @return 字符串
	 */
	public static String toString(DateTime dateTime, Locale locale) {
		return toString(dateTime, locale, LKDateTimeTypeEnum.TIMESTAMP_MIN);
	}


	/**
	 * 获取时间字符串
	 * @param dateTime 日期时间
	 * @return 字符串
	 */
	public static String toString(DateTime dateTime) {
		return toString(dateTime, Locale.ENGLISH, LKDateTimeTypeEnum.TIMESTAMP_MIN);
	}


	/**
	 * 获取时间字符串
	 * @param dateTime 日期时间
	 * @param locale 国际化类型
	 * @param dateTimeType 日期时间类型
	 * @return 字符串
	 */
	public static String toString(Date dateTime, Locale locale, LKDateTimeTypeEnum dateTimeType) {
		return new DateTime(dateTime).toString(LKI18NReader4DateTimeType.read(locale, dateTimeType));
	}


	/**
	 * 获取时间字符串
	 * @param dateTime 日期时间
	 * @param dateTimeType 日期时间类型
	 * @return 字符串
	 */
	public static String toString(Date dateTime, LKDateTimeTypeEnum dateTimeType) {
		return toString(dateTime, Locale.ENGLISH, dateTimeType);
	}


	/**
	 * 获取时间字符串
	 * @param dateTime 日期时间
	 * @param locale 国际化类型
	 * @return 字符串
	 */
	public static String toString(Date dateTime, Locale locale) {
		return toString(dateTime, locale, LKDateTimeTypeEnum.TIMESTAMP_MIN);
	}


	/**
	 * 获取时间字符串
	 * @param dateTime 日期时间
	 * @return 字符串
	 */
	public static String toString(Date dateTime) {
		return toString(dateTime, Locale.ENGLISH, LKDateTimeTypeEnum.TIMESTAMP_MIN);
	}


	/**
	 * 获取时间字符串
	 * @param dateTime 日期时间
	 * @param locale 国际化类型
	 * @param dateTimeType 日期时间类型
	 * @return 字符串
	 */
	public static String toString(java.sql.Date dateTime, Locale locale, LKDateTimeTypeEnum dateTimeType) {
		return new DateTime(dateTime).toString(LKI18NReader4DateTimeType.read(locale, dateTimeType));
	}


	/**
	 * 获取时间字符串
	 * @param dateTime 日期时间
	 * @param dateTimeType 日期时间类型
	 * @return 字符串
	 */
	public static String toString(java.sql.Date dateTime, LKDateTimeTypeEnum dateTimeType) {
		return toString(dateTime, Locale.ENGLISH, dateTimeType);
	}


	/**
	 * 获取时间字符串
	 * @param dateTime 日期时间
	 * @param locale 国际化类型
	 * @return 字符串
	 */
	public static String toString(java.sql.Date dateTime, Locale locale) {
		return toString(dateTime, locale, LKDateTimeTypeEnum.TIMESTAMP_MIN);
	}


	/**
	 * 获取时间字符串
	 * @param dateTime 日期时间
	 * @return 字符串
	 */
	public static String toString(java.sql.Date dateTime) {
		return toString(dateTime, Locale.ENGLISH, LKDateTimeTypeEnum.TIMESTAMP_MIN);
	}


	/**
	 * 获取时间
	 * @param dateTime 日期时间字符串
	 * @param locale 国际化类型
	 * @param dateTimeType 日期时间类型
	 * @return 日期时间
	 */
	public static DateTime toDateTime(String dateTime, Locale locale, LKDateTimeTypeEnum dateTimeType) {
		return DateTime.parse(dateTime, DateTimeFormat.forPattern(LKI18NReader4DateTimeType.read(locale, dateTimeType)));
	}


	/**
	 * 获取时间
	 * @param dateTime 日期时间字符串
	 * @param dateTimeType 日期时间类型
	 * @return 日期时间
	 */
	public static DateTime toDateTime(String dateTime, LKDateTimeTypeEnum dateTimeType) {
		return toDateTime(dateTime, Locale.ENGLISH, dateTimeType);
	}


	/**
	 * 获取时间
	 * @param dateTime 日期时间字符串
	 * @param locale 国际化类型
	 * @return 日期时间
	 */
	public static DateTime toDateTime(String dateTime, Locale locale) {
		return toDateTime(dateTime, locale, LKDateTimeTypeEnum.TIMESTAMP_MIN);
	}


	/**
	 * 获取时间
	 * @param dateTime 日期时间字符串
	 * @return 日期时间
	 */
	public static DateTime toDateTime(String dateTime) {
		return toDateTime(dateTime, Locale.ENGLISH, LKDateTimeTypeEnum.TIMESTAMP_MIN);
	}


	/**
	 * 获取时间
	 * @param dateTime 日期时间字符串
	 * @param locale 国际化类型
	 * @param dateTimeType 日期时间类型
	 * @return 日期时间
	 */
	public static Date toDate(String dateTime, Locale locale, LKDateTimeTypeEnum dateTimeType) {
		return new Date(toDateTime(dateTime, locale, dateTimeType).getMillis());
	}


	/**
	 * 获取时间
	 * @param dateTime 日期时间字符串
	 * @param dateTimeType 日期时间类型
	 * @return 日期时间
	 */
	public static Date toDate(String dateTime, LKDateTimeTypeEnum dateTimeType) {
		return toDate(dateTime, Locale.ENGLISH, dateTimeType);
	}


	/**
	 * 获取时间
	 * @param dateTime 日期时间字符串
	 * @param locale 国际化类型
	 * @return 日期时间
	 */
	public static Date toDate(String dateTime, Locale locale) {
		return toDate(dateTime, locale, LKDateTimeTypeEnum.TIMESTAMP_MIN);
	}


	/**
	 * 获取时间
	 * @param dateTime 日期时间字符串
	 * @return 日期时间
	 */
	public static Date toDate(String dateTime) {
		return toDate(dateTime, Locale.ENGLISH, LKDateTimeTypeEnum.TIMESTAMP_MIN);
	}


	/**
	 * 获取时间
	 * @param dateTime 日期时间字符串
	 * @param locale 国际化类型
	 * @param dateTimeType 日期时间类型
	 * @return 日期时间
	 */
	public static java.sql.Date toSqlDate(String dateTime, Locale locale, LKDateTimeTypeEnum dateTimeType) {
		return new java.sql.Date(toDateTime(dateTime, locale, dateTimeType).getMillis());
	}


	/**
	 * 获取时间
	 * @param dateTime 日期时间字符串
	 * @param dateTimeType 日期时间类型
	 * @return 日期时间
	 */
	public static java.sql.Date toSqlDate(String dateTime, LKDateTimeTypeEnum dateTimeType) {
		return toSqlDate(dateTime, Locale.ENGLISH, dateTimeType);
	}


	/**
	 * 获取时间
	 * @param dateTime 日期时间字符串
	 * @param locale 国际化类型
	 * @return 日期时间
	 */
	public static java.sql.Date toSqlDate(String dateTime, Locale locale) {
		return toSqlDate(dateTime, locale, LKDateTimeTypeEnum.TIMESTAMP_MIN);
	}


	/**
	 * 获取时间
	 * @param dateTime 日期时间字符串
	 * @return 日期时间
	 */
	public static java.sql.Date toSqlDate(String dateTime) {
		return toSqlDate(dateTime, Locale.ENGLISH, LKDateTimeTypeEnum.TIMESTAMP_MIN);
	}

}
