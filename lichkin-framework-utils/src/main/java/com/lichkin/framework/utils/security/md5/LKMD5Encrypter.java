package com.lichkin.framework.utils.security.md5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.lichkin.framework.utils.LKHexUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * MD5加密工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKMD5Encrypter {

	/** MD对象 */
	private static MessageDigest md5;

	static {
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (final NoSuchAlgorithmException e) {
			// ignore this
		}
	}


	/**
	 * 加密
	 * @param plaintext 明文
	 * @return 密文
	 */
	public static String encrypt(final String plaintext) {
		return encrypt(plaintext, "");
	}


	/**
	 * 加密
	 * @param plaintext 明文
	 * @param key 辅助加密
	 * @return 密文
	 */
	public static String encrypt(final String plaintext, final String key) {
		try {
			md5.update((plaintext + key).getBytes("UTF-8"));
		} catch (final UnsupportedEncodingException e) {
		}
		final byte[] bytes = md5.digest();
		return LKHexUtils.toHexBytesFromBinaryData(bytes);
	}

}
