package com.lichkin.android;

import android.Manifest;

import com.lichkin.android.demo.fragment.ListItemDemoFragment;
import com.lichkin.framework.app.android.activity.BaseMainActivity;
import com.lichkin.framework.app.android.fragment.FindFragment;
import com.lichkin.framework.app.android.fragment.MyFragment;
import com.lichkin.framework.app.android.fragment.NewsFragment;
import com.lichkin.framework.app.android.utils.LKAlert;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKWebView;

public class MainActivity extends BaseMainActivity {

    @Override
    protected String[] getNeedPermissions() {
        return new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.CAMERA,
                Manifest.permission.VIBRATE,
                Manifest.permission.WAKE_LOCK,
        };
    }

    @Override
    protected boolean scrollable() {
        return false;
    }

    @Override
    protected void initTabs() {
//        newTab(R.string.title_navigation_menu_home, R.drawable.ic_navigation_menu_home, new ListItemDemoFragment(), false);
        newTab(R.string.title_navigation_menu_find, R.drawable.ic_navigation_menu_find, new FindFragment(), false);
        newTab(R.string.title_navigation_menu_news, R.drawable.ic_navigation_menu_news, new NewsFragment(), false);
        newTab(R.string.title_navigation_menu_game, R.drawable.ic_navigation_menu_game, LKWebView.create(LKPropertiesLoader.getString("lichkin.main.pages.game")), false);

        // 我的页放在最后
        newTab(R.string.title_navigation_menu_my, R.drawable.ic_navigation_menu_my, new MyFragment(), false);
    }

    @Override
    protected void onTabSelected(TabPage tabPage) {
        switch (tabPage.titleResId) {
            case R.string.title_navigation_menu_home:
                LKLog.d("首页");
                break;
            case R.string.title_navigation_menu_find:
                LKLog.d("查找页");
                break;
            case R.string.title_navigation_menu_news:
                LKLog.d("新闻页");
                break;
            case R.string.title_navigation_menu_game:
                LKLog.d("游戏页");
                break;
            case R.string.title_navigation_menu_my:
                LKLog.d("我的页");
                break;
        }
    }

}
