package com.lichkin.application.invokers.FastLogin;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 快速登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface FastLoginInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + "/FastLogin")
    Call<LKResponseBean<FastLoginOut>> invoke(@Body FastLoginIn in);

}
