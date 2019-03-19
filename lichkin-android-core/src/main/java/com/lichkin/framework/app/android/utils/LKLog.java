package com.lichkin.framework.app.android.utils;

import android.util.Log;

import com.lichkin.framework.app.android.bean.LKLogBean;

/**
 * 日志工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKLog {

    /** 日志标识 */
    private static final String TAG = "LichKin";

    /**
     * 输出日志
     * @param subTag 子标识
     * @param bean 日志对象
     * @see Log
     */
    public static void log(String subTag, LKLogBean bean) {
        String msg = bean.getMsg();
        switch (bean.getType()) {
            case "verbose":
                v(subTag, msg);
                break;
            case "debug":
                d(subTag, msg);
                break;
            case "info":
                i(subTag, msg);
                break;
            case "warn":
                w(subTag, msg);
                break;
            case "error":
            case "assert":
            default:
                e(subTag, msg);
                break;
        }
    }

    /**
     * verbose日志
     * @param msg 日志内容
     * @see Log
     */
    public static void v(String msg) {
        Log.v(TAG, msg);
    }

    /**
     * verbose日志
     * @param subTag 子标识
     * @param msg 日志内容
     * @see Log
     */
    public static void v(String subTag, String msg) {
        Log.v(TAG + "-" + subTag, msg);
    }

    /**
     * verbose日志
     * @param msg 日志内容
     * @param t 异常对象
     * @see Log
     */
    public static void v(String msg, Throwable t) {
        Log.v(TAG, msg, t);
    }

    /**
     * verbose日志
     * @param subTag 子标识
     * @param msg 日志内容
     * @param t 异常对象
     * @see Log
     */
    public static void v(String subTag, String msg, Throwable t) {
        Log.v(TAG + "-" + subTag, msg, t);
    }

    /**
     * debug日志
     * @param msg 日志内容
     * @see Log
     */
    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    /**
     * debug日志
     * @param subTag 子标识
     * @param msg 日志内容
     * @see Log
     */
    public static void d(String subTag, String msg) {
        Log.d(TAG + "-" + subTag, msg);
    }

    /**
     * debug日志
     * @param msg 日志内容
     * @param t 异常对象
     * @see Log
     */
    public static void d(String msg, Throwable t) {
        Log.d(TAG, msg, t);
    }

    /**
     * debug日志
     * @param subTag 子标识
     * @param msg 日志内容
     * @param t 异常对象
     * @see Log
     */
    public static void d(String subTag, String msg, Throwable t) {
        Log.d(TAG + "-" + subTag, msg, t);
    }

    /**
     * info日志
     * @param msg 日志内容
     * @see Log
     */
    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    /**
     * info日志
     * @param subTag 子标识
     * @param msg 日志内容
     * @see Log
     */
    public static void i(String subTag, String msg) {
        Log.i(TAG + "-" + subTag, msg);
    }

    /**
     * info日志
     * @param msg 日志内容
     * @param t 异常对象
     * @see Log
     */
    public static void i(String msg, Throwable t) {
        Log.i(TAG, msg, t);
    }

    /**
     * info日志
     * @param subTag 子标识
     * @param msg 日志内容
     * @param t 异常对象
     * @see Log
     */
    public static void i(String subTag, String msg, Throwable t) {
        Log.i(TAG + "-" + subTag, msg, t);
    }

    /**
     * warning日志
     * @param msg 日志内容
     * @see Log
     */
    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    /**
     * warning日志
     * @param subTag 子标识
     * @param msg 日志内容
     * @see Log
     */
    public static void w(String subTag, String msg) {
        Log.w(TAG + "-" + subTag, msg);
    }

    /**
     * warning日志
     * @param msg 日志内容
     * @param t 异常对象
     * @see Log
     */
    public static void w(String msg, Throwable t) {
        Log.w(TAG, msg, t);
    }

    /**
     * warning日志
     * @param subTag 子标识
     * @param msg 日志内容
     * @param t 异常对象
     * @see Log
     */
    public static void w(String subTag, String msg, Throwable t) {
        Log.w(TAG + "-" + subTag, msg, t);
    }

    /**
     * error日志
     * @param msg 日志内容
     * @see Log
     */
    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    /**
     * error日志
     * @param subTag 子标识
     * @param msg 日志内容
     * @see Log
     */
    public static void e(String subTag, String msg) {
        Log.e(TAG + "-" + subTag, msg);
    }

    /**
     * error日志
     * @param msg 日志内容
     * @param t 异常对象
     * @see Log
     */
    public static void e(String msg, Throwable t) {
        Log.e(TAG, msg, t);
    }

    /**
     * error日志
     * @param subTag 子标识
     * @param msg 日志内容
     * @param t 异常对象
     * @see Log
     */
    public static void e(String subTag, String msg, Throwable t) {
        Log.e(TAG + "-" + subTag, msg, t);
    }

}
