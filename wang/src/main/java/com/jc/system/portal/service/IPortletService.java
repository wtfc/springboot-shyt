package com.jc.system.portal.service;

import java.util.Map;

import com.jc.foundation.service.IBaseService;
import com.jc.system.portal.domain.Portlet;

/**
 * @title GOA2.0
 * @description  业务接口类
 * @author 
 * @version  2014-06-16
 */

public interface IPortletService extends IBaseService<Portlet>{
	
	public Map<String,Object> getPortalAndFouction();
	
	public Map<String,Object> getPortletForPortalId(Long portalId,String portalType);
	
	/**
	  * @description  获取所有业务组件中使用的功能组件id
	  * @return Map<String,Object>
	  * @author chz
	  * @version 1.0 2014年9月24日 
	*/
	public Map<String,Object> queryByuseFuncionids(String funtionids);
	
	/**
	  * @description  根据门户id删除所有关联业务组件
	  * @return portlet
	  * @author chz
	  * @version 1.0 2014年9月25日 
	*/
	public Integer deleteByPortalId(Portlet portlet);
	
	/**
	 * 根据功能组件名称验证重复
	 * @param PortalFunction portalFunction
	 * @return Map
	 */
	public Map<String, Object> valNameEcho(Portlet portlet);
	
	/**
	  * @description  根据门户业务组件状态修改重置布局数据（处理普通组件转换快捷方式）
	  * @return Integer
	  * @author chz
	  * @version 1.0 2014年11月7日 
	*/
	public Integer reLayout(Portlet portlet);
	
	/**
	  * @description  根据门户业务组件删除重置布局数据（处理普通组件与快捷方式）
	  * @return Integer
	  * @author chz
	  * @version 1.0 2014年11月12日 
	*/
	public Integer reLayoutFordel(Portlet portlet,String ids);
	
	/**
	  * @description  更新业务组件中文本域组件 图片组件关系
	  * @return Integer
	  * @author chz
	  * @version 1.0 2015年1月12日 
	*/
	public Integer updateForRelation(Long portletId,String url);
	
	/**
	  * @description  根据业务组件id查询门户类型
	  * @return String
	  * @author chz
	  * @version 1.0 2015年1月12日 
	*/
	public String queryForPortalType(Long portletId);
}