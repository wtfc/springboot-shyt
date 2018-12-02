package com.jc.system.security.service;

import java.util.List;

import com.jc.foundation.domain.PageManager;
import com.jc.system.CustomException;
import com.jc.system.security.domain.SysRole;


/**
 * @title GOA2.0
 * @description 角色信息基本表 业务接口类
 * @author 
 * @version  2014-04-15
 *
 */
public interface ISysRoleService{
	/**
	* @description 分页查询方法
	* @param SysRole sysRole 实体类
	* @param PageManager page 分页实体类
	* @return PagingBean 查询结果
	* @author
	* @version  2014-04-15 
	*/
	public PageManager query(SysRole sysRole,PageManager page) ;
	
	public List<SysRole> queryAll(SysRole sysRole) ;

	/**
	* @description 根据主键删除多条记录方法
	* @param SysRole sysRole 实体类
	* @return Integer 处理结果
	* @author
	* @version  2014-04-15 
	*/
	public Integer deleteByIds(SysRole sysRole) throws CustomException;

	/**
	* @description 保存方法
	* @param SysRole sysRole 实体类
	* @return Integer 增加的记录数
	* @author
	* @version  2014-04-15 
	*/
	public Integer save(SysRole sysRole) throws CustomException;

	/**
	* @description 修改方法
	* @param SysRole sysRole 实体类
	* @return Integer 修改的记录数量
	* @author
	* @version  2014-04-15 
	*/
	public Integer update(SysRole sysRole) throws CustomException;

	/**
	* @description 查询单条记录方法
	* @param SysRole sysRole 实体类
	* @return Integer 查询结果
	* @throws Exception
	* @author
	* @version  2014-04-15 
	*/
	public SysRole get(SysRole sysRole) throws Exception;

	
}