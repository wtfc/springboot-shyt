package com.jc.system.log;

import com.jc.system.log.domain.LogBean;

/**
 * @description: 操作日志接口
 * @created: 2013-10-25 上午11:06:15
 */
public interface IOperateLog {
	/**
	 * @description 记录日志
	 */
	public void log(LogBean bean);

	/**
	 * @description 返回日志查询
	 */
	public ILogQuery createLogQuery();
}
