package com.stip.net.utils;

/**
 * 常量 公共参数
 * 
 * @author chenjunan
 * 
 */
public abstract class RechargeUtils {
	/**
	 * 构造函数
	 */
	private RechargeUtils() {
	}

	// 手机充值页面
	public static final String YGLIFESERVICESMOBILPAGE_URL = "https://www.yg669.com/yg/lifeServices/toLifeServicePage.do?toPage=mobilePhone";

	// 固话充值
	public static final String YGFIXEDLINE_URL = "https://www.yg669.com/yg/lifeServices/toLifeServicePage.do?toPage=fixdLine";

	// 宽带充值
	public static final String YGBROADBAND_URL = "https://www.yg669.com/yg/lifeServices/toLifeServicePage.do?toPage=broadBand";

	// 宽带充值
	public static final String YGQB_URL = "https://www.yg669.com/yg/lifeServices/toLifeServicePage.do?toPage=qb";

	// 宽带充值
	public static final String YGOILCARD_URL = "https://www.yg669.com/yg/lifeServices/toLifeServicePage.do?toPage=oilCard";

	// 欧飞账号
	public static final String USERID = "A1174213";

	// 密码
	public static final String USERPWS = ReChangeMD5Util.digest("yg669yg669.", "GBK").toLowerCase();

	// 版本号固定值
	public static final String VERSION = "6.0";

	// 默认的秘钥是OFCARD，可联系商务修改，若已经修改过的，请使用修改过的。
	public static final String KEYSTR = "OFCARD";

	/*
	 * 手机充值欧飞接口地址 userid SP编码如（A00001），可由SP自动注册生成,在登陆系统时得到以A开头的编号 是 userpws
	 * SP接入密码(为账户密码的MD5值，如登陆密码为111111,此时这个值为md5(“111111”) (32位小写) 是 cardid
	 * 所需提货商品的编码(快充：140101，慢充：170101 )这里商品编码是模糊匹配只匹配前两位 是 cardnum
	 * 快充可选面值（1、2、5、10、20、30、50、100、200、300、500）慢充可选面值（30、50、100） 是 mctype
	 * 慢充类型：0.5
	 * （半小时到账）、4（4小时到账）、12（12小时到账）、24（24小时到账）、48（48小时到账）、72（72小时到账）（不传默认为24
	 * ，不参与MD5验证）否 sporder_id Sp商家的订单号(商户传给欧飞的唯一编号) 是 sporder_time 订单时间
	 * （yyyyMMddHHmmss 如：20070323140214） 是 game_userid 手机号 是 md5_str MD5后字符串 是
	 * ret_url 订单充值成功后返回的URL地址，可为空（不参与MD5验算） 否 version 固定值为：6.0 (不参与MD5验证) 是
	 * md5_str检验码的计算方法:包体=userid+userpws+cardid+cardnum+sporder_id+sporder_time+
	 * game_userid 1: 对: “包体+KeyStr” 这个串进行md5 的32位值. 结果大写 2: KeyStr 默认为 OFCARD,
	 * 实际上线时可以修改。 3: KeyStr 不在接口间进行传送。 调用的链接如下：
	 * http://AXXXX.api2.ofpay.com/onlineorder
	 * .do?userid=Axxxxx&userpws=xxxxxxx&cardid
	 * =140101&cardnum=1&sporder_id=xxxxxxxxx
	 * &sporder_time=xxxxxxxx&game_userid=xxxxx
	 * &md5_str=xxxxxxxxxxxxx&ret_url=xxxxx&version=6.0
	 */
	public static final String OFMOBILERCHARGE_URL = "http://api2.ofpay.com/onlineorder.do";

	/*
	 * 手机号是否能充值查询接口地址
	 * http://AXXXX.api2.ofpay.com/telcheck.do?phoneno=13813856456
	 * &price=50&userid=Axxxxx price为充值面值、userid为会员编号
	 */
	public static final String OFTELCHECK_URL = "http://AXXXX.api2.ofpay.com/telcheck.do";

	/*
	 * 手机号归属地查询接口 mobilenum 手机号码前七位 是
	 * 例：http://AXXXX.api2.ofpay.com/mobinfo.do?mobilenum=1381383
	 */
	public static final String OFMOBILEPHONEINFO_URL = "http://AXXXX.api2.ofpay.com/mobinfo.do";

