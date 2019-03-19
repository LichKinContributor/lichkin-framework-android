package com.lichkin.framework.defines.beans.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 下拉列表标准Bean
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class LKDroplistBean {

	/** 显示值 */
	@NonNull
	private String text;

	/** 结果值 */
	@NonNull
	private Object value;

}
