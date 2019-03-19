package com.lichkin.application.invokers.GetMapMarkerList;

import com.lichkin.framework.defines.beans.LKRequestBean;
import com.lichkin.framework.defines.enums.impl.LKMapAPIEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 获取地图位置
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@RequiredArgsConstructor
public class GetMapMarkerListIn extends LKRequestBean {

    /** 地图类型 */
    private final LKMapAPIEnum mapType;

    /** 查询圆心纬度 */
    private final double latitude;

    /** 查询圆心经度 */
    private final double longitude;

    /** 查询关键字 */
    private final String key;

    /** 查询半径 */
    private final int radius;

}
