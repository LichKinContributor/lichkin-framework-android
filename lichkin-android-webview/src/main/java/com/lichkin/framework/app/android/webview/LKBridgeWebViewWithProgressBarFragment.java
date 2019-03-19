package com.lichkin.framework.app.android.webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lichkin.android.webview.R;

/**
 * HTML交互实现类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@SuppressLint("ValidFragment")
public class LKBridgeWebViewWithProgressBarFragment extends Fragment {

    /** 页面地址 */
    private String url;

    /**
     * 构造方法
     * @param url 页面地址
     */
    public LKBridgeWebViewWithProgressBarFragment(String url) {
        super();
        this.url = url;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.webview, container, false);

        //增加自定义实现方法
        MethodHandler.init(url, this.getActivity(), view);

        return view;
    }

}
