package com.lichkin.framework.defines.func;

/**
 * Bean转换接口定义
 * @param <Source> 源对象泛型
 * @param <Target> 目标对象泛型
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface BeanConverter<Source, Target> {

	/**
	 * 将源对象转换为目标对象
	 * @param source 源对象
	 * @return 目标对象
	 */
	Target convert(Source source);

}
