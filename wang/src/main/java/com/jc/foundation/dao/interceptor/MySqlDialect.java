package com.jc.foundation.dao.interceptor;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
public class MySqlDialect extends Dialect {

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		// TODO Auto-generated method stub
		sql = sql.trim();
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		sb.append(" limit " + offset);
		sb.append(", " + limit);
		return sb.toString();
	}

}
