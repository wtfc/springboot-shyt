package com.jc.oa.archive.dao;

import java.util.List;

import com.jc.oa.archive.ArchiveException;
import com.jc.oa.archive.domain.Permission;
import com.jc.foundation.dao.IBaseDao;


/**
 * @title  GOA2.0源代码
 * @description  dao接口类
 * @author 闻瑜
 * @version  2014-06-19
 */
 
public interface IPermissionDao extends IBaseDao<Permission>{

	/**
	 * 物理删除权限
	 * @param Permission permission 实体类
	 * @author weny
	 * @version  2014-06-19
	 */
	public Integer deletePermission(Permission permission);
	
	/**
	 * 权限查询
	 * @param Permission permission 实体类
	 * @author weny
	 * @version  2014-06-23
	 */
	public List<Permission> queryPermission(Permission permission);
	
	public Long getFolderPermissionCount(Permission permission)  throws ArchiveException;
}
