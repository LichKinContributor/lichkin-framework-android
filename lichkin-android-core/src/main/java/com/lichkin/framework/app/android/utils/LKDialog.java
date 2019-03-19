package com.lichkin.framework.app.android.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.lichkin.android.core.R;
import com.lichkin.framework.app.android.callback.LKBtnCallback;

/**
 * 对话框工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKDialog {

    /** 上下文环境 */
    private Context context;

    /** 对话框构建对象 */
    private AlertDialog.Builder builder;

    /** 弹窗对象 */
    private Dialog dialog;

    /**
     * 构造方法
     * @param context 上下文环境
     * @param withTitle 是否包含标题
     * @param title 标题
     * @param message 提示信息
     */
    public LKDialog(Context context, boolean withTitle, String title, String message) {
        this.context = context;
        this.builder = new AlertDialog.Builder(context);
        if (withTitle) {
            this.builder.setTitle(title == null ? LKAndroidUtils.getString(R.string.dlg_tip_title) : title);
        }
        this.builder.setMessage(message);
    }

    /**
     * 设置是否可以在点击对话框以外的地方退出对话框
     * @param cancelable 是否可以
     * @return 对话框对象
     */
    public LKDialog setCancelable(boolean cancelable) {
        builder.setCancelable(cancelable);
        return this;
    }

    /**
     * 设置确定按钮
     * @param btnName 按钮名称
     * @param callback 回调方法
     * @return 对话框对象
     */
    public LKDialog setPositiveButton(String btnName, final LKBtnCallback callback) {
        builder.setPositiveButton(btnName == null ? LKAndroidUtils.getString(R.string.dlg_btn_positive_name) : btnName, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    try {
                        callback.call(context, dialog);
                    } catch (Exception e) {
                    }
                }
            }

        });
        return this;
    }

    /**
     * 设置确定按钮
     * @param callback 回调方法
     * @return 对话框对象
     */
    public LKDialog setPositiveButton(final LKBtnCallback callback) {
        return setPositiveButton(null, callback);
    }

    /**
     * 设置取消按钮
     * @param btnName 按钮名称
     * @param callback 回调方法
     * @return 对话框对象
     */
    public LKDialog setNegativeButton(String btnName, final LKBtnCallback callback) {
        builder.setNegativeButton(btnName == null ? LKAndroidUtils.getString(R.string.dlg_btn_negative_name) : btnName, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    try {
                        callback.call(context, dialog);
                    } catch (Exception e) {
                    }
                }
            }

        });
        return this;
    }

    /**
     * 设置取消按钮
     * @param callback 回调方法
     * @return 对话框对象
     */
    public LKDialog setNegativeButton(final LKBtnCallback callback) {
        return setNegativeButton(null, callback);
    }

    /**
     * 显示对话框
     */
    public void show() {
        dialog = builder.show();
    }

    /**
     * 关闭对话框
     */
    public void close() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


}
