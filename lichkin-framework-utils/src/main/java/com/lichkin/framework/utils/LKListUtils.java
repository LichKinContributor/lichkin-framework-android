package com.lichkin.framework.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.lichkin.framework.defines.func.BeanConverter;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;

/**
 * 列表工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKListUtils {

	/**
	 * 深度拷贝列表
	 * @param <T> 列表内类型泛型
	 * @param list 源列表
	 * @return 新列表
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> deepCopy(List<T> list) {
		try {
			@Cleanup
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			@Cleanup
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(list);
			@Cleanup
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			@Cleanup
			ObjectInputStream in = new ObjectInputStream(bais);
			return (List<T>) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}


	/**
	 * 将源中的数据添加到目标中
	 * @param <T> 列表内类型泛型
	 * @param distinct 去重
	 * @param listSource 源
	 * @param listTarget 目标
	 */
	public static <T> void addAll(boolean distinct, List<T> listSource, List<T> listTarget) {
		if (distinct) {
			out: for (T source : listTarget) {
				for (T target : listTarget) {
					if ((source != null) && (target != null) && source.equals(target)) {
						continue out;
					}
				}
				listTarget.add(source);
			}
		} else {
			listTarget.addAll(listSource);
		}
	}


	/**
	 * 多个列表合并时去重
	 * @param <T> 列表内类型泛型
	 * @param listMain 主列表
	 * @param comparator 去重逻辑
	 * @param listAddedes 补充列表
	 * @return 去重后的列表
	 */
	@SafeVarargs
	public static <T> List<T> distinct(List<T> listMain, Comparator<? super T> comparator, List<T>... listAddedes) {
		Set<T> set = new TreeSet<>(comparator);
		if (listMain != null) {
			set.addAll(listMain);
		}
		if (listAddedes != null) {
			for (List<T> listAdded : listAddedes) {
				if (listAdded != null) {
					set.addAll(listAdded);
				}
			}
		}
		return new ArrayList<>(set);
	}


	/**
	 * 将源列表数据转换为目标列表数据
	 * @param <Source> 源对象泛型
	 * @param <Target> 目标对象泛型
	 * @param listSource 源列表数据
	 * @param converter 转换器
	 * @return 目标列表数据
	 */
	public static <Source, Target> List<Target> convert(List<Source> listSource, BeanConverter<Source, Target> converter) {
		if (listSource == null) {
			return null;
		}

		if (listSource.isEmpty()) {
			return Collections.emptyList();
		}

		List<Target> listTarget = new ArrayList<>(listSource.size());
		for (Source source : listSource) {
			listTarget.add(converter.convert(source));
		}

		return listTarget;
	}

}
