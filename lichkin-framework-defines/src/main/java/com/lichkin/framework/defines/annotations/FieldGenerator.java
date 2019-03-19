package com.lichkin.framework.defines.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 字段生成配置
 * </pre>
 *
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldGenerator {

	/**
	 * 新增类型
	 * @return 新增类型枚举
	 */
	InsertType insertType() default InsertType.COPY_COPY;


	/**
	 * 修改类型
	 * @return 修改类型枚举
	 */
	boolean updateable() default true;


	/**
	 * 是否作为校验字段
	 * @return true:作为校验字段;false:不作为校验字段;
	 */
	boolean check() default false;


	/**
	 * 是否为字典值
	 * @return true:字典值;false:非字典值;
	 */
	boolean dictionary() default false;


	/**
	 * 是否作为查询条件字段
	 * @return true:作为查询条件字段;false:不作为查询条件字段;
	 */
	boolean queryCondition() default false;


	/**
	 * 作为查询条件字段是否为模糊匹配
	 * @return true:模糊匹配;false:精确匹配;
	 */
	boolean queryConditionLike() default true;


	/**
	 * 是否作为查询结果字段
	 * @return true:作为查询结果字段;false:不作为查询结果字段;
	 */
	boolean resultColumn() default false;

}
