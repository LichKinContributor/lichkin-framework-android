package com.lichkin.framework.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;

import com.lichkin.framework.defines.enums.impl.LKDateTimeTypeEnum;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 日历工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKCalendarUtils {

	/**
	 * 根据月份获取所属季度
	 * @param month 月份(1-12)
	 * @return 所属季度
	 */
	public static int getQuarterByMonth(int month) {
		if ((month >= 1) && (month <= 3)) {
			return 1;
		}
		if ((month >= 4) && (month <= 6)) {
			return 2;
		}
		if ((month >= 7) && (month <= 9)) {
			return 3;
		}
		if ((month >= 10) && (month <= 12)) {
			return 4;
		}
		return 0;
	}


	/**
	 * 判断工作日
	 * @param date 日期
	 * @param offdays 休息日（默认为周六、周日）
	 * @return true:工作日;false:休息日.
	 */
	public static boolean isWorkDay(Date date, int... offdays) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayofWeek = c.get(Calendar.DAY_OF_WEEK);
		if (ArrayUtils.isEmpty(offdays)) {
			offdays = new int[] { Calendar.SATURDAY, Calendar.SUNDAY };
		}
		for (int offday : offdays) {
			if (dayofWeek == offday) {
				return false;
			}
		}
		return true;
	}


	/**
	 * 判断休息日
	 * @param date 日期
	 * @param workdays 工作日（默认为周一、周二、周三、周四、周五）
	 * @return true:休息日;false:工作日.
	 */
	public static boolean isOffDay(Date date, int... workdays) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayofWeek = c.get(Calendar.DAY_OF_WEEK);
		if (ArrayUtils.isEmpty(workdays)) {
			workdays = new int[] { Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY };
		}
		for (int workday : workdays) {
			if (dayofWeek == workday) {
				return false;
			}
		}
		return true;
	}


	/**
	 * 本年度的周数
	 * @param date 日期
	 * @param firstDayOfWeek 周的第一天
	 * @return 所属周
	 */
	public static int weekOfYear(Date date, int firstDayOfWeek) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(firstDayOfWeek);
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}


	/**
	 * 本年度的周数
	 * @param date 日期
	 * @return 所属周
	 */
	public static int weekOfYear(Date date) {
		return weekOfYear(date, Calendar.MONDAY);
	}


	/**
	 * 获取某周的第一天
	 * @param year 年
	 * @param week 周
	 * @param firstDayOfWeek 周的第一天
	 * @return 周的第一天
	 */
	public static String getFirstDayOfWeek(int year, int week, int firstDayOfWeek) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return LKDateTimeUtils.toString(c.getTime(), Locale.ENGLISH, LKDateTimeTypeEnum.DATE_ONLY);
	}


	/**
	 * 获取某周的第一天
	 * @param year 年
	 * @param week 周
	 * @return 周的第一天
	 */
	public static String getFirstDayOfWeek(int year, int week) {
		return getFirstDayOfWeek(year, week, Calendar.MONDAY);
	}


	/**
	 * 获取某周的最后一天
	 * @param year 年
	 * @param week 周
	 * @param firstDayOfWeek 周的第一天
	 * @return 周的最后一天
	 */
	public static String getLastDayOfWeek(int year, int week, int firstDayOfWeek) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, firstDayOfWeek + 6);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return LKDateTimeUtils.toString(c.getTime(), Locale.ENGLISH, LKDateTimeTypeEnum.DATE_ONLY);
	}


	/**
	 * 获取某周的最后一天
	 * @param year 年
	 * @param week 周
	 * @return 周的最后一天
	 */
	public static String getLastDayOfWeek(int year, int week) {
		return getLastDayOfWeek(year, week, Calendar.MONDAY);
	}


	/**
	 * 获取某月的第一天
	 * @param year 年
	 * @param month 月份(1-12)
	 * @return 月的第一天
	 */
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		if (year == -1) {
			year = c.get(Calendar.YEAR);
		}
		if (month == -1) {
			month = c.get(Calendar.MONTH) + 1;
		}
		c.set(year, month - 1, 1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return LKDateTimeUtils.toString(c.getTime(), Locale.ENGLISH, LKDateTimeTypeEnum.DATE_ONLY);
	}


	/**
	 * 获取某月的最后一天
	 * @param year 年
	 * @param month 月份(1-12)
	 * @return 月的最后一天
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		if (year == -1) {
			year = c.get(Calendar.YEAR);
		}
		if (month == -1) {
			month = c.get(Calendar.MONTH) + 1;
		}
		c.set(year, month - 1, 1);
		c.roll(Calendar.DATE, -1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return LKDateTimeUtils.toString(c.getTime(), Locale.ENGLISH, LKDateTimeTypeEnum.DATE_ONLY);
	}


	/**
	 * 获取当月的第一天
	 * @return 月的第一天
	 */
	public static String getFirstDayOfMonth() {
		return getFirstDayOfMonth(-1, -1);
	}


	/**
	 * 获取当月的最后一天
	 * @return 月的最后一天
	 */
	public static String getLastDayOfMonth() {
		return getLastDayOfMonth(-1, -1);
	}

}
