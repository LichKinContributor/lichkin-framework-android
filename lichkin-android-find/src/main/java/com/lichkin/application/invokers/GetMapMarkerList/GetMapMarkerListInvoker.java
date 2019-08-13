package com.lichkin.application.invokers.GetMapMarkerList;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 获取地图位置
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface GetMapMarkerListInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + "/GetMapMarkerList")
    Call<LKResponseBean<List<GetMapMarkerListOut>>> invoke(@Body GetMapMarkerListIn in);

}
