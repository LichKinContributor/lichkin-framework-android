package com.lichkin.framework.defines.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 目标类在字段上增加该注解，当源字段值为null时取注解中配置的默认值。
 * 目标字段类型为Integer
 * 源字段类型为Integer
 * </pre>
 *
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultIntegerValue {

	int value() default 0;

}
