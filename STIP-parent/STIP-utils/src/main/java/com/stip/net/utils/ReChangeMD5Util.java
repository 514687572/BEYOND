package com.stip.net.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by admin on 14-11-17.
 */
public class ReChangeMD5Util {
	/**
	 * 缓冲区大小
	 */
	private static final int INIT_SIZE = 20;

	private static final int STRINGPLANNINGLENGTH = 16;

	private static final int BYTEMINLENGTH = 256;

	/**
	 * 定义数组，存放16进制字符
	 */
	private static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * constructor
	 */
	private ReChangeMD5Util() {

	}

	/**
	 * 将字符串进行MD5编码
	 * 
	 * @param srcStr
	 *            源字符串
	 * @return 编码后的字符串
	 */
	public static String digest(String srcStr) {
		if (null == srcStr) {
			return null;
		}

		byte sa[] = srcStr.getBytes();
		String destStr = digest(sa);

		return destStr;
	}

	/**
	 * 将字符串进行MD5编码
	 * 
	 * @param srcStr
	 *            源字符串
	 * @return 编码后的字符串
	 */
	public static String digest(String srcStr, String encoding) {
		if (null == srcStr) {
			return null;
		}

		String destStr = null;
		byte sa[];
		try {
			sa = srcStr.getBytes(encoding);
			destStr = digest(sa);
		} catch (UnsupportedEncodingException e) {
		}

		return destStr;
	}

	public static String digest(byte sa[]) {
		if (null == sa) {
			return null;
		}

		String destStr = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(sa);

			byte theDigest[] = md.digest();
			destStr = byteArrayToHexString(theDigest);
		} catch (NoSuchAlgorithmException ex) {
			throw new UnsupportedOperationException(ex);
		}

		return destStr;
	}

	/**
	 * 将byte数组转换成16进制字符串
	 * 
	 * @param b
	 *            byte 待转化的数组
	 * @return String 转化完成生成的字符串
	 */
	private static String byteArrayToHexString(byte[] b) {
		if (b == null || b.length == 0) {
			return null;
		}

		StringBuffer result = new StringBuffer(INIT_SIZE);

		for (int i = 0; i < b.length; i++) {
			result.append(byteToHexString(b[i]));
		}

		return result.toString();
	}

	/**
	 * 将byte字节转化成16进制字符串
	 * 
	 * @param b
	 *            byte 待转化的字节
	 * @return String 转化完成生成的字符串
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = BYTEMINLENGTH + n;
		}
		int d1 = n / STRINGPLANNINGLENGTH;
		int d2 = n % STRINGPLANNINGLENGTH;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String hmacSign(String aValue, String aKey) {
		byte kIpad[] = new byte[64];
		byte kOpad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes("UTF-8");
			value = aValue.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}
		Arrays.fill(kIpad, keyb.length, 64, (byte) 54);
		Arrays.fill(kOpad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			kIpad[i] = (byte) (keyb[i] ^ 0x36);
			kOpad[i] = (byte) (keyb[i] ^ 0x5c);
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		md.update(kIpad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(kOpad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return byteArrayToHexString(dg);
	}

}
