package com.jc.system.log.creator.factory;

import com.jc.system.log.creator.LogCreator;

/**
 * @description: 日志生产者工具类
 */
public interface ILogCreatorFactory {
	public LogCreator createLogCreator();
}
