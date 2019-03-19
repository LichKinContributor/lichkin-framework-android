package com.lichkin.application.invokers.AccountLogin;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 账号登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface AccountLoginInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + "/AccountLogin")
    Call<LKResponseBean<AccountLoginOut>> invoke(@Body AccountLoginIn in);

}
