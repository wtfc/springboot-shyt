package com.jc.system.portal.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.portal.domain.Portlet;


/**
 * @title GOA2.0
 * @description  dao接口类
 * @author 
 * @version  2014-06-16
 */
 
public interface IPortletDao extends IBaseDao<Portlet>{

	/**
	  * @description  根据门户id数组串获取业务模块
	  * @param portlet
	  * @return
	  * @author chz
	  * @version 1.0 2014年9月4日 
	*/
	public List<Portlet> queryAllForPortalIds(Portlet portlet);
	
	/**
	  * @description  获取所有业务组件中使用的功能组件id
	  * @return portlet
	  * @author chz
	  * @version 1.0 2014年9月24日 
	*/
	public String queryByuseFuncionids(Portlet portlet);
	
	/**
	  * @description  根据门户id删除所有关联业务组件
	  * @return portlet
	  * @author chz
	  * @version 1.0 2014年9月25日 
	*/
	public Integer deleteByPortalId(Portlet portlet);
	
	/**
	 * 根据功能组件名称验证重复
	 * @param String funName
	 * @return Map
	 */
	public Integer valNameEcho(Portlet portlet);
}
