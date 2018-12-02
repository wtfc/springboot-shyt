package com.jc.system.log;

/**
 * @description: 日志用户类，用于实现
 * @created: 2013-10-25 上午11:09:08
 */
public interface ILogUser {
	public String getUserId();

	public String getDept();

	public String getCompany();

	public String getUsername();

	public Boolean isManager();
}
