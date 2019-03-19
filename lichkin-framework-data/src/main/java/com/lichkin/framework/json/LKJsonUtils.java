package com.lichkin.framework.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lichkin.framework.LKObjectMapper;
import com.lichkin.framework.xml.LKXmlUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * JSON工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LKJsonUtils extends LKObjectMapper {

	/**
	 * 初始化JSON对象
	 * @return JSON对象
	 */
	public static ObjectMapper newObjectMapper() {
		return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}


	/**
	 * 判断字符串是否为JSON字符串
	 * @param json 字符串
	 * @return 是JSON字符串返回true，否则返回false。
	 */
	public static boolean isJson(String json) {
		try {
			newObjectMapper().readTree(json);
			return true;
		} catch (IOException e) {
			return false;
		}
	}


	/**
	 * JSON字符串转XML字符串
	 * @param json 字符串
	 * @return XML字符串
	 */
	public static String toXml(String json) {
		return LKXmlUtils.toXml(toMap(json));
	}


	/**
	 * 对象转字符串
	 * @param obj 待转换对象
	 * @param nullable 是否允许空值
	 * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
	 * @return 字符串
	 */
	public static String toJson(Object obj, boolean nullable, boolean isArray) {
		return writeValueAsString(newObjectMapper(), obj, nullable, isArray);
	}


	/**
	 * 对象转字符串
	 * @param obj 待转换对象
	 * @return 字符串
	 */
	public static String toJson(Object obj) {
		return writeValueAsString(newObjectMapper(), obj, false, false);
	}


	/**
	 * 字符串转对象
	 * @param <T> 对象泛型
	 * @param json 字符串
	 * @param clazz 对象类型
	 * @return 对象
	 */
	public static <T> T toObj(String json, Class<T> clazz) {
		return toObj(newObjectMapper(), json, clazz);
	}


	/**
	 * 字符串转HashMap对象
	 * @param json 字符串
	 * @return Map对象
	 */
	public static HashMap<String, String> toMap(String json) {
		return toMap(newObjectMapper(), json);
	}


	/**
	 * 字符串转ArrayList对象
	 * @param <T> 对象泛型
	 * @param json 字符串
	 * @param clazz List泛型对象类型
	 * @return List对象
	 */
	public static <T> ArrayList<T> toList(String json, Class<T> clazz) {
		return toList(newObjectMapper(), json, clazz);
	}


	/**
	 * 将字符串转换为JsonNode对象
	 * @param json 字符串
	 * @return JsonNode对象
	 */
	public static JsonNode toJsonNode(String json) {
		return readTree(newObjectMapper(), json);
	}


	/**
	 * 深层读取JsonNode对象
	 * @param json 字符串
	 * @param keys 键数组
	 * @return JsonNode对象
	 */
	public static JsonNode deepGet(String json, String... keys) {
		return deepGet(newObjectMapper(), json, keys);
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
	public static String toJsonWithExcludes(Object obj, Class<?>[] ignoreFieldAnnotationClasses, boolean nullable, boolean isArray, String... excludesPropertyArray) {
		return writeValueAsStringWithExcludes(newObjectMapper(), obj, ignoreFieldAnnotationClasses, nullable, isArray, excludesPropertyArray);
	}


	/**
	 * 对象转字符串
	 * @param obj 待转换对象
	 * @param ignoreFieldAnnotationClasses 忽略字段标注的注解类型
	 * @param excludesPropertyArray 排除的字段名
	 * @return 字符串
	 */
	public static String toJsonWithExcludes(Object obj, Class<?>[] ignoreFieldAnnotationClasses, String... excludesPropertyArray) {
		return writeValueAsStringWithExcludes(newObjectMapper(), obj, ignoreFieldAnnotationClasses, false, false, excludesPropertyArray);
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
	public static String toJsonWithIncludes(Object obj, Class<?>[] ignoreFieldAnnotationClasses, boolean nullable, boolean isArray, String... includesPropertyArray) {
		return writeValueAsStringWithIncludes(newObjectMapper(), obj, ignoreFieldAnnotationClasses, nullable, isArray, includesPropertyArray);
	}


	/**
	 * 对象转字符串
	 * @param obj 待转换对象
	 * @param ignoreFieldAnnotationClasses 忽略字段标注的注解类型
	 * @param includesPropertyArray 包含的字段名
	 * @return 字符串
	 */
	public static String toJsonWithIncludes(Object obj, Class<?>[] ignoreFieldAnnotationClasses, String... includesPropertyArray) {
		return writeValueAsStringWithIncludes(newObjectMapper(), obj, ignoreFieldAnnotationClasses, false, false, includesPropertyArray);
	}

}
