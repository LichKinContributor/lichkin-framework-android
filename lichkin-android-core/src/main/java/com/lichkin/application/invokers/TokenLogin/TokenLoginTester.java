package com.lichkin.application.invokers.TokenLogin;

import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class TokenLoginTester {

    public static void test(LKRetrofit<TokenLoginIn, TokenLoginOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "TokenLoginTester");
        TokenLoginOut out = new TokenLoginOut();
        out.setUserName(LKAndroidStatics.userName());
        out.setLoginName(LKAndroidStatics.loginName());
        out.setLevel(LKAndroidStatics.level());
        out.setPhoto(LKAndroidStatics.photo());
        retrofit.addTestResponseBeans(out);
    }

}
