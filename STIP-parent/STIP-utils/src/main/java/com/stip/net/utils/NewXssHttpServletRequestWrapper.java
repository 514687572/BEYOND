package com.stip.net.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;

import com.blogspot.radialmind.html.HTMLParser;
import com.blogspot.radialmind.xss.XSSFilter;

/**
 * 转义非法字符
 * 
 * @author chen
 * 
 */
public class NewXssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	HttpServletRequest orgRequest = null;

	public NewXssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		orgRequest = request;
	}

	/**
	 * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
	 * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(xssEncode(name));
		if (value != null && ConstantUtils.NOFILTER_INTERFACE_PAR.indexOf(name) < 0) {
			value = xssEncode(value);
			value = StringEscapeUtils.escapeSql(value);
			value = StringEscapeUtils.escapeHtml(value);
			value = StringEscapeUtils.escapeJavaScript(value);
			value = StringEscapeUtils.unescapeXml(value);
		}

		return value;
	}

	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		// 遍历每一个参数，检查是否含有
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = xssEncode(values[i]);
		}
		return encodedValues;
	}

	/**
	 * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
	 * getHeaderNames 也可能需要覆盖
	 */
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(xssEncode(name));
		if (value != null) {
			value = xssEncode(value);
		}
		return value;
	}

	/**
	 * 将容易引起xss漏洞的半角字符直接替换成全角字符
	 * 
	 * @param s
	 * @return
	 */
	private static String xssEncode(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		}

		StringReader reader = new StringReader(s);
		StringWriter writer = new StringWriter();
		try {
			HTMLParser.process(reader, writer, new XSSFilter(), true);

			return writer.toString();
		} catch (NullPointerException e) {
			return s;
		} catch (Exception ex) {

		}

		return null;
	}

	/**
	 * 获取最原始的request
	 * 
	 * @return
	 */
	public HttpServletRequest getOrgRequest() {
		return orgRequest;
	}

	/**
	 * 获取最原始的request的静态方法
	 * 
	 * @return
	 */
	public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
		if (req instanceof NewXssHttpServletRequestWrapper) {
			return ((NewXssHttpServletRequestWrapper) req).getOrgRequest();
		}

		return req;
	}

}
