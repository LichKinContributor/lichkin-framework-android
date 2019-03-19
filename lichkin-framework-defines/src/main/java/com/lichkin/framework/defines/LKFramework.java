package com.lichkin.framework.defines;

import com.lichkin.framework.log.LKLog;
import com.lichkin.framework.log.LKLogFactory;

/**
 * 框架类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class LKFramework {

	/** 日志对象 */
	protected final LKLog logger = LKLogFactory.getLog(getClass());

	/** 系统编码 */
	protected String $systemTag = LKConfigStatics.SYSTEM_TAG;

}
