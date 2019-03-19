package com.lichkin.framework.app.android.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lichkin.android.main.R;
import com.lichkin.application.invokers.FastLogin.FastLoginIn;
import com.lichkin.application.invokers.FastLogin.FastLoginInvoker;
import com.lichkin.application.invokers.FastLogin.FastLoginOut;
import com.lichkin.application.invokers.FastLogin.FastLoginTester;
import com.lichkin.application.invokers.GetSmsSecurityCode.GetSmsSecurityCodeIn;
import com.lichkin.application.invokers.GetSmsSecurityCode.GetSmsSecurityCodeInvoker;
import com.lichkin.application.invokers.GetSmsSecurityCode.GetSmsSecurityCodeOut;
import com.lichkin.application.invokers.GetSmsSecurityCode.GetSmsSecurityCodeTester;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.LoginStatics;
import com.lichkin.framework.app.android.callback.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.listener.click.BtnOnClickListener4ToWebView;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.app.android.utils.LKWebView;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 短信验证页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class SmsValidateActivity extends AppCompatActivity {

    /** 重新发送按钮 */
    private TextView view_btnResend;

    /** 下一步按钮 */
    private TextView view_btnNext;

    /** 手机号码 */
    private String cellphone;

    /** 验证码 */
    private TextView view_securityCode;

    /** 加载 */
    private AVLoadingIndicatorView view_loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引用布局文件
        setContentView(R.layout.activity_sms_validate);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //绑定页面
        view_btnResend = findViewById(R.id.btn_resend);
        view_btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeGetSmsSecurityCode();
            }
        });

        view_btnNext = findViewById(R.id.btn_next);
        view_btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeFastLogin();
            }
        });

        TextView view_cellphone = findViewById(R.id.cellphone);
        view_securityCode = findViewById(R.id.security_code);

        view_loading = findViewById(R.id.loading);

        //服务协议
        TextView view_btnAgreement = findViewById(R.id.btn_service_agreement);
        view_btnAgreement.setOnClickListener(new BtnOnClickListener4ToWebView(this, "lichkin.framework.pages.serviceAgreement"));
        view_btnAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//增加下划线

        //隐私权政策
        TextView view_btnPrivacyRightPolicy = findViewById(R.id.btn_privacy_right_policy);
        view_btnPrivacyRightPolicy.setOnClickListener(new BtnOnClickListener4ToWebView(this, "lichkin.framework.pages.privacyRightPolicy"));
        view_btnPrivacyRightPolicy.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//增加下划线

        //填充手机号码
        cellphone = getIntent().getStringExtra("cellphone");
        view_cellphone.setText(cellphone);

        if (LKPropertiesLoader.testForm) {
            view_securityCode.setText(LKPropertiesLoader.getString("lichkin.framework.test.form.securityCode"));
        }
    }

    /**
     * 请求获取短信验证码
     */
    private void invokeGetSmsSecurityCode() {
        beforeInvokeGetSmsSecurityCode();

        //请求参数
        GetSmsSecurityCodeIn in = new GetSmsSecurityCodeIn(cellphone);

        //创建请求对象
        final LKRetrofit<GetSmsSecurityCodeIn, GetSmsSecurityCodeOut> retrofit = new LKRetrofit<>(this, GetSmsSecurityCodeInvoker.class);

        //测试代码
        GetSmsSecurityCodeTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<GetSmsSecurityCodeIn, GetSmsSecurityCodeOut>() {

            @Override
            protected void success(Context context, GetSmsSecurityCodeIn GetSmsSecurityCodeIn, GetSmsSecurityCodeOut responseDatas) {
                afterInvokeGetSmsSecurityCode(false);
            }

            @Override
            protected void busError(Context context, GetSmsSecurityCodeIn GetSmsSecurityCodeIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, GetSmsSecurityCodeIn, errorCode, errorType, errorBean);
                afterInvokeGetSmsSecurityCode(true);
            }

            @Override
            public void connectError(Context context, String requestId, GetSmsSecurityCodeIn GetSmsSecurityCodeIn, DialogInterface dialog) {
                super.connectError(context, requestId, GetSmsSecurityCodeIn, dialog);
                afterInvokeGetSmsSecurityCode(true);
            }

            @Override
            public void timeoutError(Context context, String requestId, GetSmsSecurityCodeIn GetSmsSecurityCodeIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, GetSmsSecurityCodeIn, dialog);
                afterInvokeGetSmsSecurityCode(true);
            }

        });
    }

    /**
     * 开始请求获取短信验证码
     */
    private void beforeInvokeGetSmsSecurityCode() {
        view_btnResend.setClickable(false);
        view_btnResend.setVisibility(View.GONE);
        view_loading.setVisibility(View.VISIBLE);
    }

    /** 验证码超时时长 */
    private static final int TIMEOUT = 60;
    int timeout = TIMEOUT;

    /**
     * 结束请求获取短信验证码
     */
    private void afterInvokeGetSmsSecurityCode(boolean error) {
        if (error) {
            view_btnResend.setClickable(true);
            view_btnResend.setVisibility(View.VISIBLE);
            view_loading.setVisibility(View.GONE);
            return;
        }
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        view_btnResend.setText(String.valueOf(timeout) + "s");
                        if (timeout == TIMEOUT) {
                            view_btnResend.setVisibility(View.VISIBLE);
                        }
                        if (timeout == 0) {
                            timer.cancel();
                            view_btnResend.setClickable(true);
                            view_btnResend.setText(R.string.btn_title_sms_resend);
                            timeout = TIMEOUT;
                        }
                        timeout--;
                    }
                });
            }
        }, 0, 1000);
        view_loading.setVisibility(View.GONE);
    }

    /**
     * 请求快速登录
     */
    private void invokeFastLogin() {
        beforeInvokeFastLogin();

        //校验验证码
        String securityCode = view_securityCode.getText().toString();
        if (securityCode.length() != 6) {
            LKToast.showTip(this, R.string.hint_security_code);
            afterInvokeFastLogin();
            return;
        }

        //请求参数
        final FastLoginIn in = new FastLoginIn(cellphone, securityCode);

        //创建请求对象
        final LKRetrofit<FastLoginIn, FastLoginOut> retrofit = new LKRetrofit<>(this, FastLoginInvoker.class);

        //测试代码
        FastLoginTester.cellphone = cellphone;
        FastLoginTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<FastLoginIn, FastLoginOut>() {

            @Override
            protected void success(Context context, FastLoginIn FastLoginIn, FastLoginOut responseDatas) {
                afterInvokeFastLogin();
                LKAndroidStatics.saveLoginInfo(responseDatas);
                if (responseDatas.isLogin()) {
                    //登录
                    SmsValidateActivity.this.setResult(LoginStatics.RESULT_CODE_LOGIN);
                    SmsValidateActivity.this.finish();
                } else {
                    //注册
                    Intent intent = new Intent(SmsValidateActivity.this, SupplementRegisterInfoActivity.class);
                    intent.putExtra("cellphone", cellphone);
                    startActivityForResult(intent, LoginStatics.REQUEST_CODE_LOGIN);
                }
            }

            @Override
            protected void busError(Context context, FastLoginIn FastLoginIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, FastLoginIn, errorCode, errorType, errorBean);
                afterInvokeFastLogin();
            }

            @Override
            public void connectError(Context context, String requestId, FastLoginIn FastLoginIn, DialogInterface dialog) {
                super.connectError(context, requestId, FastLoginIn, dialog);
                afterInvokeFastLogin();
            }

            @Override
            public void timeoutError(Context context, String requestId, FastLoginIn FastLoginIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, FastLoginIn, dialog);
                afterInvokeFastLogin();
            }

        });
    }

    /**
     * 开始请求快速登录
     */
    private void beforeInvokeFastLogin() {
        view_btnNext.setVisibility(View.GONE);
        view_loading.setVisibility(View.VISIBLE);
    }

    /**
     * 结束请求快速登录
     */
    private void afterInvokeFastLogin() {
        view_btnNext.setVisibility(View.VISIBLE);
        view_loading.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LoginStatics.REQUEST_CODE_LOGIN && resultCode == LoginStatics.RESULT_CODE_LOGIN) {
            setResult(LoginStatics.RESULT_CODE_LOGIN, data);
            finish();
        }
    }

}
