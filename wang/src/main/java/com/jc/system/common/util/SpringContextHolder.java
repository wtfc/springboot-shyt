package com.jc.system.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @title GOA2.0
 * @description 以静态变量保存Spring ApplicationContext,
 *              可在任何代码任何地方任何时候取出ApplicaitonContext Copyright (c) 2014
 * @author
 * @version 2014-03-24
 * 
 */
@Component
public class SpringContextHolder implements ApplicationContextAware,
		DisposableBean {

	private static ApplicationContext applicationContext = null;

	private static Logger logger = LoggerFactory
			.getLogger(SpringContextHolder.class);

	/**
	 * @description 取得存储在静态变量中的ApplicationContext
	 * @return 返回ApplicationContext
	 * @author
	 * @version 2014-03-24
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @description 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型
	 * @param name
	 *            bean的名字
	 * @return 返回取得的bean
	 * @author
	 * @version 2014-03-24
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

	/**
	 * @description 静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型
	 * @param requiredType
	 *            bean的类型
	 * @return 返回取得的bean
	 * @author
	 * @version 2014-03-24
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	/**
	 * @description 清除SpringContextHolder中的ApplicationContext为Null
	 * @author
	 * @version 2014-03-24
	 */
	public static void clearHolder() {
		logger.debug("清除SpringContextHolder中的ApplicationContext:"
				+ applicationContext);
		applicationContext = null;
	}

	/**
	 * @description 实现ApplicationContextAware接口, 注入Context到静态变量中
	 * @param applicationContext
	 *            注入的Context
	 * @author
	 * @version 2014-03-24
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		if (SpringContextHolder.applicationContext != null) {
			logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
					+ SpringContextHolder.applicationContext);
		}
		SpringContextHolder.applicationContext = applicationContext; // NOSONAR
	}

	/**
	 * @description 实现DisposableBean接口, 在Context关闭时清理静态变量.
	 * @throws Exception
	 * @author
	 * @version 2014-03-24
	 */
	@Override
	public void destroy() throws Exception {
		SpringContextHolder.clearHolder();
	}
}