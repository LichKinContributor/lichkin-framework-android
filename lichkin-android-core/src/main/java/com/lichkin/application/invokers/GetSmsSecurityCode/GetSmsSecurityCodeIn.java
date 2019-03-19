package com.lichkin.application.invokers.GetSmsSecurityCode;

import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 获取短信验证码
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@RequiredArgsConstructor
public class GetSmsSecurityCodeIn extends LKRequestBean {

    /** 手机号码 */
    private final String cellphone;

}
