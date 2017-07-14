package com.stip.net.utils;


/**
 * 常量 公共参数
 * 
 * @author chenjunan
 * 
 */
public abstract class ConstantUtils {
	/**
	 * 构造函数
	 */
	private ConstantUtils() {
	}

	/**
	 * 存储当前登录用户id的字段名
	 */
	public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

	/**
	 * token有效期（小时）
	 */
	public static final int TOKEN_EXPIRES_HOUR = 72;

	/**  存放Token的header字段   */      
	public static final String DEFAULT_TOKEN_NAME = "X-Token";
	
	/**需要过滤的页面 */
	public static final String FILTRATION_PAGES = "";
	/**不需要转义处理的接口 */
	public static final String NOFILTER_INTERFACE = "";
	/**不需要转义的参数 */
	public static final String NOFILTER_INTERFACE_PAR = "";
	
	public static final String SENSITIVE_PATH1 = "com/yuguo/net/utils/eroticism.txt";// 热敏词汇地址
	public static final String SENSITIVE_PATH2 = "com/yuguo/net/utils/heresy.txt";// 热敏词汇地址
	public static final String SENSITIVE_PATH3 = "com/yuguo/net/utils/political.txt";// 热敏词汇地址
	public static final String SENSITIVE_PATH4 = "com/yuguo/net/utils/politician.txt";// 热敏词汇地址


}
