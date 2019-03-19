package com.lichkin.application.invokers.TokenLogin;

import com.lichkin.framework.app.android.bean.LKLoginBean;

import lombok.Getter;
import lombok.Setter;

/**
 * 令牌登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
public class TokenLoginOut extends LKLoginBean {

    /** 登录名 */
    private String loginName;

}
