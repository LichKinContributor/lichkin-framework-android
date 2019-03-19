package com.lichkin.framework.log;

import org.apache.commons.logging.LogFactory;

import com.lichkin.framework.log.log4j2.LKLog4j2LogImpl;

/**
 * 日志对象分析工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKLogFactory {

	/**
	 * 构造方法
	 */
	private LKLogFactory() {
	}


	/**
	 * 获取日志对象
	 * @param clazz 日志对象持有的类型
	 * @return 日志对象
	 */
	public static LKLog getLog(Class<?> clazz) {
		return new LKLog4j2LogImpl(LogFactory.getLog(clazz));
	}

}
