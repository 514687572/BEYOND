package com.stip.net.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 * 
 * @author AIDAN
 * 
 */
public class CookieUtils {
	/* 保存用户名密码到cookie */
	public static void saveCookie(String userName, String pwd, String userId, HttpServletRequest request, HttpServletResponse response) {
		// long validTime = System.currentTimeMillis() + (60 * 60 * 24 * 7 *
		// 5000);
		// String cookieValue = userName + ":" + pwd + ":" + userId;
		Cookie nameCookie = new Cookie("userName", userName);
		Cookie pwdCookie = new Cookie("pwd", pwd);
		Cookie idCookie = new Cookie("userId", userId);
		nameCookie.setMaxAge(60 * 60 * 24 * 7);
		pwdCookie.setMaxAge(60 * 60 * 24 * 7);
		idCookie.setMaxAge(60 * 60 * 24 * 7);

		// 设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
		nameCookie.setPath("/");
		pwdCookie.setPath("/");
		idCookie.setPath("/");
		response.addCookie(nameCookie);
		response.addCookie(pwdCookie);
		response.addCookie(idCookie);
	}

	/**
	 * 判断cookie是否存在
	 * 
	 * @param userId
	 * @param request
	 * @param response
	 * @return 存在返回false 不存在返回true
	 * @throws Exception
	 */
	public static Boolean checkCookie(String userId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean flag = false;
		// 根据cookieName取cookieValue
		Cookie cookies[] = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if ("userId".equals(c.getName())) {
					if (userId.equals(c.getValue())) {// 判断userId是否存在
						flag = false;
					} else {
						flag = true;
					}
				} else {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 删除cookie
	 * 
	 * @param request
	 * @param response
	 * @param name
	 */
	public static void delCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (!"JSESSIONID".equals(cookie.getName())) {
					cookie.setValue(null);
					cookie.setMaxAge(-1);// 立即销毁cookie
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}
	}
}
