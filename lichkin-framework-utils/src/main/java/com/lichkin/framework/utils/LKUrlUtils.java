package com.lichkin.framework.utils;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * URL工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKUrlUtils {

	/**
	 * 解析BASE64或URL并创建图片
	 * @param newThread 是否开启线程创建图片
	 * @param base64OrUrl 图片BASE64编码或URL
	 * @param fileServerRootUrl 文件服务器URL根路径
	 * @param fileSaveRootPath 文件服务器保存根路径
	 * @param fileSaveSubPath 文件服务器保存子路径
	 * @return URL 相对地址
	 */
	public static String analysisBase64ImageUrl(boolean newThread, final String base64OrUrl, String fileServerRootUrl, String fileSaveRootPath, String fileSaveSubPath) {
		// 空值直接返回空值
		if (StringUtils.isBlank(base64OrUrl)) {
			return null;
		}

		// 如果以文件服务器URL根路径开头，转为相对路径。
		if (base64OrUrl.startsWith(fileServerRootUrl)) {
			return base64OrUrl.replace(fileServerRootUrl, "");
		}

		// 如果以/开头，则为相对路径，直接返回。
		if (base64OrUrl.startsWith("/")) {
			return base64OrUrl;
		}

		// 其它情况视为BASE64编码，转文件存储路径并创建图片。
		final String filePath = LKFileUtils.createFilePath(fileSaveRootPath + fileSaveSubPath, ".jpeg");

		if (newThread) {
			// 开启线程执行文件的创建
			new Thread(new Runnable() {
				@Override
				public void run() {
					LKImageUtils.base64ToImage(base64OrUrl, filePath);
				}
			}).start();
		} else {
			// 不开启线程执行文件的创建
			LKImageUtils.base64ToImage(base64OrUrl, filePath);
		}

		// 返回相对地址
		return filePath.replace(fileSaveRootPath, "");
	}

}
