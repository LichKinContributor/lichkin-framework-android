package com.lichkin.application.invokers.AccountLogin;

import com.lichkin.framework.app.android.bean.LKLoginBean;

import lombok.Getter;
import lombok.Setter;

/**
 * 账号登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
public class AccountLoginOut extends LKLoginBean {

    /** 令牌 */
    private String token;

    /** 登录名 */
    private String loginName;

}
