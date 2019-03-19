package com.lichkin.application.invokers.FastLogin;

import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 快速登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@RequiredArgsConstructor
public class FastLoginIn extends LKRequestBean {

    /** 手机号码 */
    private final String cellphone;

    /** 验证码 */
    private final String securityCode;

}
