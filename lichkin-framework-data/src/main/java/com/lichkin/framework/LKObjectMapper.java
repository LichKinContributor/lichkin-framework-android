package com.lichkin.framework;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lichkin.framework.defines.func.BeanConverter;
import com.lichkin.framework.log.LKLog;
import com.lichkin.framework.log.LKLogFactory;
import com.lichkin.framework.utils.LKFieldUtils;
import com.lichkin.framework.utils.LKListUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 对象映射工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LKObjectMapper {

	/** 日志对象 */
	private static final LKLog LOGGER = LKLogFactory.getLog(LKObjectMapper.class);


	/**
	 * 默认值
	 * @param mapper 对象映射对象
	 * @param nullable 是否允许空值
	 * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
	 * @return 默认值
	 */
	private static String defaultValue(ObjectMapper mapper, boolean nullable, boolean isArray) {
		if (nullable) {
			return null;
		}
		if (isArray) {
			if (mapper instanceof XmlMapper) {
				return "";
			} else {
				return "[]";
			}
		} else {
			if (mapper instanceof XmlMapper) {
				return "";
			} else {
				return "{}";
			}
		}
	}


	/**
	 * 对象转字符串
	 * @param mapper 对象映射对象
	 * @param obj 待转换对象
	 * @param nullable 是否允许空值
	 * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
	 * @return 字符串
	 */
	protected static String writeValueAsString(ObjectMapper mapper, Object obj, boolean nullable, boolean isArray) {
		if (obj != null) {
			try {
				String result = mapper.writeValueAsString(obj);
				if (mapper instanceof XmlMapper) {
					if (result.startsWith("<ArrayList>") && result.endsWith("</ArrayList>")) {
						@SuppressWarnings("unchecked")
						String className = ((ArrayList<Object>) obj).get(0).getClass().getSimpleName();
						result = result.replaceAll("<item>", "<" + className + ">").replaceAll("</item>", "</" + className + ">");
					}
				}
				return result;
			} catch (final Exception e) {
				// ignore this
				LOGGER.error(e);
			}
		}
		return defaultValue(mapper, nullable, isArray);
	}


	/**
	 * 字符串转对象
	 * @param mapper 对象映射对象
	 * @param <T> 对象泛型
	 * @param str 字符串
	 * @param clazz 对象类型
	 * @return 对象
	 */
	protected static <T> T toObj(ObjectMapper mapper, String str, Class<T> clazz) {
		if (StringUtils.isNotBlank(str)) {
			try {
				return mapper.readValue(str, clazz);
			} catch (final Exception e) {
				// ignore this
				LOGGER.error(e);
			}
		}
		return null;
	}


	/**
	 * 字符串转HashMap对象
	 * @param mapper 对象映射对象
	 * @param str 字符串
	 * @return Map对象
	 */
	protected static HashMap<String, String> toMap(ObjectMapper mapper, String str) {
		if (StringUtils.isNotBlank(str)) {
			try {
				if (mapper instanceof XmlMapper) {
					if (str.startsWith("<HashMap>") && str.endsWith("</HashMap>")) {
						str = str.substring("<HashMap>".length(), str.length() - "</HashMap>".length());
					}
				}
				return mapper.readValue(str, mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, String.class));
			} catch (final Exception e) {
				// ignore this
				LOGGER.error(e);
			}
		}
		return null;
	}


	/**
	 * 字符串转ArrayList对象
	 * @param mapper 对象映射对象
	 * @param <T> 对象泛型
	 * @param str 字符串
	 * @param clazz List泛型对象类型
	 * @return List对象
	 */
	protected static <T> ArrayList<T> toList(ObjectMapper mapper, String str, Class<T> clazz) {
		if (StringUtils.isNotBlank(str)) {
			try {
				return mapper.readValue(str, mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz));
			} catch (final Exception e) {
				// ignore this
				LOGGER.error(e);
			}
		}
		return null;
	}


	/**
	 * 将字符串转换为JsonNode对象
	 * @param mapper 对象映射对象
	 * @param str 字符串
	 * @return JsonNode对象
	 */
	protected static JsonNode readTree(ObjectMapper mapper, String str) {
		try {
			return mapper.readTree(str);
		} catch (final Exception e) {
			// ignore this
			LOGGER.error(e);
		}
		return null;
	}


	/**
	 * 深层读取JsonNode对象
	 * @param mapper 对象映射对象
	 * @param str 字符串
	 * @param keys 键数组
	 * @return JsonNode对象
	 */
	protected static JsonNode deepGet(ObjectMapper mapper, String str, String... keys) {
		try {
			JsonNode jsonNode = mapper.readTree(str);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("deepGet -> ", jsonNode.asText());
			}
			if (ArrayUtils.isNotEmpty(keys)) {
				for (final String key : keys) {
					jsonNode = jsonNode.get(key);
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("deepGet -> ", jsonNode.asText());
					}
				}
			}
			return jsonNode;
		} catch (final Exception e) {
			// ignore this
			LOGGER.error(e);
		}
		return null;
	}


	/** 过滤器ID */
	private static final String JSON_FILTER_ID = "JsonFilter";


	/**
	 * 默认过滤器，什么都不处理。
	 * @author SuZhou LichKin Information Technology Co., Ltd.
	 */
	@JsonFilter(JSON_FILTER_ID)
	private interface LKJsonFilter {
	}


	/**
	 * 计算属性数组
	 * @param obj 对象
	 * @param ignoreFieldAnnotationClasses 忽略字段标注的注解类型
	 * @param propertyArray 属性数组
	 * @return 属性数组
	 */
	private static String[] calcPropertyArray(Object obj, Class<?>[] ignoreFieldAnnotationClasses, String... propertyArray) {
		if (ArrayUtils.isNotEmpty(ignoreFieldAnnotationClasses)) {
			List<String> ignoreFields = LKListUtils.convert(LKFieldUtils.getFieldListWithAnnotations(obj.getClass(), ignoreFieldAnnotationClasses), new BeanConverter<Field, String>() {
				@Override
				public String convert(Field field) {
					return field.getName();
				}
			});
			if (CollectionUtils.isNotEmpty(ignoreFields)) {
				if (ArrayUtils.isNotEmpty(propertyArray)) {
					ignoreFields.addAll(Arrays.asList(propertyArray));
				}
				propertyArray = ignoreFields.toArray(propertyArray);
			}
		}
		return propertyArray;
	}


	/**
	 * 对象转字符串
	 * @param mapper 对象映射对象
	 * @param obj 待转换对象
	 * @param ignoreFieldAnnotationClasses 忽略字段标注的注解类型
	 * @param nullable 是否允许空值
	 * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
	 * @param excludesPropertyArray 排除的字段名
	 * @return 字符串
	 */
	protected static String writeValueAsStringWithExcludes(ObjectMapper mapper, Object obj, Class<?>[] ignoreFieldAnnotationClasses, boolean nullable, boolean isArray, String... excludesPropertyArray) {
		excludesPropertyArray = calcPropertyArray(obj, ignoreFieldAnnotationClasses, excludesPropertyArray);
		if (ArrayUtils.isNotEmpty(excludesPropertyArray)) {
			mapper.setFilterProvider(new SimpleFilterProvider().addFilter(JSON_FILTER_ID, SimpleBeanPropertyFilter.serializeAllExcept(excludesPropertyArray)));
			mapper.addMixIn(obj.getClass(), LKJsonFilter.class);
		}
		return writeValueAsString(mapper, obj, nullable, isArray);
	}


	/**
	 * 对象转字符串
	 * @param mapper 对象映射对象
	 * @param obj 待转换对象
	 * @param ignoreFieldAnnotationClasses 忽略字段标注的注解类型
	 * @param nullable 是否允许空值
	 * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
	 * @param includesPropertyArray 包含的字段名
	 * @return 字符串
	 */
	protected static String writeValueAsStringWithIncludes(ObjectMapper mapper, Object obj, Class<?>[] ignoreFieldAnnotationClasses, boolean nullable, boolean isArray, String... includesPropertyArray) {
		includesPropertyArray = calcPropertyArray(obj, ignoreFieldAnnotationClasses, includesPropertyArray);
		if (ArrayUtils.isNotEmpty(includesPropertyArray)) {
			mapper.setFilterProvider(new SimpleFilterProvider().addFilter(JSON_FILTER_ID, SimpleBeanPropertyFilter.filterOutAllExcept(includesPropertyArray)));
			mapper.addMixIn(obj.getClass(), LKJsonFilter.class);
		}
		return writeValueAsString(mapper, obj, nullable, isArray);
	}

}
