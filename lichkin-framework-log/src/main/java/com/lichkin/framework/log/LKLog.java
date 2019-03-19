package com.lichkin.framework.log;

import org.apache.commons.logging.Log;

/**
 * 日志接口定义
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface LKLog extends Log {

	/**
	 * 格式化日志
	 * @param formats 日志格式
	 * @param contents 日志内容
	 * @return 格式化后的日志
	 */
	public String format(String formats, String... contents);


	/**
	 * 输出日志
	 * @param formats 日志格式
	 * @param contents 日志内容
	 */
	public void trace(String formats, String... contents);


	/**
	 * 输出日志
	 * @param formats 日志格式
	 * @param contents 日志内容
	 */
	public void debug(String formats, String... contents);


	/**
	 * 输出日志
	 * @param formats 日志格式
	 * @param contents 日志内容
	 */
	public void info(String formats, String... contents);


	/**
	 * 输出日志
	 * @param formats 日志格式
	 * @param contents 日志内容
	 */
	public void warn(String formats, String... contents);


	/**
	 * 输出日志
	 * @param formats 日志格式
	 * @param contents 日志内容
	 */
	public void error(String formats, String... contents);


	/**
	 * 输出日志
	 * @param formats 日志格式
	 * @param contents 日志内容
	 */
	public void fatal(String formats, String... contents);

}