package com.lichkin.framework.app.android.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.lichkin.android.core.R;
import com.lichkin.framework.app.android.LKApplication;
import com.lichkin.framework.app.android.bean.LKScreenBean;
import com.lichkin.framework.app.android.callback.LKBtnCallback;
import com.lichkin.framework.app.android.callback.LKCallback;

import java.util.List;
import java.util.Locale;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 常用工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKAndroidUtils {

    /**
     * 获取资源
     * @return 资源
     */
    private static Resources getResources() {
        return LKApplication.getInstance().getResources();
    }


    /**
     * 获取字符串内容
     * @param resId 资源ID
     * @return 字符串内容
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }


    /**
     * 获取大小
     * @param resId 资源ID
     * @return 大小
     */
    public static float getDimension(int resId) {
        return getResources().getDimension(resId);
    }


    /**
     * 获取图片资源
     * @param resId 资源ID
     * @return 图片资源
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }


    /**
     * 获取国际化类型
     * @return 国际化类型
     */
    public static String getLocale() {
        Resources res = LKApplication.getInstance().getResources();
        Locale locale = res.getConfiguration().locale;

        if (locale.equals(Locale.CHINA)) {
            return Locale.CHINA.toString();
        }
        return Locale.ENGLISH.toString();
    }

    /**
     * 获取状态栏高度
     * @return 状态栏高度
     */
    public static float getStatusBarHeight() {
        Resources resources = getResources();
        return resources.getDimension(resources.getIdentifier("status_bar_height", "dimen", "android"));
    }

    /** 导航栏高度 */
    private static int navigationHeight = 154;

    /**
     * 获取导航栏高度
     * @return 导航栏高度
     */
    public static int getNavigationBarHeight() {
        return navigationHeight;
    }

    /**
     * 设置导航栏高度
     * @param navigationHeight 导航栏高度
     */
    public static void setNavigationBarHeight(int navigationHeight) {
        LKAndroidUtils.navigationHeight = navigationHeight;
    }

    /**
     * 获取屏幕像素比例
     * @return 屏幕像素比例
     */
    public static float getDpToPxRatio() {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
    }

    /**
     * 根据dp值获取px值
     * @param dp dp值
     * @return px值
     */
    public static int getPxValueByDpValue(final int dp) {
        return (int) (getDpToPxRatio() * dp + 0.5f);
    }

    /**
     * 根据dp值获取px值
     * @param dp dp值
     * @return px值
     */
    public static int getPxValueByDpValue(final float dp) {
        return (int) (getDpToPxRatio() * dp + 0.5f);
    }

    /**
     * 根据px值获取dp值
     * @param px px值
     * @return dp值
     */
    public static int getDpValueByPxValue(final int px) {
        return (int) (px / getDpToPxRatio() + 0.5f);
    }

    /**
     * 获取屏幕分辨率
     * @return 屏幕分辨率
     */
    public static LKScreenBean getScreenDispaly() {
        WindowManager windowManager = (WindowManager) LKApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            return new LKScreenBean(1080, 1920);
        }

        final DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return new LKScreenBean(dm.widthPixels, dm.heightPixels);
    }

    /**
     * 获取布局填充器
     * @return 布局填充器
     */
    public static LayoutInflater getLayoutInflater() {
        return (LayoutInflater) LKApplication.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 是否安装了应用
     * @param packageName 应用包名
     * @return true:安装了应用;false:没有安装应用;
     */
    public static boolean isAppInstalled(String packageName) {
        final PackageManager packageManager = LKApplication.getInstance().getPackageManager();
        List<PackageInfo> listPackageInfo = packageManager.getInstalledPackages(0);
        if (listPackageInfo != null) {
            for (PackageInfo packageInfo : listPackageInfo) {
                if (packageInfo.packageName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 安装应用
     * @param context 环境上下文
     * @param packageName 应用包名
     * @param packageDescription 应用包没有安装时的描述
     * @param callback 回调函数
     */
    public static void appInstall(Context context, final String packageName, String packageDescription, LKCallback callback) {
        if (isAppInstalled(packageName)) {
            callback.call();
        } else {
            LKAlert.alert(context, packageDescription, new LKBtnCallback() {
                @Override
                public void call(Context context, DialogInterface dialog) {
                    Uri uri = Uri.parse("market://details?id=" + packageName);
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        context.startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private static final String SHARE_URL = LKPropertiesLoader.getString("lichkin.framework.share.url");
    private static final String SHARE_IMAGE_URL = LKPropertiesLoader.getString("lichkin.framework.share.imageUrl");

    /**
     * 分享应用
     * @param context 环境上下文
     */
    public static void appShare(Context context) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(LKAndroidUtils.getString(R.string.app_name));
        oks.setTitleUrl(SHARE_URL);
        oks.setImageUrl(SHARE_IMAGE_URL);
        oks.setText(SHARE_URL);
        oks.setUrl(SHARE_URL);
        oks.setSite(getString(R.string.app_name));
        oks.setSiteUrl(SHARE_URL);
        oks.show(context);
    }

}
