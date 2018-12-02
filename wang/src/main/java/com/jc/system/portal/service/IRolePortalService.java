package com.jc.system.portal.service;

import java.util.List;
import java.util.Map;

import com.jc.foundation.service.IBaseService;
import com.jc.system.portal.domain.RolePortal;
import com.jc.system.security.domain.LeftRight;

/**
 * @title GOA2.0
 * @description  业务接口类
 * @author 
 * @version  2014-06-16
 */

public interface IRolePortalService extends IBaseService<RolePortal>{
	public List<LeftRight> getData(String type) throws Exception;
	public List<RolePortal> parseToList(RolePortal rolePortal,String userids,String roleids,String deptids,String organids) throws Exception;
	public Map<String, String> getIds(RolePortal rolePortal) throws Exception;
	public Long getCount(RolePortal rolePortal);
	public Integer deleteRolePortalItem(RolePortal rolePortal) throws Exception;
	public Integer deleteForPortalsOrPortalets(RolePortal rolePortal) throws Exception;
	/**
	 * 根据登录用户信息返回访问门户组件权限
	 * @param rolePortal
	 * @return List<RolePortal>
	 * 2014.7.3 chz
	 */
	public List<RolePortal> queryPortaletPower(RolePortal rolePortal) throws Exception;
}