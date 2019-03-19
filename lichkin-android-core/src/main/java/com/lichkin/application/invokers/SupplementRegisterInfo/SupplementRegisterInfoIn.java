package com.lichkin.application.invokers.SupplementRegisterInfo;

import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 补充注册信息
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@RequiredArgsConstructor
public class SupplementRegisterInfoIn extends LKRequestBean {

    /** 姓名 */
    private final String userName;

    /** 用户名 */
    private final String loginName;

    /** 密码 */
    private final String pwd;

}
