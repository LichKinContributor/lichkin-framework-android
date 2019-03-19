package com.lichkin.framework.defines.entities;

/**
 * 公司接口
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface I_Comp extends I_ID {

	public String getParentCode();


	public void setParentCode(String parentCode);


	public String getCompCode();


	public void setCompCode(String compCode);


	public String getCompName();


	public void setCompName(String compName);


	public String getCompKey();


	public void setCompKey(String compKey);


	public String getToken();


	public void setToken(String token);


	public String getEmail();


	public void setEmail(String email);


	public String getPhoto();


	public void setPhoto(String photo);

}
