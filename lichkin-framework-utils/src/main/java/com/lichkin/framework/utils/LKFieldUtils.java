package com.lichkin.framework.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Field工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKFieldUtils {

	/**
	 * 获取真实的Field列表。即子类定义了与父类相同的Field时，只返回子类定义的Field。
	 * @param clazz 类型
	 * @param excludeStatics 是否排除static修饰的字段
	 * @param excludeFieldNames 排除字段名
	 * @return Field列表
	 */
	public static List<Field> getRealFieldList(final Class<?> clazz, boolean excludeStatics, String... excludeFieldNames) {
		List<Field> listFields = getRealFieldList(clazz, excludeFieldNames);
		if (excludeStatics) {
			for (int i = listFields.size() - 1; i >= 0; i--) {
				if (Modifier.isStatic(listFields.get(i).getModifiers())) {
					listFields.remove(i);
				}
			}
		}
		return listFields;
	}


	/**
	 * 获取真实的Field列表。即子类定义了与父类相同的Field时，只返回子类定义的Field。
	 * @param clazz 类型
	 * @param includeFieldNames 不排除字段名
	 * @return Field列表
	 */
	public static List<Field> getRealFieldListOnlyIncludes(final Class<?> clazz, String... includeFieldNames) {
		List<Field> listFields = getRealFieldList(clazz);
		List<Field> listResult = new ArrayList<>(includeFieldNames.length);
		out: for (String includeFieldName : includeFieldNames) {
			for (Field field : listFields) {
				if (includeFieldName.equals(field.getName())) {
					listResult.add(field);
					continue out;
				}
			}
			listResult.add(null);
		}
		return listResult;
	}


	/**
	 * 获取真实的Field列表。即子类定义了与父类相同的Field时，只返回子类定义的Field。
	 * @param clazz 类型
	 * @param excludeFieldNames 排除字段名
	 * @return Field列表
	 */
	public static List<Field> getRealFieldList(final Class<?> clazz, String... excludeFieldNames) {
		final List<Field> listFields = new ArrayList<>();
		if (ArrayUtils.isEmpty(excludeFieldNames)) {
			for (Class<?> currentClass = clazz; currentClass != null; currentClass = currentClass.getSuperclass()) {
				final Field[] currentFields = currentClass.getDeclaredFields();
				if (currentClass == clazz) {
					Collections.addAll(listFields, currentFields);
					continue;
				}
				Field[] tempFields = new Field[] {};
				out: for (Field currentField : currentFields) {
					for (Field field : listFields) {
						if (field.getName().equals(currentField.getName()) && field.getType().equals(currentField.getType())) {
							continue out;
						}
					}
					tempFields = ArrayUtils.add(tempFields, currentField);
				}
				Collections.reverse(listFields);
				ArrayUtils.reverse(tempFields);
				Collections.addAll(listFields, tempFields);
				Collections.reverse(listFields);
			}
		} else {
			for (Class<?> currentClass = clazz; currentClass != null; currentClass = currentClass.getSuperclass()) {
				final Field[] currentFields = currentClass.getDeclaredFields();
				Field[] tempFields = new Field[] {};
				out: for (Field currentField : currentFields) {
					for (String excludeFieldName : excludeFieldNames) {
						if (excludeFieldName.equals(currentField.getName())) {
							continue out;
						}
					}
					for (Field field : listFields) {
						if (field.getName().equals(currentField.getName()) && field.getType().equals(currentField.getType())) {
							continue out;
						}
					}
					tempFields = ArrayUtils.add(tempFields, currentField);
				}
				Collections.reverse(listFields);
				ArrayUtils.reverse(tempFields);
				Collections.addAll(listFields, tempFields);
				Collections.reverse(listFields);
			}
		}
		return listFields;
	}


	/**
	 * 获取serialVersionUID值
	 * @param clazz 类型
	 * @return serialVersionUID值
	 */
	public static Long getSerialVersionUID(final Class<?> clazz) {
		try {
			Field serialVersionUIDField = clazz.getDeclaredField("serialVersionUID");
			serialVersionUIDField.setAccessible(true);
			Object serialVersionUID = serialVersionUIDField.get(null);
			if (serialVersionUID != null) {
				return (Long) serialVersionUID;
			}
		} catch (Exception e) {
		}
		return null;
	}


	/**
	 * 获取带注解的字段
	 * @param clazz 类型
	 * @param annotationClass 注解类型
	 * @param excludeFieldNames 排除字段名
	 * @return Field列表
	 */
	public static List<Field> getFieldListWithAnnotation(final Class<?> clazz, Class<? extends Annotation> annotationClass, String... excludeFieldNames) {
		List<Field> listFields = getRealFieldList(clazz, excludeFieldNames);
		if (CollectionUtils.isEmpty(listFields)) {
			return Collections.emptyList();
		}
		for (Iterator<Field> it = listFields.iterator(); it.hasNext();) {
			Field field = it.next();
			if (!field.isAnnotationPresent(annotationClass)) {
				it.remove();
			}
		}
		return listFields;
	}


	/**
	 * 获取带注解的字段
	 * @param clazz 类型
	 * @param annotationClasses 注解类型
	 * @return Field列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Field> getFieldListWithAnnotations(final Class<?> clazz, Class<?>[] annotationClasses) {
		if (ArrayUtils.isEmpty(annotationClasses)) {
			return Collections.emptyList();
		}
		List<Field> listFields = getRealFieldList(clazz);
		if (CollectionUtils.isEmpty(listFields)) {
			return Collections.emptyList();
		}
		out: for (Iterator<Field> it = listFields.iterator(); it.hasNext();) {
			Field field = it.next();
			for (Class<?> annotationClass : annotationClasses) {
				if (field.isAnnotationPresent((Class<? extends Annotation>) annotationClass)) {
					break out;
				}
			}
			it.remove();
		}
		return listFields;
	}

}
