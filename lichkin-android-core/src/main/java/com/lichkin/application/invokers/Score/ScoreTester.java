package com.lichkin.application.invokers.Score;

import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class ScoreTester {

    public static void test(LKRetrofit<ScoreIn, ScoreOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "ScoreTester");
        ScoreOut out = new ScoreOut();
        retrofit.addTestResponseBeans(out);
    }

}
