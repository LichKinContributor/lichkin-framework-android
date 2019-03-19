package com.lichkin.framework.app.android.utils;

import android.content.Context;
import android.widget.Toast;

import com.lichkin.framework.app.android.bean.LKToastBean;

/**
 * 提示窗工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKToast {

    /**
     * 提示并显示内容
     * @param context 环境上下文
     * @param text 提示内容
     * @param duration 持续时间
     * @see Toast
     * @deprecated 使用showTip或showError
     */
    @Deprecated
    public static void toast(final Context context, final CharSequence text, int duration) {
        if (duration > 2000) {
            duration = Toast.LENGTH_LONG;
        } else {
            duration = Toast.LENGTH_SHORT;
        }

        Toast.makeText(context, text, duration).show();
    }

    /**
     * 提示并显示内容
     * @param context 环境上下文
     * @param resId 提示内容
     * @param duration 持续时间
     * @see Toast
     * @deprecated 使用showTip或showError
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public static void toast(final Context context, final int resId, final int duration) {
        toast(context, context.getString(resId), duration);
    }

    /**
     * 显示错误信息
     * @param context 环境上下文
     * @param bean 提示窗对象
     * @see Toast
     */
    @SuppressWarnings("deprecation")
    public static void toast(final Context context, final LKToastBean bean) {
        toast(context, bean.getMsg(), bean.getTimeout());
    }

    /**
     * 显示提示信息
     * @param context 环境上下文
     * @param text 提示内容
     * @see Toast
     */
    @SuppressWarnings("deprecation")
    public static void showTip(final Context context, final CharSequence text) {
        toast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示提示信息
     * @param context 环境上下文
     * @param resId 提示内容
     * @see Toast
     */
    @SuppressWarnings("deprecation")
    public static void showTip(final Context context, final int resId) {
        toast(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示错误信息
     * @param context 环境上下文
     * @param text 提示内容
     * @see Toast
     */
    @SuppressWarnings("deprecation")
    public static void showError(final Context context, final CharSequence text) {
        toast(context, text, Toast.LENGTH_LONG);
    }

    /**
     * 显示错误信息
     * @param context 环境上下文
     * @param resId 提示内容
     * @see Toast
     */
    @SuppressWarnings("deprecation")
    public static void showError(final Context context, final int resId) {
        toast(context, resId, Toast.LENGTH_LONG);
    }

}
