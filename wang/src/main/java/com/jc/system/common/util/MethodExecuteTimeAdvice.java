/**
 * 
 */
package com.jc.system.common.util;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @title GOA V2.0
 * @version 2014年5月26日上午8:50:28
 */
public class MethodExecuteTimeAdvice implements MethodInterceptor {
	Logger logger = Logger.getLogger(MethodExecuteTimeAdvice.class);

	/**
	 * 
	 */
	public MethodExecuteTimeAdvice() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
	 * .MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		// TODO Auto-generated method stub
		StopWatch clock = new StopWatch();
		clock.start(); // 计时开始
		Object result = mi.proceed();
		clock.stop(); // 计时结束

		// 方法参数类型，转换成简单类型
		Method method = mi.getMethod();
		if(method.isAnnotationPresent(RequestMapping.class)){
			RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
			String uri = Arrays.toString(requestMapping.value());
			Class[] params = method.getParameterTypes();
			String[] simpleParams = new String[params.length];
			for (int i = 0; i < params.length; i++) {
				simpleParams[i] = params[i].getSimpleName();
			}
			Class<?> controllerClass = mi.getThis().getClass();
			if(controllerClass.isAnnotationPresent(RequestMapping.class)){
				String prefix = Arrays.toString(controllerClass.getAnnotation(RequestMapping.class).value());
				uri =  prefix.substring(0,prefix.length()-1)+"/"+uri.substring(1);
			}
			logger.info( uri+"请求执行耗费:" + clock.getTime() + " ms ["
					+ controllerClass.getName() + "."
					+ method.getName() + "("
					+ StringUtils.join(simpleParams, ",") + ")] ");
		}
		
		return result;

	}

}
