package com.lichkin.framework.defines.beans;

import com.lichkin.framework.defines.entities.I_AppKey;
import com.lichkin.framework.defines.enums.impl.LKClientTypeEnum;

/**
 * 方法调用内置对象类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface LKInvokeDatas extends I_AppKey {

	public static final String KEY_CLIENT_TYPE = "KEY_CLIENT_TYPE";


	public LKClientTypeEnum getClientType();


	public void setClientType(LKClientTypeEnum clientType);


	public static final String KEY_VERSION_X = "KEY_VERSION_X";


	public Byte getVersionX();


	public void setVersionX(Byte versionX);


	public static final String KEY_VERSION_Y = "KEY_VERSION_Y";


	public Byte getVersionY();


	public void setVersionY(Byte versionY);


	public static final String KEY_VERSION_Z = "KEY_VERSION_Z";


	public Short getVersionZ();


	public void setVersionZ(Short versionZ);


	public static final String KEY_TOKEN = "KEY_TOKEN";


	public String getToken();


	public void setToken(String token);


	public static final String KEY_COMP_TOKEN = "KEY_COMP_TOKEN";


	public String getCompToken();


	public void setCompToken(String compToken);

}
