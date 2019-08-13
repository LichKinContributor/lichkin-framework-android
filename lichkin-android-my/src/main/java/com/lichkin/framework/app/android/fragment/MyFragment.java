package com.lichkin.framework.app.android.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichkin.android.my.R;
import com.lichkin.application.invokers.GetDynamicTabs.GetDynamicTabsIn;
import com.lichkin.application.invokers.GetDynamicTabs.GetDynamicTabsInvoker;
import com.lichkin.application.invokers.GetDynamicTabs.GetDynamicTabsOut;
import com.lichkin.application.invokers.GetDynamicTabs.GetDynamicTabsTester;
import com.lichkin.application.invokers.SignIn.SignInIn;
import com.lichkin.application.invokers.SignIn.SignInInvoker;
import com.lichkin.application.invokers.SignIn.SignInOut;
import com.lichkin.application.invokers.SignIn.SignInTester;
import com.lichkin.application.invokers.TokenLogin.TokenLoginIn;
import com.lichkin.application.invokers.TokenLogin.TokenLoginInvoker;
import com.lichkin.application.invokers.TokenLogin.TokenLoginOut;
import com.lichkin.application.invokers.TokenLogin.TokenLoginTester;
import com.lichkin.application.invokers.UploadPhoto.UploadPhotoIn;
import com.lichkin.application.invokers.UploadPhoto.UploadPhotoOut;
import com.lichkin.application.invokers.UploadPhoto.UploadPhotoTester;
import com.lichkin.application.invokers.UploadPhoto.UploadPhotoInvoker;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.LKSharedPreferences;
import com.lichkin.framework.app.android.LoginStatics;
import com.lichkin.framework.app.android.activity.AboutActivity;
import com.lichkin.framework.app.android.activity.BaseMainActivity;
import com.lichkin.framework.app.android.activity.FastLoginActivity;
import com.lichkin.framework.app.android.activity.SettingsActivity;
import com.lichkin.framework.app.android.callback.LKBtnCallback;
import com.lichkin.framework.app.android.callback.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.fragment.dialog.FeedbackDialogFragment;
import com.lichkin.framework.app.android.fragment.dialog.LevelDialogFragment;
import com.lichkin.framework.app.android.fragment.dialog.ScoreDialogFragment;
import com.lichkin.framework.app.android.listener.click.BtnOnClickListener4PopDialogFragment;
import com.lichkin.framework.app.android.listener.click.BtnOnClickListener4ToActivity;
import com.lichkin.framework.app.android.listener.click.BtnOnClickListener4ToWebView;
import com.lichkin.framework.app.android.utils.LKAlert;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKBase64;
import com.lichkin.framework.app.android.utils.LKDialog;
import com.lichkin.framework.app.android.utils.LKImageLoader;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.wang.avi.AVLoadingIndicatorView;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.model.TakePhotoOptions;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.List;

public class MyFragment extends Fragment implements TakePhoto.TakeResultListener, InvokeListener {

    private View view;

    LinearLayout layout_beforeLogin;
    Button btn_login;

    LinearLayout layout_afterLogin;
    ImageView view_photo;
    TextView tx_userName;
    TextView tx_loginName;
    LinearLayout layout_level;

    ImageView btn_signIn;
    AVLoadingIndicatorView btn_signIn_loading;

