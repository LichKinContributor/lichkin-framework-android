package com.lichkin.framework.app.android.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lichkin.android.my.R;
import com.lichkin.framework.app.android.listener.click.BtnOnClickListener4ToWebView;

/**
 * 设置页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 引用布局文件
        setContentView(R.layout.activity_settings);

        // 只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // 点击事件
        findViewById(R.id.btn_account_and_security).setOnClickListener(new BtnOnClickListener4ToWebView(true, this, "lichkin.framework.pages.securityCenter", null));
    }

}
