package com.lichkin.android.demo.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lichkin.android.R;
import com.lichkin.framework.app.android.bean.LKAlertBean;
import com.lichkin.framework.app.android.bean.LKLogBean;
import com.lichkin.framework.app.android.bean.LKToastBean;
import com.lichkin.framework.app.android.callback.LKBtnCallback;
import com.lichkin.framework.app.android.utils.LKAlert;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.app.android.utils.LKWebView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ListItemDemoFragment extends Fragment {

    private View view;
    private Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_demo_list_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Log)
    void Log_OnClick() {
        LKLog.v("verbose");
        LKLog.d("debug");
        LKLog.i("info");
        LKLog.w("warn");
        LKLog.e("error");
        LKLog.log("demo", new LKLogBean("verbose", "verbose"));
        LKLog.log("demo", new LKLogBean("debug", "debug"));
        LKLog.log("demo", new LKLogBean("info", "info"));
        LKLog.log("demo", new LKLogBean("warn", "warn"));
        LKLog.log("demo", new LKLogBean("error", "error"));
    }

    @OnClick(R.id.Toast1)
    void Toast1_OnClick() {
        LKToast.showTip(this.getContext(), "Toast1");
    }

    @OnClick(R.id.Toast2)
    void Toast2_OnClick() {
        LKToast.toast(this.getContext(), new LKToastBean("Toast2"));
    }

    @OnClick(R.id.Toast3)
    void Toast3_OnClick() {
        LKToast.toast(this.getContext(), new LKToastBean("Toast3", 3000));
    }

    @OnClick(R.id.Alert1)
    void Alert1_OnClick() {
        LKAlert.alert(this.getContext(), "Alert1");
    }

    @OnClick(R.id.Alert2)
    void Alert2_OnClick() {
        LKAlert.alert(this.getContext(), "Alert2", new LKBtnCallback() {
            @Override
            public void call(Context context, DialogInterface dialog) {
                LKToast.showTip(ListItemDemoFragment.this.getContext(), "Callback");
            }
        });
    }

    @OnClick(R.id.Alert3)
    void Alert3_OnClick() {
        LKAlert.alert(this.getContext(), new LKAlertBean("Alert3"));
    }

    @OnClick(R.id.Alert4)
    void Alert4_OnClick() {
        LKAlert.alert(this.getContext(), new LKAlertBean("Alert4", new LKBtnCallback() {
            @Override
            public void call(Context context, DialogInterface dialog) {
                LKToast.showTip(ListItemDemoFragment.this.getContext(), "Callback");
            }
        }));
    }

    @OnClick(R.id.WebView)
    void WebView_OnClick() {
        LKWebView.open(this.getActivity(), LKPropertiesLoader.testWebViewUrl);
    }

    @OnClick(R.id.Share)
    void Share_OnClick() {
        LKAndroidUtils.appShare(getContext());
    }

}
