package com.lichkin.framework.app.android.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.WalkStep;
import com.lichkin.android.find.R;
import com.lichkin.application.invokers.GetMapMarkerList.GetMapMarkerListIn;
import com.lichkin.application.invokers.GetMapMarkerList.GetMapMarkerListInvoker;
import com.lichkin.application.invokers.GetMapMarkerList.GetMapMarkerListOut;
import com.lichkin.application.invokers.GetMapMarkerList.GetMapMarkerListTester;
import com.lichkin.framework.app.android.bean.LKParamsBean;
import com.lichkin.framework.app.android.callback.LKCallback;
import com.lichkin.framework.app.android.callback.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.listener.click.BtnOnClickListener4ToWebView;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.defines.enums.impl.LKMapAPIEnum;
import com.lichkin.framework.utils.LKAreaUtils;

import java.util.ArrayList;
import java.util.List;

public class FindFragment extends Fragment implements LocationSource, AMapLocationListener, AMap.OnMarkerClickListener, AMap.InfoWindowAdapter, RouteSearch.OnRouteSearchListener {

    /** 高德地图包名 */
    public static final String PACKAGE_NAME_AMAP = "com.autonavi.minimap";

    private MapView mapView;
    private AMap map;
    private OnLocationChangedListener locationChangedListener;
    private AMapLocationClient locationClient;
    private RouteSearch mRouteSearch;
    private boolean searched = false;// 是否查询过

