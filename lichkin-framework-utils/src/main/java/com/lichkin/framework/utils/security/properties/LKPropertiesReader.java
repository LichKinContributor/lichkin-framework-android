package com.lichkin.framework.utils.security.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;

/**
 * 属性文件读取工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKPropertiesReader {

	/** 内置缓存对象 */
	private static final Map<String, Properties> cache = new HashMap<>();


	/**
	 * 读取属性文件
	 * @param fileName 文件名
	 * @return 属性
	 */
	public static Properties read(String fileName) {
		if (cache.containsKey(fileName)) {
			return cache.get(fileName);
		}
		try {
			@Cleanup
			InputStream br = new FileInputStream(fileName);
			Properties properties = new Properties();
			properties.load(br);
			cache.put(fileName, properties);
			return properties;
		} catch (IOException e) {
			e.printStackTrace();
			throw new LKRuntimeException(LKErrorCodesEnum.CONFIG_ERROR);
		}
	}

}
