package com.jc.system.log.creator.factory;

import com.jc.system.log.creator.LogCreator;
import com.jc.system.log.creator.impl.ManagerLocalLogCreator;

/**
 * @description: 本地管理员日志生产者
 */
public class ManagerLocalCreatorFactory implements ILogCreatorFactory {

	@Override
	public LogCreator createLogCreator() {
		return new ManagerLocalLogCreator();
	}

}
