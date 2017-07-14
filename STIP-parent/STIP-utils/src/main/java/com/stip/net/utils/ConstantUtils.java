package com.stip.net.utils;

import java.util.HashMap;
import java.util.Map;

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

	public static final String APP_REQUEST = "APP"; // 手机端请求
	public static final String IOS_REQUEST = "IOS"; // IOS手机端请求
	public static final String WEB_REQUEST = "WEB"; // WEB端请求
	public static final String H5_REQUEST = "H5"; // H5端请求

	// 生活服务订单状态 0待付款 1支付成功（待确认）2订单进行中（已确认） 3订单已完成 4订单已取消 5订单配送中
	// 6已经评论7退款审核8退款中9退款成功10已删除
	// 购物订单状态 1待付款 2待收货 3待评价 4待退换货 5完成 all 所有订单 6 取消订单 7删除状态
	// 销售记录表 状态：0已支付（待发货） 1申请退货退款 2退货退款中 3退货退款完成 4已发货（待收货） 5待评价（已收货）6已评价
	// 人人夺宝状态 :0未开始，1进行中，2正在揭晓，3已揭晓，4流筹，5未审核，6审核未通过，7未上传，8上传失败',
	public static final String MALL_ZERO = "0";
	public static final String MALL_OBLIGATION = "1";
	public static final String MALL_RECEIPT = "2";
	public static final String MALL_WAIT_COMMENT = "3";
	public static final String MALL_EXCHANGE = "4";
	public static final String MALL_FINISH = "5";
	public static final String ALL_ORDERS = "all";
	public static final String CANCEL_ORDER = "6";
	public static final String ISDELETE_ORDER = "7";
	public static final String REFUND_AUDIT = "7";
	public static final String REFUND_ING = "8";
	public static final String REFUND_SUCCESS = "9";
	public static final String ORDER_DELETED = "10";

	// 商户、活动、商品、分类通用状态
	public static final String AUDIT_UNPASS = "0"; // 未通过审核
	public static final String AUDIT_PASSED = "1"; // 通过审核
	public static final String AUDIT_COMMITE = "2"; // 提交审核
	public static final String AUDIT_ONSHELVE = "3"; // 已上架
	public static final String AUDIT_UNDERSHELVE = "4"; // 已下架
	public static final String AUDIT_UPLOAD_FAIL = "5"; // 上传失败
	public static final String AUDIT_DELETED = "9"; // 已删除

	// 订单验证状态
	public static final String VALIDATE_USED = "1"; // 已使用
	public static final String VALIDATE_UNUSE = "2"; // 未使用

	public static final String NOT_PUTAWAY = "0"; // 下架 删除 不推荐
	public static final String IS_PUTAWAY = "1"; // 上架 正常 推荐

	public static final String NOT_DEFAULT = "0"; // 非默认收货地址
	public static final String IS_DEFAULT = "1"; // 默认收货地址

	public static final String APP_DATA = "1"; // 手机端数据
	public static final String WEB_DATA = "2"; // WEB端数据

	public static final String UNACTIVE = "0"; // 未激活
	public static final String NORMAL = "1"; // 正常
	public static final String FORBIDDEN = "2"; // 禁用

	public static final String PAYWAY1 = "1"; // 支付宝
	public static final String PAYWAY2 = "2"; // 网银
	public static final String PAYWAY3 = "3"; // 其他
	public static final String PAYWAY4 = "4"; // 微信
	public static final String PAYWAY5 = "5"; // 货到付款
	public static final String PAYWAY6 = "6"; // 在线支付

	public static final String VALIDITY1 = "0"; // 未使用
	public static final String VALIDITY2 = "1"; // 已使用

	public static final String DISPOSE_RESULT1 = "0"; // 未处理
	public static final String DISPOSE_RESULT2 = "1"; // 已处理

	public static final String ADVICE = "1"; // 建议
	public static final String COMPLAINT = "2"; // 投诉

	// 需要过滤的页面
	public static final String FILTRATION_PAGES = "/alipayapi.jsp;/bankPay.jsp;/alipayLogin.jsp;/alipayRefund.jsp;/alipayRel.jsp;/alipayThirdLogin.jsp;" + "/bankPay.jsp;/notify_refund.jsp;/notify_url.jsp;/return_lgUrl.jsp;/return_url.jsp;/transfer_refund.jsp;/transfer.jsp;/transferThirdLg.jsp;" + "/milk.jsp;/order_logistics.jsp;/shopping.jsp;/member.jsp;/wx_pay.jsp;/wx_pays.jsp;";

	// 不需要转义处理的接口
	public static final String NOFILTER_INTERFACE = "/getGoodsInfo.do;/login;/getGoodsInfo.do;/getShopByOSS.do;/getGoodsByOSS.do;/getMenuByOSS.do;/getGoodsClassByOSS.do;" + "/getPactByOSS.do;/getActivityByOSS.do;/getLfesByOSS.do;/synchronizationRefundStatus.do;/getLogisticsNum.do;/logisticsInfo.do;/sendShipmentsToUser.do;/creatLsesShopsOrder.do;" + "/orderStatusSynchronization.do;/getADInfo.do;/getUploadNoticeData.do;/getGroupBy;/getActivitysMoney.do;/createOrder.do;/saveUserOrder.do;/delGoodsToCar.do;/checkAttrId.do" + "/getGoodsClassInfo.do;/imgUpload;/modifyUserHradIcon.do;/uploadHeadIcon.do;/addGoodsCommentForApp.do;/addLsesGoodsCommentForApp.do;/addRefundForApp.do;/saveOrder.do;"
			+ "/createOrder.do;/addCart.do;/getMallStoreInfo.do;/toShowPage.do;/addSingUpInfo.do;/modifyUserHradIconForSinger.do;/getSingerInfoByUserId.do;/updateSingerInfo.do;/messageUploadInfo.do;/saveMeadia.do;" + "/merchantApply.do;/getCrowdRecordsInfo.do;/uploadCommentImg.do;/getUploadDictionaryData.do;/getAdminApk.do;/getSecKillGoodsForAdmin.do;/checkAttrId.do;/getLfesClassAllByOSS.do";

	public static final String NOFILTER_INTERFACE_PAR = "yuguo_token_669;password;payPassword;newPassword;XnKllf;userName;oldPassword;checkedAll";
	/**
	 * H5页面
	 */
	public static final String NOFILTER_H5 = "/apkDownload.do;/activity/;/cybercafe/;/singer/;/activitySing/;/app/;/alipayapi.jsp;/toWapReturnIndiana.do;/getAdminApk.do";
	/**
	 * 无需HEADER的API
	 */
	public static final String NOFILTER_NO_HEADER = "/ws;/ws/sockjs;/toUserBound.do;/qcCheck.do;/logisticsInfo.do;";

	public static final int SIGN_VALID = 1; // 注册验证
	public static final int MODIFY_VALID = 2; // 修改信息验证
	public static final int BOUND_VALID = 3; // 手机绑定验证码
	public static final int CHANG_PAY_PASSWORD = 4; // 支付密码修改验证码
	public static final int LOGIN_PASSWORD = 5; // 登录密码修改验证码
	public static final int SHOP_PASSWORD = 6;// 商家后台登录密码修改验证码 // 登录密码修改验证码
	public static final int SELLER_PASSWORD = 7; // 卖家后台登录密码修改验证码
	public static final int ORDER_MESSAGE = 6;
	public static final int APPOI_MESSAGE = 8; // 预约KTV验证
	public static final int WEBSHOPPING_MESSAGE = 9; // 购物同步后台成功发信息到对应商家
	public static final int MERCHANT_MESSAGE = 10; // web商户注册成功后账户密码信息
	public static final int SHOP_DELIVER = 11; // 骑手注册验证码
	public static final int SHOP_DELIVER_OLDTELPHONE = 14; // 骑手修改手机号验证码(新)
	public static final int SHOP_DELIVER_CHANGEPWD = 13; // 骑手修改密码验证码
	public static final int SHOP_DELIVER_NEWTELPHONE = 12;
	// 订单短信提醒
	public static final int RECHARGE_MESSAGE = 7; // 手机充值短信提醒

	public static final String IS_VALID = "1"; // 有效
	public static final String IS_NOT_VALID = "0"; // 无效

	public static final String SEX_B = "0"; // 男
	public static final String SEX_G = "1"; // 女
	public static final String SEX_S = "2"; // 保密

	// public static final String SERVER_URL =
	// "http://6s.net579.com:16176/yuguo/";
	public static final String SERVER_URL_ADMIN = "http://yuguoadmin.com:8081/"; // 与果外网
	public static final String SERVER_URL_TEST = "http://yuguoadmin.com:8081/"; // 与果网测试地址
	// public static final String SERVER_URL_TEST =
	// "http://192.168.0.188:8080/yuguoAdmin/"; // 与果网测试地址
	// public static final String SERVER_URL_TEST =
	// "http://192.168.0.201:8085/yuguoAdmin/"; // 与果网测试地址
	// public static final String SERVER_URL =
	// "http://192.168.0.20:8082/yuguoAdmin/"; // 与果外网地址
	public static final String SERVER_URL_OSS = "http://yuguoimages.com:8082/"; // 运营支撑系统外网地址
	public static final String SERVER_URL = "https://www.yg669.com/"; // 与果外网地址
	public static final String IMAGE_SERVER_URL = "https://yuguoimages.com/"; // 与果网图片服务器地址
	// public static final String
	// IMAGE_SERVER_URL="http://192.168.0.200:8080/filesMS/" ; //与果网图片服务器地址
	// public static final String
	// IMAGE_SERVER_URL="http://192.168.0.239:8082/filesMS";
	public static final String SERVER_URL_SAS = "http://yuguoadmin.com:8082/"; // 商户管理系统外网地址
	//public static final String SERVER_URL_SAS = "http://192.168.0.239:8084/yuguoSAS/"; // 商户管理系统外网地址
