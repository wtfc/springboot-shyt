package com.jc.system.log.test;

import com.jc.system.log.IOperateLog;
import com.jc.system.log.domain.LogBean;
import com.jc.system.log.executor.impl.CommonLocalLogExecutor;
import com.jc.system.log.impl.OperateLogImpl;

public class TestLocalCommonReceive {
	public static void main(String[] args) {
		CommonLocalLogExecutor executors = new CommonLocalLogExecutor();
		executors.run();
		sendLog();
	}

	public static void sendLog() {
		IOperateLog log = new OperateLogImpl();
		for (int i = 0; i < 100; i++) {
			LogBean bean = new LogBean();
			bean.setUserId("userId");
			bean.setCompany("company");
			bean.setContent("content");
			bean.setDept("dept");
			bean.setIp("IP");
			bean.setManagerFlag(LogBean.MANAGE_FLAG_FALSE);
			bean.setMenuName("menuName");
			bean.setOperType("operType");
			bean.setUserName("username");
			log.log(bean);
		}
	}
}
