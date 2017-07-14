package com.stip.net.utils;

import java.util.Arrays;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 高德地图API
 * 
 * @author lihang
 * 
 */
public class MapUtil {
	private static final String geocode_url = "http://restapi.amap.com/v3/geocode/geo"; // 地理编码地址
	private static int count = 3; // 剩余执行次数

	/**
	 * 根据详细地址获取坐标
	 * 
	 * @param address
	 * @return
	 */
	public static String[] getCoordinate(String address) {
		String[] locations = null;
		try {
			String result = HttpClientUtil.sendGetRequest(geocode_url, "address=" + address + "&output=json&key=46a0649b7fe044057ef5a34f340da7a8", 1000, 1000);
			JSONObject jsonObject = JSONObject.fromObject(result);
			JSONArray geocodesObj = jsonObject.getJSONArray("geocodes");
			JSONObject info = geocodesObj.getJSONObject(0);
			locations = info.getString("location").split(",");
			System.out.println(Arrays.toString(locations));
			count = 3; // 初始化
		} catch (Exception e) {
			e.printStackTrace();
			count--;
			if (count > 0) {
				getCoordinate(address);
			}
		}
		return locations;

	}

	public static void main(String[] args) {
		System.out.println(getCoordinate("四川省成都市青羊区太升路街道青羊东二街91号"));
	}
}
