package com.lichkin.framework.app.android.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lichkin.android.main.R;
import com.lichkin.application.invokers.GetSmsSecurityCode.GetSmsSecurityCodeIn;
import com.lichkin.application.invokers.GetSmsSecurityCode.GetSmsSecurityCodeInvoker;
import com.lichkin.application.invokers.GetSmsSecurityCode.GetSmsSecurityCodeOut;
import com.lichkin.application.invokers.GetSmsSecurityCode.GetSmsSecurityCodeTester;
import com.lichkin.framework.app.android.LoginStatics;
import com.lichkin.framework.app.android.callback.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.listener.click.BtnOnClickListener4ToWebView;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 快速登录页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class FastLoginActivity extends AppCompatActivity {

    /** 登录按钮 */
    TextView view_btnLogin;

    /** 下一步按钮 */
    TextView view_btnNext;

    /** 手机号码 */
    EditText view_cellphone;

    /** 加载 */
    AVLoadingIndicatorView view_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引用布局文件
        setContentView(R.layout.activity_fast_login);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //绑定页面
        view_btnLogin = findViewById(R.id.btn_login);
        view_btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(FastLoginActivity.this, AccountLoginActivity.class), LoginStatics.REQUEST_CODE_LOGIN);
            }
        });

        view_btnNext = findViewById(R.id.btn_next);
        view_btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeGetSmsSecurityCode();
            }
        });

        view_cellphone = findViewById(R.id.cellphone);

        view_loading = findViewById(R.id.loading);

        //服务协议
        TextView view_btnAgreement = findViewById(R.id.btn_service_agreement);
        view_btnAgreement.setOnClickListener(new BtnOnClickListener4ToWebView(this, "lichkin.framework.pages.serviceAgreement"));
        view_btnAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//增加下划线

        //隐私权政策
        TextView view_btnPrivacyRightPolicy = findViewById(R.id.btn_privacy_right_policy);
        view_btnPrivacyRightPolicy.setOnClickListener(new BtnOnClickListener4ToWebView(this, "lichkin.framework.pages.privacyRightPolicy"));
        view_btnPrivacyRightPolicy.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//增加下划线

        if (LKPropertiesLoader.testForm) {
            view_cellphone.setText(LKPropertiesLoader.getString("lichkin.framework.test.form.cellphone"));
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.pop_in, R.anim.pop_out);
    }

    /**
     * 请求获取短信验证码
     */
    private void invokeGetSmsSecurityCode() {
        beforeInvokeGetSmsSecurityCode();

        //校验手机号码
        final String cellphone = view_cellphone.getText().toString();
        if (cellphone.length() != 11) {
            LKToast.showTip(this, R.string.hint_cellphone);
            afterInvokeGetSmsSecurityCode();
            return;
        }

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
                afterInvokeGetSmsSecurityCode();
                Intent intent = new Intent(FastLoginActivity.this, SmsValidateActivity.class);
                intent.putExtra("cellphone", cellphone);
                startActivityForResult(intent, LoginStatics.REQUEST_CODE_LOGIN);
            }

            @Override
            protected void busError(Context context, GetSmsSecurityCodeIn GetSmsSecurityCodeIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, GetSmsSecurityCodeIn, errorCode, errorType, errorBean);
                afterInvokeGetSmsSecurityCode();
            }

            @Override
            public void connectError(Context context, String requestId, GetSmsSecurityCodeIn GetSmsSecurityCodeIn, DialogInterface dialog) {
                super.connectError(context, requestId, GetSmsSecurityCodeIn, dialog);
                afterInvokeGetSmsSecurityCode();
            }

            @Override
            public void timeoutError(Context context, String requestId, GetSmsSecurityCodeIn GetSmsSecurityCodeIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, GetSmsSecurityCodeIn, dialog);
                afterInvokeGetSmsSecurityCode();
            }

        });
    }

    /**
     * 开始请求获取短信验证码
     */
    private void beforeInvokeGetSmsSecurityCode() {
        view_btnNext.setVisibility(View.GONE);
        view_loading.setVisibility(View.VISIBLE);
    }

    /**
     * 结束请求获取短信验证码
     */
    private void afterInvokeGetSmsSecurityCode() {
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
