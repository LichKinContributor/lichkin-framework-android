package com.lichkin.framework.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKClassUtils {

	/**
	 * 获取注解对象
	 * @param clazz 类型
	 * @param annotationClassName 注解类名称
	 * @return 注解对象
	 */
	public static Annotation getAnnotation(Class<?> clazz, String annotationClassName) {
		Annotation[] annotations = clazz.getAnnotations();
		if (ArrayUtils.isNotEmpty(annotations)) {
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().getName().equals(annotationClassName)) {
					return annotation;
				}
			}
		}
		return null;
	}


	/**
	 * 是否包含注解
	 * @param clazz 类型
	 * @param annotationClassName 注解类名称
	 * @return true:包含;false:不包含。
	 */
	public static boolean containsAnnotation(Class<?> clazz, String annotationClassName) {
		return getAnnotation(clazz, annotationClassName) != null;
	}


	/**
	 * 获取注解值
	 * @param clazz 类型
	 * @param annotationClassName 注解类名称
	 * @param fieldName 字段类型
	 * @return 注解值
	 * @throws NoSuchMethodException NoSuchMethodException
	 */
	public static Object getAnnotationValue(Class<?> clazz, String annotationClassName, String fieldName) throws NoSuchMethodException {
		Annotation annotation = getAnnotation(clazz, annotationClassName);
		if (annotation != null) {
			try {
				return annotation.annotationType().getMethod(fieldName).invoke(annotation);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
				e.printStackTrace();// ignore this
			}
		}
		return null;
	}


	/**
	 * 获取注解值
	 * @param clazz 类型
	 * @param annotationClassName 注解类名称
	 * @param fieldName 字段类型
	 * @return 注解值
	 * @throws NoSuchMethodException NoSuchMethodException
	 */
	public static String getAnnotationStringValue(Class<?> clazz, String annotationClassName, String fieldName) throws NoSuchMethodException {
		Object value = getAnnotationValue(clazz, annotationClassName, fieldName);
		if (value != null) {
			return value.toString();
		}
		return null;
	}


	/**
	 * 获取所有父类类型
	 * @param clazz 当前类型
	 * @return 父类类型列表
	 */
	public static List<Class<?>> getAllExtendsClasses(final Class<?> clazz) {
		if (clazz.equals(Object.class)) {
			return Collections.emptyList();
		}

		final ArrayList<Class<?>> list = new ArrayList<>();
		for (Class<?> superclass = clazz.getSuperclass(); !superclass.equals(Object.class); superclass = superclass.getSuperclass()) {
			list.add(superclass);
		}
		return list;
	}


	/**
	 * 获取注解对象
	 * @param clazz 类型
	 * @param annotationClassName 注解类名称
	 * @return 注解对象
	 */
	public static Annotation deepGetAnnotation(Class<?> clazz, String annotationClassName) {
		if (clazz.equals(Object.class)) {
			return null;
		}

		Annotation annotation = getAnnotation(clazz, annotationClassName);
		if (annotation != null) {
			return annotation;
		}

		for (Class<?> superclass = clazz.getSuperclass(); !superclass.equals(Object.class); superclass = superclass.getSuperclass()) {
			annotation = getAnnotation(superclass, annotationClassName);
			if (annotation != null) {
				return annotation;
			}
		}

		return null;
	}


	/**
	 * 判断是否继承父类
	 * @param clazz 当前类
	 * @param supperClass 父类
	 * @return 继承父类返回true，否则返回false。
	 */
	public static boolean checkExtendsClass(final Class<?> clazz, final Class<?> supperClass) {
		if (clazz.equals(supperClass)) {
			return false;
		}

		if (supperClass.equals(Object.class)) {
			return true;
		}

		for (Class<?> cls = clazz.getSuperclass(); !cls.equals(Object.class); cls = cls.getSuperclass()) {
			if (cls.equals(supperClass)) {
				return true;
			}
		}

		return false;
	}


	/**
	 * 获取所有接口类型
	 * @param clazz 当前类型
	 * @return 接口类型列表
	 */
	public static List<Class<?>> getAllImplementsClasses(final Class<?> clazz) {
		if (clazz.equals(Object.class)) {
			return Collections.emptyList();
		}
		final List<Class<?>> list = new ArrayList<>();
		for (Class<?> tmpClass = clazz; !tmpClass.equals(Object.class); tmpClass = tmpClass.getSuperclass()) {
			Class<?>[] iArr = tmpClass.getInterfaces();// 取类上所有接口
			out: for (Class<?> i : iArr) {
				for (Class<?> cls : list) {
					if (cls.equals(i)) {
						continue out;
					}
				}
				list.add(i);
				Class<?>[] iiArr = i.getInterfaces();// 取该接口上所有接口
				in: for (Class<?> ii : iiArr) {
					for (Class<?> cls : list) {
						if (cls.equals(ii)) {
							continue in;
						}
					}
					list.add(ii);
				}
			}
		}
		return list;
	}


	/**
	 * 判断是否实现接口类
	 * @param clazz 当前类
	 * @param interfaceClass 接口类
	 * @return 实现接口类返回true，否则返回false。
	 */
	public static boolean checkImplementsInterface(final Class<?> clazz, final Class<?> interfaceClass) {
		if (clazz.equals(Object.class)) {
			return false;
		}

		if (clazz.equals(interfaceClass)) {
			return false;
		}

		for (Class<?> tmpClass = clazz; (tmpClass != null) && !tmpClass.equals(Object.class); tmpClass = tmpClass.getSuperclass()) {
			Class<?>[] iArr = tmpClass.getInterfaces();// 取类上所有接口
			for (Class<?> i : iArr) {
				if (i.equals(interfaceClass)) {
					return true;
				}
				Class<?>[] iiArr = i.getInterfaces();// 取该接口上所有接口
				for (Class<?> ii : iiArr) {
					if (ii.equals(interfaceClass)) {
						return true;
					}
				}
			}
		}

		return false;
	}

}
