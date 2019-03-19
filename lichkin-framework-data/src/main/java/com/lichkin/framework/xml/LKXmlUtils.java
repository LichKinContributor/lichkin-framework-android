package com.lichkin.framework.xml;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lichkin.framework.LKObjectMapper;
import com.lichkin.framework.json.LKJsonUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * XML工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LKXmlUtils extends LKObjectMapper {

	/**
	 * XML字符串转JSON字符串
	 * @param xml 字符串
	 * @return JSON字符串
	 */
	public static String toJson(String xml) {
		return LKJsonUtils.toJson(toMap(xml));
	}


	/**
	 * 对象转字符串
	 * @param obj 待转换对象
	 * @param nullable 是否允许空值
	 * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
	 * @return 字符串
	 */
	public static String toXml(Object obj, boolean nullable, boolean isArray) {
		return writeValueAsString(new XmlMapper(), obj, nullable, isArray);
	}


	/**
	 * 对象转字符串
	 * @param obj 待转换对象
	 * @return 字符串
	 */
	public static String toXml(Object obj) {
		return writeValueAsString(new XmlMapper(), obj, false, false);
	}


	/**
	 * 字符串转对象
	 * @param <T> 对象泛型
	 * @param xml 字符串
	 * @param clazz 对象类型
	 * @return 对象
	 */
	public static <T> T toObj(String xml, Class<T> clazz) {
		return toObj(new XmlMapper(), xml, clazz);
	}


	/**
	 * 字符串转HashMap对象
	 * @param xml 字符串
	 * @return Map对象
	 */
	public static HashMap<String, String> toMap(String xml) {
		return toMap(new XmlMapper(), xml);
	}


	/**
	 * 字符串转ArrayList对象
	 * @param <T> 对象泛型
	 * @param xml 字符串
	 * @param clazz List泛型对象类型
	 * @return List对象
	 */
	public static <T> ArrayList<T> toList(String xml, Class<T> clazz) {
		return toList(new XmlMapper(), xml, clazz);
	}


	/**
	 * 将字符串转换为JsonNode对象
	 * @param xml 字符串
	 * @return JsonNode对象
	 */
	public static JsonNode toJsonNode(String xml) {
		return readTree(new XmlMapper(), xml);
	}


	/**
	 * 深层读取JsonNode对象
	 * @param xml 字符串
	 * @param keys 键数组
	 * @return JsonNode对象
	 */
	public static JsonNode deepGet(String xml, String... keys) {
		return deepGet(new XmlMapper(), xml, keys);
	}


	/**
	 * 对象转字符串
	 * @param obj 待转换对象
	 * @param ignoreFieldAnnotationClasses 忽略字段标注的注解类型
	 * @param nullable 是否允许空值
	 * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
	 * @param excludesPropertyArray 排除的字段名
	 * @return 字符串
	 */
	public static String toXmlWithExcludes(Object obj, Class<?>[] ignoreFieldAnnotationClasses, boolean nullable, boolean isArray, String... excludesPropertyArray) {
		return writeValueAsStringWithExcludes(new XmlMapper(), obj, ignoreFieldAnnotationClasses, nullable, isArray, excludesPropertyArray);
	}


	/**
	 * 对象转字符串
	 * @param obj 待转换对象
	 * @param ignoreFieldAnnotationClasses 忽略字段标注的注解类型
	 * @param excludesPropertyArray 排除的字段名
	 * @return 字符串
	 */
	public static String toXmlWithExcludes(Object obj, Class<?>[] ignoreFieldAnnotationClasses, String... excludesPropertyArray) {
		return writeValueAsStringWithExcludes(new XmlMapper(), obj, ignoreFieldAnnotationClasses, false, false, excludesPropertyArray);
	}


	/**
	 * 对象转字符串
	 * @param obj 待转换对象
	 * @param ignoreFieldAnnotationClasses 忽略字段标注的注解类型
	 * @param nullable 是否允许空值
	 * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
	 * @param includesPropertyArray 包含的字段名
	 * @return 字符串
	 */
	public static String toXmlWithIncludes(Object obj, Class<?>[] ignoreFieldAnnotationClasses, boolean nullable, boolean isArray, String... includesPropertyArray) {
		return writeValueAsStringWithIncludes(new XmlMapper(), obj, ignoreFieldAnnotationClasses, nullable, isArray, includesPropertyArray);
	}


	/**
	 * 对象转字符串
	 * @param obj 待转换对象
	 * @param ignoreFieldAnnotationClasses 忽略字段标注的注解类型
	 * @param includesPropertyArray 包含的字段名
	 * @return 字符串
	 */
	public static String toXmlWithIncludes(Object obj, Class<?>[] ignoreFieldAnnotationClasses, String... includesPropertyArray) {
		return writeValueAsStringWithIncludes(new XmlMapper(), obj, ignoreFieldAnnotationClasses, false, false, includesPropertyArray);
	}

}