	/*
	 * 手机号+面额 是否能充值接口地址
	 * http://AXXXX.api2.ofpay.com/telquery.do?userid=Axxxxx&userpws
	 * =xxxxx&phoneno=xxxxx&pervalue=xx&version=6.0 userid
	 * SP编码如（A00001），可由SP自动注册生成,在登陆系统时得到以A开头的编号 是 userpws
	 * SP接入密码(为账户密码的MD5值，如登陆密码为111111,此时这个值为md5(“111111”) (32位小写) 是 phoneno 手机号码
	 * 是 pervalue 快充可选面值（1、2、5、10、20、30、50、100、300、500）慢充可选面值（30、50、100） 是
	 * mctype
	 * （慢充才传）慢充类型：0.5（半小时到账）、4（4小时到账）、12（12小时到账）、24（24小时到账）、48（48小时到账）、72（
	 * 72小时到账） 否 version 固定值为：6.0 是
	 */
	public static final String OFTELQUERY_URL = "http://AXXXX.api2.ofpay.com/telquery.do";

	// 固定/宽带+面额 是否能充值接口地址
	public static final String OFFIXEDLINECHECK_URL = "http://AXXXX.api2.ofpay.com/fixtelquery.do";

	// 固定/宽带充值提交接口地址
	public static final String OFFIXEDLINEBROADBAND_URL = "http://AXXXX.api2.ofpay.com/fixtelorder.do";

	// 游戏QB充值接口地址
	public static final String OFGAMEANDQBRECHARGE_URL = "http://AXXXX.api2.ofpay.com/onlineorder.do";

	// 订单查询接口地址
	public static final String OFORDERSTATASQUERY_URL = "http://AXXXX.api2.ofpay.com/api/query.do";

	// 手机充值结果回调地址
	public static final String YGRCHARGERESULT_URL = "https://www.yg669.com/yg/recharge/rechargeResult.do";

	/*
	 * 自动对账接口地址 userid SP编码如（A00001），可由SP自动注册生成,在登陆系统时得到以A开头的编号 是 userpws
	 * SP接入密码(为账户密码的MD5值，如登陆密码为111111,此时这个值为md5(“111111”) (32位小写) 是 cardid
	 * 所需提货商品的编码（支持2位，4位和6位），不填为全部 是 starttime 开始时间 （yyyyMMdd 如：20070323） 是
	 * endtime 结束时间 （yyyyMMdd 如：20070323） 是 md5_str 验证字符串 (32位大写) 是 version
	 * 固定值为：6.0 (不参与MD5验证) 是
	 * Md5_str=md5(userid+userpws+cardid+starttime+endtime+key)
	 * 调用的链接如下：http://AXXXX
	 * .api2.ofpay.com/querybill.do?userid=Axxxx&userpws=xxxxxxx&cardid=360101
	 * &starttime =xxxxxxxxx&endtime=xxxxxxxx&md5_str=xxxxxxxxxxxxx&version=6.0
	 */
	public static final String OFQUERYBILL_URL = "http://AXXXX.api2.ofpay.com/querybill.do";

	/*
	 * userid SP编码如（A00001），可由SP自动注册生成,在登陆系统时得到以A开头的编号 是 spbillid
	 * Sp000001,商户系统订单号 是 订单号查询充值状态 api/query.do
	 * http://AXXXX.api2.ofpay.com/api/query.do?userid=xxxxx&spbillid=spxxxxxx
	 */
	public static final String OFAPIQUERY_URL = "http://AXXXX.api2.ofpay.com/api/query.do";

	/*
	 * 
	 * 查询订单详情接口(queryOrderInfo.do) userid
	 * SP编码如（A00001），可由SP自动注册生成,在登陆系统时得到以A开头的编号 是 userpws
	 * SP接入密码(为账户密码的MD5值，如登陆密码为111111,此时这个值为md5(“111111”) (32位小写) 是 sporder_id
	 * Sp商家的订单号 是 md5_str MD5后字符串 是 version 固定值为：6.0 是 md5_str检验码的计算方法:
	 * 包体=userid+userpws+sporder_id 1: 对: “包体+KeyStr” 这个串进行md5 的32位值. 结果大写 2:
	 * KeyStr 在测试阶段先选择为 OFCARD, 实际上线时再修改。 3: KeyStr 不在接口间进行传送。
	 */
	public static final String OFQUERYORDERINFO_URL = "http://AXXX.api2.ofpay.com/queryOrderInfo.do";

