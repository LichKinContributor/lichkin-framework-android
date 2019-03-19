package com.lichkin.framework.log.log4j2;

import java.util.Locale;

import org.apache.commons.logging.Log;

import com.lichkin.framework.log.LKLog;

/**
 * log4j2日志实现类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKLog4j2LogImpl implements LKLog {

	/** 日志对象 */
	private final Log logger;


	/**
	 * 构造方法
	 * @param logger 日志对象
	 */
	public LKLog4j2LogImpl(Log logger) {
		this.logger = logger;
	}


	@Override
	public String format(String formats, String... contents) {
		if ((contents == null) || (contents.length == 0)) {
			return formats;
		}
		return String.format(Locale.getDefault(Locale.Category.FORMAT), formats, (Object[]) contents);
	}


	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}


	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}


	@Override
	public boolean isFatalEnabled() {
		return logger.isFatalEnabled();
	}


	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}


	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}


	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}


	@Override
	public void trace(Object message) {
		logger.trace(message);
	}


	@Override
	public void trace(Object message, Throwable t) {
		logger.trace(message, t);
	}


	@Override
	public void trace(String formats, String... contents) {
		logger.trace(format(formats, contents));
	}


	@Override
	public void debug(Object message) {
		logger.debug(message);
	}


	@Override
	public void debug(Object message, Throwable t) {
		logger.debug(message, t);
	}


	@Override
	public void debug(String formats, String... contents) {
		logger.debug(format(formats, contents));
	}


	@Override
	public void info(Object message) {
		logger.info(message);
	}


	@Override
	public void info(Object message, Throwable t) {
		logger.info(message, t);
	}


	@Override
	public void info(String formats, String... contents) {
		logger.info(format(formats, contents));
	}


	@Override
	public void warn(Object message) {
		logger.warn(message);
	}


	@Override
	public void warn(Object message, Throwable t) {
		logger.warn(message, t);
	}


	@Override
	public void warn(String formats, String... contents) {
		logger.warn(format(formats, contents));
	}


	@Override
	public void error(Object message) {
		logger.error(message);
	}


	@Override
	public void error(Object message, Throwable t) {
		logger.error(message, t);
	}


	@Override
	public void error(String formats, String... contents) {
		logger.error(format(formats, contents));
	}


	@Override
	public void fatal(Object message) {
		logger.fatal(message);
	}


	@Override
	public void fatal(Object message, Throwable t) {
		logger.fatal(message, t);
	}


	@Override
	public void fatal(String formats, String... contents) {
		logger.fatal(format(formats, contents));
	}

}
