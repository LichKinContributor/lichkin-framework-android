package com.lichkin.framework.defines.beans.impl;

import com.lichkin.framework.defines.entities.I_Login;
import com.lichkin.framework.defines.entities.I_User;
import com.lichkin.framework.defines.enums.impl.LKGenderEnum;

import lombok.Getter;

/**
 * ROOT权限用户登录对象
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
public class LichKinUserLogin implements I_User, I_Login {

	/** 单例实例 */
	private static final LichKinUserLogin instance = new LichKinUserLogin();


	/**
	 * 获取实例
	 * @return 实例
	 */
	public static LichKinUserLogin getInstance() {
		return instance;
	}


	private LichKinUserLogin() {
		LichKin lichKin = LichKin.getInstance();
		id = lichKin.getId();
		compId = lichKin.getId();
		userName = lichKin.getCompName();
		email = lichKin.getEmail();
		token = lichKin.getToken();
		photo = lichKin.getPhoto();
		pwd = null;
	}


	/** 主键 */
	private final String id;

	/** 公司ID */
	private final String compId;

	/** 姓名 */
	private final String userName;

	/** 性别（枚举） */
	private final LKGenderEnum gender = LKGenderEnum.FEMALE;

	/** 邮箱 */
	private final String email;

	/** 账号等级 */
	private final Byte level = (byte) 63;

	/** 登录令牌 */
	private final String token;

	/** true:超级管理员;false:普通管理员; */
	private final Boolean superAdmin = true;

	/** 照片（Base64） */
	private final String photo;

	/** 密码 */
	private final String pwd;


	@Override
	public void setId(String id) {
	}


	@Override
	public void setPwd(String pwd) {
	}


	@Override
	public void setPhoto(String photo) {
	}

}
