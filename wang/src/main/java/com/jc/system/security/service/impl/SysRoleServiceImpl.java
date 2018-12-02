package com.jc.system.security.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;
import com.jc.system.security.domain.SysRole;
import com.jc.system.security.dao.ISysRoleDao;
import com.jc.system.security.dao.ISysUserDao;
import com.jc.system.security.service.ISysRoleService;

/**
 * @title GOA2.0
 * @description 角色信息基本表 业务实现类
 * @version  2014-04-15
 *
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements ISysRoleService{

	private ISysRoleDao sysRoleDao;
	
	@Autowired
	public SysRoleServiceImpl(ISysRoleDao dao) {
		super(dao);
		this.sysRoleDao = dao;
	}
	public SysRoleServiceImpl(){
		
	}

	/**
	* @description 分页查询方法
	* @param SysRole sysRole 实体类
	* @param PageManager page 分页实体类
	* @return PagingBean 查询结果
	* @author
	* @version  2014-04-15 
	*/
	public PageManager query(SysRole sysRole,PageManager page) {
		return sysRoleDao.queryByPage(sysRole, page);
	}
	
	public List<SysRole> queryAll(SysRole sysRole) {
		return sysRoleDao.queryAll(sysRole);
	}

	/**
	* @description 根据主键删除多条记录方法
	* @param SysRole sysRole 实体类
	* @return Integer 处理结果
	* @author
	* @version  2014-04-15 
	*/
	@Transactional(rollbackFor=Exception.class)
	public Integer deleteByIds(SysRole sysRole) throws CustomException{
		Integer result = -1;
		try{
			result = sysRoleDao.delete(sysRole, false);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(sysRole);
			throw ce;
		}
		return result;
	}

	/**
	* @description 保存方法
	* @param SysRole sysRole 实体类
	* @return Integer 增加的记录数
	* @author
	* @version  2014-04-15 
	*/
	@Transactional(rollbackFor=Exception.class)
	public Integer save(SysRole sysRole) throws CustomException{
		Integer result = -1;
		try{
			result = sysRoleDao.save(sysRole);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(sysRole);
			throw ce;
		}
		return result;
	}

	/**
	* @description 修改方法
	* @param SysRole sysRole 实体类
	* @return Integer 修改的记录数量
	* @author
	* @version  2014-04-15 
	*/
	@Transactional(rollbackFor=Exception.class)
	public Integer update(SysRole sysRole) throws CustomException{
		Integer result = -1;
		try{
			result = sysRoleDao.update(sysRole);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(sysRole);
			throw ce;
		}
		return result;
	}

	/**
	* @description 查询单条记录方法
	* @param SysRole sysRole 实体类
	* @return Integer 查询结果
	* @throws Exception
	* @author
	* @version  2014-04-15 
	*/
	public SysRole get(SysRole sysRole) throws CustomException{
		return sysRoleDao.get(sysRole);
	}

}