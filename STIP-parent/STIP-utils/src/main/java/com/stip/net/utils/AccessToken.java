package com.stip.net.utils;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 请求钥匙
 * 
 * @author chenjunan 20160531
 */
public class AccessToken {
	/**
	 * 生成请求KEY
	 * 
	 * @return
	 */
	public static String getToken(HttpServletRequest request) {
		String token = UUID.randomUUID().toString();
		request.getSession().setAttribute("token", token);
		return token;
	}

	/**
	 * 验证请求KEY
	 * 
	 * @param token
	 * @param request
	 * @return token一致返回TRUE否则返回FALSE， 无论何种状态都将重置token
	 */
	public static boolean verifyToken(String token, HttpServletRequest request) {
		boolean verifyResult = false;
		if (StringUtils.isBlank(token)) {
			verifyResult = false;
		} else {
			if (request.getSession().getAttribute("token") != null) {
				String tempToken = request.getSession().getAttribute("token").toString();
				if (StringUtils.isNotBlank(tempToken) && token.equals(tempToken)) {
					verifyResult = true;
				} else {
					verifyResult = false;
				}
			} else {
				verifyResult = false;
			}
		}

		return verifyResult;
	}

}
