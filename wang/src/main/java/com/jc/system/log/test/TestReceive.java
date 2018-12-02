package com.jc.system.log.test;

import com.jc.system.log.executor.impl.ManagerLogExecutor;

public class TestReceive {
	public static void main(String[] args) {
		ManagerLogExecutor executor = new ManagerLogExecutor();
		executor.run();
	}
}
