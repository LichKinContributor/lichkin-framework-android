package com.lichkin.framework.defines.beans;

/**
 * 方法调用基本对象类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface LKInvokeBean<D extends LKInvokeDatas> {

	D getDatas();


	void setDatas(D datas);

}
