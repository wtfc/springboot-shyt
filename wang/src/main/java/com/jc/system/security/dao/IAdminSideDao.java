package com.jc.system.security.dao;

import java.util.List;

import com.jc.system.security.domain.AdminSide;
import com.jc.foundation.dao.IBaseDao;


/**
 * @title GOA2.0
 * @description  dao接口类
 * @author 
 * @version  2014-04-16
 */
 
public interface IAdminSideDao extends IBaseDao<AdminSide>{

	/**
	  * 删除方法
	  * @param adminSide
	  * @return
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	public Integer deleteAdminSide(AdminSide adminSide);
	/**
	  * 查询管理员机构树
	  * @param adminSide
	  * @return
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	public List<AdminSide> queryManagerDeptRree(AdminSide adminSide);
	/**
	  *  根据部门ID查出AdminSide关联ID
	  * @param adminSide
	  * @return
	  * @version 1.0 2014年4月22日 上午10:11:28
	*/
	public List<AdminSide> queryAdminSideIdByDeptId(AdminSide adminSide);
}
