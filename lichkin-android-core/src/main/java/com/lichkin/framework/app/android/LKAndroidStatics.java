package com.lichkin.framework.app.android;

import com.lichkin.android.core.R;
import com.lichkin.application.invokers.AccountLogin.AccountLoginOut;
import com.lichkin.framework.app.android.bean.LKDynamicTabBean;
import com.lichkin.framework.app.android.bean.LKLoginBean;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;

import java.util.List;
import java.util.UUID;

/**
 * 静态值，所有设置方法由框架实现，开发人员应只调用获取方法。
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKAndroidStatics {

    /** 客户端唯一标识 */
    private static String appKey;

    /** 客户端版本号（大版本号） */
    private static Byte versionX;

    /** 客户端版本号（中版本号） */
    private static Byte versionY;

    /** 客户端版本号（小版本号） */
    private static Short versionZ;

    /** 客户端系统版本 */
    private static String osVersion = android.os.Build.VERSION.RELEASE;

    /** 客户端SDK版本 */
    private static int targetSdkVersion;

    /** 生产厂商 */
    private static String brand = android.os.Build.BRAND;

    /** 机型信息 */
    private static String model = android.os.Build.MODEL;

    /** 设备标识 */
    private static String uuid;

    /** 设备标识已读 */
    private static boolean uuidLoaded = false;

    /** 屏幕宽度 */
    private static Short screenWidth;

    /** 屏幕高度 */
    private static Short screenHeight;

    /** 令牌 */
    private static String token;

    /** 姓名 */
    private static String userName = "";

    /** 登录名 */
    private static String loginName = "";

    /** 等级 */
    private static Integer level;

    /** 头像 */
    private static String photo;

    /** 动态TAB页列表 */
    private static List<LKDynamicTabBean> dynamicTabs;

    /**
     * 获取客户端唯一标识
     * @return 客户端唯一标识
     */
    public static String appKey() {
        return appKey;
    }

    /**
     * 设置客户端唯一标识
     * @param appKey 客户端唯一标识
     */
    @Deprecated
    public static void appKey(String appKey) {
        LKAndroidStatics.appKey = appKey;
    }

    /**
     * 获取大版本号
     * @return 大版本号
     */
    public static Byte versionX() {
        return versionX;
    }

    /**
     * 设置大版本号
     * @param versionX 大版本号
     */
    @Deprecated
    public static void versionX(Byte versionX) {
        LKAndroidStatics.versionX = versionX;
    }

    /**
     * 获取中版本号
     * @return 中版本号
     */
    public static Byte versionY() {
        return versionY;
    }

    /**
     * 设置中版本号
     * @param versionY 中版本号
     */
    @Deprecated
    public static void versionY(Byte versionY) {
        LKAndroidStatics.versionY = versionY;
    }

    /**
     * 获取小版本号
     * @return 小版本号
     */
    public static Short versionZ() {
        return versionZ;
    }

    /**
     * 设置小版本号
     * @param versionZ 小版本号
     */
    @Deprecated
    public static void versionZ(Short versionZ) {
        LKAndroidStatics.versionZ = versionZ;
    }

    /**
     * 获取客户端系统版本
     * @return 客户端系统版本
     */
    public static String osVersion() {
        return osVersion;
    }

    /**
     * 获取客户端SDK版本
     * @return 客户端SDK版本
     */
    public static int targetSdkVersion() {
        return targetSdkVersion;
    }

    /**
     * 设置客户端SDK版本
     * @param targetSdkVersion 客户端SDK版本
     */
    @Deprecated
    public static void targetSdkVersion(int targetSdkVersion) {
        LKAndroidStatics.targetSdkVersion = targetSdkVersion;
    }

    /**
     * 获取生产厂商
     * @return 生产厂商
     */
    public static String brand() {
        return brand;
    }

    /**
     * 获取机型信息
     * @return 机型信息
     */
    public static String model() {
        return model;
    }

    /**
     * 获取设备标识
     * @return 设备标识
     */
    public static String uuid() {
        //没有加载过则先加载
        if (!uuidLoaded) {
            uuidLoaded = true;

            uuid = LKSharedPreferences.getString(LKSharedPreferences.UUID, null);
            if (uuid == null) {
                uuid = UUID.randomUUID().toString();
                LKSharedPreferences.putString(LKSharedPreferences.UUID, uuid);
            }
        }
        return uuid;
    }

    /**
     * 获取屏幕宽度
     * @return 屏幕宽度
     */
    public static Short screenWidth() {
        return screenWidth;
    }

    /**
     * 设置屏幕宽度
     * @param screenWidth 屏幕宽度
     */
    @Deprecated
    public static void screenWidth(Short screenWidth) {
        LKAndroidStatics.screenWidth = screenWidth;
    }

    /**
     * 获取屏幕高度
     * @return 屏幕高度
     */
    public static Short screenHeight() {
        return screenHeight;
    }

    /**
     * 设置屏幕高度
     * @param screenHeight 屏幕高度
     */
    @Deprecated
    public static void screenHeight(Short screenHeight) {
        LKAndroidStatics.screenHeight = screenHeight;
    }

    /**
     * 获取令牌
     * @return 令牌
     */
    public static String token() {
        if (token == null || "".equals(token)) {
            token = LKSharedPreferences.getString(LKSharedPreferences.TOKEN, "");
        }
        return token;
    }

    /**
     * 设置令牌
     * @param token 令牌
     */
    private static void token(String token) {
        LKAndroidStatics.token = token;
        LKSharedPreferences.putString(LKSharedPreferences.TOKEN, token);
    }

    /**
     * 获取姓名
     * @return 姓名
     */
    public static String userName() {
        if (userName == null || "".equals(userName)) {
            userName = LKSharedPreferences.getString(LKSharedPreferences.USER_NAME, "");
            if ("".equals(userName)) {
                userName = LKAndroidUtils.getString(R.string.no_user_name);
            }
        }
        return userName;
    }

    /**
     * 设置姓名
     * @param userName 姓名
     */
    public static void userName(String userName) {
        LKAndroidStatics.userName = userName;
        LKSharedPreferences.putString(LKSharedPreferences.USER_NAME, userName);
    }

    /**
     * 获取登录名
     * @return 登录名
     */
    public static String loginName() {
        if (loginName == null || "".equals(loginName)) {
            loginName = LKSharedPreferences.getString(LKSharedPreferences.LOGIN_NAME, "");
            if ("".equals(loginName)) {
                loginName = LKAndroidUtils.getString(R.string.no_login_name);
            }
        }
        return loginName;
    }

    /**
     * 设置登录名
     * @param loginName 登录名
     */
    public static void loginName(String loginName) {
        LKAndroidStatics.loginName = loginName;
        LKSharedPreferences.putString(LKSharedPreferences.LOGIN_NAME, loginName);
    }

    /**
     * 获取等级
     * @return 等级
     */
    public static Integer level() {
        if (level == null) {
            level = LKSharedPreferences.getInt(LKSharedPreferences.LEVEL, 1);
        }
        return level;
    }

    /**
     * 设置等级
     * @param level 等级
     */
    private static void level(int level) {
        LKAndroidStatics.level = level;
        LKSharedPreferences.putInt(LKSharedPreferences.LEVEL, level);
    }

    /**
     * 获取头像
     * @return 头像
     */
    public static String photo() {
        if (photo == null || "".equals(photo)) {
            photo = LKSharedPreferences.getString(LKSharedPreferences.PHOTO, "");
        }
        return photo;
    }

    /**
     * 设置头像
     * @param photo 头像
     */
    @Deprecated
    public static void photo(String photo) {
        LKAndroidStatics.photo = photo;
        LKSharedPreferences.putString(LKSharedPreferences.PHOTO, photo);
    }

    /**
     * 获取动态TAB页列表
     * @return 动态TAB页列表
     */
    public static List<LKDynamicTabBean> dynamicTabs() {
        if (dynamicTabs == null || dynamicTabs.isEmpty()) {
            String dynamicTabsStr = LKSharedPreferences.getString(LKSharedPreferences.TABS, "");
            if (!"".equals(dynamicTabsStr)) {
                dynamicTabs = LKDynamicTabBean.convertListObject(dynamicTabsStr);
            }
        }
        return dynamicTabs;
    }

    /**
     * 设置动态TAB页列表
     * @param dynamicTabs 动态TAB页列表
     */
    public static void dynamicTabs(List<LKDynamicTabBean> dynamicTabs) {
        LKAndroidStatics.dynamicTabs = dynamicTabs;
        StringBuilder sb = new StringBuilder();
        if (dynamicTabs != null && !dynamicTabs.isEmpty()) {
            for (LKDynamicTabBean info : dynamicTabs) {
                sb.append(info.convertString());
            }
        }
        LKSharedPreferences.putString(LKSharedPreferences.TABS, sb.toString());
    }

    /**
     * 保存登录信息
     * @param login 登录信息
     */
    @SuppressWarnings("deprecation")
    public static void saveLoginInfo(LKLoginBean login) {
        if (login == null) {
            userName(null);
            photo(null);
            level(1);
            token(null);
            loginName(null);
            return;
        }
        userName(login.getUserName());
        photo(login.getPhoto());
        level(login.getLevel());
        if (login instanceof AccountLoginOut) {
            token(((AccountLoginOut) login).getToken());
            loginName(((AccountLoginOut) login).getLoginName());
        }
    }

    /**
     * 判断是否登录状态
     * @return true:登录;false:未登录;
     */
    public static boolean isLogin() {
        return loginName != null && !"".equals(loginName) && !LKAndroidUtils.getString(R.string.no_login_name).equals(loginName);
    }

}