//	public static final String SERVER_URL_SAS_TEST = "http://192.168.0.103:6060/yuguoSAS/"; // 商户管理系统外网地址
//	public static final String SERVER_URL_SAS_TEST = "http://192.168.0.239:8084/yuguoSAS/"; // 商户管理系统外网地址
	//public static final String SERVER_URL_SAS_TEST1 = "http://192.168.0.188:8080/yuguoSAS/"; // 商户管理系统外网地址
	//public static final String SERVER_URL_SAS_TEST2 = "http://192.168.0.115:8081/yuguoSAS/"; // 商户管理系统外网地址

	public static final String USER_LEVEL_1 = "注册用户"; //
	public static final String USER_LEVEL_2 = "铜牌用户"; //
	public static final String USER_LEVEL_3 = "银牌用户"; //
	public static final String USER_LEVEL_4 = "金牌用户"; //
	public static final String USER_LEVEL_5 = "钻石用户"; //

	public static final String INTEGRAL_NOTE = "INTEGRAL_NOTE"; //

	public static final String DEFAUT_ICO = "/images/member/member2(1).png"; // 默认用户头像

	public static final String SEARCH_GOODS = "goods"; // 搜索商品
	public static final String SEARCH_SHOPS = "shops"; // 搜索商家
	public static final String GOODS_BARCODE = "goods_barcode"; // 搜索商家

	public static final String LOGISTICS_KEY = "jHgfEWhe3441"; // 物流key

	public static final String SUCCESS = "success"; // 成功
	public static final String FAIL = "fail"; // 失败

	public static final String IS_CHILD_GOODS = "0"; // 是子商品
	public static final String IS_PARENT_GOODS = "1";// 是父商品

	public static final String SALES_RETURN = "0"; // 优惠券状态1正常 0 已过期 2 已经使用
	public static final String SALES_CHANGE = "1"; // 优惠券状态1正常 0 已过期 2 已经使用
	public static final String SALES_REPAIR = "2"; // 优惠券状态1正常 0 已过期 2 已经使用

	public static final String LSES_CLASS_TYPE1 = "1";// 分类1 普通分类 销售属性存在默认并选中
														// 按件计费
	public static final String LSES_CLASS_TYPE2 = "2";// 分类2 品牌 销售属性存在不选中 按重量计费
	public static final String LSES_CLASS_TYPE3 = "3";// 分类3 其他 销售属性不存在 按体积计费

	public static final String CLASS_TYPE3 = "3";// 分类classType 3网咖
	public static final String CLASS_TYPE4 = "4";// 分类classType 4夺宝

	public static final String RECHARGE_STATAS0 = "0"; // 充值中
	public static final String RECHARGE_STATAS1 = "1"; // 充值成功
	public static final String RECHARGE_STATAS9 = "9"; // 充值失败
	public static final String RECHARGE_STATAS2 = "2"; // 末付款订单
	public static final String RECHARGE_STATAS_1 = "-1"; // 需要根欧飞人工核实 1 成功
	public static final String RECHARGE_STATAS5 = "5"; // 错误的命令
	public static final String RECHARGE_STATAS6 = "6"; // 接口版本错
	public static final String RECHARGE_STATAS7 = "7"; // 代理商校验错
	public static final String RECHARGE_STATAS8 = "8"; // 不存在的代理商
	public static final String RECHARGE_STATAS10 = "10"; // 未定义
	public static final String RECHARGE_STATAS11 = "11"; // 运营商地区维护，暂不能充值
	public static final String RECHARGE_STATAS12 = "12"; // 库存不足
	public static final String RECHARGE_STATAS1001 = "1001"; // 商户名验证错误
	public static final String RECHARGE_STATAS1002 = "1002"; // 商户IP验证错误
	public static final String RECHARGE_STATAS1003 = "1003"; // MD5串验证错误
	public static final String RECHARGE_STATAS1004 = "1004"; // 此商品暂不可用
	public static final String RECHARGE_STATAS1005 = "1005"; // 购买的商品数量超出系统要求
	public static final String RECHARGE_STATAS1007 = "1007"; // 账户金额不足
	public static final String RECHARGE_STATAS1008 = "1008"; // 缺少必需参数
	public static final String RECHARGE_STATAS1009 = "1009"; // 会员账户不存在
	public static final String RECHARGE_STATAS1010 = "1010"; // 查询结果为空
	public static final String RECHARGE_STATAS9999 = "9999"; // 未知错误(如果返回该结果码先调用查询订单状态接口，确认一下订单状态，避免不必要的损失)
	public static final String RECHARGE_STATAS105 = "105"; // 请求失败(如果返回该结果码先调用查询订单状态接口，确认一下订单状态，避免不必要的损失)
	public static final String RECHARGE_STATAS302 = "302"; // 您的账号暂时不能进行交易
	public static final String RECHARGE_STATAS305 = "305"; // 充值失败未知错误
	public static final String RECHARGE_STATAS319 = "319"; // 充值的手机号不正确
	public static final String RECHARGE_STATAS320 = "320"; // 身份证号码格式不正确
	public static final String RECHARGE_STATAS321 = "321"; // 暂时不支持此类手机号的充值
	public static final String RECHARGE_STATAS331 = "331"; // 订单生成失败
	public static final String RECHARGE_STATAS334 = "334"; // 订单生成超时(如果返回该结果码先调用查询订单状态接口，确认一下订单状态，避免不必要的损失)
	public static final String RECHARGE_STATAS1043 = "1043"; // 支付超时，订单处理失败(如果返回该结果码先调用查询订单状态接口，确认一下订单状态，避免不必要的损失)
	public static final String RECHARGE_STATAS2000 = "2000"; // 订单号不存在
	public static final String RECHARGE_STATAS2001 = "2001"; // 该订单已经被处理过了
	public static final String RECHARGE_STATAS2010 = "2010"; // 发生错误，订单处理失败

	public static final String IS_COLLECT = "1"; // 已收藏
	public static final String NOT_COLLECT = "2";// 未收藏

	public static final String NEW_APK_STATUS = "1"; // 新版本
	public static final String OLD_APK_STATUS = "0";// 旧版本
	public static final String APK_DOWNLOAD_URL = "/downLoad/query/apkDownload.do";// apk下载地址
	public static final String IOS_DOWNLOAD_URL = "https://itunes.apple.com/cn/app/yu-guo/id1130063421";

	public static final String ORDER_TYPE_LIFE = "1"; // 生活服务订单
	public static final String ORDER_TYPE_GROUP = "2"; // 团购订单
	public static final String ORDER_TYPE_MILK = "3"; // 鲜奶订单

	// 生活服务活动类型 0 团购 1 代金券 2 满减 3主题汇 4首页活动 5首单立减 6满免配送费 7定时优惠 8返现
	public static final String ACTIVITY_ZERO = "0"; // 团购
	public static final String ACTIVITY_ONE = "1"; // 代金券
	public static final String ACTIVITY_TWO = "2"; // 满减
	public static final String ACTIVITY_THREE = "3"; // 首单
	public static final String ACTIVITY_FOUR = "4"; // 首页活动 赠送活动
	public static final String ACTIVITY_FIVE = "5"; // 商品折扣
	public static final String ACTIVITY_SIX = "6"; // 满免配送费
	public static final String ACTIVITY_SEVEN = "7"; // 定时优惠
	public static final String ACTIVITY_EIGHT = "8"; // 返现
	public static final String ACTIVITY_NINE = "9"; // 送上楼
	public static final String ACTIVITY_TEN = "10"; // 秒杀
	public static final String ACTIVITY_ELEVEN = "11"; // 充送
	public static final String ACTIVITY_TWLEVE = "12"; // 优惠满减
	public static final String ACTIVITY_THIRTEEN = "13"; // 优惠折扣

	// 商城活动类型 1满减 2打折
	public static final String ACTIVITY_MJ = "1"; // 满减
	public static final String ACTIVITY_DZ = "2"; // 打折

	// 特殊字符
	@SuppressWarnings("serial")
	public static final Map<String, String> HTML_ESC_MAP = new HashMap<String, String>() {
		{
			put("<", "&lt;");
			put(">", "&gt;");
			put("\"", "&#034;");
			put("\'", "&#039;");
		}
	};

	public static final String CERTIFICATE_PATH = "com/yuguo/net/imessage/push.p12";// ios消息推送证书
	public static final String CERTIFICATE_PATH_TEST = "com/yuguo/net/imessage/pushtest.p12";// ios消息推送证书
	public static final String CERTIFICATE_PASSWORD = "yuguo669";// 此处注意导出的证书密码不能为空因为空密码会报错
	public static final String MESSAGE_TYPE_ORDER = "ORDER";// 订单
	public static final String MESSAGE_TYPE_COUPON = "COUPON";// 优惠
	public static final String MESSAGE_TYPE_LOGISTICT = "LOGISTICT";// 物流
	public static final String MESSAGE_TYPE_CASH = "CASH";// 现金券
	public static final String MESSAGE_TYPE_SIGN = "SIGN";// 打卡 签到

	public static final String MESSAGE_IS_READ = "3";// 已读
	public static final String MESSAGE_IS_SUCCESS = "1";// 发送成功

	public static final String SINA_APP_KEY = "492424342";// 新浪app key
	public static final String SINA_APP_SECRET = "4322a833c80f2897d860795d865b4431";// 新浪app
																					// secret
	public static final String SINA_GENDER_MAN = "m";// 男
	public static final String SINA_GENDER_WOMEN = "f";// 女
	public static final String SINA_GENDER_PRIVARY = "n";// 保密

	// 用户来源｛QQ，微信 ，新浪微博， 支付宝｝
	public static final String USER_FROM_QQ = "QQ";
	public static final String USER_FROM_WECHAT = "WECHAT";
	public static final String USER_FROM_SINA = "SINA";
	public static final String USER_FROM_ALIPAY = "ALIPAY";
	/** 商户私钥，pkcs8格式 */
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL26761dzS+8jV/fTrbU9RQSPHD+1ir1ARDDfxBhl2n6S34LNiGZi7v9lmC3p3mgNu58IU4dDlTssuTy2WzBqhCCR03BLRpv1/FAN223UoUsDT8Uj30mGeNQebrISc2H0HdDaRl+kJU7JSddouKZNs7jTqqOU1B2nURLCAmOacDhAgMBAAECgYBZURtrvfDOPDFBpNplb7NAE6WofA9OgbBSt/WlPIwPM+k+GQEOqD4D7clRFqQcubOiaYU0RLFugU9mQHzimfj4o5n/wOlpgmiFKnv2wR07tgIeKahczQs+dMt2dJoFArOChDlKUedntR4HDd5PsFN2P+e75RyY1vec5Uuxs7WQUQJBAPiWiIjpjAOTQTvIZ+cj10I96rkrySg/BPU1/Dsgz6N9kUavqgNK/WRHOeBnMp0SyZR3qtvDZaBBC3I0ZDUdQGsCQQDDYySE9T1F6ouMihiXGCtQa5+JK/SaLs/ikTe67RoylU2LYTGFafTvWOXbL0tcqpZ0yybBhvCMvU5Y1qQxLWbjAkALpwK/aDMn978XDk3Qh92PPVfDVkoXmNyioeUw4FjbUQd+SL2vadR23t/XpP66XicUzoczxfT9jg77S3lnA/gBAkAIXZhUsQNcmPw8mjUPUxOXr+P2xZEFgPeZpcoTEM/MVqQpUZIDOlQoY1AEIHRDlXI3sfMI2jQ2cmyGVTpWeLEtAkEAtSM2cB2TZrWBSz4UebT1XJfcQC9fCY0bxN9IuXI4bjcG4OCwepRZjNkUJTbCBmfjXAXiEGyr41JbpQHGD66sWg==";
	public static final String USUAL_RSA_PRIVATE = "MIICWwIBAAKBgQC3Havj8pKGpcBaqDBnbjjTwhP4A75chrHZwYGzJ7CCNFR+QZ0OezMIYtXjpe+FOsByYKHDQX/rkeib787pN+xt80fd0dENQGMHAXUl07fGBf6EqxOJsjcA5ulyid+tsNS4MW7QJpGCutG/PNbbxM4IbDlwBPIJpN9w/CIYVjpyzwIDAQABAn8dU6Mz3qWY6vLjkgwuhHiVcX/BONhD+U6CkXs0NicOs1LU5a1BpHmH3kxjKTAzJjWsG83Md7EFCVk3oDCDKXGz5NjZYkUPp7XhOzjIZTl7MnkNCQnLgWLXCvyVO6tR09vFRj3gmeabhGviESkd2gVFwQa7M70tTkg8LcutBAY5AkEA4dLSkx+hAgXBU42EuFZqiDyGvu19EK4WYzTRfZ1lPGxY8e335tFybjB+zElVsXHqTgrGwOgYMUJWo3169CEuDQJBAM+V3hh8sWVA9N84tjqN1bx7wo3u6gROkGvP8Ni7tb447gc7NuN6E+7KdZW+VAxCXfbadeNxuPoc2QdH3QARiUsCQQCvQdznDY75cNn/LD+qiHaZUNAKcWm0fkclqyJCaNrer/+/kr3oRviWB9EAXpHA6vW6p6ZkpDvSTvfKXmIEAZQpAkAjdnrmvAaiQaKTr+CRyEFHXe0yyin4amOJx5Bhtk+qG5eubJNH8UcOeE/2MPmyznO3BEM3g+oR8HOzJre+l0BZAkEArEK57rJVrbCtagkskYx0Z7LCah4W16VgL2z52JfKYSzb/il+i7Ean3TLVZO4QS39vdvl1CfyRZVx6TR6pwTQuA==";

	/** 支付宝公钥 */
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9uu+tXc0vvI1f30621PUUEjxw/tYq9QEQw38QYZdp+kt+CzYhmYu7/ZZgt6d5oDbufCFOHQ5U7LLk8tlswaoQgkdNwS0ab9fxQDdtt1KFLA0/FI99JhnjUHm6yEnNh9B3Q2kZfpCVOyUnXaLimTbO406qjlNQdp1ESwgJjmnA4QIDAQAB";

	public static final String COUPON_VALID_STATUS = "1";// 有效优惠券

	public static final String YUGUO_SPLIT_CHAR = "yg";// 分隔符
	public static final int YUGUO_ACCESS_TIME = 5;// 分隔符

	public static final String REPEAT_ORDER = "REPEAT";// 分隔符

	public static final String UNCHECK_URL = "/downLoad/query/apkDownload.do";// 无须接口授权

	public static final String CONFIRM_ORDER = "确认收货奖励";// 确认收货积分

	// 退款状态 0申请退款 1审核中 2商家收货 3退款中 4完成
	public static final String refund_O = "REPEAT";// 申请退款
	public static final String refund_1 = "REPEAT";// 审核中
	public static final String refund_2 = "REPEAT";// 商家收货
	public static final String refund_3 = "REPEAT";// 退款中
	public static final String refund_4 = "REPEAT";// 完成

	public static final String REMIND_SIGN = "1";// 打卡提醒

	// SAS角色权限名
	public static final String ROLE_NORMAL = "mct_normal"; // 普通商户，拥有SAS所有模块权限
	public static final String ROLE_SPEA = "mct_spea"; // 休闲等类型的商户所拥有的权限
	public static final String ROLE_SPEB = "mct_speb"; // 停车等类型的商户所拥有的权限
	public static final String ROLE_SPEC = "mct_spec"; // 酒店、周边游、KTV等类型的商户所拥有的权限

	public static final String MESSAGE_STROE = "【与果网-商家入驻】恭喜您，注册成功！商家：%s 用户名：%s 密码：%s";
	public static final String MESSAGE_SHOPPING = "【与果网】订单号：%s 付款成功，购买(%s)，(%s)请及时处理。";
	public static final String MESSAGE_GROUP = "【与果网】\"%s\"购买成功，请前往至商家[%s]凭校验码%s进行消费。";
	public static final String MESSAGE_HUNDUN = "【与果网】您已成功预订:%s,订单号:%s,房型:%s,数量:%s间,入住时间:%s,离店时间:%s,请准时入住!";

	public static final int RESPONSE_SUCCESS_STATUS = 0; // 响应成功

	public static final String UPLOAD_WAIT = "0"; // 未上传
	public static final String UPLOAD_SUCCESS = "1"; // 已上传
	public static final String UPLOAD_FAIL = "2"; // 上传失败

	public static final String MERCHANTTYPE_SELF = "SELF_SUPPORT"; // 商户类型- 自营
	public static final String MERCHANTTYPE_THIRD = "THIRD_PARTY"; // 商户类型- 第三方
	public static final String MERCHANTTYPE_ORTHER = "ORTHER"; // 商户类型- ORTHER
	public static final String MERCHANT_AUTHORITY = "1160303175218973000102"; // 商户默认权限

	// 商家推荐类型
	public static final String RECOMMEND_HOME_HOT = "1"; // 首页热门推荐

	public static final String EMAIL_TYPE = "YG";

	public static final String SENSITIVE_PATH1 = "com/yuguo/net/utils/eroticism.txt";// 热敏词汇地址
	public static final String SENSITIVE_PATH2 = "com/yuguo/net/utils/heresy.txt";// 热敏词汇地址
	public static final String SENSITIVE_PATH3 = "com/yuguo/net/utils/political.txt";// 热敏词汇地址
	public static final String SENSITIVE_PATH4 = "com/yuguo/net/utils/politician.txt";// 热敏词汇地址

	// 全名K歌报名提示信息
	public static final String SINGER_SING_SUCCESS = "恭喜您，报名成功！";
	public static final String SINGER_UPDATE_SUCCESS = "修改信息成功！";
	public static final String SINGER_SING_FAIL = "报名失败了，请稍后再试！";
	public static final String SINGER_SING_WARNING = "您提交的信息中含有违规信息，请重新填写！";

	// OSS账号karaoke的ID
	public static final String SELLER_KARAOKE = "1160303175218975000349";

	// 成功预约KTV发送的短信内容
	public static final String KARAOKE_MESSAGE = "【全民K歌】%s您好，您已成功预约%s%s月%s日14:00-18:00下午免费练歌，请于当日下午凭验证码：%s排队领号练歌，逾期无效。（此验证仅限本人有效）咨询热线：028－87751138";

	public static final String KARAOKE_INFO_SUCCESS = "预约成功！";
	public static final String KARAOKE_INFO_FAIL = "预约失败，请稍后再试！";
	public static final String KARAOKE_INFO_ENOUGH = "抱歉，该KTV预约人数已满！";
	public static final String KARAOKE_INFO_UNABLE = "您已经预约过了！";
	public static final String KARAOKE_INFO_MSG = "请于%s间，持收到的验证短信前往“%s”练歌";
	public static final String KARAOKE_INFO_SING = "您还没有报名，赶紧点击“报名参赛”进行报名吧！";

	/**
	 * 酒店对接,混沌数据接口测试地址
	 */
	public static final String HUNDUN_URL = "http://120.24.75.164:8999/OtaInterfaceConnections/OtaDataInterFace/getOtaMethod";
	/**
	 * 混沌数据接口正式地址 用于获取酒店 和 房态信息
	 */
	public static final String GET_HUNDUN_HOTELINFO = "http://120.25.128.119:8999/OtaInterfaceConnections/OtaDataInterFace/getOtaMethod";
	/**
	 * 混沌数据接口正式地址 用于可定检查,下单,取消,pushOrder
	 */
	public static final String OPERATE_HUNDUN_HOTEL = "http://120.24.166.230:8999/OtaInterfaceConnections/OtaDataInterFace/getOtaMethod";

	// 新注册用户领券活动时间 券ID
	public static final String SING_COUPON_BEGEN = "2016-12-12 00:00:00";
	public static final String SING_COUPON_END = "2016-12-31 23:59:59";
	public static final String SING_COUPON_ID1 = "zc00120501";
	public static final String SING_COUPON_ID2 = "100110502";
	public static final String SING_COUPON_ID3 = "100111001";
	public static final String SING_COUPON_ID4 = "100111002";
	public static final String SING_COUPON_ID5 = "100112001";

	public static final String REMARK1 = "1"; // 人人夺宝状态
	public static final String PHONE_REGEX = "/^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$/";

	// 天天打卡查询商品标号 RecommendType
	public static final String EVERY_TIME_TYPE = "314";
	// 默认头像地址
	public static final String HEAD_ICO = "images/karaoke/noJb.png";

	// 一元购首页请求地址
	public static final String INDIANA_INDEX = "/everyoneIndiana/appIndex.do?requestType=H5&userId=&page=1";
	// ANDROID和IOS客户端下载地址
	public static final String APPS_DOWNLOAD_URL = "/downLoad/query/toApkDownload.do?requestType=WEB";

}
