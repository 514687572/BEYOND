package com.stip.net.utils;

import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author AIDAN
 * 
 */
public class StringUtil {

	/**
	 * 判断是否为数字字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static void main(String args[]) {
		new Thread(new Runnable() {

			public void run() {
				try {
					Thread.sleep(10000);
					System.out.println("1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();

		System.out.println("2");
	}

}
