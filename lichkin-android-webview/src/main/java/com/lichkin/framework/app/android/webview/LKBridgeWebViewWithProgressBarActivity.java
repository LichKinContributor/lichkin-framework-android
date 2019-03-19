package com.lichkin.framework.app.android.webview;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.lichkin.android.webview.R;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKWebView;
import com.lichkin.framework.defines.LKFrameworkStatics;

/**
 * HTML交互实现类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKBridgeWebViewWithProgressBarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引用布局文件
        setContentView(R.layout.webview);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //获取地址
        String url = getIntent().getStringExtra(LKWebView.KEY_URL);
        LKLog.i(url);

        if (url.contains(LKFrameworkStatics.WEB_MAPPING_PAGES) || url.contains("&LK=true")) {
            //增加自定义实现方法
            LKLog.i("启用交互脚本开始");
            MethodHandler.init(url, this, this);
            LKLog.i("启用交互脚本结束");
        }
    }

}
