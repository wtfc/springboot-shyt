package com.jc.system.portal.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.portal.domain.PortalFunction;


/**
 * @title GOA2.0
 * @description  dao接口类
 * @author 
 * @version  2014-06-11
 */
 
public interface IPortalFunctionDao extends IBaseDao<PortalFunction>{

	/**
	 * 根据功能组件名称验证重复
	 * @param String funName
	 * @return Map
	 */
	public Integer valNameEcho(PortalFunction portalFunction);
}
