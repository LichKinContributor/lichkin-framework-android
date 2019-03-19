package com.lichkin.framework.defines.exceptions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.lichkin.framework.defines.LKConfigStatics;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;

import lombok.Getter;

/**
 * 运行时异常
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
public class LKRuntimeException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = -1111111111111110L;

	/** 错误编码 */
	private LKCodeEnum errorCode = LKErrorCodesEnum.INTERNAL_SERVER_ERROR;

	/** 国际化类型 */
	private Locale locale;

	/** 替换参数 */
	private Map<String, Object> params;


	/**
	 * 构造方法
	 * @param errorCode 错误编码
	 * @param locale 国际化类型
	 * @param cause 异常对象
	 */
	public LKRuntimeException(LKCodeEnum errorCode, Locale locale, Throwable cause) {
		super(String.format("errorCode -> %s", errorCode.toString()), cause);
		this.errorCode = errorCode;
		this.locale = locale;
	}


	/**
	 * 构造方法
	 * @param errorCode 错误编码
	 * @param cause 异常对象
	 */
	public LKRuntimeException(LKCodeEnum errorCode, Throwable cause) {
		this(errorCode, LKConfigStatics.DEFAULT_LOCALE, cause);
	}


	/**
	 * 构造方法
	 * @param errorCode 错误编码
	 * @param locale 国际化类型
	 */
	public LKRuntimeException(LKCodeEnum errorCode, Locale locale) {
		super(String.format("errorCode -> %s", errorCode.toString()));
		this.errorCode = errorCode;
		this.locale = locale;
	}


	/**
	 * 构造方法
	 * @param errorCode 错误编码
	 */
	public LKRuntimeException(LKCodeEnum errorCode) {
		this(errorCode, LKConfigStatics.DEFAULT_LOCALE);
	}


	/**
	 * 构造方法
	 * @param key 键
	 * @param value 值
	 * @return 当前对象
	 */
	public LKRuntimeException withParam(String key, Object value) {
		if (params == null) {
			params = new HashMap<>();
		}
		params.put(key, value);
		return this;
	}

}
