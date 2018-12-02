package com.jc.foundation.dao.interceptor;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
public abstract class Dialect {
	
	/**
	 * 
	 * @title GOA V2.0
	 * @description 目前系统支持的数据库类型
	 * @version  2014年5月23日下午2:02:41
	 */
	public static enum Type {
		MYSQL, ORACLE
	}

	/**
	  * 获取分页语句
	  * @param sql  原始sql语句
	  * @param offset 起始偏移量  
	  * @param limit  限制行数
	  * @return 返回分页语句
	  * @version 1.0 2014年5月23日 下午1:59:32
	 */
	public abstract String getLimitString(String sql, int offset, int limit);
}