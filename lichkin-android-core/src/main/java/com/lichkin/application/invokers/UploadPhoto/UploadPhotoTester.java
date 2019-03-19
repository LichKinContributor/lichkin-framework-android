package com.lichkin.application.invokers.UploadPhoto;

import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class UploadPhotoTester {

    public static void test(LKRetrofit<UploadPhotoIn, UploadPhotoOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "UploadPhotoTester");
        UploadPhotoOut out = new UploadPhotoOut();
        retrofit.addTestResponseBeans(out);
    }

}
