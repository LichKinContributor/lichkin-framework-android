package com.lichkin.application.invokers.Feedback;

import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class FeedbackTester {

    public static void test(LKRetrofit<FeedbackIn, FeedbackOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "FeedbackTester");
        FeedbackOut out = new FeedbackOut();
        retrofit.addTestResponseBeans(out);
    }

}
