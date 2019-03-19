package com.lichkin.framework.defines.entities;

/**
 * 登录接口
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface I_Login extends I_ID {

	/**
	 * 获取登录令牌
	 * @return 登录令牌
	 */
	public String getToken();


	/**
	 * 获取密码
	 * @return 密码
	 */
	public String getPwd();


	/**
	 * 设置密码
	 * @param pwd 密码
	 */
	public void setPwd(String pwd);


	/**
	 * 设置头像
	 * @param photo 头像
	 */
	public void setPhoto(String photo);

}
