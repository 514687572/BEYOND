package com.stip.net.utils;

public class ToolsUtils {
	/**
	 * 返回支付方式
	 * 
	 * @param receiverId
	 * @param UserId
	 * @return
	 */
	public static String getPayWay(String payWay) {
		if (ConstantUtils.PAYWAY1.equals(payWay)) {
			return "支付宝";
		} else if (ConstantUtils.PAYWAY2.equals(payWay)) {
			return "银联";
		} else if (ConstantUtils.PAYWAY3.equals(payWay)) {
			return "其他";
		} else if (ConstantUtils.PAYWAY4.equals(payWay)) {
			return "微信支付";
		} else if (ConstantUtils.PAYWAY5.equals(payWay)) {
			return "货到付款";
		} else if (ConstantUtils.PAYWAY6.equals(payWay)) {
			return "在线支付";
		}

		return payWay;
	}
}
