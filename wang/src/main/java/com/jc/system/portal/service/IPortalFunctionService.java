package com.jc.system.portal.service;

import java.util.Map;

import com.jc.foundation.service.IBaseService;
import com.jc.system.portal.domain.PortalFunction;

/**
 * @title GOA2.0
 * @description  业务接口类
 * @author 
 * @version  2014-06-11
 */

public interface IPortalFunctionService extends IBaseService<PortalFunction>{
	/**
	 * 根据功能组件名称验证重复
	 * @param PortalFunction portalFunction
	 * @return Map
	 */
	public Map<String, Object> valNameEcho(PortalFunction portalFunction);
}