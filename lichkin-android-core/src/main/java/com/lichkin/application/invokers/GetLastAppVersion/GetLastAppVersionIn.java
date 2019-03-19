package com.lichkin.application.invokers.GetLastAppVersion;

import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;

/**
 * 获取最新客户端版本信息
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
public class GetLastAppVersionIn extends LKRequestBean {

    /** 客户端系统版本 */
    private String osVersion = LKAndroidStatics.osVersion();

    /** 生产厂商 */
    private String brand = LKAndroidStatics.brand();

    /** 机型信息 */
    private String model = LKAndroidStatics.model();

    /** 设备唯一标识 */
    private String uuid = LKAndroidStatics.uuid();

    /** 屏幕宽 */
    private Short screenWidth = LKAndroidStatics.screenWidth();

    /** 屏幕高 */
    private Short screenHeight = LKAndroidStatics.screenHeight();

}
