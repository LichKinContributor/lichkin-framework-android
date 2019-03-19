package com.lichkin.framework.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 地址工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKAreaUtils {

	/** 地球半径 */
	private static final double EARTH_RADIUS = 6378137;

	/** 经纬分片长 */
	private static double PIECE_LONG = Math.PI / 180.0;

	/** 夹角计算倍数 */
	private static double MULTIPLE = 2.0;


	/**
	 * 计算两点之间距离
	 * @param latitude 圆心纬度
	 * @param longitude 圆心经度
	 * @param latitudeX 待判定纬度
	 * @param longitudeX 待判定经度
	 * @return 距离
	 */
	public static double distance(double latitude, double longitude, double latitudeX, double longitudeX) {
		double pieceLatitude = latitude * PIECE_LONG;
		double pieceLatitudeX = latitudeX * PIECE_LONG;
		double distanceLatitude = pieceLatitude - pieceLatitudeX;
		double distanceLongtitude = (longitude - longitudeX) * PIECE_LONG;
		return MULTIPLE * Math.asin(Math.sqrt(Math.pow(Math.sin(distanceLatitude / MULTIPLE), 2) + (Math.cos(pieceLatitude) * Math.cos(pieceLatitudeX) * Math.pow(Math.sin(distanceLongtitude / MULTIPLE), 2)))) * EARTH_RADIUS;
	}


	/**
	 * 校验是否在范围内（柱状范围）
	 * @param latitude 圆心纬度
	 * @param longitude 圆心经度
	 * @param altitude 圆心高度
	 * @param radius 水平面半径（米）
	 * @param ogham 垂直面落差（米）
	 * @param latitudeX 待判定纬度
	 * @param longitudeX 待判定经度
	 * @param altitudeX 待判定高度
	 * @return 在范围内返回true，否则返回false。
	 */
	public static boolean check(double latitude, double longitude, Double altitude, int radius, Integer ogham, double latitudeX, double longitudeX, Double altitudeX) {
		// 无高度做平面校验
		if ((altitude == null) || (ogham == null) || (altitudeX == null)) {
			return check(latitude, longitude, radius, latitudeX, longitudeX);
		}

		// 平面校验不通过直接返回
		if (!check(latitude, longitude, radius, latitudeX, longitudeX)) {
			return false;
		}

		// 平面校验通过，校验高度。
		return Math.abs(altitude - altitudeX) < ogham;
	}


	/**
	 * 校验是否在范围内（平面圆范围）
	 * @param latitude 圆心纬度
	 * @param longitude 圆心经度
	 * @param radius 水平面半径
	 * @param latitudeX 待判定纬度
	 * @param longitudeX 待判定经度
	 * @return 在范围内返回true，否则返回false。
	 */
	public static boolean check(double latitude, double longitude, int radius, double latitudeX, double longitudeX) {
		return distance(latitude, longitude, latitudeX, longitudeX) < radius;
	}

}
