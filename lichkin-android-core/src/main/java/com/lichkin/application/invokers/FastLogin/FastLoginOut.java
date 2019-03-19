package com.lichkin.application.invokers.FastLogin;

import com.lichkin.application.invokers.AccountLogin.AccountLoginOut;

import lombok.Getter;
import lombok.Setter;

/**
 * 快速登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
public class FastLoginOut extends AccountLoginOut {

    /** ture:登录;false:注册. */
    private boolean login;

}
