package com.lichkin.framework.app.android.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.lichkin.android.main.R;
import com.lichkin.application.invokers.GetDynamicTabs.GetDynamicTabsOut;
import com.lichkin.application.invokers.GetLastAppVersion.GetLastAppVersionIn;
import com.lichkin.application.invokers.GetLastAppVersion.GetLastAppVersionInvoker;
import com.lichkin.application.invokers.GetLastAppVersion.GetLastAppVersionOut;
import com.lichkin.application.invokers.GetLastAppVersion.GetLastAppVersionTester;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.bean.LKDynamicTabBean;
import com.lichkin.framework.app.android.callback.LKBtnCallback;
import com.lichkin.framework.app.android.callback.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAlert;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKBase64;
import com.lichkin.framework.app.android.utils.LKDialog;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKWebView;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.lichkin.framework.defines.beans.LKFieldErrorMessageBean;
import com.lichkin.framework.defines.func.BeanConverter;
import com.lichkin.framework.utils.LKListUtils;
import com.lichkin.framework.utils.LKRandomUtils;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

/**
 * 基本MainActivity
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class BaseMainActivity extends BasePermissionActivity {

    /** 本对象 */
    public static BaseMainActivity activity;
    /** 导航栏页面ID */
    private static final int NAVIGATION_MENU_GROUP_ID = Menu.NONE;
    /** 所有页面 */
    private Map<Integer, TabPage> allTabPage = new HashMap<>();
    /** 当前显示的页面 */
    public List<TabPage> listShowedTabPage = new ArrayList<>();
    /** 固定页面 */
    public List<TabPage> listFixedTabPage = new ArrayList<>();
    /** 动态页面 */
    public List<TabPage> listDynamicTabPage = new ArrayList<>();
    /** 首次创建 */
    private boolean create = true;
    /** 导航栏对象 */
    private BottomNavigationView navigation;
    /** 滑动页面 */
    private ViewPager viewPager;
    /** 适配器 */
    private FragmentPagerAdapter viewPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

        @Override
        public Fragment getItem(int position) {
            return listShowedTabPage.get(position).fragment;
        }

        @Override
        public int getCount() {
            return listShowedTabPage.size();
        }

        @Override
        public long getItemId(int position) {
            return listShowedTabPage.get(position).hashCode();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return PagerAdapter.POSITION_NONE;
        }

    };

    /**
     * 是否可滑动
     * @return true:可滑动;false:不可滑动;
     */
    protected boolean scrollable() {
        return false;
    }

    /** 强制更新请求码 */
    private static final int REQUEST_CODE_FORCE_UPDATE = 0x00000006;
    /** 扫描请求码 */
    public static final int REQUEST_CODE_SCAN = 0x00000010;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_FORCE_UPDATE: {
                System.exit(0);
            }
            break;
            case REQUEST_CODE_SCAN: {
                if (data == null) {
                    return;
                }
                final String result = data.getStringExtra(Constant.CODED_CONTENT);
                if (StringUtils.isBlank(result)) {
                    return;
                }
                if (result.startsWith("http") && result.contains(LKFrameworkStatics.WEB_MAPPING_PAGES)) {
                    LKWebView.open(this, result);
                } else {
                    LKAlert.alert(this, LKAndroidUtils.getString(R.string.dlg_tip_title), result, LKAndroidUtils.getString(R.string.dlg_btn_positive_name_copy_to_clipboard), new LKBtnCallback() {
                        @Override
                        public void call(Context context, DialogInterface dialog) {
                            ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("text", result));
                        }
                    });
                }
            }
            break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        create = false;

        if (LKPropertiesLoader.testWebView) {
            LKWebView.open(this, LKPropertiesLoader.testWebViewUrl);
            return;
        }

        if (dontNeedRequestPermission(Manifest.permission.INTERNET)) {
            //有权限请求接口
            getLastAppVersion();
        }

        String token = LKAndroidStatics.token();
        if (token == null || "".equals(token)) {
            return;
        }

        showDynamicTabs(LKAndroidStatics.dynamicTabs());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化静态值
        initStatics();

        //当前对象赋值
        activity = this;

        //引用布局文件
        setContentView(R.layout.activity_main);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //初始化Tab页面
        initTabs();

        //初始化标题栏
        initTopTitleView();

        //初始化导航栏
        initBottomNavigationView();

        //初始化滑动页面
        initViewPager();

        //显示固定页
        showFixedTabs();
    }

    /**
     * 初始化静态值
     */
    @SuppressWarnings("deprecation")
    private void initStatics() {
        String packageName = this.getPackageName();
        //客户端唯一标识
        LKAndroidStatics.appKey(packageName);

        PackageManager packageManager = this.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);

            //客户端SDK版本
            LKAndroidStatics.targetSdkVersion(packageInfo.applicationInfo.targetSdkVersion);

            //客户端版本号
            String[] versionArr = packageInfo.versionName.split("\\.");
            LKAndroidStatics.versionX(Byte.parseByte(versionArr[0]));
            LKAndroidStatics.versionY(Byte.parseByte(versionArr[1]));
            LKAndroidStatics.versionZ(Short.parseShort(versionArr[2]));
        } catch (PackageManager.NameNotFoundException e) {
            //客户端SDK版本
            LKAndroidStatics.targetSdkVersion(-1);

            //客户端版本号
            LKAndroidStatics.versionX((byte) 1);
            LKAndroidStatics.versionY((byte) 0);
            LKAndroidStatics.versionZ((short) 0);
        }

        //屏幕宽高
        Object wm = this.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) wm).getDefaultDisplay().getMetrics(dm);
            LKAndroidStatics.screenWidth((short) dm.widthPixels);
            LKAndroidStatics.screenHeight((short) dm.heightPixels);
        } else {
            LKAndroidStatics.screenWidth((short) -1);
            LKAndroidStatics.screenHeight((short) -1);
        }
    }

    /**
     * 处理动态TAB页
     * @param dynamicTabOuts 动态TAB页
     */
    public void handleDynamicTabs(List<GetDynamicTabsOut> dynamicTabOuts) {
        List<LKDynamicTabBean> savedDynamicTabs = LKAndroidStatics.dynamicTabs();
        if (dynamicTabOuts == null || dynamicTabOuts.isEmpty()) {
            if (savedDynamicTabs == null || savedDynamicTabs.isEmpty()) {
                LKLog.d("dynamicTabs -> handle null, saved null. nothing to do.");
            } else {
                LKLog.d("dynamicTabs -> handle null, saved not null. do clear.");
                LKAndroidStatics.dynamicTabs(null);
                hideDynamicTabs();
            }
        } else {
            List<LKDynamicTabBean> dynamicTabs = LKListUtils.convert(dynamicTabOuts, new BeanConverter<GetDynamicTabsOut, LKDynamicTabBean>() {
                @Override
                public LKDynamicTabBean convert(GetDynamicTabsOut dynamicTabsOut) {
                    return dynamicTabsOut.toBean();
                }
            });

            if (savedDynamicTabs == null || savedDynamicTabs.isEmpty()) {
                LKLog.d("dynamicTabs -> handle not null, saved null. do add.");
                LKAndroidStatics.dynamicTabs(dynamicTabs);
                showDynamicTabs(dynamicTabs);
            } else {
                if (dynamicTabs.size() != savedDynamicTabs.size()) {
                    LKLog.d("dynamicTabs -> handle not null, saved not null. size not match, do clear and add.");
                    LKAndroidStatics.dynamicTabs(dynamicTabs);
                    hideDynamicTabs();
                    showDynamicTabs(dynamicTabs);
                    return;
                }

                out:
                for (LKDynamicTabBean dynamicTab : dynamicTabs) {
                    for (LKDynamicTabBean savedDynamicTab : savedDynamicTabs) {
                        if (dynamicTab.getTabId().equals(savedDynamicTab.getTabId())) {
                            continue out;
                        }
                    }
                    LKLog.d("dynamicTabs -> handle not null, saved not null. size match but tab not match, do clear and add.");
                    LKAndroidStatics.dynamicTabs(dynamicTabs);
                    hideDynamicTabs();
                    showDynamicTabs(dynamicTabs);
                    return;
                }
                LKLog.d("dynamicTabs -> handle not null, saved not null. size match and tab match, nothing to do.");
            }
        }
    }

    @Override
    protected void onRequestPermissionResultGranted(String permissionName) {
        super.onRequestPermissionResultGranted(permissionName);
        switch (permissionName) {
            case Manifest.permission.INTERNET:
                //授权通过后请求接口
                getLastAppVersion();
                break;
        }
    }

    /** 更新对话框开启过 */
    private boolean dlgUpdateOpened = false;
    /** 更新对话框关闭过 */
    private boolean dlgUpdateClosed = false;

    @Override
    protected void onRequestPermissionResultNotGranted(String permissionName) {
        super.onRequestPermissionResultNotGranted(permissionName);
        switch (permissionName) {
            case Manifest.permission.INTERNET:
                //无网络权限则重启
                restart();
                break;
        }
    }

    /**
     * 重启
     */
    private void restart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    /**
     * 获取最新客户端版本信息
     */
    private void getLastAppVersion() {
        //开启过或者关闭过都不再执行版本更新操作
        if (dlgUpdateOpened || dlgUpdateClosed) {
            return;
        }

        //强制使用主线程
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //请求参数
        GetLastAppVersionIn in = new GetLastAppVersionIn();

        //创建请求对象
        final LKRetrofit<GetLastAppVersionIn, GetLastAppVersionOut> retrofit = new LKRetrofit<>(this, GetLastAppVersionInvoker.class, "LichKin");

        //测试代码
        GetLastAppVersionTester.test(retrofit);

        //执行请求
        retrofit.callSync(in, new LKBaseInvokeCallback<GetLastAppVersionIn, GetLastAppVersionOut>() {

            @Override
            protected void success(Context context, GetLastAppVersionIn getLastAppVersionIn, final GetLastAppVersionOut responseDatas) {
                if (responseDatas == null) {
                    return;
                }
                final boolean forceUpdate = responseDatas.isForceUpdate();
                String tip = responseDatas.getTip();
                LKDialog dlg = new LKDialog(context, true, LKAndroidUtils.getString(R.string.dlg_update_tip_title), tip).setCancelable(false);
                dlg.setPositiveButton(LKAndroidUtils.getString(R.string.dlg_update_btn_positive_name), new LKBtnCallback() {
                    @Override
                    public void call(Context context, DialogInterface dialog) {
                        dlgUpdateClosed = true;
                        if (forceUpdate) {
                            LKWebView.open(BaseMainActivity.this, responseDatas.getUrl(), REQUEST_CODE_FORCE_UPDATE);
                        } else {
                            LKWebView.open(BaseMainActivity.this, responseDatas.getUrl());
                        }
                    }
                });
                if (forceUpdate) {
                    LKLog.w(tip);
                    dlg.show();
                } else {
                    LKLog.i(tip);
                    dlg.setNegativeButton(new LKBtnCallback() {
                        @Override
                        public void call(Context context, DialogInterface dialog) {
                            dlgUpdateClosed = true;
                        }
                    }).show();
                }
                dlgUpdateOpened = true;
            }

            @Override
            protected void busError(Context context, GetLastAppVersionIn getLastAppVersionIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                switch (errorCode) {
                    case 49999: {//应用已下架
                        String[] errorMessageArr = errorBean.getErrorMessageArr();
                        LKAlert.alert(context, errorMessageArr[1], errorMessageArr[0], errorMessageArr[2], new LKBtnCallback() {
                            @Override
                            public void call(Context context, DialogInterface dialog) {
                                System.exit(0);
                            }
                        });
                    }
                    break;
                    case 49998: {//版本非法，提示信息。
                        String[] errorMessageArr = errorBean.getErrorMessageArr();
                        LKAlert.alert(context, errorMessageArr[1], errorMessageArr[0], errorMessageArr[2]);
                    }
                    break;
                    case 40000://最新版本，不处理。
                        break;
                    case -1://服务器异常
                        LKAlert.alert(context, LKAndroidUtils.getString(R.string.error_INTERNAL_SERVER_ERROR), new LKBtnCallback() {
                            @Override
                            public void call(Context context, DialogInterface dialog) {
                                restart();
                            }
                        });
                        break;
                    default:
                        // 其它错误，待约定与实现。
                        switch (errorType) {
                            case STRING:
                                LKAlert.alert(context, errorBean.getErrorMessage());
                                break;
                            case STRING_ARR:
                                String[] errorMessageArr = errorBean.getErrorMessageArr();
                                for (int i = errorMessageArr.length - 1; i >= 0; i--) {
                                    LKAlert.alert(context, errorMessageArr[i]);
                                }
                                break;
                            case FIELD_ARR:
                                LKFieldErrorMessageBean[] fieldErrorMessageArr = errorBean.getFieldErrorMessageArr();
                                for (int i = fieldErrorMessageArr.length - 1; i >= 0; i--) {
                                    LKFieldErrorMessageBean bean = fieldErrorMessageArr[i];
                                    LKAlert.alert(context, bean.getFieldName(), bean.getErrorMessage());
                                }
                                break;
                        }
                        break;
                }
            }

            @Override
            public void connectError(Context context, String requestId, GetLastAppVersionIn getLastAppVersionIn, DialogInterface dialog) {
                if (dlgUpdateOpened || dlgUpdateClosed) {
                    return;
                }
                LKAlert.alert(context, LKAndroidUtils.getString(R.string.error_NOT_FOUND), new LKBtnCallback() {
                    @Override
                    public void call(Context context, DialogInterface dialog) {
                        restart();
                    }
                });
            }

            @Override
            public void timeoutError(Context context, String requestId, GetLastAppVersionIn getLastAppVersionIn, DialogInterface dialog) {
                if (dlgUpdateOpened || dlgUpdateClosed) {
                    return;
                }
                LKAlert.alert(context, LKAndroidUtils.getString(R.string.error_NOT_FOUND), new LKBtnCallback() {
                    @Override
                    public void call(Context context, DialogInterface dialog) {
                        restart();
                    }
                });
            }

        });
    }

    /**
     * 初始化标题栏
     */
    private void initTopTitleView() {
        // 扫描按钮
        findViewById(R.id.btn_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseMainActivity.this, CaptureActivity.class);
                ZxingConfig config = new ZxingConfig();
                config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
                config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
                config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
    }

    /**
     * 初始化导航栏
     */
    private void initBottomNavigationView() {
        navigation = findViewById(R.id.navigation);

        //设置导航栏高度
        navigation.measure(0, 0);
        LKAndroidUtils.setNavigationBarHeight(navigation.getMeasuredHeight());

        //为导航栏增加监听事件
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                LKLog.d("onNavigationItemSelected -> " + item.getTitle());
                int tabId = item.getItemId();
                TabPage tabPage = allTabPage.get(tabId);
                viewPager.setCurrentItem(listShowedTabPage.indexOf(tabPage));
                onTabSelected(tabPage);
                return true;
            }

        });

        //禁用mShiftingMode
        navigation.setLabelVisibilityMode(1);
    }

    /**
     * 页面点击事件
     * @param tabPage Tab页面对象
     */
    protected abstract void onTabSelected(TabPage tabPage);

    /**
     * 初始化滑动页面
     */
    private void initViewPager() {
        //根据条件选择对应的视图
        viewPager = findViewById(scrollable() ? R.id.viewpager : R.id.noscroll_viewpager);
        //显示该视图
        viewPager.setVisibility(View.VISIBLE);
        //禁止销毁数量
        viewPager.setOffscreenPageLimit(5);
        //添加适配器
        viewPager.setAdapter(viewPagerAdapter);
        //为滑动页面增加监听事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().findItem(listShowedTabPage.get(position).tabId).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }

    /**
     * Tab页面对象
     */
    @RequiredArgsConstructor
    protected class TabPage {
        /** 页面ID */
        private final int tabId;
        /** 标题资源ID */
        public final int titleResId;
        /** 图标资源ID */
        private final int iconResId;
        /** 排序号 */
        private final int orderId;
        /** 页面对象 */
        private final Fragment fragment;
        /** 是否动态页 */
        private final boolean dynamic;
    }

    /**
     * 初始化Tab页面。使用newTab方法进行初始化。
     */
    protected abstract void initTabs();

    /**
     * 初始化对象
     * @param titleResId 标题资源ID
     * @param iconResId 图标资源ID
     * @param fragment 页面对象
     * @param dynamic 是否动态页
     */
    protected void newTab(int titleResId, int iconResId, Fragment fragment, boolean dynamic) {
        int menuId = LKRandomUtils.randomInRange(10, 99);
        if (iconResId == R.drawable.ic_navigation_menu_my) {
            dynamic = false;
        }

        TabPage menuPage = new TabPage(menuId, titleResId, iconResId, allTabPage.size(), fragment, dynamic);
        allTabPage.put(menuId, menuPage);
        if (dynamic) {
            listDynamicTabPage.add(menuPage);
        } else {
            listFixedTabPage.add(menuPage);
        }
    }

    /**
     * 将页面滑动到指定位置
     * @param position 位置
     */
    private void switchTab(final int position) {
        if (listShowedTabPage.isEmpty()) {// 为空时：直接结束
            LKLog.w("已经没有可显示的页面了");
            return;
        }
        viewPager.setCurrentItem(position);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                navigation.getMenu().getItem(position).setChecked(true);
            }
        }, 0);
    }

    /**
     * 显示页面
     * @param menuId 页面ID
     * @param title 标题
     * @param icon 图标
     */
    private void showTab(int menuId, String title, Drawable icon) {
        // 最多支持5个TAB页
        if (listShowedTabPage.size() >= 5) {
            LKLog.w("最多支持5个TAB页");
            return;
        }

        // 已经显示的页面不能做显示页面操作
        for (TabPage menuPage : listShowedTabPage) {
            if (menuPage.tabId == menuId) {
                LKLog.w("已经显示的页面不能做显示页面操作");
                return;
            }
        }

        // 从所有页面中取到页面对象
        TabPage menuPage = allTabPage.get(menuId);

        if (menuPage == null) {
            LKLog.w("没有可用的页面");
            return;
        }

        // 增加页面到导航栏
        navigation.getMenu().add(NAVIGATION_MENU_GROUP_ID, menuId, menuPage.orderId, title).setIcon(icon);

        // 增加页面到ViewPager
        if (listShowedTabPage.isEmpty()) {// 为空时：直接增加
            listShowedTabPage.add(menuPage);
        } else {// 不为空时：按照序号选择应该存入的位置
            boolean added = false;
            for (int i = listShowedTabPage.size() - 1; i >= 0; i--) {
                if (menuPage.orderId > listShowedTabPage.get(i).orderId) {
                    listShowedTabPage.add(i + 1, menuPage);
                    added = true;
                    break;
                }
            }
            if (!added) {
                listShowedTabPage.add(menuPage);
            }
        }

        // 更新ViewPager状态
        viewPagerAdapter.notifyDataSetChanged();

        // 将页面滑动到刚打开的页面
        switchTab(create ? 0 : listShowedTabPage.indexOf(menuPage));
    }

    /**
     * 显示所有固定页面
     */
    private void showFixedTabs() {
        for (TabPage tabPage : listFixedTabPage) {
            showTab(tabPage.tabId, LKAndroidUtils.getString(tabPage.titleResId), LKAndroidUtils.getDrawable(tabPage.iconResId));
        }
    }

    /**
     * 显示动态页面
     * @param dynamicTabs 动态页面对象
     */
    private void showDynamicTabs(List<LKDynamicTabBean> dynamicTabs) {
        if (dynamicTabs == null || dynamicTabs.isEmpty()) {
            LKLog.w("no dynamic tabs.");
            return;
        }

        for (int i = 0; i < dynamicTabs.size(); i++) {
            if (i >= listDynamicTabPage.size()) {
                LKLog.w("没有可用的动态页");
                return;
            }

            LKDynamicTabBean tabInfo = dynamicTabs.get(i);
            Bundle bundle = new Bundle();
            bundle.putString("tabId", tabInfo.getTabId());
            bundle.putString("tabName", tabInfo.getTabName());

            TabPage tabPage = listDynamicTabPage.get(i);
            tabPage.fragment.setArguments(bundle);
            showTab(tabPage.tabId, tabInfo.getTabName(), LKBase64.toDrawable(tabInfo.getTabIcon()));
        }
    }

    /**
     * 隐藏动态页面
     * @param tabId 页面ID
     */
    private void hideDynamicTab(int tabId) {
        // 没有显示的页面不能做隐藏页面操作
        boolean contains = false;
        for (TabPage menuPage : listShowedTabPage) {
            if (menuPage.tabId == tabId) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            LKLog.w("没有显示的页面不能做隐藏页面操作");
            return;
        }

        // 从所有页面中取到页面对象
        TabPage tabPage = allTabPage.get(tabId);

        if (tabPage == null) {
            LKLog.w("没有可用的页面");
            return;
        }

        // 固定的页面不能做隐藏页面操作
        if (!tabPage.dynamic) {
            LKLog.w("固定的页面不能做隐藏页面操作");
            return;
        }

        // 从导航栏移除页面
        navigation.getMenu().removeItem(tabId);

        // 从ViewPager移除页面
        listShowedTabPage.remove(tabPage);

        // 更新ViewPager状态
        viewPagerAdapter.notifyDataSetChanged();

        // 将页面滑动到第一个页面
        switchTab(0);
    }

    /**
     * 隐藏所有动态页面
     */
    private void hideDynamicTabs() {
        for (int idx = 0; idx < listDynamicTabPage.size(); idx++) {
            hideDynamicTab(listDynamicTabPage.get(idx).tabId);
        }
    }

}