    private void initialize() {
        map = mapView.getMap();

        // 地图样式设置
        UiSettings uiSettings = map.getUiSettings();

        uiSettings.setZoomControlsEnabled(false);// 禁用放大缩小按钮

        // 定位小蓝点
        map.setLocationSource(this);// 启用定位
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        map.setMyLocationStyle(myLocationStyle);
        map.setMyLocationEnabled(true);

        // 自定义标记点
        map.setOnMarkerClickListener(this);
        map.setInfoWindowAdapter(this);

        // 路径规划
        mRouteSearch = new RouteSearch(this.getContext());
        mRouteSearch.setRouteSearchListener(this);
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        locationChangedListener = listener;
        if (locationClient == null) {
            locationClient = new AMapLocationClient(this.getContext());
            locationClient.setLocationListener(this);

            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            locationClient.setLocationOption(mLocationOption);

            locationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        locationChangedListener = null;
        if (locationClient != null) {
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        locationClient = null;
    }

    private static final int QUERY_ACCURACY = LKPropertiesLoader.getInteger("lichkin.framework.find.query.accuracy");//精度达到范围内时调用查询

    @Override
    public void onLocationChanged(final AMapLocation location) {
        if (locationChangedListener != null) {
            locationChangedListener.onLocationChanged(location);
        }

        if (location == null) {
            return;
        }

        int errorCode = location.getErrorCode();
        if (errorCode != 0) {
            Log.e("LichKin-AMAP", "定位失败," + errorCode + ": " + location.getErrorInfo());
            return;
        }

        float accuracy = location.getAccuracy();// 精度
        if (accuracy < QUERY_ACCURACY) {// 精度达到范围内时调用POI查询
            // 只查询一次控制
            if (searched) {
                return;
            }
            searched = true;

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            String cityCode = location.getCityCode();

            // 设置地图中心点以及缩放级别
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 16));

            // 调用接口查询
            invokeGetMapMarkerList(latitude, longitude, cityCode);

            // 调用POI查询
            //invokeAMapPOISearchQuery(latitude, longitude, cityCode, null);
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
        if (errorCode != AMapException.CODE_AMAP_SUCCESS) {
            return;
        }
        if (result == null) {
            return;
        }
        List<WalkPath> listPath = result.getPaths();
        if (listPath == null || listPath.isEmpty()) {
            return;
        }
        for (WalkPath path : listPath) {
            if (path == null) {
                continue;
            }
            List<WalkStep> listStep = path.getSteps();
            if (listStep == null || listStep.isEmpty()) {
                continue;
            }
            PolylineOptions options = new PolylineOptions().width(20).color(R.color.colorAccent);
            for (WalkStep step : listStep) {
                if (step == null) {
                    continue;
                }
                List<LatLonPoint> listPoint = step.getPolyline();
                for (LatLonPoint point : listPoint) {
                    options.add(new LatLng(point.getLatitude(), point.getLongitude()));
                }
            }
            map.addPolyline(options);
            break;
        }
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    /**
     * 自定义标记点
     */
    @SuppressLint("SetTextI18n")
    private BitmapDescriptor userDefinedMarker(MarkerInfo markerInfo) {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.map_user_defined_marker, null);

        ImageView icon = view.findViewById(R.id.ic);
        icon.setImageDrawable(getResources().getDrawable(markerInfo.fromApi ? R.drawable.ic_map_second : R.drawable.ic_map_first));

        TextView distanceView = view.findViewById(R.id.distance);
        distanceView.setTextColor(getResources().getColor(markerInfo.fromApi ? R.color.colorMapSecond : R.color.colorMapFirst));
        distanceView.setText(markerInfo.distance + LKAndroidUtils.getString(R.string.meter));

        return BitmapDescriptorFactory.fromView(view);
    }

    /**
     * 自定义标记点气泡
     */
    private View userDefinedMarkerInfoWindow(final MarkerInfo markerInfo) {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.map_user_defined_marker_info_window, null);

        ImageView navigationView = view.findViewById(R.id.navigation);
        navigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LKAndroidUtils.appInstall(getContext(), PACKAGE_NAME_AMAP, LKAndroidUtils.getString(R.string.NotInstalledAMAP),
                        new LKCallback() {
                            @Override
                            public void call() {
                                StringBuilder builder = new StringBuilder("amapuri://route/plan");//服务类型
                                builder.append("?sourceApplication=").append(LKPropertiesLoader.appToken);//第三方调用应用名称

                                builder.append("&dlat=").append(markerInfo.latitude);//终点纬度
                                builder.append("&dlon=").append(markerInfo.longitude);//终点经度
                                builder.append("&dname=").append(markerInfo.name);//终点名称

                                builder.append("&dev=0");//不需要国测加密
                                builder.append("&t=2");//步行

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse(builder.toString()));
                                intent.setPackage(PACKAGE_NAME_AMAP);
                                if (FindFragment.this.getContext() != null) {
                                    FindFragment.this.getContext().startActivity(intent);
                                }
                            }
                        });
            }
        });

        ImageView detailView = view.findViewById(R.id.detail);
        if (markerInfo.fromApi) {
            detailView.setOnClickListener(new BtnOnClickListener4ToWebView(false, FindFragment.this, "lichkin.framework.find.pages.detail", LKParamsBean.i().a("id", markerInfo.id)));
        } else {
            detailView.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // 路径规划
//        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(currentPoint, new LatLonPoint(marker.getPosition().latitude, marker.getPosition().longitude));
//        RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault);
//        mRouteSearch.calculateWalkRouteAsyn(query);
        return false;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return userDefinedMarkerInfoWindow((MarkerInfo) marker.getObject());
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);

        mapView = view.findViewById(R.id.map);// 取高德地图视图对象
        mapView.onCreate(savedInstanceState);// 一致方法调用

        initialize();// 初始化高德地图

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mapView.onDestroy();// 一致方法调用

        if (locationClient != null) {
            locationClient.onDestroy();
        }
        searched = false;
    }

    @Override
    public void onResume() {
        super.onResume();

        mapView.onResume();// 一致方法调用
    }

    @Override
    public void onPause() {
        super.onPause();

        mapView.onPause();// 一致方法调用
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        mapView.onSaveInstanceState(outState);// 一致方法调用
    }

    /**
     * 地图点信息
     */
    private class MarkerInfo {
        private boolean fromApi;
        private String id;
        private int distance;
        private double latitude;
        private double longitude;
        private String name;

        MarkerInfo(PoiItem poiItem) {
            this.fromApi = false;
            this.id = null;
            this.distance = poiItem.getDistance();
            this.latitude = poiItem.getLatLonPoint().getLatitude();
            this.longitude = poiItem.getLatLonPoint().getLongitude();
            this.name = LKAndroidUtils.getString(R.string.find_key);
        }

        MarkerInfo(GetMapMarkerListOut out) {
            this.fromApi = true;
            this.id = out.getId();
            this.distance = out.getDistance();
            this.latitude = out.getLatitude();
            this.longitude = out.getLongitude();
            this.name = out.getName();
        }
    }

    /**
     * 在地图上设置点
     * @param markerInfo 地图点信息
     */
    private void addMarker(MarkerInfo markerInfo) {
        Marker marker = map.addMarker(
                new MarkerOptions()
                        .position(new LatLng(markerInfo.latitude, markerInfo.longitude))// 标记点位置
                        .anchor(0.5f, 0.5f)// 图标位置居中
                        .title(markerInfo.name)// 标题
                        .snippet(markerInfo.distance + LKAndroidUtils.getString(R.string.meter))// 描述
        );
        marker.setObject(markerInfo);
        marker.setIcon(userDefinedMarker(markerInfo));// 自定义标记点
    }

    private static final int API_QUERY_RADIUS = LKPropertiesLoader.getInteger("lichkin.framework.find.api.query.radius");//接口查询半径
    private static final String API_QUERY_KEY = LKPropertiesLoader.getString("lichkin.framework.find.api.query.key");//接口查询关键字

    /**
     * 请求获取地图位置
     */
    private void invokeGetMapMarkerList(final double latitude, final double longitude, final String cityCode) {
        //请求参数
        GetMapMarkerListIn in = new GetMapMarkerListIn(LKMapAPIEnum.AMAP, latitude, longitude, API_QUERY_KEY, API_QUERY_RADIUS);

        //创建请求对象
        final LKRetrofit<GetMapMarkerListIn, List<GetMapMarkerListOut>> retrofit = new LKRetrofit<>(this.getActivity(), GetMapMarkerListInvoker.class);

        //测试代码
        GetMapMarkerListTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<GetMapMarkerListIn, List<GetMapMarkerListOut>>() {

            @Override
            protected void success(Context context, GetMapMarkerListIn getLastAppVersionIn, final List<GetMapMarkerListOut> responseDatas) {
                if (responseDatas == null) {
                    return;
                }
                // 遍历每个查询结果，在地图上设置点。
                for (GetMapMarkerListOut out : responseDatas) {
                    addMarker(new MarkerInfo(out));
                }

                // 调用POI查询
                invokeAMapPOISearchQuery(latitude, longitude, cityCode, responseDatas);
            }

        });
    }

    private static final int POI_SEARCH_QUERY_RADIUS = LKPropertiesLoader.getInteger("lichkin.framework.find.poi.search.query.radius");//POI查询半径
    private static final int DUPLICATED_MARKER_DISTANCE = LKPropertiesLoader.getInteger("lichkin.framework.find.duplicatedMarkerDistance");//重复标记距离

    /**
     * 请求高德地图POI查询接口
     */
    private void invokeAMapPOISearchQuery(double latitude, double longitude, String cityCode, final List<GetMapMarkerListOut> responseDatas) {
        PoiSearch.Query query = new PoiSearch.Query(LKAndroidUtils.getString(R.string.find_key), "", cityCode);// 查询关键字“find_key”；类型“”；城市编码“位置信息中获取”；
        PoiSearch search = new PoiSearch(this.getContext(), query);
        search.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), POI_SEARCH_QUERY_RADIUS));// 查询圆心“位置信息中获取”；查询半径；
        search.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {

            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                // 遍历每个查询结果，在地图上设置点。
                ArrayList<PoiItem> list = poiResult.getPois();
                if (responseDatas == null) {
                    for (PoiItem poiItem : list) {
                        addMarker(new MarkerInfo(poiItem));
                    }
                } else {
                    out:
                    for (PoiItem poiItem : list) {
                        MarkerInfo markerInfo = new MarkerInfo(poiItem);
                        for (GetMapMarkerListOut out : responseDatas) {
                            if (LKAreaUtils.check(markerInfo.latitude, markerInfo.longitude, DUPLICATED_MARKER_DISTANCE, out.getLatitude(), out.getLongitude())) {
                                continue out;
                            }
                        }
                        addMarker(markerInfo);
                    }
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {
            }

        });// 绑定查询结果监听
        search.searchPOIAsyn();// 开始查询
    }

}
