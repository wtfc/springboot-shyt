package com.jc.system.common.util;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
public class Identities {

	private static SecureRandom random = new SecureRandom();

	/**
	 * @description 封装JDK自带的UUID, 通过Random数字生成
	 * @return 生成的UUID,中间有-分割
	 * @author
	 * @version 2014-03-24
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * @description 封装JDK自带的UUID, 通过Random数字生成
	 * @return 生成的UUID,中间无-分割
	 * @author
	 * @version 2014-03-24
	 */
	public static String uuid2() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * @description 使用SecureRandom随机生成Long.
	 * @return 生成的随机长整形
	 * @author
	 * @version 2014-03-24
	 */
	public static long randomLong() {
		long value = random.nextLong();
		if(value ==Long.MIN_VALUE){
			return randomLong();
		}
		else{
			return Math.abs(value);
		}
	}
}
