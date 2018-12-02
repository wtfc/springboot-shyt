package com.jc.oa.archive.service;

import java.util.List;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.archive.domain.SubPermission;

/**
 * @title  GOA2.0源代码
 * @description  业务接口类
 * @author 闻瑜
 * @version  2014-06-19
 */

public interface ISubPermissionService extends IBaseService<SubPermission>{
	/**
	 * 物理删除权限
	 * @param Permission permission 实体类
	 * @author weny
	 * @version  2014-06-19
	 */
	public Integer deleteSubPermission(SubPermission subpermission);
	
	/**
	 * 修改部门人员权限
	 * @param Permission permission 实体类
	 * @author weny
	 * @version  2014-06-23
	 */
	public List<SubPermission> updatePermission(SubPermission subpermission);
	
	/**
	 * 修改部门人员权限
	 * @param Permission permission 实体类
	 * @author weny
	 * @version  2014-06-23
	 */
	public Integer updateBatchPermission(SubPermission subpermission);
}