package com.lichkin.framework.defines.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lichkin.framework.defines.enums.impl.LKDateTimeTypeEnum;

/**
 * <pre>
 * 目标类在字段上增加该注解，并配置转换类型，将进行对应的转换。
 * 目标字段类型为String
 * 源字段类型为java.util.Date、java.sql.Date、org.joda.time.DateTime
 * </pre>
 *
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateToString {

	LKDateTimeTypeEnum value() default LKDateTimeTypeEnum.TIMESTAMP_MIN;

}
