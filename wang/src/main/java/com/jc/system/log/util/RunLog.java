package com.jc.system.log.util;

import com.jc.system.log.executor.impl.CommonLogExecutor;
import com.jc.system.log.executor.impl.ManagerLogExecutor;

/**
 * @description: 系统入口
 * @created: 2013-10-28 下午2:40:15
 */
public class RunLog {
	public static void main(String[] args) {
		CommonLogExecutor executor = new CommonLogExecutor();
		executor.run();
		ManagerLogExecutor managerExecutor = new ManagerLogExecutor();
		managerExecutor.run();
	}
}
