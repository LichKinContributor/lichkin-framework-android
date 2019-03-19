package com.lichkin.framework.app.android.utils;

import android.content.Context;

import com.lichkin.android.core.R;
import com.lichkin.framework.app.android.bean.LKAlertBean;
import com.lichkin.framework.app.android.callback.LKBtnCallback;

/**
 * 提示对话框工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKAlert {

    /**
     * 提示信息
     * @param context 上下文环境
     * @param title 标题信息
     * @param message 提示信息
     * @param btnName 按钮名称
     * @param callback 按钮点击回调方法
     */
    public static void alert(Context context, String title, String message, String btnName, LKBtnCallback callback) {
        new LKDialog(context, true, title, message)
                .setCancelable(false)
                .setNegativeButton(btnName == null ? LKAndroidUtils.getString(R.string.dlg_btn_positive_name) : btnName, callback)
                .show();
    }

    /**
     * 提示信息
     * @param context 上下文环境
     * @param bean 提示信息对象
     */
    public static void alert(Context context, LKAlertBean bean) {
        alert(context, null, bean.getMsg(), null, bean.getDialogCallback());
    }

    /**
     * 提示信息
     * @param context 上下文环境
     * @param title 标题信息
     * @param message 提示信息
     * @param callback 按钮点击回调方法
     */
    public static void alert(Context context, String title, String message, LKBtnCallback callback) {
        alert(context, title, message, null, callback);
    }

    /**
     * 提示信息
     * @param context 上下文环境
     * @param title 标题信息
     * @param message 提示信息
     * @param btnName 按钮名称
     */
    public static void alert(Context context, String title, String message, String btnName) {
        alert(context, title, message, btnName, null);
    }

    /**
     * 提示信息
     * @param context 上下文环境
     * @param title 标题信息
     * @param message 提示信息
     */
    public static void alert(Context context, String title, String message) {
        alert(context, title, message, null, null);
    }

    /**
     * 提示信息
     * @param context 上下文环境
     * @param message 提示信息
     */
    public static void alert(Context context, String message) {
        alert(context, null, message, null, null);
    }

    /**
     * 提示信息
     * @param context 上下文环境
     * @param message 提示信息
     * @param callback 按钮点击回调方法
     */
    public static void alert(Context context, String message, LKBtnCallback callback) {
        alert(context, null, message, null, callback);
    }

}
