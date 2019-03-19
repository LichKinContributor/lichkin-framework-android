package com.lichkin.framework.defines.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 目标类在字段上增加该注解，当源字段值为null时取注解中配置的默认值。
 * 目标字段类型为String
 * 源字段类型为String
 * </pre>
 *
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultStringValue {

	/** 调用方法取默认值前缀 */
	public static final String START = "!!!";

	/** 调用方法取默认值分隔符 */
	public static final String SPLITOR = "#";

	/** 令牌默认值 */
	public static final String TOKEN = START + "com.lichkin.framework.utils.LKRandomUtils" + SPLITOR + "create64";

	/** GMT开始时间 */
	public static final String GMT_START = "19700101000000000";


	String value() default "";

}