    private boolean fromActivity = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);

        layout_beforeLogin = view.findViewById(R.id.layout_beforeLogin);
        btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), FastLoginActivity.class), LoginStatics.REQUEST_CODE_LOGIN);
                FragmentActivity activity = getActivity();
                if (activity == null) {
                    return;
                }
                activity.overridePendingTransition(R.anim.pop_in, R.anim.pop_out);
            }
        });

        layout_afterLogin = view.findViewById(R.id.layout_afterLogin);

        // 头像
        view_photo = view.findViewById(R.id.view_photo);
        view_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LKDialog(MyFragment.this.getContext(), true, null, LKAndroidUtils.getString(R.string.photo_choose_title))
                        .setPositiveButton(LKAndroidUtils.getString(R.string.btn_positive_name_gallery), new LKBtnCallback() {
                            @Override
                            public void call(Context context, DialogInterface dialog) {
                                takePhoto(true);
                            }
                        })
                        .setNegativeButton(LKAndroidUtils.getString(R.string.btn_negative_name_camera), new LKBtnCallback() {
                            @Override
                            public void call(Context context, DialogInterface dialog) {
                                takePhoto(false);
                            }
                        })
                        .show();
            }
        });

        tx_userName = view.findViewById(R.id.userName);
        tx_loginName = view.findViewById(R.id.loginName);

        // 等级
        layout_level = view.findViewById(R.id.layout_level);
        layout_level.setOnClickListener(new BtnOnClickListener4PopDialogFragment(this, LevelDialogFragment.class));

        // 签到
        btn_signIn = view.findViewById(R.id.btn_signIn);
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_signIn_click();
            }
        });
        btn_signIn_loading = view.findViewById(R.id.btn_signIn_loading);
        btn_signIn_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_signIn_loading.setVisibility(View.GONE);
                btn_signIn.setVisibility(View.VISIBLE);
            }
        });

        // 评分
        view.findViewById(R.id.btn_score).setOnClickListener(new BtnOnClickListener4PopDialogFragment(this, ScoreDialogFragment.class));

        // 反馈
        view.findViewById(R.id.btn_feedback).setOnClickListener(new BtnOnClickListener4PopDialogFragment(this, FeedbackDialogFragment.class));

        // 关于
        view.findViewById(R.id.btn_about).setOnClickListener(new BtnOnClickListener4ToActivity(this, AboutActivity.class));

        // 分享
        View btnShare=  view.findViewById(R.id.btn_share);
        if (LKPropertiesLoader.getBoolean("lichkin.framework.share.show")){
            btnShare .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LKAndroidUtils.appShare(MyFragment.this.getContext());
                }
            });
        }else{
            btnShare .setVisibility(View.GONE);
        }

        // 积分
        view.findViewById(R.id.btn_accumulate).setOnClickListener(new BtnOnClickListener4ToWebView(true, this, "lichkin.framework.pages.accumulate", null));

        // 设置
        View btnSettings = view.findViewById(R.id.btn_settings);
        if (LKPropertiesLoader.getBoolean("lichkin.framework.settings.show")){
            btnSettings.setOnClickListener(new BtnOnClickListener4ToActivity(this, SettingsActivity.class));
        }else{
            btnSettings .setVisibility(View.GONE);
        }

        // 退出
        view.findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LKAndroidStatics.isLogin()) {
                    new LKDialog(MyFragment.this.getContext(), true, null, LKAndroidUtils.getString(R.string.fragment_my__tip_exit))
                            .setPositiveButton(new LKBtnCallback() {
                                @Override
                                public void call(Context context, DialogInterface dialog) {
                                    clearMyInfo();
                                }
                            })
                            .setNegativeButton(null)
                            .show();
                } else {
                    LKAlert.alert(MyFragment.this.getContext(), LKAndroidUtils.getString(R.string.not_login));
                }
            }
        });

        //初始化我的信息
        initMyInfo();

        //更新个人信息
        String token = LKAndroidStatics.token();
        if (token != null && !"".equals(token) && !fromActivity) {
            invokeTokenLogin();
        }

        fromActivity = false;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (LKSharedPreferences.getBoolean("firstLaunchMy", true)) {
            LKSharedPreferences.putBoolean("firstLaunchMy", false);
            startActivityForResult(new Intent(getContext(), FastLoginActivity.class), LoginStatics.REQUEST_CODE_LOGIN);
            FragmentActivity activity = getActivity();
            if (activity == null) {
                return;
            }
            activity.overridePendingTransition(R.anim.pop_in, R.anim.pop_out);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        fromActivity = true;
        if (requestCode == LoginStatics.REQUEST_CODE_LOGIN && resultCode == LoginStatics.RESULT_CODE_LOGIN) {
            initMyInfo();
            invokeGetDynamicTabs();
        }
        takePhoto.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 请求令牌登录
     */
    private void invokeTokenLogin() {
        //请求参数
        TokenLoginIn in = new TokenLoginIn();

        //创建请求对象
        final LKRetrofit<TokenLoginIn, TokenLoginOut> retrofit = new LKRetrofit<>(this.getContext(), TokenLoginInvoker.class);

        //测试代码
        TokenLoginTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<TokenLoginIn, TokenLoginOut>() {

            @Override
            protected void success(Context context, TokenLoginIn TokenLoginIn, TokenLoginOut responseDatas) {
                LKAndroidStatics.saveLoginInfo(responseDatas);
                //初始化我的信息
                initMyInfo();
                invokeGetDynamicTabs();
            }

            @Override
            protected void busError(Context context, TokenLoginIn TokenLoginIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, TokenLoginIn, errorCode, errorType, errorBean);
                clearMyInfo();
                startActivityForResult(new Intent(getContext(), FastLoginActivity.class), LoginStatics.REQUEST_CODE_LOGIN);
                FragmentActivity activity = getActivity();
                if (activity == null) {
                    return;
                }
                activity.overridePendingTransition(R.anim.pop_in, R.anim.pop_out);
            }

            @Override
            public void connectError(Context context, String requestId, TokenLoginIn TokenLoginIn, DialogInterface dialog) {
                super.connectError(context, requestId, TokenLoginIn, dialog);
            }

            @Override
            public void timeoutError(Context context, String requestId, TokenLoginIn TokenLoginIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, TokenLoginIn, dialog);
            }

        });
    }

    /**
     * 初始化我的信息
     */
    private void initMyInfo() {
        String token = LKAndroidStatics.token();
        if (token == null || "".equals(token)) {
            initMyInfoExtendsWhenNoToken();
            return;
        }

        initMyInfoExtends();

        String photo = LKAndroidStatics.photo();
        if (photo != null && !"".equals(photo)) {
            if ("photo".equals(photo)) {
                view_photo.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.demo_photo));
            } else {
                view_photo.setImageBitmap(LKBase64.toBitmap(photo));
            }
        }

        tx_userName.setText(LKAndroidStatics.userName());
        tx_loginName.setText(LKAndroidStatics.loginName());

        layout_level.removeAllViews();
        LevelDialogFragment.inflateLevel(layout_level, LKAndroidStatics.level());

        layout_beforeLogin.setVisibility(View.GONE);
        layout_afterLogin.setVisibility(View.VISIBLE);
    }

    protected void initMyInfoExtends() {
    }

    protected void initMyInfoExtendsWhenNoToken() {
    }

    protected void clearMyInfoExtends() {
    }

    /**
     * 请求获取动态TAB页
     */
    private void invokeGetDynamicTabs() {
        //请求参数
        GetDynamicTabsIn in = new GetDynamicTabsIn();

        //创建请求对象
        final LKRetrofit<GetDynamicTabsIn, List<GetDynamicTabsOut>> retrofit = new LKRetrofit<>(this.getContext(), GetDynamicTabsInvoker.class);

        //测试代码
        GetDynamicTabsTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<GetDynamicTabsIn, List<GetDynamicTabsOut>>() {

            @Override
            protected void success(Context context, GetDynamicTabsIn GetDynamicTabsIn, List<GetDynamicTabsOut> responseDatas) {
                BaseMainActivity.activity.handleDynamicTabs(responseDatas);
            }

            @Override
            protected void busError(Context context, GetDynamicTabsIn GetDynamicTabsIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, GetDynamicTabsIn, errorCode, errorType, errorBean);
                clearMyInfo();
            }

            @Override
            public void connectError(Context context, String requestId, GetDynamicTabsIn GetDynamicTabsIn, DialogInterface dialog) {
                super.connectError(context, requestId, GetDynamicTabsIn, dialog);
                BaseMainActivity.activity.handleDynamicTabs(null);
            }

            @Override
            public void timeoutError(Context context, String requestId, GetDynamicTabsIn GetDynamicTabsIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, GetDynamicTabsIn, dialog);
                BaseMainActivity.activity.handleDynamicTabs(null);
            }

        });
    }

    /**
     * 清除我的信息
     */
    private void clearMyInfo() {
        LKAndroidStatics.saveLoginInfo(null);
        clearMyInfoExtends();

        view_photo.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.no_photo));

        tx_userName.setText("");
        tx_loginName.setText("");

        layout_level.removeAllViews();

        layout_beforeLogin.setVisibility(View.VISIBLE);
        layout_afterLogin.setVisibility(View.GONE);

        BaseMainActivity.activity.handleDynamicTabs(null);
    }

    /**
     * 请求签到
     */
    private void btn_signIn_click() {
        beforeInvokeSignIn();

        //请求参数
        SignInIn in = new SignInIn();

        //创建请求对象
        final LKRetrofit<SignInIn, SignInOut> retrofit = new LKRetrofit<>(this.getContext(), SignInInvoker.class);

        //测试代码
        SignInTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<SignInIn, SignInOut>() {

            @Override
            protected void success(Context context, SignInIn SignInIn, SignInOut responseDatas) {
                afterInvokeSignIn();
                LKToast.showTip(MyFragment.this.getContext(), R.string.fragment_my__tip_sign_in_success);
            }

            @Override
            protected void busError(Context context, SignInIn SignInIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, SignInIn, errorCode, errorType, errorBean);
                afterInvokeSignIn();
            }

            @Override
            public void connectError(Context context, String requestId, SignInIn SignInIn, DialogInterface dialog) {
                super.connectError(context, requestId, SignInIn, dialog);
                afterInvokeSignIn();
            }

            @Override
            public void timeoutError(Context context, String requestId, SignInIn SignInIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, SignInIn, dialog);
                afterInvokeSignIn();
            }

        });
    }

    /**
     * 开始请求签到
     */
    private void beforeInvokeSignIn() {
        btn_signIn.setVisibility(View.GONE);
        btn_signIn_loading.setVisibility(View.VISIBLE);
    }

    /**
     * 结束请求签到
     */
    private void afterInvokeSignIn() {
        btn_signIn.setVisibility(View.VISIBLE);
        btn_signIn_loading.setVisibility(View.GONE);
    }

    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private String imgDir = Environment.getExternalStorageDirectory() + "/temp";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        takePhoto.onCreate(savedInstanceState);
        File imgDirFile = new File(imgDir);
        if (!imgDirFile.exists() && !imgDirFile.mkdirs()) {
            LKToast.showTip(MyFragment.this.getContext(), R.string.can_not_use_storage);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        takePhoto.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void takeSuccess(TResult result) {
        String imgPath = "file:///" + result.getImage().getCompressPath();
        //noinspection deprecation
        LKAndroidStatics.photo(LKBase64.toBase64(imgPath));
        LKImageLoader.load(imgPath, view_photo);
        invokePhotoUpload();
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    /**
     * 调用取照片
     * @param fromGralley true:相册;false:拍照.
     */
    private void takePhoto(boolean fromGralley) {
        Uri uri = Uri.fromFile(new File(imgDir, "/" + System.currentTimeMillis() + ".jpg"));
        takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(102400)
                .setMaxPixel(512)
                .enableReserveRaw(false)
                .create(), false);
        takePhoto.setTakePhotoOptions(new TakePhotoOptions.Builder().setWithOwnGallery(false).create());
        CropOptions cropOptions = new CropOptions.Builder().setAspectX(512).setAspectY(512).setOutputX(512).setOutputY(512).setWithOwnCrop(false).create();
        if (fromGralley) {
            takePhoto.onPickFromGalleryWithCrop(uri, cropOptions);
        } else {
            takePhoto.onPickFromCaptureWithCrop(uri, cropOptions);
        }
    }

    /**
     * 请求上传头像
     */
    private void invokePhotoUpload() {
        //请求参数
        UploadPhotoIn in = new UploadPhotoIn(LKAndroidStatics.photo());

        //创建请求对象
        final LKRetrofit<UploadPhotoIn, UploadPhotoOut> retrofit = new LKRetrofit<>(this.getActivity(), UploadPhotoInvoker.class);

        //测试代码
        UploadPhotoTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<UploadPhotoIn, UploadPhotoOut>() {

            @Override
            protected void success(Context context, UploadPhotoIn PhotoUploadIn, UploadPhotoOut responseDatas) {
            }

            @Override
            protected void busError(Context context, UploadPhotoIn PhotoUploadIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
            }

            @Override
            public void connectError(Context context, String requestId, UploadPhotoIn PhotoUploadIn, DialogInterface dialog) {
            }

            @Override
            public void timeoutError(Context context, String requestId, UploadPhotoIn PhotoUploadIn, DialogInterface dialog) {
            }

        });
    }

}