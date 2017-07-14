package com.stip.net.utils;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

public class GenerateSequenceUtil {

	/** .log */
	private static final Logger logger = Logger.getLogger(GenerateSequenceUtil.class);

	/** The FieldPosition. */
	private static final FieldPosition HELPER_POSITION = new FieldPosition(0);

	/** This Format for format the data to special format. */
	private final static Format dateFormat = new SimpleDateFormat("MMddHHmmssS");

	/** This Format for format the number to special format. */
	private final static NumberFormat numberFormat = new DecimalFormat("0000");

	/** This int is the sequence number ,the default value is 0. */
	private static int seq = 0;

	private static final int MAX = 9999;

	/**
	 * 时间格式生成序列
	 * 
	 * @return String
	 */
	public static synchronized String generateSequenceNo() {

		Calendar rightNow = Calendar.getInstance();

		StringBuffer sb = new StringBuffer();

		dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);

		numberFormat.format(seq, sb, HELPER_POSITION);

		if (seq == MAX) {
			seq = 0;
		} else {
			seq++;
		}

		logger.info("THE SQUENCE IS :" + sb.toString());
		Date date = new Date();
		String sequence = date.getYear() + sb.toString() + date.getSeconds();

		return sequence;
	}

	/**
	 * 生成生活服务ORDERID
	 * 
	 * @param startStr
	 *            起始字符
	 * @param pwd_len
	 *            生成随机数长度不包括起始字符
	 * @return 随机数
	 */
	public static String genRandomNum(String startStr, int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return startStr + pwd.toString();
	}

	/**
	 * 生成商户编号
	 * 
	 * @param pwd_len
	 * @return
	 */
	public static String genShopNum(int pwd_len) {
		String startStr = "669";
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { '0', '3', '2', '1', '4', '9', '6', '7', '8', '5' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return startStr + getTimeSeconds() + pwd.toString();
	}

	/**
	 * 生成商家编号
	 * 
	 * @param pwd_len
	 * @return
	 */
	public static String genSellerNum() {
		String startStr = "15";
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { '0', '3', '2', '1', '4', '9', '6', '7', '8', '5' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < 8) {
			// 生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return startStr + pwd.toString();
	}

	/**
	 * 生成密码
	 * 
	 * @return
	 */
	public static String genPassword() {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { '0', '3', '2', '1', '4', '9', '6', '7', '8', '5' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < 12) {
			// 生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	/**
	 * 随机生成8位大写字母+数字的验证码
	 * 
	 * @return
	 */
	public static String genVerifyCode() {
		char c = 'i';
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			int a = Math.abs((new Random()).nextInt(57));// 产生0~57的随机数
			if (a <= 9) {// 将0~9转为char的0~9
				sb.append((char) (a + 48));
			} else if (a < 33) {// 将10~33转为char的A~Z
				if ((a + 55) == 79 || (a + 55) == 73) {
					sb.append((char) (a + 63));
				} else {
					sb.append((char) (a + 55));
				}
			} else {// 将34~57转为char的a~z
				if (a == 33) {
					a = Math.abs((new Random()).nextInt(55));
				}
				sb.append((char) (a + 63));
			}
		}

		return sb.toString().toUpperCase();
	}

	public static String getTimeSeconds() {
		Date date = new Date();
		String timeSeconds = date.getYear() + "" + date.getMonth() + "" + date.getDay() + "" + date.getSeconds() + "";
		int i = 4 - timeSeconds.length();
		for (int j = 0; j <= i; j++) {
			timeSeconds = "0" + timeSeconds;
		}

		return timeSeconds;
	}

	public static void main(String args[]) {
		System.out.println(GenerateSequenceUtil.genSellerNum());
	}

	/**
	 * 支付宝第三方 授权 商户自定义参数，用户授权后，重定向到redirect_uri时会原样回传给商户。
	 * 为防止CSRF攻击，建议开发者请求授权时传入state参数， 该参数要做到既不可预测，又可以证明客户端和当前第三方网站的登录认证状态存在关联。
	 * 
	 * @return
	 */
	public static String getAlipayState() {
		char c = 'i';
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			int a = Math.abs((new Random()).nextInt(57));// 产生0~57的随机数
			if (a <= 9) {// 将0~9转为char的0~9
				sb.append((char) (a + 48));
			} else if (a < 33) {// 将10~33转为char的A~Z
				if ((a + 55) == 79 || (a + 55) == 73) {
					sb.append((char) (a + 63));
				} else {
					sb.append((char) (a + 55));
				}
			} else {// 将34~57转为char的a~z
				if (a == 33) {
					a = Math.abs((new Random()).nextInt(55));
				}
				sb.append((char) (a + 63));
			}
		}

		return "YG" + sb.toString().toUpperCase();
	}

	/**
	 * 第三方登录没昵称时随机生成一个
	 * 
	 * @return
	 */
	public static String getNickName() {
		Random rad = new Random();
		return "YG_" + rad.nextInt(10000000) + "";
	}

	/**
	 * 商家登录密码
	 * 
	 * @param userName
	 * @param phone
	 * @return
	 */
	public static String getPassWord(String userName, String phone) {
		String result = PinYin4jUtil.cn2FirstSpell(userName) + phone.substring(5);
		return result;
	}
}