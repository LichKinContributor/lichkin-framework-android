package com.lichkin.application.invokers.SignIn;

import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class SignInTester {

    public static void test(LKRetrofit<SignInIn, SignInOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "SignInTester");
        SignInOut out = new SignInOut();
        retrofit.addTestResponseBeans(out);
    }

}
