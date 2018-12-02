package com.jc.system.log.creator.factory;

import com.jc.system.log.creator.LogCreator;
import com.jc.system.log.creator.impl.CommonLogCreator;

/**
 * @description: 普通日志生产者
 * @created: 2013-10-25 下午2:54:48
 */
public class CommonCreatorFactory implements ILogCreatorFactory {

	@Override
	public LogCreator createLogCreator() {
		return new CommonLogCreator();
	}

}
