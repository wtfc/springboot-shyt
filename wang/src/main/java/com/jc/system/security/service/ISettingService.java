package com.jc.system.security.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.security.domain.Setting;

/**
 * @title GOA2.0
 * @description  业务接口类
 * @author 
 * @version  2014-04-28
 */

public interface ISettingService extends IBaseService<Setting>{
	
	public Setting getOne(Setting setting);
}