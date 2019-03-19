package com.lichkin.framework.app.android.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lichkin.android.main.R;
import com.lichkin.application.invokers.SupplementRegisterInfo.SupplementRegisterInfoIn;
import com.lichkin.application.invokers.SupplementRegisterInfo.SupplementRegisterInfoInvoker;
import com.lichkin.application.invokers.SupplementRegisterInfo.SupplementRegisterInfoOut;
import com.lichkin.application.invokers.SupplementRegisterInfo.SupplementRegisterInfoTester;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.LoginStatics;
import com.lichkin.framework.app.android.callback.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.lichkin.framework.utils.security.md5.LKMD5Encrypter;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 补充注册信息
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class SupplementRegisterInfoActivity extends AppCompatActivity {

    /** 姓名 */
    EditText view_userName;
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

    /** 提交按钮 */
    Button view_btnSubmit;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引用布局文件
        setContentView(R.layout.activity_supplement_register_info);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //绑定页面
        view_userName = findViewById(R.id.userName);
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

        view_btnSubmit = findViewById(R.id.btn_submit);
        view_btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeSupplementRegisterInfo();
            }
        });

        if (LKPropertiesLoader.testForm) {
            view_userName.setText(LKPropertiesLoader.getString("lichkin.framework.test.form.userName.Test"));
            view_loginName.setText(LKPropertiesLoader.getString("lichkin.framework.test.form.loginName.Test"));
            view_pwd.setText(LKPropertiesLoader.getString("lichkin.framework.test.form.pwd.Test"));
        }
    }

    /**
     * 请求补充注册信息
     */
    private void invokeSupplementRegisterInfo() {
        beforeInvokeSupplementRegisterInfo();

        //校验姓名
        String userName = view_userName.getText().toString().trim();
        if ("".equals(userName)) {
            LKToast.showTip(SupplementRegisterInfoActivity.this, R.string.hint_userName);
            afterInvokeSupplementRegisterInfo();
            return;
        }

        //校验用户名
        String loginName = view_loginName.getText().toString().trim();
        if ("".equals(loginName)) {
            LKToast.showTip(this, R.string.hint_loginName);
            afterInvokeSupplementRegisterInfo();
            return;
        }

        //校验密码
        String pwd = view_pwd.getText().toString().trim();
        if ("".equals(pwd)) {
            LKToast.showTip(this, R.string.hint_pwd);
            afterInvokeSupplementRegisterInfo();
            return;
        }

        //请求参数
        final SupplementRegisterInfoIn in = new SupplementRegisterInfoIn(userName, loginName, LKMD5Encrypter.encrypt(pwd));

        //创建请求对象
        final LKRetrofit<SupplementRegisterInfoIn, SupplementRegisterInfoOut> retrofit = new LKRetrofit<>(this, SupplementRegisterInfoInvoker.class);

        //测试代码
        SupplementRegisterInfoTester.userName = userName;
        SupplementRegisterInfoTester.loginName = loginName;
        SupplementRegisterInfoTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<SupplementRegisterInfoIn, SupplementRegisterInfoOut>() {

            @Override
            protected void success(Context context, SupplementRegisterInfoIn SupplementRegisterInfoIn, SupplementRegisterInfoOut responseDatas) {
                afterInvokeSupplementRegisterInfo();
                LKAndroidStatics.userName(responseDatas.getUserName());
                LKAndroidStatics.loginName(responseDatas.getLoginName());
                SupplementRegisterInfoActivity.this.setResult(LoginStatics.RESULT_CODE_LOGIN);
                SupplementRegisterInfoActivity.this.finish();
            }

            @Override
            protected void busError(Context context, SupplementRegisterInfoIn SupplementRegisterInfoIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, SupplementRegisterInfoIn, errorCode, errorType, errorBean);
                afterInvokeSupplementRegisterInfo();
            }

            @Override
            public void connectError(Context context, String requestId, SupplementRegisterInfoIn SupplementRegisterInfoIn, DialogInterface dialog) {
                super.connectError(context, requestId, SupplementRegisterInfoIn, dialog);
                afterInvokeSupplementRegisterInfo();
            }

            @Override
            public void timeoutError(Context context, String requestId, SupplementRegisterInfoIn SupplementRegisterInfoIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, SupplementRegisterInfoIn, dialog);
                afterInvokeSupplementRegisterInfo();
            }

        });
    }

    /**
     * 开始请求补充注册信息
     */
    private void beforeInvokeSupplementRegisterInfo() {
        view_btnSubmit.setVisibility(View.GONE);
        view_loading.setVisibility(View.VISIBLE);
    }

    /**
     * 结束请求补充注册信息
     */
    private void afterInvokeSupplementRegisterInfo() {
        view_btnSubmit.setVisibility(View.VISIBLE);
        view_loading.setVisibility(View.GONE);
    }

}
