package com.jc.foundation.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.dao.IBaseDao;
import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.foundation.service.IBeanPropertyService;
import com.jc.system.CustomException;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.RoleBlocks;
import com.jc.system.security.domain.SysUserRole;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IRoleService;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.service.impl.RoleServiceImpl;
import com.jc.system.security.service.impl.UserServiceImpl;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
public class BaseServiceImpl<T extends BaseBean> implements IBaseService<T> {

	protected IBaseDao<T> dao;
    @Autowired
	protected IBeanPropertyService propertyService;
    
    protected IRoleService roleService;
	
    protected IUserService userService;
    
	public BaseServiceImpl() {

	}

	public BaseServiceImpl(IBaseDao<T> dao) {
		this.dao = dao;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public Integer save(T t) throws CustomException {
		Integer result = -1;
		try {
			propertyService.fillProperties(t,false);
			result = dao.save(t);
		} catch (Exception e) {
			CustomException ce = new CustomException(e);
			ce.setBean(t);
			throw ce;
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer saveList(List<T> list) throws CustomException {
		Integer result = -1;
		try {
			List<T> clone =propertyService.fillProperties(list,false);
			result = dao.save(clone);
		} catch (Exception e) {
			CustomException ce = new CustomException(e);
			throw ce;
		}
		return result;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public Integer update(T t) throws CustomException {
		Integer result = -1;
		try {
			propertyService.fillProperties(t,true);
			result = dao.update(t);
		} catch (Exception e) {
			CustomException ce = new CustomException(e);
			ce.setBean(t);
			throw ce;
		}
		return result;
	}

	@Override
	public T get(T t) throws CustomException {
		return (T) dao.get(t);
	}

	@Override
	public PageManager query(T t, PageManager page) {
		return dao.queryByPage(t, page);
	}
	
	@Override
	public PageManager queryForPermission(T t, PageManager page) throws CustomException {
		User user = SystemSecurityUtils.getUser();
		if(user.getIsSystemAdmin()){//超级管理员不需要过滤
			return dao.queryForPermissionByPage(t, page);
		}
		List<SysUserRole> sysUserRoleList = user.getSysUserRole();
		Long roleId [] = new Long[sysUserRoleList.size()];
		for(int i=0	;	i<	sysUserRoleList.size();i++){
			SysUserRole userRole = sysUserRoleList.get(i);
			roleId[i] = userRole.getRoleId();
		}
		List<RoleBlocks> orgList = this.getRoleService().getAllDeptWithRolesPermission(roleId);
		String dataAccessDynamicSQL = "";
		for(RoleBlocks rBlock : orgList){
			if(rBlock.getFlag()){
				if(dataAccessDynamicSQL.length()>0){
					dataAccessDynamicSQL += " or t.`create_user_dept` = "+rBlock.getDeptId()+" or t.`create_user_org` = "+rBlock.getDeptId()+" ";
				}else{
					dataAccessDynamicSQL += " t.`create_user_dept` = "+rBlock.getDeptId()+" or t.`create_user_org` = "+rBlock.getDeptId()+" ";
				}
			}else{
				if(dataAccessDynamicSQL.length()>0){
					dataAccessDynamicSQL += " or t.`create_user` = "+rBlock.getDeptId()+" ";
				}else{
					dataAccessDynamicSQL += " t.`create_user` = "+rBlock.getDeptId()+" ";
				}
			}
		}
		dataAccessDynamicSQL = dataAccessDynamicSQL.length() > 0 ? "and ("+dataAccessDynamicSQL+")":dataAccessDynamicSQL;
		t.setDataAccessDynamicSQL(dataAccessDynamicSQL);
		return dao.queryForPermissionByPage(t, page);
	}
	
	@Override
	public PageManager queryForPermission4M(T t, String userId,PageManager page) throws CustomException {
		userService = getUserService();
		User tempUser = new User();
		tempUser.setId(Long.valueOf(userId));
		User user = userService.getUserById(tempUser);
		if(user.getIsSystemAdmin()){//超级管理员不需要过滤
			return dao.queryForPermissionByPage(t, page);
		}
		List<SysUserRole> sysUserRoleList = user.getSysUserRole();
		Long roleId [] = new Long[sysUserRoleList.size()];
		for(int i=0	;	i<	sysUserRoleList.size();i++){
			SysUserRole userRole = sysUserRoleList.get(i);
			roleId[i] = userRole.getRoleId();
		}
		List<RoleBlocks> orgList = this.getRoleService().getAllDeptWithRolesPermission(roleId);
		String dataAccessDynamicSQL = "";
		for(RoleBlocks rBlock : orgList){
			if(rBlock.getFlag()){
				if(dataAccessDynamicSQL.length()>0){
					dataAccessDynamicSQL += " or t.`create_user_dept` = "+rBlock.getDeptId()+" or t.`create_user_org` = "+rBlock.getDeptId()+" ";
				}else{
					dataAccessDynamicSQL += " t.`create_user_dept` = "+rBlock.getDeptId()+" or t.`create_user_org` = "+rBlock.getDeptId()+" ";
				}
			}else{
				if(dataAccessDynamicSQL.length()>0){
					dataAccessDynamicSQL += " or t.`create_user` = "+rBlock.getDeptId()+" ";
				}else{
					dataAccessDynamicSQL += " t.`create_user` = "+rBlock.getDeptId()+" ";
				}
			}
		}
		dataAccessDynamicSQL = dataAccessDynamicSQL.length() > 0 ? "and ("+dataAccessDynamicSQL+")":dataAccessDynamicSQL;
		t.setDataAccessDynamicSQL(dataAccessDynamicSQL);
		return dao.queryForPermissionByPage(t, page);
	}

	@Override
	public PageManager queryForPermission(T t, PageManager page, String countSQL, String querySQL)  {
		try{
			User user = SystemSecurityUtils.getUser();
			if(user.getIsSystemAdmin()){//超级管理员不需要过滤
				return dao.queryForPermissionByPage(t, page, countSQL, querySQL);
			}
			List<SysUserRole> sysUserRoleList = user.getSysUserRole();
			Long roleId [] = new Long[sysUserRoleList.size()];
			for(int i=0	;	i<	sysUserRoleList.size();i++){
				SysUserRole userRole = sysUserRoleList.get(i);
				roleId[i] = userRole.getRoleId();
			}
			List<RoleBlocks> orgList = this.getRoleService().getAllDeptWithRolesPermission(roleId);
			String dataAccessDynamicSQL = "";
			for(RoleBlocks rBlock : orgList){
				if(rBlock.getFlag()){
					if(dataAccessDynamicSQL.length()>0){
						dataAccessDynamicSQL += " or t.`create_user_dept` = "+rBlock.getDeptId()+" or t.`create_user_org` = "+rBlock.getDeptId()+" ";
					}else{
						dataAccessDynamicSQL += " t.`create_user_dept` = "+rBlock.getDeptId()+" or t.`create_user_org` = "+rBlock.getDeptId()+" ";
					}
				}else{
					if(dataAccessDynamicSQL.length()>0){
						dataAccessDynamicSQL += " or t.`create_user` = "+rBlock.getDeptId()+" ";
					}else{
						dataAccessDynamicSQL += " t.`create_user` = "+rBlock.getDeptId()+" ";
					}
				}
			}
			dataAccessDynamicSQL = dataAccessDynamicSQL.length() > 0 ? "and ("+dataAccessDynamicSQL+")":dataAccessDynamicSQL;
			t.setDataAccessDynamicSQL(dataAccessDynamicSQL);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dao.queryForPermissionByPage(t, page, countSQL, querySQL);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer delete(T t) throws CustomException {
		return delete(t,dao.isLogicDelete());
	}

	@Override
	public List<T> queryAll(T t) throws CustomException {
		return dao.queryAll(t);
	}

	protected transient final Logger log = Logger.getLogger(this.getClass());

	@Override
	public Integer delete(T t, Boolean logicDelete) throws CustomException {
		Integer result = -1;
		try {
			if(logicDelete){
				propertyService.fillProperties(t,true);
			}
			result = dao.delete(t,logicDelete);
		} catch (Exception e) {
			CustomException ce = new CustomException(e);
			ce.setBean(t);
			throw ce;
		}
		return result;
	}

	private IRoleService getRoleService() {
		if(roleService == null){
			roleService = SpringContextHolder.getBean(RoleServiceImpl.class);
		}
		return roleService;
	}
	
	private IUserService getUserService() {
		if(userService == null){
			userService = SpringContextHolder.getBean(UserServiceImpl.class);
		}
		return userService;
	}

}