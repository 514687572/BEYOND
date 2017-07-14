package com.stip.net.utils;

import java.security.MessageDigest;
import java.util.Map;

public class SignUtil {
	public static String createSign(Map<Object, Object> map, String accountKey) throws Exception {
		if (accountKey == null || accountKey.equals("")) throw new Exception("缺少API账户密钥");

		String[] originalArray = { "Version=" + map.get("version"), "AccountID=" + map.get("accountId"), "ServiceName=" + map.get("serviceName"), "ReqTime=" + map.get("reqTime") };
		String[] sortedArray = bubbleSort(originalArray);
		String sign = getMD5ByArray(sortedArray, accountKey, "utf-8");
		return sign;
	}

	private static String getMD5ByArray(String[] sortedArray, String accountKey, String charset) {
		StringBuilder prestr = new StringBuilder();
		for (int i = 0; i < sortedArray.length; i++) {
			if (i == sortedArray.length - 1) {
				prestr.append(sortedArray[i]);
			} else {
				prestr.append(sortedArray[i] + "&");
			}
		}
		prestr.append(accountKey);
		return getMD5(prestr.toString(), charset);
	}

	private static String getMD5(String input, String charset) {
		String s = null;
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes(charset));
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	private static String[] bubbleSort(String[] originalArray) {
		int i, j;
		String temp;
		Boolean exchange;
		for (i = 0; i < originalArray.length; i++) {
			exchange = false;
			for (j = originalArray.length - 2; j >= i; j--) {
				if (originalArray[j + 1].compareTo(originalArray[j]) < 0) {
					temp = originalArray[j + 1];
					originalArray[j + 1] = originalArray[j];
					originalArray[j] = temp;
					exchange = true;
				}
			}
			if (!exchange) {
				break;
			}
		}
		return originalArray;
	}
}
