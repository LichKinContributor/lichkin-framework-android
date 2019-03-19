package com.lichkin.framework.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 数组工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKArrayUtils {

	/**
	 * 转换成字符串
	 * @param arr 数组
	 * @return 字符串
	 */
	public static String toString(Object[] arr) {
		StringBuilder sb = new StringBuilder();
		int length = arr.length - 1;
		for (int i = 0; i <= length; i++) {
			Object obj = arr[i];
			sb.append(obj == null ? "" : obj.toString());
			if (i != length) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

}
