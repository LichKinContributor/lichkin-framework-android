package com.lichkin.framework.utils.security.base64;

import java.util.Base64;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Base64解码工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKBase64Decoder {

	/**
	 * Base64解码
	 * @param base64 base64字符串
	 * @return 字节数组
	 */
	public static byte[] decode(String base64) {
		return Base64.getDecoder().decode(base64);
	}

}
