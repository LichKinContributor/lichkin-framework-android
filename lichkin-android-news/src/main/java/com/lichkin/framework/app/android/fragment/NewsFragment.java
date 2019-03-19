package com.lichkin.framework.app.android.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lichkin.android.news.R;
import com.lichkin.application.invokers.GetBannerList.GetBannerListIn;
import com.lichkin.application.invokers.GetBannerList.GetBannerListInvoker;
import com.lichkin.application.invokers.GetBannerList.GetBannerListOut;
import com.lichkin.application.invokers.GetBannerList.GetBannerListTester;
import com.lichkin.application.invokers.GetNewsPage.GetNewsPageIn;
import com.lichkin.application.invokers.GetNewsPage.GetNewsPageInvoker;
import com.lichkin.application.invokers.GetNewsPage.GetNewsPageOut;
import com.lichkin.application.invokers.GetNewsPage.GetNewsPageTester;
import com.lichkin.framework.app.android.callback.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKImageLoader;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.app.android.utils.LKWebView;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.lichkin.framework.defines.beans.LKPageBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class NewsFragment extends Fragment {

    /** 当前视图 */
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //填充页面
        view = inflater.inflate(R.layout.fragment_news, container, false);

        //初始化Banner栏
        initBannerSection();

        //初始化新闻栏
        initNewsSection();

        //返回视图
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pageNumber = 0;
        //清空列表
        newsList.clear();
        //更新列表状态
        adapter.notifyDataSetChanged();
    }

    /** 配置键：Banner图片高宽比例 */
    private static final String CONFIG_KEY_BANNER_RATIO = "lichkin.framework.banner.ratio";
    /** 配置值：Banner图片高宽比例 */
    private static final double CONFIG_VALUE_BANNER_RATIO = LKPropertiesLoader.getDivision(CONFIG_KEY_BANNER_RATIO);
    /** Banner栏控件 */
    private Banner bannerSection;
    /** Banner栏高度 */
    private int bannerSectionHeight;

    /**
     * 初始化Banner栏
     */
    private void initBannerSection() {
        //获取Banner控件
        bannerSection = view.findViewById(R.id.banner);

        //设置样式-数字指示器和标题
        bannerSection.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        bannerSection.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object obj, ImageView imageView) {
                LKImageLoader.load(((GetBannerListOut) obj).getImageUrl(), imageView, R.drawable.no_banner);
            }
        });

        //图片按比例设置值
        bannerSectionHeight = (int) (getResources().getDisplayMetrics().widthPixels * CONFIG_VALUE_BANNER_RATIO);
        ViewGroup.LayoutParams bannerParams = bannerSection.getLayoutParams();
        bannerParams.height = bannerSectionHeight;
        bannerSection.setLayoutParams(bannerParams);

        //设置图片
        bannerSection.setImages(new ArrayList<>());

        //开始轮播-如果请求没有结果时会显示无图片
        bannerSection.start();

        //请求获取Banner列表
        invokeGetBannerList();
    }

    /**
     * 请求获取Banner列表
     */
    private void invokeGetBannerList() {
        //请求参数
        GetBannerListIn in = new GetBannerListIn();

        //创建请求对象
        final LKRetrofit<GetBannerListIn, List<GetBannerListOut>> retrofit = new LKRetrofit<>(this.getActivity(), GetBannerListInvoker.class);

        //测试代码
        GetBannerListTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<GetBannerListIn, List<GetBannerListOut>>() {

            @Override
            protected void success(Context context, GetBannerListIn getLastAppVersionIn, final List<GetBannerListOut> responseDatas) {
                if (responseDatas == null) {
                    return;
                }

                //设置图片
                bannerSection.setImages(responseDatas);
                //设置标题
                bannerSection.setBannerTitles(extractBannerTitleList(responseDatas));
                //设置点击事件
                bannerSection.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        LKWebView.open(NewsFragment.this.getActivity(), responseDatas.get(position).getPageUrl());
                    }
                });
                //开始轮播
                bannerSection.start();
            }

        });
    }

    /**
     * 提取Banner标题列表
     * @param bannerList Banner列表
     * @return Banner标题列表
     */
    private List<String> extractBannerTitleList(List<GetBannerListOut> bannerList) {
        List<String> titleList = new ArrayList<>(bannerList.size());
        for (int i = 0; i < bannerList.size(); i++) {
            titleList.add(bannerList.get(i).getTitle());
        }
        return titleList;
    }


    /** 配置键：新闻图片位置。true:顶部；false:底部。 */
    private static final String CONFIG_KEY_NEWS_IMAGE_POSITION_TOP = "lichkin.framework.news.image.position.top";
    /** 配置值：新闻图片位置。true:顶部；false:底部。 */
    private static final boolean CONFIG_VALUE_NEWS_IMAGE_POSITION_TOP = LKPropertiesLoader.getBoolean(CONFIG_KEY_NEWS_IMAGE_POSITION_TOP);
    /** 新闻栏控件 */
    private PullToRefreshListView newsSection;
    /** 数据列表 */
    private List<GetNewsPageOut> newsList = new ArrayList<>();
    /** 适配器 */
    private BaseAdapter adapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public GetNewsPageOut getItem(int position) {
            return newsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //取对应的新闻对象
            final GetNewsPageOut news = getItem(position);

            //根据图片数量确定布局
            List<String> imageUrls = news.getImageUrls();
            int imageSize = imageUrls == null ? 0 : imageUrls.size();
            int layoutResId = 0;
            switch (imageSize) {
                case 9:
                    if (CONFIG_VALUE_NEWS_IMAGE_POSITION_TOP) {
                        layoutResId = R.layout.news_item_9_top;
                    } else {
                        layoutResId = R.layout.news_item_9_bottom;
                    }
                    break;
                case 8:
                    if (CONFIG_VALUE_NEWS_IMAGE_POSITION_TOP) {
                        layoutResId = R.layout.news_item_8_top;
                    } else {
                        layoutResId = R.layout.news_item_8_bottom;
                    }
                    break;
                case 7:
                    if (CONFIG_VALUE_NEWS_IMAGE_POSITION_TOP) {
                        layoutResId = R.layout.news_item_7_top;
                    } else {
                        layoutResId = R.layout.news_item_7_bottom;
                    }
                    break;
                case 6:
                    if (CONFIG_VALUE_NEWS_IMAGE_POSITION_TOP) {
                        layoutResId = R.layout.news_item_6_top;
                    } else {
                        layoutResId = R.layout.news_item_6_bottom;
                    }
                    break;
                case 5:
                    if (CONFIG_VALUE_NEWS_IMAGE_POSITION_TOP) {
                        layoutResId = R.layout.news_item_5_top;
                    } else {
                        layoutResId = R.layout.news_item_5_bottom;
                    }
                    break;
                case 4:
                    if (CONFIG_VALUE_NEWS_IMAGE_POSITION_TOP) {
                        layoutResId = R.layout.news_item_4_top;
                    } else {
                        layoutResId = R.layout.news_item_4_bottom;
                    }
                    break;
                case 3:
                    if (CONFIG_VALUE_NEWS_IMAGE_POSITION_TOP) {
                        layoutResId = R.layout.news_item_3_top;
                    } else {
                        layoutResId = R.layout.news_item_3_bottom;
                    }
                    break;
                case 2:
                    if (CONFIG_VALUE_NEWS_IMAGE_POSITION_TOP) {
                        layoutResId = R.layout.news_item_2_top;
                    } else {
                        layoutResId = R.layout.news_item_2_bottom;
                    }
                    break;
                case 1:
                    if (CONFIG_VALUE_NEWS_IMAGE_POSITION_TOP) {
                        layoutResId = R.layout.news_item_1_top;
                    } else {
                        layoutResId = R.layout.news_item_1_bottom;
                    }
                    break;
                case 0:
                    //无图样式
                    layoutResId = R.layout.news_item_0;
                    break;
            }

            //添加布局
            convertView = View.inflate(view.getContext(), layoutResId, null);

            //点击事件监听
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LKWebView.open(NewsFragment.this.getActivity(), news.getUrl());
                }
            });

            //设置标题
            ((TextView) convertView.findViewById(R.id.news_title)).setText(news.getTitle());

            //设置简介
            ((TextView) convertView.findViewById(R.id.news_brief)).setText(news.getBrief());

            //设置图片
            switch (imageSize) {
                case 9:
                    LKImageLoader.load(imageUrls.get(8), (ImageView) convertView.findViewById(R.id.news_image_9));
                case 8:
                    LKImageLoader.load(imageUrls.get(7), (ImageView) convertView.findViewById(R.id.news_image_8));
                case 7:
                    LKImageLoader.load(imageUrls.get(6), (ImageView) convertView.findViewById(R.id.news_image_7));
                case 6:
                    LKImageLoader.load(imageUrls.get(5), (ImageView) convertView.findViewById(R.id.news_image_6));
                case 5:
                    LKImageLoader.load(imageUrls.get(4), (ImageView) convertView.findViewById(R.id.news_image_5));
                case 4:
                    LKImageLoader.load(imageUrls.get(3), (ImageView) convertView.findViewById(R.id.news_image_4));
                case 3:
                    LKImageLoader.load(imageUrls.get(2), (ImageView) convertView.findViewById(R.id.news_image_3));
                case 2:
                    LKImageLoader.load(imageUrls.get(1), (ImageView) convertView.findViewById(R.id.news_image_2));
                case 1:
                    LKImageLoader.load(imageUrls.get(0), (ImageView) convertView.findViewById(R.id.news_image_1));
            }

            //返回视图
            return convertView;
        }

    };

    /** 页码 */
    private int pageNumber = 0;
    /** 已经最后一页 */
    private boolean last;

    /**
     * 初始化新闻栏
     */
    private void initNewsSection() {
        //获取新闻控件
        newsSection = view.findViewById(R.id.news);

        //新闻栏高度
        int newsSectionHeight = getResources().getDisplayMetrics().heightPixels//总高度
                - (int) LKAndroidUtils.getStatusBarHeight()//状态栏高度
                - bannerSectionHeight //Banner栏高度
                - LKAndroidUtils.getNavigationBarHeight()//导航栏高度
                ;
        ViewGroup.LayoutParams newsParams = newsSection.getLayoutParams();
        newsParams.height = newsSectionHeight;
        newsSection.setLayoutParams(newsParams);

        //设置适配器
        newsSection.getRefreshableView().setAdapter(adapter);

        //设置刷新模式
        newsSection.setMode(PullToRefreshBase.Mode.DISABLED);

        //设置刷新事件监听
        newsSection.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //清空列表
                newsList.clear();
                //更新列表状态
                adapter.notifyDataSetChanged();
                //请求新闻数据
                invokeGetNewsPage();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (last) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LKToast.showTip(NewsFragment.this.getContext(), R.string.invoke_no_more_data);
                            //更新列表状态
                            newsSection.onRefreshComplete();
                        }
                    }, 500);
                    return;
                }
                //请求新闻数据
                invokeGetNewsPage();
            }

        });

        //有新闻背景
        newsSection.setBackground(LKAndroidUtils.getDrawable(R.drawable.bg_news_no));

        //请求新闻数据
        invokeGetNewsPage();
    }

    /**
     * 请求获取新闻分页
     */
    private void invokeGetNewsPage() {
        //请求参数
        GetNewsPageIn in = new GetNewsPageIn(pageNumber++);

        //创建请求对象
        final LKRetrofit<GetNewsPageIn, LKPageBean<GetNewsPageOut>> retrofit = new LKRetrofit<>(this.getActivity(), GetNewsPageInvoker.class);

        //测试代码
        GetNewsPageTester.test(retrofit, in);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<GetNewsPageIn, LKPageBean<GetNewsPageOut>>() {

            @Override
            protected void success(Context context, GetNewsPageIn getLastAppVersionIn, final LKPageBean<GetNewsPageOut> responseDatas) {
                if (responseDatas == null || responseDatas.getContent().isEmpty()) {
                    //更新列表状态
                    newsSection.onRefreshComplete();

                    //无数据禁用上拉刷新
                    if (newsSection.getMode().equals(PullToRefreshBase.Mode.PULL_FROM_END)) {
                        newsSection.setMode(PullToRefreshBase.Mode.DISABLED);
                    }
                    return;
                }

                //有数据开启上拉刷新
                if (newsSection.getMode().equals(PullToRefreshBase.Mode.DISABLED)) {
                    newsSection.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                    //有新闻背景
                    newsSection.setBackground(LKAndroidUtils.getDrawable(R.drawable.bg_news_has));
                }

                //将结果数据加入到列表中
                newsList.addAll(responseDatas.getContent());

                //更新列表状态
                newsSection.onRefreshComplete();
                adapter.notifyDataSetChanged();

                //最后一页禁用刷新
                if (responseDatas.isLast()) {
                    last = true;
                }
            }

            @Override
            protected void busError(Context context, GetNewsPageIn getNewsPageIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, getNewsPageIn, errorCode, errorType, errorBean);
                newsSection.onRefreshComplete();
            }

            @Override
            public void connectError(Context context, String requestId, GetNewsPageIn getNewsPageIn, DialogInterface dialog) {
                super.connectError(context, requestId, getNewsPageIn, dialog);
                newsSection.onRefreshComplete();
            }

            @Override
            public void timeoutError(Context context, String requestId, GetNewsPageIn getNewsPageIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, getNewsPageIn, dialog);
                newsSection.onRefreshComplete();
            }

        });
    }

}