package com.lichkin.framework.defines.enums.impl;

import com.lichkin.framework.defines.enums.LKEnum;

/**
 * 实名认证等级枚举
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public enum LKAuthenticationEnum implements LKEnum {

	/** 未认证 */
	NO,

	/** 认证中 */
	AUTHENTICATING,

	/** 已实名 */
	AUTHENTICATED,

	/** 已绑卡 */
	CARD_BINDED,

	/** 已传身份证照片 */
	USER_CARD_UPLOADED;

}
