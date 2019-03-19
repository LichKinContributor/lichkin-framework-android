package com.lichkin.application.invokers.FastLogin;

import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.utils.LKRandomUtils;

/**
 * 测试用例
 */
public class FastLoginTester {

    public static String cellphone = LKPropertiesLoader.getString("lichkin.framework.test.form.cellphone");

    public static void test(LKRetrofit<FastLoginIn, FastLoginOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "FastLoginTester");
        FastLoginOut out = new FastLoginOut();
        if (cellphone.equals(LKPropertiesLoader.getString("lichkin.framework.test.form.cellphone"))) {
            out.setLogin(true);
            out.setUserName(LKPropertiesLoader.getString("lichkin.framework.test.form.userName"));
            out.setLoginName(LKPropertiesLoader.getString("lichkin.framework.test.form.loginName"));
            out.setLevel(63);
            out.setPhoto("photo");
        } else {
            out.setLogin(false);
            out.setUserName(null);
            out.setLoginName(cellphone);
            out.setLevel(1);
            out.setPhoto(null);
        }
        out.setToken(LKRandomUtils.create(32));
        retrofit.addTestResponseBeans(out);
    }

}
