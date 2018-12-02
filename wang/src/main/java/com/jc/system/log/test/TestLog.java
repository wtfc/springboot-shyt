package com.jc.system.log.test;

import com.jc.system.log.IOperateLog;
import com.jc.system.log.domain.LogBean;
import com.jc.system.log.impl.OperateLogImpl;

public class TestLog {
	public static void main(String[] args) {
		IOperateLog log = new OperateLogImpl();
		for (int i = 0; i < 40; i++) {
			LogBean bean = new LogBean();
			bean.setUserId("userId");
			bean.setCompany("company");
			bean.setContent("content");
			bean.setDept("dept");
			bean.setIp("IP");
			bean.setManagerFlag(LogBean.MANAGE_FLAG_TRUE);
			bean.setMenuName("menuName");
			bean.setOperType("operType");
			bean.setUserName("username");
			log.log(bean);
		}
	}
}
