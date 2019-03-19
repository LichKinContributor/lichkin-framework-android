package com.lichkin.application.invokers.SupplementRegisterInfo;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 补充注册信息
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface SupplementRegisterInfoInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + "/SupplementRegisterInfo")
    Call<LKResponseBean<SupplementRegisterInfoOut>> invoke(@Body SupplementRegisterInfoIn in);

}
