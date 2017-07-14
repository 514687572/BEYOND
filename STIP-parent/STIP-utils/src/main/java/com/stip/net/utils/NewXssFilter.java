package com.stip.net.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

/**
 * 防止xss攻击
 * 
 * @author cja
 * 
 */
public class NewXssFilter extends UrlRewriteFilter implements Filter {

	FilterConfig filterConfig = null;

	public void destroy() {
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest) request).getRequestURI();

		boolean flag = false;
		String[] noFilter = ConstantUtils.NOFILTER_INTERFACE.split(";");
		for (int i = 0; i < noFilter.length; i++) {
			if (path.indexOf(noFilter[i]) > -1) {
				flag = true;
				break;
			}
		}

		if (flag) {
			chain.doFilter(request, response);
		} else {
			NewXssHttpServletRequestWrapper xssRequest = new NewXssHttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(xssRequest, response);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

}
