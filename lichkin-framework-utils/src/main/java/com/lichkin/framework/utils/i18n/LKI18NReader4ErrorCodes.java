package com.lichkin.framework.utils.i18n;

import java.util.Locale;
import java.util.Map;

import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 错误编码读取工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class LKI18NReader4ErrorCodes extends LKI18NReader {

	/**
	 * 读取配置值
	 * @param errorCode 错误编码
	 * @param locale 国际化类型
	 * @param params 替换参数
	 * @return 配置值
	 */
	private static String read(LKCodeEnum errorCode, Locale locale, Map<String, Object> params) {
		Integer code = errorCode.getCode();
		if (code == null) {
			return read(LKErrorCodesEnum.PARAM_ERROR, locale, params) + " [" + errorCode.toString() + "]";
		}
		return read(locale, analysisFolderName(code), errorCode.toString(), params);
	}


	/**
	 * 解析目录名
	 * @param code 错误编码
	 * @return 目录名
	 */
	private static String analysisFolderName(int code) {
		if (code < 0) {// 负数为框架内部使用
			return "errorCodes";
		}

		int section = code / 10000;// 每一万一个段
		switch (section) {
			case 0:// 0-9999之间为框架内部使用
				return "errorCodes";
			case 1:// 10000-19999之间为具体项目使用编码
				return "app-errorCodes";
			default:// 其它段为projects框架使用
				// projects-core -> 20000+ -> errorCodes-20000
				// projects-activiti -> 30000+ -> errorCodes-30000
				// projects-app -> 40000+ -> errorCodes-40000
				// projects-attendance -> 50000+ -> errorCodes-50000
				// projects-pss -> 60000+ -> errorCodes-60000
				// projects-alipay -> 70000+ -> errorCodes-70000
				// projects-wechatpay -> 80000+ -> errorCodes-80000
				// projects-cashier-desk -> 90000+ -> errorCodes-90000
				return "errorCodes-" + (section * 10000);
		}
	}


	/**
	 * 读取配置值
	 * @param locale 国际化类型
	 * @param errorCode 错误编码
	 * @param params 替换参数
	 * @return 配置值
	 */
	public static String read(Locale locale, LKCodeEnum errorCode, Map<String, Object> params) {
		return read(errorCode, locale, params);
	}


	/**
	 * 读取配置值
	 * @param locale 国际化类型
	 * @param errorCode 错误编码
	 * @return 配置值
	 */
	public static String read(Locale locale, LKCodeEnum errorCode) {
		return read(errorCode, locale, null);
	}


	/**
	 * 获取值
	 * @param locale 国际化类型
	 * @param key 键
	 * @param params 替换参数
	 * @return 值
	 */
	public static Object read(Locale locale, String key, Map<String, Object> params) {
		return read(locale, "app-errorCodes", key, params);
	}


	/**
	 * 获取值
	 * @param locale 国际化类型
	 * @param key 键
	 * @return 值
	 */
	public static Object read(Locale locale, String key) {
		return read(locale, "app-errorCodes", key, null);
	}

}
