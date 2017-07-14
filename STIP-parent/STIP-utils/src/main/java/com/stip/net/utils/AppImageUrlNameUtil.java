package com.stip.net.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class AppImageUrlNameUtil {

	public static String getImageUrl(String img, String status) throws Exception {
		String fileName = "";
		String picPath = "";
		String suffix = ".png";
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		fileName = sf.format(new Date());
		// 生成3位随机数拼接到日期后面,防止文件名重复
		Random ran = new Random();
		for (int j = 0; j < 3; j++) {
			fileName += ran.nextInt(9);
		}

		if ("1".equals(status)) {
			picPath = "upload/headIco/" + fileName + suffix;
		} else if ("2".equals(status)) {
			picPath = "upload/refund/" + fileName + suffix;
		} else if ("3".equals(status)) {
			picPath = "upload/comment/" + fileName + suffix;
		} else {
			picPath = "upload/error/" + fileName + suffix;
		}

		return picPath;
	}
}
