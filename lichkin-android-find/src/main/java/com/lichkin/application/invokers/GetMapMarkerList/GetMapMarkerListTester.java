package com.lichkin.application.invokers.GetMapMarkerList;

import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试用例
 */
public class GetMapMarkerListTester {

    public static void test(LKRetrofit<GetMapMarkerListIn, List<GetMapMarkerListOut>> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "GetMapMarkerList");
        final List<GetMapMarkerListOut> list = new ArrayList<>();
        GetMapMarkerListOut out1 = new GetMapMarkerListOut();
        out1.setId("Marker1");
        out1.setName("第一个");
        out1.setLatitude(31.2661208713);
        out1.setLongitude(120.7486402988);
        out1.setDistance(372);
        list.add(out1);
        GetMapMarkerListOut out2 = new GetMapMarkerListOut();
        out2.setId("Marker2");
        out2.setName("第二个");
        out2.setLatitude(31.2677165497);
        out2.setLongitude(120.7494825125);
        out2.setDistance(490);
        list.add(out2);
        GetMapMarkerListOut out3 = new GetMapMarkerListOut();
        out3.setId("Marker3");
        out3.setName("第三个");
        out3.setLatitude(31.2653000952);
        out3.setLongitude(120.7463979721);
        out3.setDistance(484);
        list.add(out3);
        retrofit.addTestResponseBeans(list);
    }

}
