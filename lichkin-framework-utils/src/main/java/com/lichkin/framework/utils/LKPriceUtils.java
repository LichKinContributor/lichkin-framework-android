package com.lichkin.framework.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 金额工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKPriceUtils {

	/**
	 * 解析金额（转换为标准的0.00形式）
	 * @param price 金额
	 * @return 金额
	 */
	public static String analysisPrice(String price) {
		if (StringUtils.isBlank(price)) {
			return "0.00";
		}
		return new BigDecimal(price).setScale(2, RoundingMode.HALF_UP).toString();
	}

}
