package com.stip.net.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ContextBeanUtils implements ApplicationContextAware {
	private static ApplicationContext appCtx;

	/**
	 * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。
	 * 
	 * @param applicationContext
	 *            ApplicationContext 对象.
	 * @throws BeansException
	 * @author c
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appCtx = applicationContext;
	}

	/**
	 * 获取ApplicationContext
	 * 
	 * @return
	 * @author c
	 */
	public static ApplicationContext getApplicationContext() {
		return appCtx;
	}

	/**
	 * 这是一个便利的方法，帮助我们快速得到一个BEAN
	 * 
	 * @param beanName
	 *            bean的名字
	 * @return 返回一个bean对象
	 * @author c
	 */
	public static Object getBean(String beanName) {
		return appCtx.getBean(beanName);
	}
	
	/**     
	 * @description 获取HTTP请求    
	 * @created 2017年7月4日 下午5:18:08     
	 * @return     
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}

}
