package com.lichkin.framework.utils.security.rsa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;

/**
 * RSA读取工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKRSAReader {

	/**
	 * 从文件中读取RSA私钥/公钥
	 * @param pk true:私钥;false:公钥;
	 * @param fileName 文件名
	 * @param lineBreak 是否换行
	 * @param withComments 是否带注释
	 * @return RSA私钥/公钥
	 */
	private static String readKey(boolean pk, String fileName, boolean lineBreak, boolean withComments) {
		StringBuilder sb = new StringBuilder();
		try {
			@Cleanup
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			if (withComments) {
				String begin = pk ? "-----BEGIN PRIVATE KEY-----" : "-----BEGIN PUBLIC KEY-----";
				String end = pk ? "-----END PRIVATE KEY-----" : "-----END PUBLIC KEY-----";
				while (begin.equals(br.readLine())) {
					for (String line = br.readLine(); !end.equals(line); line = br.readLine()) {
						sb.append(line);
						if (lineBreak) {
							sb.append("\n");
						}
					}
					break;
				}
			} else {
				for (String line = br.readLine(); line != null; line = br.readLine()) {
					sb.append(line);
					if (lineBreak) {
						sb.append("\n");
					}
				}
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new LKRuntimeException(LKErrorCodesEnum.CONFIG_ERROR);
		}

	}


	/**
	 * 从文件中读取RSA私钥
	 * @param fileName 文件名
	 * @param lineBreak 是否换行
	 * @param withComments 是否带注释
	 * @return RSA私钥
	 */
	public static String readPrimaryKey(String fileName, boolean lineBreak, boolean withComments) {
		return readKey(true, fileName, lineBreak, withComments);
	}


	/**
	 * 从文件中读取RSA私钥
	 * @param fileName 文件名
	 * @return RSA私钥
	 */
	public static String readPrimaryKey(String fileName) {
		return readKey(true, fileName, false, true);
	}


	/**
	 * 从文件中读取RSA公钥
	 * @param fileName 文件名
	 * @param lineBreak 是否换行
	 * @param withComments 是否带注释
	 * @return RSA公钥
	 */
	public static String readPublicKey(String fileName, boolean lineBreak, boolean withComments) {
		return readKey(false, fileName, lineBreak, withComments);
	}


	/**
	 * 从文件中读取RSA公钥
	 * @param fileName 文件名
	 * @return RSA公钥
	 */
	public static String readPublicKey(String fileName) {
		return readKey(false, fileName, false, true);
	}

}
