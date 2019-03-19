package com.lichkin.framework.app.android.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lichkin.android.my.R;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.listener.click.BtnOnClickListener4ToWebView;

/**
 * 关于页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class AboutActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 引用布局文件
        setContentView(R.layout.activity_about);

        // 只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // 版本号
        TextView tx_versions = findViewById(R.id.tx_versions);
        tx_versions.setText("Version " + LKAndroidStatics.versionX() + "." + LKAndroidStatics.versionY() + "." + LKAndroidStatics.versionZ());

        // 点击事件
        findViewById(R.id.btn_versions).setOnClickListener(new BtnOnClickListener4ToWebView(this, "lichkin.framework.pages.versions"));
        findViewById(R.id.btn_service_agreement).setOnClickListener(new BtnOnClickListener4ToWebView(this, "lichkin.framework.pages.serviceAgreement"));
        findViewById(R.id.btn_privacy_right_policy).setOnClickListener(new BtnOnClickListener4ToWebView(this, "lichkin.framework.pages.privacyRightPolicy"));
    }

}
