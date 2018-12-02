package com.jc.oa.archive.service;

import java.util.List;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.oa.archive.ArchiveException;
import com.jc.oa.archive.domain.Permission;
import com.jc.oa.archive.domain.SubPermission;

/**
 * @title  GOA2.0源代码
 * @description  业务接口类
 * @author 闻瑜
 * @version  2014-06-19
 */

public interface IPermissionService extends IBaseService<Permission>{
	
	/**
	 * 查询授权权限初始化
	 * @param Permission permission 实体类
	 * @param PageManager page 分页实体类
	 * @return PageManager 查询结果
	 * @throws ArchiveException
	 * @author
	 * @version  2014-06-19
	 */
	public PageManager manageListPermission(Permission permission,PageManager page) throws ArchiveException;
	
	/**
	 * 物理删除权限
	 * @param Permission permission 实体类
	 * @author weny
	 * @version  2014-06-19
	 */
	public Integer delectPermission(Permission permission);
	
	/**
	 * 添加组织人员权限方法
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 闻瑜
	 * @version  2014-06-20
	 */
	public Integer batchSave(Long folderId, String id, String text, String type) throws ArchiveException;
	
	
	/**
	 * 修改组织人员权限方法
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 闻瑜
	 * @version  2014-06-20
	 * @throws ArchiveException 
	 */
	public List<SubPermission> updatePermission(Long permissionId) throws ArchiveException;
	
	/**
	 * 修改组织人员权限方法
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 闻瑜
	 * @version  2014-06-20
	 * @throws ArchiveException 
	 */
	public Integer updateBatchPermission(Long permissionId, String id, String text, String type) throws ArchiveException;
	
	/**
	 * 权限查询
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 闻瑜
	 * @version  2014-06-23
	 * @throws ArchiveException 
	 */
	public List<Permission> queryPermission(Permission permission) throws ArchiveException;
	
	/**
	 * 复制权限给下级文件夹
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 闻瑜
	 * @version  2014-07-03
	 * @throws ArchiveException 
	 */
	public void copyPermission(Long id, Long folderId) throws ArchiveException;
	
	public Long getFolderPermissionCount(Permission permission)  throws ArchiveException;
}