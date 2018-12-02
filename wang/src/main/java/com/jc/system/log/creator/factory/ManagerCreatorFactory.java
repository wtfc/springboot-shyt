package com.jc.system.log.creator.factory;

import com.jc.system.log.creator.LogCreator;
import com.jc.system.log.creator.impl.ManagerLogCreator;

/**
 * @description: 管理员日志生产者
 */
public class ManagerCreatorFactory implements ILogCreatorFactory {

	@Override
	public LogCreator createLogCreator() {
		return new ManagerLogCreator();
	}

}
