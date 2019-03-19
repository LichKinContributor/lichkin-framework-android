package com.lichkin.framework.utils;

import java.io.File;

import org.joda.time.DateTime;

import com.lichkin.framework.defines.enums.impl.LKDateTimeTypeEnum;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 文件工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKFileUtils {

	/**
	 * 创建文件路径
	 * @param baseUrl 文件存储目录
	 * @param extName 文件扩展名
	 * @return 文件全名（相对地址）
	 */
	public static String createFilePath(String baseUrl, String extName) {
		String dirPath = baseUrl + DateTime.now().toString("/yyyy/yyyyMM/yyyyMMdd/");
		File file = new File(dirPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return dirPath + LKDateTimeUtils.now(LKDateTimeTypeEnum.TIMESTAMP_MIN) + LKRandomUtils.create(10) + extName;
	}

}
