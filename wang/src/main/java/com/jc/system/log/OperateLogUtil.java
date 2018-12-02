package com.jc.system.log;

import com.jc.system.log.domain.LogBean;
import com.jc.system.log.executor.impl.CommonLocalLogExecutor;
import com.jc.system.log.impl.OperateLogImpl;

/**
 * @description:日志操作类
 * @created: 2013-10-25 上午11:15:32
 *            Rights Reserved.
 */
public class OperateLogUtil {
	/**
	 * @description 插入日志
	 * @param user
	 *            操作人员
	 */
	public static void log(LogBean bean) {
		IOperateLog operateLog = new OperateLogImpl();
		operateLog.log(bean);
	}

	/**
	 * @description 初始化函数
	 * @version 1.0 上午9:53:46
	 */
	public static void init() {

		// 初始化日志任务
		CommonLocalLogExecutor executors = new CommonLocalLogExecutor();
		executors.run();

	}
}
