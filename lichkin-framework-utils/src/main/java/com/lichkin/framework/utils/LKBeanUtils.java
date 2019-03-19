package com.lichkin.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;

import com.lichkin.framework.defines.annotations.DateToString;
import com.lichkin.framework.defines.annotations.DefaultBooleanValue;
import com.lichkin.framework.defines.annotations.DefaultByteValue;
import com.lichkin.framework.defines.annotations.DefaultDoubleValue;
import com.lichkin.framework.defines.annotations.DefaultFloatValue;
import com.lichkin.framework.defines.annotations.DefaultIntegerValue;
import com.lichkin.framework.defines.annotations.DefaultLongValue;
import com.lichkin.framework.defines.annotations.DefaultShortValue;
import com.lichkin.framework.defines.annotations.DefaultStringValue;
import com.lichkin.framework.defines.annotations.StringToDate;
import com.lichkin.framework.defines.enums.impl.LKDateTimeTypeEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Bean工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKBeanUtils {

	/**
	 * 复制属性
	 * @param <B> 返回值类型泛型
	 * @param source 源对象
	 * @param target 目标对象
	 * @param excludeFieldNames 排除字段名
	 * @return 目标对象
	 */
	public static <B> B copyProperties(Object source, B target, String... excludeFieldNames) {
		return copyProperties(null, source, target, excludeFieldNames);
	}


	/**
	 * 复制属性
	 * @param <B> 返回值类型泛型
	 * @param type
	 *
	 *            <pre>
	 * null:source字段为null或没有对应到target的字段都将设置为null;
	 * true:source字段为null或没有对应到target的字段都将设置为defaultValue;
	 * false:source字段为null时，设置为null。没有对应到target的字段不处理.
	 *            </pre>
	 *
	 * @param source 源对象
	 * @param target 目标对象
	 * @param excludeFieldNames 排除字段名
	 * @return 目标对象
	 */
	public static <B> B copyProperties(Boolean type, Object source, B target, String... excludeFieldNames) {
		if ((source == null) || (target == null)) {
			return target;
		}

		List<Field> sourceFields = LKFieldUtils.getRealFieldList(source.getClass(), true, excludeFieldNames);
		List<Field> targetFields = LKFieldUtils.getRealFieldList(target.getClass(), true, excludeFieldNames);

		try {
			out: for (Field targetField : targetFields) {
				// 开启访问权限
				if (!targetField.isAccessible()) {
					targetField.setAccessible(true);
				}

				// 获取名称
				String targetName = targetField.getName();

				for (Field sourceField : sourceFields) {
					// 获取名称
					String sourceName = sourceField.getName();

					// 不是同一字段跳过
					if (!sourceName.equals(targetName)) {
						continue;
					}

					// 开启访问权限
					if (!sourceField.isAccessible()) {
						sourceField.setAccessible(true);
					}

					// 获取值
					Object sourceValue = sourceField.get(source);

					// 空值直接设置
					if (sourceValue == null) {
						if (Boolean.TRUE.equals(type)) {
							setDefaultValue(target, targetField);
						} else {
							targetField.set(target, null);
						}
						continue out;
					}

					// 获取类型
					Class<?> sourceType = sourceField.getType();
					Class<?> targetType = targetField.getType();

					// 同样类型直接设置
					if (sourceType.equals(targetType)) {
						targetField.set(target, sourceValue);
						continue out;
					}

					boolean setted = false;
					String sourceFieldClassName = sourceType.getName();
					String targetFieldClassName = targetType.getName();

					switch (targetFieldClassName) {
						case "java.lang.String": {
							switch (sourceFieldClassName) {
								case "java.util.Date": {
									DateToString annotation = targetField.getAnnotation(DateToString.class);
									LKDateTimeTypeEnum dateTimeType = annotation == null ? LKDateTimeTypeEnum.TIMESTAMP_MIN : annotation.value();
									targetField.set(target, LKDateTimeUtils.toString((java.util.Date) sourceValue, dateTimeType));
								}
								break;
								case "java.sql.Date": {
									DateToString annotation = targetField.getAnnotation(DateToString.class);
									LKDateTimeTypeEnum dateTimeType = annotation == null ? LKDateTimeTypeEnum.TIMESTAMP_MIN : annotation.value();
									targetField.set(target, LKDateTimeUtils.toString((java.sql.Date) sourceValue, dateTimeType));
								}
								break;
								case "org.joda.time.DateTime": {
									DateToString annotation = targetField.getAnnotation(DateToString.class);
									LKDateTimeTypeEnum dateTimeType = annotation == null ? LKDateTimeTypeEnum.TIMESTAMP_MIN : annotation.value();
									targetField.set(target, LKDateTimeUtils.toString((DateTime) sourceValue, dateTimeType));
								}
								break;
								default: {
									if (sourceType.isEnum()) {
										targetField.set(target, sourceValue.toString());
									} else {
										targetField.set(target, String.valueOf(sourceValue));
									}
								}
								break;
							}
							setted = true;
						}
						break;
						case "java.util.Date": {
							switch (sourceFieldClassName) {
								case "java.lang.String": {
									StringToDate annotation = targetField.getAnnotation(StringToDate.class);
									LKDateTimeTypeEnum dateTimeType = annotation == null ? LKDateTimeTypeEnum.TIMESTAMP_MIN : annotation.value();
									targetField.set(target, LKDateTimeUtils.toDate((String) sourceValue, dateTimeType));
									setted = true;
								}
								break;
								case "java.sql.Date": {
									targetField.set(target, new java.util.Date(((java.sql.Date) sourceValue).getTime()));
									setted = true;
								}
								break;
								case "org.joda.time.DateTime": {
									targetField.set(target, ((org.joda.time.DateTime) sourceValue).toDate());
									setted = true;
								}
								break;
							}
						}
						break;
						case "java.sql.Date": {
							switch (sourceFieldClassName) {
								case "java.lang.String": {
									StringToDate annotation = targetField.getAnnotation(StringToDate.class);
									LKDateTimeTypeEnum dateTimeType = annotation == null ? LKDateTimeTypeEnum.TIMESTAMP_MIN : annotation.value();
									targetField.set(target, LKDateTimeUtils.toSqlDate((String) sourceValue, dateTimeType));
									setted = true;
								}
								break;
								case "java.util.Date": {
									targetField.set(target, new java.sql.Date(((java.util.Date) sourceValue).getTime()));
									setted = true;
								}
								break;
								case "org.joda.time.DateTime": {
									targetField.set(target, new java.sql.Date(((org.joda.time.DateTime) sourceValue).getMillis()));
									setted = true;
								}
								break;
							}
						}
						break;
						case "org.joda.time.DateTime": {
							switch (sourceFieldClassName) {
								case "java.lang.String": {
									StringToDate annotation = targetField.getAnnotation(StringToDate.class);
									LKDateTimeTypeEnum dateTimeType = annotation == null ? LKDateTimeTypeEnum.TIMESTAMP_MIN : annotation.value();
									targetField.set(target, LKDateTimeUtils.toDateTime((String) sourceValue, dateTimeType));
									setted = true;
								}
								break;
								case "java.util.Date": {
									targetField.set(target, new org.joda.time.DateTime(((java.util.Date) sourceValue).getTime()));
									setted = true;
								}
								break;
								case "java.sql.Date": {
									targetField.set(target, new org.joda.time.DateTime(((java.sql.Date) sourceValue).getTime()));
									setted = true;
								}
								break;
							}
						}
						break;
						default: {
							// 目标类型为枚举且源类型为字符串
							if (targetType.isEnum() && sourceType.equals(String.class)) {
								targetField.set(target, LKEnumUtils.getEnum(targetType, (String) sourceValue));
								setted = true;
							}
						}
						break;
					}

					if (setted) {
						continue out;
					}
				}

				if (Boolean.TRUE.equals(type)) {
					setDefaultValue(target, targetField);
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();// ignore this
		}

		return target;
	}


	/**
	 * 设置默认值
	 * @param target 目标对象
	 * @param targetField 目标字段
	 */
	private static <B> void setDefaultValue(B target, Field targetField) {
		Object sourceValue = null;
		String targetFieldClassName = targetField.getType().getName();
		switch (targetFieldClassName) {
			case "java.lang.Byte": {
				DefaultByteValue annotation = targetField.getAnnotation(DefaultByteValue.class);
				if (annotation != null) {
					sourceValue = annotation.value();
				}
			}
			break;
			case "java.lang.Short": {
				DefaultShortValue annotation = targetField.getAnnotation(DefaultShortValue.class);
				if (annotation != null) {
					sourceValue = annotation.value();
				}
			}
			break;
			case "java.lang.Integer": {
				DefaultIntegerValue annotation = targetField.getAnnotation(DefaultIntegerValue.class);
				if (annotation != null) {
					sourceValue = annotation.value();
				}
			}
			break;
			case "java.lang.Long": {
				DefaultLongValue annotation = targetField.getAnnotation(DefaultLongValue.class);
				if (annotation != null) {
					sourceValue = annotation.value();
				}
			}
			break;
			case "java.lang.Boolean": {
				DefaultBooleanValue annotation = targetField.getAnnotation(DefaultBooleanValue.class);
				if (annotation != null) {
					sourceValue = annotation.value();
				}
			}
			break;
			case "java.lang.Float": {
				DefaultFloatValue annotation = targetField.getAnnotation(DefaultFloatValue.class);
				if (annotation != null) {
					sourceValue = annotation.value();
				}
			}
			break;
			case "java.lang.Double": {
				DefaultDoubleValue annotation = targetField.getAnnotation(DefaultDoubleValue.class);
				if (annotation != null) {
					sourceValue = annotation.value();
				}
			}
			break;
			case "java.lang.String": {
				DefaultStringValue annotation = targetField.getAnnotation(DefaultStringValue.class);
				if (annotation != null) {
					sourceValue = annotation.value();
					String value = (String) sourceValue;
					if (value.startsWith(DefaultStringValue.START)) {
						String[] strs = value.substring(DefaultStringValue.START.length()).split(DefaultStringValue.SPLITOR);
						String className = strs[0];
						String methodName = strs[1];
						try {
							sourceValue = Class.forName(className).getMethod(methodName).invoke(null);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
			break;
		}

		try {
			targetField.set(target, sourceValue);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();// ignore this
		}
	}


	/**
	 * 创建对象
	 * @param <B> 目标对象类型泛型
	 * @param source 源对象
	 * @param targetClass 目标对象类型
	 * @param excludeFieldNames 排除字段名
	 * @return 目标对象
	 */
	public static <B> B newInstance(final Object source, final Class<B> targetClass, final String... excludeFieldNames) {
		return newInstance(null, source, targetClass, excludeFieldNames);
	}


	/**
	 * 创建对象
	 * @param <B> 目标对象类型泛型
	 * @param type
	 *
	 *            <pre>
	 * null:source字段为null或没有对应到target的字段都将设置为null;
	 * true:source字段为null或没有对应到target的字段都将设置为defaultValue;
	 * false:source字段为null时，设置为null。没有对应到target的字段不处理.
	 *            </pre>
	 *
	 * @param source 源对象
	 * @param targetClass 目标对象类型
	 * @param excludeFieldNames 排除字段名
	 * @return 目标对象
	 */
	public static <B> B newInstance(Boolean type, final Object source, final Class<B> targetClass, final String... excludeFieldNames) {
		try {
			return copyProperties(type, source, targetClass.newInstance(), excludeFieldNames);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new LKRuntimeException(LKErrorCodesEnum.INTERNAL_SERVER_ERROR, e);
		}
	}


	/**
	 * 从源对象列表读取内容进行拼接后设置到目标对象中
	 * @param listSource 源对象列表
	 * @param target 目标对象
	 * @param sourceFiledNames 源对象取值字段名
	 * @param targetFiledNames 目标对象设置值字段名
	 * @param splitors 拼接操作分隔符
	 */
	public static void setStringValues(List<?> listSource, Object target, String[] sourceFiledNames, String[] targetFiledNames, String[] splitors) {
		Class<?> targetClass = target.getClass();
		List<Field> targetFields = LKFieldUtils.getRealFieldListOnlyIncludes(targetClass, targetFiledNames);

		try {
			if (CollectionUtils.isEmpty(listSource)) {
				for (Field targetFiled : targetFields) {
					if (!targetFiled.isAccessible()) {
						targetFiled.setAccessible(true);
					}
					targetFiled.set(target, "");
				}
			} else {
				Object source0 = listSource.get(0);
				Class<?> sourceClass = source0.getClass();
				List<Field> sourceFields = LKFieldUtils.getRealFieldListOnlyIncludes(sourceClass, sourceFiledNames);

				StringBuilder[] sbs = new StringBuilder[targetFiledNames.length];
				int size = listSource.size() - 1;
				for (int i = 0; i <= size; i++) {
					Object source = listSource.get(i);
					for (int j = 0; j < sourceFields.size(); j++) {
						Field sourceField = sourceFields.get(j);
						if (!sourceField.isAccessible()) {
							sourceField.setAccessible(true);
						}
						StringBuilder sb = sbs[j];
						if (sb == null) {
							sb = sbs[j] = new StringBuilder();
						}
						sb.append(sourceField.get(source));
						if (i != size) {
							sb.append(splitors[j]);
						}
					}
				}

				for (int i = 0; i < targetFields.size(); i++) {
					Field targetFiled = targetFields.get(i);
					if (!targetFiled.isAccessible()) {
						targetFiled.setAccessible(true);
					}
					targetFiled.set(target, sbs[i].toString());
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();// ignore this
		}
	}

}
