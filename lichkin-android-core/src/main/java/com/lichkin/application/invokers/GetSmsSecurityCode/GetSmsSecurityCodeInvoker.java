package com.lichkin.application.invokers.GetSmsSecurityCode;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 获取短信验证码
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface GetSmsSecurityCodeInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + "/GetSmsSecurityCode")
    Call<LKResponseBean<GetSmsSecurityCodeOut>> invoke(@Body GetSmsSecurityCodeIn in);

}
