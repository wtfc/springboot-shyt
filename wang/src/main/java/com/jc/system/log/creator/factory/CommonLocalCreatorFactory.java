package com.jc.system.log.creator.factory;

import com.jc.system.log.creator.LogCreator;
import com.jc.system.log.creator.impl.CommonLocalLogCreator;

/**
 * @description: 本地普通日志生产者
 */
public class CommonLocalCreatorFactory implements ILogCreatorFactory {

	@Override
	public LogCreator createLogCreator() {
		return new CommonLocalLogCreator();
	}

}
