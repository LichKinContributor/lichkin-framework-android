package com.lichkin.application.invokers.AccountLogin;

import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.utils.LKRandomUtils;

/**
 * 测试用例
 */
public class AccountLoginTester {

    public static String loginName = LKPropertiesLoader.getString("lichkin.framework.test.form.loginName");

    public static void test(LKRetrofit<AccountLoginIn, AccountLoginOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "AccountLoginTester");
        AccountLoginOut out = new AccountLoginOut();
        if (loginName.equals(LKPropertiesLoader.getString("lichkin.framework.test.form.cellphone")) || loginName.equals(LKPropertiesLoader.getString("lichkin.framework.test.form.loginName"))) {
            out.setUserName(LKPropertiesLoader.getString("lichkin.framework.test.form.userName"));
            out.setLoginName(LKPropertiesLoader.getString("lichkin.framework.test.form.loginName"));
            out.setLevel(63);
            out.setPhoto("photo");
        } else {
            out.setUserName(null);
            out.setLoginName(loginName);
            out.setLevel(1);
            out.setPhoto(null);
        }
        out.setToken(LKRandomUtils.create(32));
        retrofit.addTestResponseBeans(out);
    }

}