	/*
	 * userid SP编码如（A00001），可由SP自动注册生成,在登陆系统时得到以A开头的编号. 是 userpws
	 * SP接入密码(为账户密码的MD5值，如登陆密码为111111,此时这个值为md5(“111111”) (32位小写) 是 version
	 * 固定值为：6.0 是
	 * http://AXXXX.api2.ofpay.com/queryuserinfo.do?userid=Axxxxx&userpws
	 * =xxxxxxx&version=6.0
	 */
	public static final String OFQUERYUSERINFO_URL = "http://AXXXX.api2.ofpay.com/queryuserinfo.do";

	/*
	 * 加油卡卡号信息查询 （sinopec/queryCardInfo.do）
	 * 注:同一个卡号查询必须隔30秒请求一次,避开每天晚上官方维护的时间进行查询操作 userpws
	 * SP接入密码(为账户密码的MD5值，如登陆密码为111111,此时这个值为md5(“111111”) (32位小写) 是 game_userid
	 * 加油卡号（充值账号）中石化：以100011开头共19位、中石油：以9开头共16位 是 chargeType 加油卡类型
	 * （1:中石化、2:中石油；默认为1，不参与MD5校验） 否 md5_str MD5后字符串 是 version 固定值为：6.0 是
	 * md5_str检验码的计算方法: 包体=userid+userpws+game_userid 1: 对: “包体+KeyStr” 这个串进行md5
	 * 的32位值. 结果大写 2: KeyStr 默认为 OFCARD, 实际上线时可以修改。 3: KeyStr 不在接口间进行传送。
	 * queryCardInfo.do
	 */
	public static final String OFQueryCardInfo = "http://AXXXX.api2.ofpay.com/sinopec/queryCardInfo.do";

	/*
	 * 
	 * 加油卡充值接口 （sinopec/onlineorder.do） userpws
	 * SP接入密码(为账户密码的MD5值，如登陆密码为111111,此时这个值为md5(“111111”) (32位小写) 是 cardid
	 * 64127500（全国中石化加油卡任意充） 是 64349102(（中石油任意充） 是 cardnum 充值数量 任意充
	 * （整数（元）），其余面值固定值为1 是 sporder_id Sp商家的订单号(商户传给欧飞的唯一订单号) 是 sporder_time 订单时间
	 * （yyyyMMddHHmmss 如：20070323140214） 是 game_userid
	 * 加油卡号（充值账号）中石化：以100011开头共19位、中石油：以9开头共16位 是 chargeType 加油卡类型
	 * （1:中石化、2:中石油；默认为1） 否 gasCardTel 持卡人手机号码 否 gasCardName 持卡人姓名 否 invoiceFlag
	 * 是否需要发票
	 * (!!注意:此参数只有cardid为64127500有控制作用，cardid为64157002、64157001、64157004、64157005
	 * 、64357107、64357108、64357109没有控制作用) 1:是 、0:否(默认为0) 否 md5_str MD5后字符串 是
	 * ret_url 订单充值成功后返回的URL地址，可为空请使用80端口 否 version 固定值为：6.0 是 md5_str检验码的计算方法:
	 * 包体=userid+userpws+cardid+cardnum+sporder_id+sporder_time+ game_userid 1:
	 * 对: “包体+KeyStr” 这个串进行md5 的32位值. 结果大写 2: KeyStr 默认为 OFCARD, 实际上线时可以修改。
	 * http:
	 * //AXXXX.api2.ofpay.com/sinopec/onlineorder.do?userid=A0****&userpws=4
	 * c625b7861a*******9c2fd3c4a
	 * &cardid=6******&cardnum=2&sporder_id=sinopecorder
	 * &sporder_time=20070323140214&game_userid=123456
	 * &ret_url=http://www.baidu.
	 * com&version=6.0&md5_str=89D4AB082C*****2471E188713D
	 * CBF67&gasCardTel=159****6144&gasCardName=test
	 */
	public static final String OFSINOPECONLINEORDER_URL = "http://AXXXX.api2.ofpay.com/sinopec/queryCardInfo.do";

}
