package com.lichkin.framework.utils;

import java.math.BigInteger;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 十六进制工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKHexUtils {

	// ===============================================十进制转换为十六进制=============================================== //
	/**
	 * 十进制数字转换为十六进制字符串
	 * @param decimal 十进制数字
	 * @return 十六进制字符串
	 */
	public static String toHexFromDecimal(final String decimal) {
		return new BigInteger(decimal).toString(16);
	}


	/**
	 * 十进制数字转换为十六进制字符串
	 * @param bytes 字节数组
	 * @return 十六进制字符串
	 */
	public static String toHexFromBinaryData(final byte[] bytes) {
		return new BigInteger(bytes).toString(16);
	}


	/**
	 * 十进制数字转换为十六进制字符串
	 * @param decimal 十进制数字
	 * @return 十六进制字符串
	 */
	public static String toHexFromBigInteger(final BigInteger decimal) {
		return decimal.toString(16);
	}


	/**
	 * 十进制数字转换为十六进制字符串
	 * @param decimal 十进制数字
	 * @return 十六进制字符串
	 */
	public static String toHexFromLong(final long decimal) {
		return Long.toHexString(decimal);
	}


	/**
	 * 十进制数字转换为十六进制字符串
	 * @param decimal 十进制数字
	 * @return 十六进制字符串
	 */
	public static String toHexFromInteger(final int decimal) {
		return Integer.toHexString(decimal);
	}


	/**
	 * 十进制数字转换为十六进制字符串
	 * @param decimal 十进制数字
	 * @return 十六进制字符串
	 */
	public static String toHexFromShort(final short decimal) {
		return Integer.toHexString(decimal);
	}


	/**
	 * 十进制数字转换为十六进制字符串
	 * @param decimal 十进制数字
	 * @return 十六进制字符串
	 */
	public static String toHexFromByte(final byte decimal) {
		return Integer.toHexString(decimal);
	}


	// ===============================================十进制转换为十六进制=============================================== //

	/**
	 * 不足位补0
	 * @param hex 十六进制表示的字符串
	 * @return 十六进制字符串（字节表示，即两位十六进制表示一个字节）
	 */
	private static String fillZero(final String hex) {
		if ((hex == null) || "".equals(hex)) {
			return hex;
		}
		if ((hex.length() % 2) != 0) {
			return "0" + hex;
		}
		return hex;
	}


	/**
	 * 十进制数字转换为十六进制字符串（字节表示，即两位十六进制表示一个字节）
	 * @param decimal 十进制数字
	 * @return 十六进制字符串
	 */
	public static String toHexBytesFromDecimal(final String decimal) {
		return toHexBytesFromBinaryData(new BigInteger(decimal).toByteArray());
	}


	/**
	 * 十进制数字转换为十六进制字符串（字节表示，即两位十六进制表示一个字节）
	 * @param bytes 字节数组
	 * @return 十六进制字符串
	 */
	public static String toHexBytesFromBinaryData(final byte[] bytes) {
		final StringBuilder sb = new StringBuilder();
		for (final byte b : bytes) {
			sb.append(toHexBytesFromByte(b));
		}
		return sb.toString();
	}


	/**
	 * 十进制数字转换为十六进制字符串（字节表示，即两位十六进制表示一个字节）
	 * @param decimal 十进制数字
	 * @return 十六进制字符串
	 */
	public static String toHexBytesFromBigInteger(final BigInteger decimal) {
		return toHexBytesFromBinaryData(decimal.toByteArray());
	}


	/**
	 * 十进制数字转换为十六进制字符串（字节表示，即两位十六进制表示一个字节）
	 * @param decimal 十进制数字
	 * @return 十六进制字符串
	 */
	public static String toHexBytesFromLong(final long decimal) {
		return toHexBytesFromBinaryData(new BigInteger(String.valueOf(decimal)).toByteArray());
	}


	/**
	 * 十进制数字转换为十六进制字符串（字节表示，即两位十六进制表示一个字节）
	 * @param decimal 十进制数字
	 * @return 十六进制字符串
	 */
	public static String toHexBytesFromInteger(final int decimal) {
		return toHexBytesFromBinaryData(new BigInteger(String.valueOf(decimal)).toByteArray());
	}


	/**
	 * 十进制数字转换为十六进制字符串（字节表示，即两位十六进制表示一个字节）
	 * @param decimal 十进制数字
	 * @return 十六进制字符串
	 */
	public static String toHexBytesFromShort(final short decimal) {
		return toHexBytesFromBinaryData(new BigInteger(String.valueOf(decimal)).toByteArray());
	}


	/**
	 * 十进制数字转换为十六进制字符串（字节表示，即两位十六进制表示一个字节）
	 * @param decimal 十进制数字
	 * @return 十六进制字符串
	 */
	public static String toHexBytesFromByte(final byte decimal) {
		final StringBuilder sb = new StringBuilder();
		int x = decimal;
		if (x < 0) {
			x += 256;
		}
		if (x < 16) {
			sb.append("0");
		}
		sb.append(Integer.toHexString(x));
		return sb.toString();
	}


	// ===============================================十六进制转换为十进制=============================================== //

	/**
	 * 十六进制字符串转换为十进制数字
	 * @param hex 十六进制字符串
	 * @return 十进制数字
	 */
	public static String toDecimalFromHex(final String hex) {
		return toBigIntegerFromHex(hex).toString();
	}


	/**
	 * 十六进制字符串转换为十进制数字
	 * @param hex 十六进制字符串
	 * @return 十进制数字
	 */
	public static BigInteger toBigIntegerFromHex(final String hex) {
		return new BigInteger(hex, 16);
	}


	/**
	 * 十六进制字符串转换为十进制数字
	 * @param hex 十六进制字符串
	 * @return 十进制数字
	 */
	public static long toLongFromHex(final String hex) {
		return toBigIntegerFromHex(hex).longValue();
	}


	/**
	 * 十六进制字符串转换为十进制数字
	 * @param hex 十六进制字符串
	 * @return 十进制数字
	 */
	public static int toIntegerFromHex(final String hex) {
		return toBigIntegerFromHex(hex).intValue();
	}


	/**
	 * 十六进制字符串转换为十进制数字
	 * @param hex 十六进制字符串
	 * @return 十进制数字
	 */
	public static short toShortFromHex(final String hex) {
		return toBigIntegerFromHex(hex).shortValue();
	}


	/**
	 * 十六进制字符串转换为十进制数字
	 * @param hex 十六进制字符串
	 * @return 十进制数字
	 */
	public static byte toByteFromHex(final String hex) {
		return toBigIntegerFromHex(hex).byteValue();
	}


	// ===============================================转换为字节数组=============================================== //

	/**
	 * 转换为字节数组
	 * @param hex 十六进制字符串
	 * @return 字节数组
	 */
	public static byte[] toBytesFromHex(String hex) {
		hex = fillZero(hex);
		final byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0, start = 0; i < bytes.length; i++, start = i * 2) {
			bytes[i] = toByteFromHex(hex.substring(start, start + 2));
		}
		return bytes;
	}


	/**
	 * 转换为字节数组
	 * @param decimal 十进制字符串
	 * @return 字节数组
	 */
	public static byte[] toBytesFromDecimal(final String decimal) {
		return toBytesFromHex(toHexFromDecimal(decimal));
	}


	/**
	 * 转换为字节数组
	 * @param decimal 数字
	 * @return 字节数组
	 */
	public static byte[] toBytesFromBigInteger(final BigInteger decimal) {
		return toBytesFromHex(toHexFromBigInteger(decimal));
	}


	/**
	 * 转换为字节数组
	 * @param decimal 数字
	 * @return 字节数组
	 */
	public static byte[] toBytesFromLong(final long decimal) {
		return toBytesFromHex(toHexFromLong(decimal));
	}


	/**
	 * 转换为字节数组
	 * @param decimal 数字
	 * @return 字节数组
	 */
	public static byte[] toBytesFromInteger(final int decimal) {
		return toBytesFromHex(toHexFromInteger(decimal));
	}


	/**
	 * 转换为字节数组
	 * @param decimal 数字
	 * @return 字节数组
	 */
	public static byte[] toBytesFromShort(final short decimal) {
		return toBytesFromHex(toHexFromShort(decimal));
	}


	/**
	 * 转换为字节数组
	 * @param decimal 数字
	 * @return 字节数组
	 */
	public static byte[] toBytesFromByte(final byte decimal) {
		return toBytesFromHex(toHexFromByte(decimal));
	}

}
