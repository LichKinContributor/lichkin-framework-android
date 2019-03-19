package com.lichkin.framework.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 匹配工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKMatcherUtils {

	/**
	 * 获取匹配对象
	 * @param regex 正则表达式
	 * @param flags 匹配标志
	 * @param input 待匹配字符串
	 * @return 匹配对象
	 */
	public static Matcher getMatcher(final String regex, int flags, final CharSequence input) {
		return Pattern.compile(regex, flags).matcher(input);
	}


	/**
	 * 获取匹配对象
	 * @param regex 正则表达式
	 * @param input 待匹配字符串
	 * @return 匹配对象
	 */
	public static Matcher getMatcher(final String regex, final CharSequence input) {
		return getMatcher(regex, 0, input);
	}


	/**
	 * 匹配字符串
	 * @param regex 正则表达式
	 * @param flags 匹配标志
	 * @param input 待匹配字符串
	 * @return 匹配成功返回true，否则返回false。
	 */
	public static boolean matches(final String regex, int flags, final CharSequence input) {
		return getMatcher(regex, flags, input).matches();
	}


	/**
	 * 匹配字符串
	 * @param regex 正则表达式
	 * @param input 待匹配字符串
	 * @return 匹配成功返回true，否则返回false。
	 */
	public static boolean matches(final String regex, final CharSequence input) {
		return getMatcher(regex, 0, input).matches();
	}


	/**
	 * 获取匹配字符串
	 * @param regex 正则表达式
	 * @param flags 匹配标志
	 * @param input 待匹配字符串
	 * @return 匹配成功返回true，否则返回false。
	 */
	public static String getString(final String regex, int flags, final CharSequence input) {
		Matcher matcher = getMatcher(regex, flags, input);
		while (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}


	/**
	 * 获取匹配字符串
	 * @param regex 正则表达式
	 * @param input 待匹配字符串
	 * @return 匹配成功返回true，否则返回false。
	 */
	public static String getString(final String regex, final CharSequence input) {
		return getString(regex, 0, input);
	}


	/**
	 * 获取匹配字符串
	 * @param regex 正则表达式
	 * @param flags 匹配标志
	 * @param input 待匹配字符串
	 * @return 匹配成功返回true，否则返回false。
	 */
	public static List<String> getList(final String regex, int flags, final CharSequence input) {
		List<String> list = new ArrayList<>();
		Matcher matcher = getMatcher(regex, flags, input);
		while (matcher.find()) {
			list.add(matcher.group());
		}
		return list;
	}


	/**
	 * 获取匹配字符串
	 * @param regex 正则表达式
	 * @param input 待匹配字符串
	 * @return 匹配成功返回true，否则返回false。
	 */
	public static List<String> getList(final String regex, final CharSequence input) {
		return getList(regex, 0, input);
	}

}
