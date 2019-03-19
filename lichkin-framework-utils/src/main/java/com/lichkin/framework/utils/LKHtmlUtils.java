package com.lichkin.framework.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * HTML工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKHtmlUtils {

	private static final String IMG = "<img[^>]*?>";

	private static final String SRC = "src\\s*=\\s*\"?(.*?)(\"|>|\\s+)";


	/**
	 * 获取IMG标签
	 * @param html HTML文本
	 * @return IMG标签
	 */
	public static List<String> extractImgTag(String html) {
		return LKMatcherUtils.getList(IMG, Pattern.CASE_INSENSITIVE, html);
	}


	/**
	 * 获取IMG标签中的SRC值
	 * @param html HTML文本
	 * @return IMG标签中的SRC值
	 */
	public static List<String> extractImgTag_src(String html) {
		List<String> imgTag = extractImgTag(html);
		if (CollectionUtils.isEmpty(imgTag)) {
			return Collections.emptyList();
		}

		List<String> src = new ArrayList<>(imgTag.size());
		for (String tag : imgTag) {
			String result = LKMatcherUtils.getString(SRC, Pattern.CASE_INSENSITIVE, tag);
			if (StringUtils.isNotBlank(result)) {
				src.add(result);
			}
		}
		return src;
	}


	/**
	 * 替换IMG标签中的SRC值
	 * @param html HTML文本
	 * @param fileServerRootUrl 文件服务器URL根路径
	 * @return 替换后的HTML文本
	 */
	public static String replaceImgTag_src(String html, String fileServerRootUrl) {
		return html.replaceAll("<img src=\"/", "<img src=\"" + fileServerRootUrl + "/");
	}

}
