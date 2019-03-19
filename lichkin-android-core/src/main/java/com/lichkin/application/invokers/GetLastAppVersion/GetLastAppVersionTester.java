package com.lichkin.application.invokers.GetLastAppVersion;

import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class GetLastAppVersionTester {

    public static void test(LKRetrofit<GetLastAppVersionIn, GetLastAppVersionOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
        //模拟服务器异常
//        retrofit.addTest_INTERNAL_SERVER_ERROR();
        //模拟地址错误
//        retrofit.addTest_NOT_FOUND();
        //模拟配置错误
//        retrofit.addTest_CONFIG_ERROR();
        //模拟参数错误
//        retrofit.addTest_PARAM_ERROR();
        //模拟存表参数错误
//        retrofit.addTest_DB_VALIDATE_ERROR();

        //应用已下架
//        retrofit.addTestResponseBeans(49999, "应用已下架" + LKFrameworkStatics.SPLITOR + "重要提示" + LKFrameworkStatics.SPLITOR + "心灰意冷的离去");
        //版本非法
//        retrofit.addTestResponseBeans(49998, "版本非法" + LKFrameworkStatics.SPLITOR + "重要提示" + LKFrameworkStatics.SPLITOR + "知道了");
        //无新版本
        retrofit.addTestResponseBeans(40000, "无新版本");
        //其它错误
//        retrofit.addTestResponseBeans(777, "简单错误提示");
//        retrofit.addTestResponseBeans(888, String.format("多个错误提示%s第1个%s第2个%s等等等等%s", LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR));
//        retrofit.addTestResponseBeans(999, String.format("msg%s多个带字段信息的错误提示%sfield1%s字段1%sfield2%s字段2%s", LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR_FIELDS, LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR_FIELDS, LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR_FIELDS));

        //正常返回结果模拟
        //1===>>>有新版本，且强制升级。
//        GetLastAppVersionOut out1 = new GetLastAppVersionOut();
//        out1.setForceUpdate(true);
//        out1.setTip("有最新版本");
//        out1.setUrl("file:///android_asset/version/version.html");
//        out1.setVersionX(LKAndroidStatics.versionX());
//        out1.setVersionY(LKAndroidStatics.versionY());
//        out1.setVersionZ((short) (LKAndroidStatics.versionZ() + 1));
//        retrofit.addTestResponseBeans(out1);

        //2===>>>有新版本，不强制升级。
//        GetLastAppVersionOut out2 = new GetLastAppVersionOut();
//        out2.setForceUpdate(false);
//        out2.setTip("有最新版本");
//        out2.setUrl("file:///android_asset/version/version.html");
//        out2.setVersionX(LKAndroidStatics.versionX());
//        out2.setVersionY(LKAndroidStatics.versionY());
//        out2.setVersionZ((short) (LKAndroidStatics.versionZ() + 1));
//        retrofit.addTestResponseBeans(out2);
    }

}
