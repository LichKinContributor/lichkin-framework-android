package com.lichkin.application.invokers.GetMapMarkerList;

import lombok.Getter;
import lombok.Setter;

/**
 * 获取地图位置
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
public class GetMapMarkerListOut {

    /** 主键 */
    private String id;

    /** 名字 */
    private String name;

    /** 经度 */
    private double longitude;

    /** 纬度 */
    private double latitude;

    /** 距离 */
    private int distance;

}
