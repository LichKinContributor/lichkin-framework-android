package com.lichkin.application.invokers.GetDynamicTabs;

import com.lichkin.application.invokers.TesterStatics;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试用例
 */
public class GetDynamicTabsTester {

    public static void test(LKRetrofit<GetDynamicTabsIn, List<GetDynamicTabsOut>> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
        List<GetDynamicTabsOut> out = new ArrayList<>();
        out.add(new GetDynamicTabsOut("LichKin", "鑫宏利业", TesterStatics.LichKin_LOGO_BASE64));
        retrofit.addTestResponseBeans(out);
    }

}
