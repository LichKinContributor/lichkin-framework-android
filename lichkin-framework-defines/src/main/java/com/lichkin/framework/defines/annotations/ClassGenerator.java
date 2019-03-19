package com.lichkin.framework.defines.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 类生成配置
 * </pre>
 *
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassGenerator {

	/**
	 * 主表保存后是否需要额外操作
	 * @return true:需要;false:不需要;
	 */
	boolean afterSaveMain();


	/**
	 * 需要在新增/修改主表后操作的子表实体类名数组
	 * @return 子表实体类名数组
	 */
	String[] IUSubTables() default {};


	/**
	 * 新增校验类型
	 * @return 新增校验类型枚举
	 */
	InsertCheckType insertCheckType();


	/**
	 * 新增操作额外字段数组
	 * @return 新增操作额外字段数组
	 */
	String[] insertFields() default {};


	/**
	 * 修改校验类型
	 * @return 修改校验类型枚举
	 */
	UpdateCheckType updateCheckType();


	/**
	 * 修改操作额外字段数组
	 * @return 修改操作额外字段数组
	 */
	String[] updateFields() default {};


	/**
	 * 查询分页操作入参额外字段数组
	 * @return 入参额外字段数组
	 */
	String[] pageQueryConditions() default {};


	/**
	 * 查询分页操作出参额外字段数组
	 * @return 出参额外字段数组
	 */
	String[] pageResultColumns() default {};


	/**
	 * 查询单个数据操作出参额外字段数组
	 * @return 出参额外字段数组
	 */
	String[] oneResultColumns() default {};

}
