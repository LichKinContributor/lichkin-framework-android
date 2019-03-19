package com.lichkin.framework.utils.security.otp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.lichkin.framework.utils.LKStringUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * OTP编码工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@SuppressWarnings("resource")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKOTPEncrypter {

	/** 间隔时间（毫秒） */
	private static final long GAP_MILLISECONDS = 600000;

	/** 秘钥 */
	private static String KEY = "LichKin2014";
	static {
		byte[] bytes = new byte[1024];
		try {
			new FileInputStream(new File("/opt/security/OTP")).read(bytes);
			KEY = new String(bytes, "UTF-8");
		} catch (IOException e) {
			// ignore this
			e.printStackTrace();
		}
	}


	/**
	 * 编码
	 * @return 编码
	 */
	public static String encrypt() {
		String hexString = Long.toHexString(new Date().getTime() / GAP_MILLISECONDS).toUpperCase();

		byte[] hexBytes = new BigInteger("10" + LKStringUtils.fillZero(hexString, 16, true), 16).toByteArray();
		byte[] bytes = new byte[hexBytes.length - 1];
		System.arraycopy(hexBytes, 1, bytes, 0, bytes.length);

		byte[] finalBytes = null;
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(new SecretKeySpec(KEY.getBytes(), "AES"));
			finalBytes = mac.doFinal(bytes);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			// ignore this
		}

		int offset = finalBytes[finalBytes.length - 1] & 0xf;
		int binary = ((finalBytes[offset] & 0x7f) << 24) | ((finalBytes[offset + 1] & 0xff) << 16) | ((finalBytes[offset + 2] & 0xff) << 8) | (finalBytes[offset + 3] & 0xff);
		int otp = binary % (int) Math.pow(10, 8);

		return LKStringUtils.fillZero(Integer.toString(otp), 8, true);
	}

}
