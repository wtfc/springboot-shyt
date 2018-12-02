package com.jc.oa.archive.dao;

import java.util.List;

import com.jc.oa.archive.domain.SubPermission;
import com.jc.foundation.dao.IBaseDao;


/**
 * @title  GOA2.0源代码
 * @description  dao接口类
 * @author 闻瑜
 * @version  2014-06-19
 */
 
public interface ISubPermissionDao extends IBaseDao<SubPermission>{
	/**
	 * 物理删除权限
	 * @param Permission permission 实体类
	 * @author weny
	 * @version  2014-06-19
	 */
	public Integer deleteSubPermission(SubPermission subPermission);
	
	/**
	 * 修改权限
	 * @param SubPermission subPermission 实体类
	 * @author weny
	 * @version  2014-06-19
	 */
	public List<SubPermission> updateSubPermission(SubPermission subPermission);
	
}
