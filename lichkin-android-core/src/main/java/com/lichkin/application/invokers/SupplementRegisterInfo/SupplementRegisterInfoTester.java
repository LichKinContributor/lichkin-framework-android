package com.lichkin.application.invokers.SupplementRegisterInfo;

import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class SupplementRegisterInfoTester {

    public static String userName = LKPropertiesLoader.getString("lichkin.framework.test.form.userName.Test");
    public static String loginName = LKPropertiesLoader.getString("lichkin.framework.test.form.loginName.Test");

    public static void test(LKRetrofit<SupplementRegisterInfoIn, SupplementRegisterInfoOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "SupplementRegisterInfoTester");
        SupplementRegisterInfoOut out = new SupplementRegisterInfoOut();
        out.setUserName(userName);
        out.setLoginName(loginName);
        retrofit.addTestResponseBeans(out);
    }

}
