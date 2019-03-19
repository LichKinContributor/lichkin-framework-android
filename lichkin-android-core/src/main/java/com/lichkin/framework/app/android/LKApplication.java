package com.lichkin.framework.app.android;

import android.app.Application;

import com.lichkin.framework.app.android.utils.LKPropertiesLoader;

/**
 * 应用实例对象
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKApplication extends Application {

    /** 实例 */
    private static LKApplication instance;

    /**
     * 获取实例
     * @return 实例
     */
    public static LKApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LKShare.init(this, LKPropertiesLoader.getString("lichkin.framework.share.appKey"), LKPropertiesLoader.getString("lichkin.framework.share.appSecret"));
    }

}
