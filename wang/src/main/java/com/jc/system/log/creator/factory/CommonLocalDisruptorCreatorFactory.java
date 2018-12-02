package com.jc.system.log.creator.factory;

import com.jc.system.log.creator.LogCreator;
import com.jc.system.log.creator.impl.CommonLocalDisruptorLogCreator;

/**
 * @description: 本地普通(disruptor)日志生产者
 */
public class CommonLocalDisruptorCreatorFactory implements ILogCreatorFactory {

	@Override
	public LogCreator createLogCreator() {
		return new CommonLocalDisruptorLogCreator();
	}

}
