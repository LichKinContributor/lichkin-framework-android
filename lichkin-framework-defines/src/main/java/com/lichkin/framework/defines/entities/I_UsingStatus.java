package com.lichkin.framework.defines.entities;

import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;

/**
 * 基础表接口
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface I_UsingStatus {

	/**
	 * 获取在用状态
	 * @return 在用状态
	 */
	public LKUsingStatusEnum getUsingStatus();


	/**
	 * 设置在用状态
	 * @param usingStatus 在用状态
	 */
	public void setUsingStatus(final LKUsingStatusEnum usingStatus);

}
