package com.lichkin.application.invokers.GetNewsPage;

import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.defines.beans.LKPageBean;
import com.lichkin.framework.defines.enums.impl.LKRangeTypeEnum;
import com.lichkin.framework.utils.LKRandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试用例
 */
public class GetNewsPageTester {

    public static void test(LKRetrofit<GetNewsPageIn, LKPageBean<GetNewsPageOut>> retrofit, GetNewsPageIn in) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "GetNewsPageTester");
        retrofit.addTestResponseBeans(new LKPageBean<>(getSubList(in), in.getPageNumber(), in.getPageSize(), total));
    }

    private static List<GetNewsPageOut> getSubList(GetNewsPageIn in) {
        List<GetNewsPageOut> resultList = new ArrayList<>(total);
        int from = in.getPageNumber() * in.getPageSize();
        int end = from + in.getPageSize();
        if (end > total) {
            end = total;
        }
        for (int i = from; i < end; i++) {
            resultList.add(list.get(i));
        }
        return resultList;
    }

    private static int total = 100;

    private static List<GetNewsPageOut> list = new ArrayList<>(total);

    static {
        for (int i = 1; i <= total; i++) {
            GetNewsPageOut news = new GetNewsPageOut();
            news.setUrl("file:///android_asset/test/test.html");
            news.setTitle("News" + LKRandomUtils.create(LKRandomUtils.randomInRange(10, 200), LKRangeTypeEnum.NUMBER_AND_LETTER_FULL));
            news.setBrief("Brief" + LKRandomUtils.create(LKRandomUtils.randomInRange(10, 1000), LKRangeTypeEnum.NUMBER_AND_LETTER_FULL));
            List<String> imageUrls = new ArrayList<>();
            int random = LKRandomUtils.randomInRange(0, 9);
            for (int j = 1; j <= random; j++) {
                imageUrls.add("file:///android_asset/test/image/" + LKRandomUtils.randomInRange(11, 60) + ".jpg");
            }
            news.setImageUrls(imageUrls);
            list.add(news);
        }
    }

}
