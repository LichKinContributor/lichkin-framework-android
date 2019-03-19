package com.lichkin.framework.app.android.listener.click;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.lichkin.android.webview.R;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.bean.LKParamsBean;
import com.lichkin.framework.app.android.utils.LKAlert;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKWebView;
import com.lichkin.framework.defines.LKFrameworkStatics;

/**
 * 按钮点击事件 -> 跳转到交互页面
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class BtnOnClickListener4ToWebView implements View.OnClickListener {

    /** 登录后操作 */
    private boolean afterLogin;
    /** 当前Activity */
    private final Activity activity;
    /** 页面地址配置键 */
    private final String urlKey;
    /** 附带参数 */
    private LKParamsBean params;

    /**
     * 构造方法
     * @param afterLogin 登录后操作
     * @param activity 当前Activity
     * @param urlKey 页面地址配置键
     * @param params 附带参数
     */
    public BtnOnClickListener4ToWebView(boolean afterLogin, Activity activity, String urlKey, LKParamsBean params) {
        this.afterLogin = afterLogin;
        this.activity = activity;
        this.urlKey = urlKey;
        this.params = params;
    }

    /**
     * 构造方法
     * @param activity 当前Activity
     * @param urlKey 页面地址配置键
     */
    public BtnOnClickListener4ToWebView(Activity activity, String urlKey) {
        this(false, activity, urlKey, null);
    }

    /**
     * 构造方法
     * @param afterLogin 登录后操作
     * @param fragment 当前Fragment
     * @param urlKey 页面地址配置键
     * @param params 附带参数
     */
    public BtnOnClickListener4ToWebView(boolean afterLogin, Fragment fragment, String urlKey, LKParamsBean params) {
        this(afterLogin, fragment.getActivity(), urlKey, params);
    }

    /**
     * 构造方法
     * @param fragment 当前Fragment
     * @param urlKey 页面地址配置键
     */
    public BtnOnClickListener4ToWebView(Fragment fragment, String urlKey) {
        this(false, fragment, urlKey, null);
    }

    @Override
    public void onClick(View v) {
        if (afterLogin && !LKAndroidStatics.isLogin()) {
            LKAlert.alert(BtnOnClickListener4ToWebView.this.activity, LKAndroidUtils.getString(R.string.not_login));
            return;
        }
        if (LKPropertiesLoader.testPage) {
            LKWebView.open(BtnOnClickListener4ToWebView.this.activity, LKPropertiesLoader.testPageUrl, params);
        } else {
            LKWebView.open(BtnOnClickListener4ToWebView.this.activity, LKPropertiesLoader.baseUrl + LKPropertiesLoader.getString(urlKey) + LKFrameworkStatics.WEB_MAPPING_PAGES, params);
        }
    }

}
