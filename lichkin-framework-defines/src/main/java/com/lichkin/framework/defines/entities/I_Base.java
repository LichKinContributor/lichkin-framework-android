package com.lichkin.framework.defines.entities;

/**
 * 基础表接口
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface I_Base extends I_UsingStatus_ID {

	/**
	 * 获取新增操作时间
	 * @return 新增操作时间
	 */
	public String getInsertTime();


	/**
	 * 设置新增操作时间
	 * @param insertTime 新增操作时间
	 */
	public void setInsertTime(final String insertTime);

}
