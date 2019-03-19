package com.lichkin.framework.app.android.utils;

import com.lichkin.framework.app.android.LKApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;

/**
 * 配置文件加载工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKPropertiesLoader {

    /** 客户端令牌 */
    public static final String appToken;

    /** 请求根路径 */
    public static final String baseUrl;
    /** 请求超时时长 */
    static final int timeout;

    /** 测试接口请求 */
    public static final boolean testRetrofit;

    /** 测试交互 */
    public static final boolean testWebView;
    /** 测试交互页面URL */
    public static final String testWebViewUrl;

    /** 测试表单 */
    public static final boolean testForm;

    /** 页面测试 */
    public static final boolean testPage;
    /** 页面测试地址 */
    public static final String testPageUrl;

    /** 属性配置 */
    private static final Properties prop;


    static {
        prop = new Properties();
        try {
            @Cleanup
            InputStream is = LKApplication.getInstance().getAssets().open("lichkin.properties");
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        appToken = getString("lichkin.framework.app.token");

        baseUrl = getString("lichkin.framework.api.baseUrl");
        timeout = getInteger("lichkin.framework.api.timeout");

        testRetrofit = getBoolean("lichkin.framework.test.retrofit");

        testWebView = getBoolean("lichkin.framework.test.webView");
        testWebViewUrl = getString("lichkin.framework.test.webView.url");

        testForm = getBoolean("lichkin.framework.test.form");

        testPage = getBoolean("lichkin.framework.test.page");
        testPageUrl = getString("lichkin.framework.test.page.url");
    }


    /**
     * 获取字符串
     * @param key 键
     * @return 值
     */
    public static String getString(String key) {
        return prop.getProperty(key);
    }


    /**
     * 获取整数
     * @param key 键
     * @return 值
     */
    public static int getInteger(String key) {
        return Integer.parseInt(getString(key));
    }


    /**
     * 获取布尔
     * @param key 键
     * @return 值
     */
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(getString(key));
    }


    /**
     * 获取浮点
     * @param key 键
     * @return 值
     */
    public static double getDouble(String key) {
        return Double.valueOf(getString(key));
    }


    /**
     * 获取除法公式结果
     * @param key 键
     * @return 值
     */
    public static double getDivision(String key) {
        String division = getString(key);
        String[] result = division.split("/");
        double divisor = Double.valueOf(result[0]);
        double dividend = Double.valueOf(result[1]);
        return divisor / dividend;
    }

}
