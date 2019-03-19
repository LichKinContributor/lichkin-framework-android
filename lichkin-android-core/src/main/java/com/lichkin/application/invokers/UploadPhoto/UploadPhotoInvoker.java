package com.lichkin.application.invokers.UploadPhoto;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 头像上传
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface UploadPhotoInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + "/UploadPhoto")
    Call<LKResponseBean<UploadPhotoOut>> invoke(@Body UploadPhotoIn in);

}
