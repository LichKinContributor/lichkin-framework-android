package com.lichkin.framework.app.android;

import android.app.Application;

import com.mob.MobSDK;

/**
 * ShareSDK初始化
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
class LKShare {

    /**
     * 初始化
     * @param app 应用
     * @param appKey Appkey
     * @param appSecret AppSecret
     */
    static void init(Application app, String appKey, String appSecret) {
        MobSDK.init(app, appKey, appSecret);
    }

}
