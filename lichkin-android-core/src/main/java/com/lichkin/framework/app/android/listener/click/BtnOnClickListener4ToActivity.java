package com.lichkin.framework.app.android.listener.click;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.lichkin.framework.app.android.bean.LKParamsBean;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 按钮点击事件 -> 跳转到Activity
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@RequiredArgsConstructor
@AllArgsConstructor
public class BtnOnClickListener4ToActivity implements View.OnClickListener {

    /** 当前Activity */
    private final Activity activity;
    /** 跳转到Activity */
    private final Class<?> toActivityClass;
    /** 附带参数 */
    private LKParamsBean params;

    /**
     * 构造方法
     * @param fragment 当前Fragment
     * @param toActivityClass 跳转到Activity
     * @param params 附带参数
     */
    public BtnOnClickListener4ToActivity(Fragment fragment, Class<?> toActivityClass, LKParamsBean params) {
        this(fragment.getActivity(), toActivityClass, params);
    }

    /**
     * 构造方法
     * @param fragment 当前Fragment
     * @param toActivityClass 跳转到Activity
     */
    public BtnOnClickListener4ToActivity(Fragment fragment, Class<?> toActivityClass) {
        this(fragment.getActivity(), toActivityClass, null);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, toActivityClass);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.getParams().entrySet()) {
                intent.putExtra(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        activity.startActivity(intent);
    }

}
