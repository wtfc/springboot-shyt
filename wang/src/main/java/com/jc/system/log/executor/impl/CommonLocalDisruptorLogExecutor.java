package com.jc.system.log.executor.impl;

import com.jc.system.log.disruptor.DisruptorContext;
import com.jc.system.log.executor.LogExecutor;

/**
 * @description: 普通日志线程执行类（用disruptor）
 * @author：孙圣然
 * @created: 2013-10-25 下午4:06:54
 * @version：$Id: CommonLocalDisruptorLogExecutor.java 40770 2014-04-03 13:38:29Z
 *               sunjf $
 */
public class CommonLocalDisruptorLogExecutor extends LogExecutor {

	public void run() {
		log.info("开始disruptor监听器");
		DisruptorContext.startLogExecutor();
	}

}
