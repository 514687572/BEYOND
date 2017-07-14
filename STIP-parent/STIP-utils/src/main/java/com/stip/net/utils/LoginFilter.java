package com.stip.net.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 * 是否需要验证
 * 
 * @author AIDAN 2015-0730
 */
public class LoginFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 获得在下面代码中要用的request,response,session对象
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session = servletRequest.getSession();
		String ua = ((HttpServletRequest) request).getHeader("User-Agent");
		boolean flag = false, isH5 = true;
		String path = servletRequest.getRequestURI();// 获得用户请求的URI
		String userId = (String) session.getAttribute("userId");
		String requestType = request.getParameter("requestType");
		String[] noFilterH5 = ConstantUtils.NOFILTER_H5.split(";");
		String[] noFilter = ConstantUtils.FILTRATION_PAGES.split(";");

		for (int i = 0; i < noFilterH5.length; i++) {
			if (path.indexOf(noFilterH5[i]) > -1) {
				isH5 = false;
				break;
			}
		}

		if (isH5) {
			if (StringUtils.isNotBlank(ua)) {
				if (ua.contains("Android") || ua.contains("iPhone") || ua.contains("iPod") || ua.contains("iPad") || ua.contains("Windows Phone") || ua.contains("MQQBrowser")) {
					if (ConstantUtils.APP_REQUEST != requestType && ConstantUtils.IOS_REQUEST != requestType) {
						servletResponse.sendRedirect(servletRequest.getContextPath() + ConstantUtils.INDIANA_INDEX);
						return;
					} else {
						servletResponse.sendRedirect(servletRequest.getContextPath() + ConstantUtils.APPS_DOWNLOAD_URL);
						return;
					}
				}
			}
		}

		// 无需过滤的请求
		for (int i = 0; i < noFilter.length; i++) {
			if (path.indexOf(noFilter[i]) > -1) {
				flag = true;
				break;
			}
		}

		// 登陆页面无需过滤
		if (path.equals("/yuguo/")) {
			servletResponse.sendRedirect(servletRequest.getContextPath() + "/index.do");
			return;
		} else if (path.equals("/")) {// yg669.com
			servletResponse.sendRedirect(servletRequest.getContextPath() + "/index.do");
			return;
		} else if (path.equals("/index.jsp")) {
			servletResponse.sendRedirect(servletRequest.getContextPath() + "/index.do");
			return;
		} else if (path.equals("/yuguo/index.jsp")) {
			servletResponse.sendRedirect(servletRequest.getContextPath() + "/index.do");
			return;
		}

		if (!flag) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}

		// 判断
		if (userId == null || "".equals(userId)) {
			// 跳转到登陆页面
			if (path.contains("member.jsp")) {
				String toPage = request.getParameter("toPage");
				servletResponse.sendRedirect(servletRequest.getContextPath() + "/login.jsp?requestModel=VIPCenter&toPage=" + toPage);
			} else {
				servletResponse.sendRedirect(servletRequest.getContextPath() + "/login.jsp?requestModel=Index");
			}

		} else {
			// 已经登陆,继续此次请求
			chain.doFilter(request, response);
		}

	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

}
