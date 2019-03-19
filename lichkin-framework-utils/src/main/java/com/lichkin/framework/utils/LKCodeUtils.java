package com.lichkin.framework.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 编码工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKCodeUtils {

	private static final String ROOT = "ROOT";

	/** 编码长度 */
	private static int LENGHT = 7;

	/** 层级编码 */
	private static final String[] strs = new String[] { "A", "B", "C", "D", "E", "F", "G", "H" };


	/**
	 * 补全编码
	 * @param code 编码
	 * @return 编码
	 */
	public static String fillCode(String code) {
		if (code.equalsIgnoreCase(ROOT)) {
			return ROOT;
		}

		final int len = code.length() / (LENGHT + 1);
		for (int i = len; i < strs.length; i++) {
			code += strs[i] + LKStringUtils.fillZero(0, LENGHT);
		}
		return code;
	}


	/**
	 * 当前级别
	 * @param currentCode 编码
	 * @return 级别
	 */
	public static int currentLevel(String currentCode) {
		if (currentCode.equalsIgnoreCase(ROOT)) {
			return 0;
		}

		for (int i = strs.length - 1; i >= 0; i--) {
			final int x = Integer.parseInt(currentCode.substring((i * (LENGHT + 1)) + 1, (i + 1) * (LENGHT + 1)));
			if (x != 0) {
				return i + 1;
			}
		}
		return 1;
	}


	/**
	 * 真实编码
	 * @param currentCode 当前编码
	 * @return 编码
	 */
	public static String realCode(String currentCode) {
		if ((currentCode == null) || currentCode.equalsIgnoreCase(ROOT)) {
			return ROOT;
		}
		for (int i = strs.length - 1; i >= 0; i--) {
			final int x = Integer.parseInt(currentCode.substring((i * (LENGHT + 1)) + 1, (i + 1) * (LENGHT + 1)));
			if (x != 0) {
				return currentCode.substring(0, ((i + 1) * (LENGHT + 1)));
			}
		}
		return null;
	}


	/**
	 * 下一个编码
	 * @param currentCode 当前编码
	 * @return 编码
	 */
	public static String nextCode(String currentCode) {
		int current = 0;
		int level = 0;
		for (int i = strs.length - 1; i >= 0; i--) {
			final int x = Integer.parseInt(currentCode.substring((i * (LENGHT + 1)) + 1, (i + 1) * (LENGHT + 1)));
			if (x != 0) {
				current = x + 1;
				level = i;
				break;
			}
		}
		return fillCode(currentCode.substring(0, (level * (LENGHT + 1)) + 1) + LKStringUtils.fillZero(current, LENGHT));
	}


	/**
	 * 上一个编码
	 * @param currentCode 当前编码
	 * @return 编码
	 */
	public static String prevCode(String currentCode) {
		int current = 0;
		int level = 0;
		for (int i = strs.length - 1; i >= 0; i--) {
			final int x = Integer.parseInt(currentCode.substring((i * (LENGHT + 1)) + 1, (i + 1) * (LENGHT + 1)));
			if (x != 0) {
				current = x - 1;
				level = i;
				break;
			}
		}
		if (current == 0) {
			return null;
		}
		return fillCode(currentCode.substring(0, (level * (LENGHT + 1)) + 1) + LKStringUtils.fillZero(current, LENGHT));
	}


	/**
	 * 创建编码
	 * @param parentCode 上级编码
	 * @return 编码
	 */
	public static String createCode(String parentCode) {
		if (StringUtils.isBlank(parentCode)) {
			parentCode = ROOT;
		}
		String realCode = realCode(parentCode);
		if (parentCode.equalsIgnoreCase(ROOT)) {
			realCode = "";
		}
		final int len = realCode.length() / (LENGHT + 1);
		realCode += strs[len] + LKStringUtils.fillZero(1, LENGHT);
		for (int i = len + 1; i < strs.length; i++) {
			realCode += strs[i] + LKStringUtils.fillZero(0, LENGHT);
		}
		return realCode;
	}


	/**
	 * 创建编码
	 * @param parentCode 上级编码
	 * @param orderId 排序号
	 * @return 编码
	 */
	public static String createCode(String parentCode, int orderId) {
		if (StringUtils.isBlank(parentCode)) {
			parentCode = ROOT;
		}
		String menuCode = createCode(parentCode);
		for (int i = 1; i <= orderId; i++) {
			menuCode = nextCode(menuCode);
		}
		return menuCode;
	}


	/**
	 * 获取当前编码的所有上级编码
	 * @param currentCode 编码
	 * @param withRoot 是否包含ROOT编码
	 * @return 所有上级编码
	 */
	public static List<String> parentsCode(String currentCode, boolean withRoot) {
		if (StringUtils.isBlank(currentCode)) {
			currentCode = ROOT;
		}

		int level = LKCodeUtils.currentLevel(currentCode);
		String realCode = LKCodeUtils.realCode(currentCode);
		List<String> list = new ArrayList<>();
		if (withRoot) {
			list.add(ROOT);
		}
		for (int i = 1; i < level; i++) {
			String parentCode = realCode.substring(0, realCode.length() - (i * (LENGHT + 1)));
			list.add(LKCodeUtils.fillCode(parentCode));
		}
		return list;
	}


	/**
	 * 获取当前编码的所有上级编码
	 * @param codeList 编码List
	 * @param withRoot 是否包含ROOT编码
	 * @return 所有上级编码
	 */
	public static List<String> parentsCode(List<String> codeList, boolean withRoot) {
		if (CollectionUtils.isEmpty(codeList)) {
			return Collections.emptyList();
		}

		Map<String, String> map = new HashMap<>();
		for (String currentCode : codeList) {
			if (!map.containsKey(currentCode)) {
				List<String> parentsCodeList = LKCodeUtils.parentsCode(currentCode, false);
				for (String parentCode : parentsCodeList) {
					map.put(parentCode, null);
				}
			}
		}

		List<String> list = new ArrayList<>(map.keySet());
		if (withRoot) {
			list.add(ROOT);
		}
		return list;
	}

}
