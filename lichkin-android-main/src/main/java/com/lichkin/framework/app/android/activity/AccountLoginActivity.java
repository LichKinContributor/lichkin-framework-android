package com.lichkin.framework.app.android.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichkin.android.main.R;
import com.lichkin.application.invokers.AccountLogin.AccountLoginIn;
import com.lichkin.application.invokers.AccountLogin.AccountLoginInvoker;
import com.lichkin.application.invokers.AccountLogin.AccountLoginOut;
import com.lichkin.application.invokers.AccountLogin.AccountLoginTester;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.LoginStatics;
import com.lichkin.framework.app.android.callback.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.listener.click.BtnOnClickListener4ToWebView;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.lichkin.framework.utils.security.md5.LKMD5Encrypter;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 账号登录页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class AccountLoginActivity extends AppCompatActivity {

    /** 快速登录按钮 */
    TextView view_btnFastLogin;

    /** 用户名 */
    EditText view_loginName;
    /** 密码 */
    EditText view_pwd;
    /** 密码状态控制按钮 */
    ImageView view_btnPwdSwitcher;
    /** 是否显示密码 */
    private boolean eyeClosed = true;

    /** 加载 */
    AVLoadingIndicatorView view_loading;

    /** 下一步按钮 */
    TextView view_btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引用布局文件
        setContentView(R.layout.activity_account_login);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //绑定页面
        view_btnFastLogin = findViewById(R.id.btn_fast_login);
        view_btnFastLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        view_loginName = findViewById(R.id.loginName);
        view_pwd = findViewById(R.id.pwd);
        view_btnPwdSwitcher = findViewById(R.id.btn_pwd_switcher);
        view_btnPwdSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eyeClosed) {
                    view_btnPwdSwitcher.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.ic_btn_eye_opend));
                    view_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    view_btnPwdSwitcher.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.ic_btn_eye_closed));
                    view_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                view_pwd.setSelection(view_pwd.getText().toString().length());
                eyeClosed = !eyeClosed;
            }
        });

        view_loading = findViewById(R.id.loading);

        view_btnNext = findViewById(R.id.btn_next);
        view_btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeAccountLogin();
            }
        });

        //服务协议
        TextView view_btnAgreement = findViewById(R.id.btn_service_agreement);
        view_btnAgreement.setOnClickListener(new BtnOnClickListener4ToWebView(this, "lichkin.framework.pages.serviceAgreement"));
        view_btnAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//增加下划线

        //隐私权政策
        TextView view_btnPrivacyRightPolicy = findViewById(R.id.btn_privacy_right_policy);
        view_btnPrivacyRightPolicy.setOnClickListener(new BtnOnClickListener4ToWebView(this, "lichkin.framework.pages.privacyRightPolicy"));
        view_btnPrivacyRightPolicy.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//增加下划线

        if (LKPropertiesLoader.testForm) {
            view_loginName.setText(LKPropertiesLoader.getString("lichkin.framework.test.form.loginName"));
            view_pwd.setText(LKPropertiesLoader.getString("lichkin.framework.test.form.pwd"));
        }
    }

    /**
     * 请求账号登录
     */
    private void invokeAccountLogin() {
        beforeInvokeAccountLogin();

        //校验用户名
        String loginName = view_loginName.getText().toString().trim();
        if ("".equals(loginName)) {
            LKToast.showTip(AccountLoginActivity.this, R.string.hint_loginName_or_cellphone);
            afterInvokeAccountLogin();
            return;
        }

        //校验密码
        String pwd = view_pwd.getText().toString().trim();
        if ("".equals(pwd)) {
            LKToast.showTip(AccountLoginActivity.this, R.string.hint_pwd);
            afterInvokeAccountLogin();
            return;
        }

        //请求参数
        AccountLoginIn in = new AccountLoginIn(loginName, LKMD5Encrypter.encrypt(pwd));

        //创建请求对象
        final LKRetrofit<AccountLoginIn, AccountLoginOut> retrofit = new LKRetrofit<>(this, AccountLoginInvoker.class);

        //测试代码
        AccountLoginTester.loginName = loginName;
        AccountLoginTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<AccountLoginIn, AccountLoginOut>() {

            @Override
            protected void success(Context context, AccountLoginIn AccountLoginIn, AccountLoginOut responseDatas) {
                afterInvokeAccountLogin();
                LKAndroidStatics.saveLoginInfo(responseDatas);
                AccountLoginActivity.this.setResult(LoginStatics.RESULT_CODE_LOGIN);
                AccountLoginActivity.this.finish();
            }

            @Override
            protected void busError(Context context, AccountLoginIn AccountLoginIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, AccountLoginIn, errorCode, errorType, errorBean);
                afterInvokeAccountLogin();
            }

            @Override
            public void connectError(Context context, String requestId, AccountLoginIn AccountLoginIn, DialogInterface dialog) {
                super.connectError(context, requestId, AccountLoginIn, dialog);
                afterInvokeAccountLogin();
            }

            @Override
            public void timeoutError(Context context, String requestId, AccountLoginIn AccountLoginIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, AccountLoginIn, dialog);
                afterInvokeAccountLogin();
            }

        });
    }

    /**
     * 开始请求账号登录
     */
    private void beforeInvokeAccountLogin() {
        view_btnNext.setVisibility(View.GONE);
        view_loading.setVisibility(View.VISIBLE);
    }

    /**
     * 结束请求账号登录
     */
    private void afterInvokeAccountLogin() {
        view_btnNext.setVisibility(View.VISIBLE);
        view_loading.setVisibility(View.GONE);
    }

}
