package com.lichkin.framework.app.android.utils;

import android.app.Activity;
import android.content.Intent;

import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.bean.LKParamsBean;
import com.lichkin.framework.app.android.webview.LKBridgeWebViewWithProgressBarActivity;
import com.lichkin.framework.app.android.webview.LKBridgeWebViewWithProgressBarFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面交互工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKWebView {

    /** 页面地址键 */
    public static final String KEY_URL = "url";

    /** 默认请求码 */
    public static final int REQUEST_CODE = 0x00000008;

    /** 固定参数URL */
    private static final String PARAMS_URL;

    static {
        PARAMS_URL =
                ""
                        + "&appKey=" + LKAndroidStatics.appKey()
                        + "&clientType=" + "ANDROID"
                        + "&versionX=" + LKAndroidStatics.versionX()
                        + "&versionY=" + LKAndroidStatics.versionY()
                        + "&versionZ=" + LKAndroidStatics.versionZ()
                        + "&compToken=" + LKPropertiesLoader.appToken
                        + "&uuid=" + LKAndroidStatics.uuid()
                        + "&screenWidth=" + LKAndroidStatics.screenWidth()
                        + "&screenHeight=" + LKAndroidStatics.screenHeight();
    }

    /**
     * 拼接参数地址
     * @param to 页面地址
     * @param params 参数
     * @return 参数地址
     */
    private static String spliceUrlParams(String to, Map<String, Object> params) {
        // 初始化参数
        if (params == null) {
            params = new HashMap<>();
        }
        params.put("token", LKAndroidStatics.token());

        // 拼接参数
        StringBuilder sb = new StringBuilder(to);
        boolean first = !to.contains("?");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (first) {
                sb.append("?");
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(key).append("=").append(value == null ? "" : value.toString());
        }

        // 拼接固定参数
        sb.append(PARAMS_URL);

        // 返回结果
        return sb.toString();
    }

    /**
     * 打开页面
     * @param from 发起页面
     * @param to 页面地址
     * @param params 参数
     * @param requestCode 请求码
     */
    public static void open(Activity from, String to, LKParamsBean params, int requestCode) {
        Intent intent = new Intent(from, LKBridgeWebViewWithProgressBarActivity.class);
        intent.putExtra(KEY_URL, spliceUrlParams(to, params == null ? null : params.getParams()));
        from.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开页面
     * @param from 发起页面
     * @param to 页面地址
     * @param params 参数
     */
    public static void open(Activity from, String to, LKParamsBean params) {
        open(from, to, params, REQUEST_CODE);
    }

    /**
     * 打开页面
     * @param from 发起页面
     * @param to 页面地址
     * @param requestCode 请求码
     */
    public static void open(Activity from, String to, int requestCode) {
        open(from, to, null, requestCode);
    }

    /**
     * 打开页面
     * @param from 发起页面
     * @param to 页面地址
     */
    public static void open(Activity from, String to) {
        open(from, to, null, REQUEST_CODE);
    }

    /**
     * 创建页面
     * @param to 页面地址
     * @param params 参数
     */
    public static LKBridgeWebViewWithProgressBarFragment create(String to, Map<String, Object> params) {
        return new LKBridgeWebViewWithProgressBarFragment(spliceUrlParams(to, params));
    }

    /**
     * 创建页面
     * @param to 页面地址
     */
    public static LKBridgeWebViewWithProgressBarFragment create(String to) {
        return create(to, null);
    }

}
