package com.jc.system.log.test;

import java.util.Date;
import java.util.Map;

import com.jc.foundation.domain.IPageManager;
import com.jc.foundation.domain.PageManager;
import com.jc.system.log.IOperateLog;
import com.jc.system.log.impl.OperateLogImpl;

public class TestLogQuery {
	public static void main(String[] args) {
		IOperateLog log = new OperateLogImpl();
		log.createLogQuery().isManager(false).setStartDate(new Date()).list();

		IPageManager pageManager = (IPageManager) new PageManager();
		Map<String, Object> map = log.createLogQuery().setEndDate(new Date())
				.listByPage(pageManager);
		map.get("list");
		map.get("page");
	}
}
