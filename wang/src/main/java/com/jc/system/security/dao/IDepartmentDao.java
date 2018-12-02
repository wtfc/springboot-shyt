package com.jc.system.security.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.foundation.domain.PageManager;
import com.jc.system.security.domain.Department;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-18
 * 
 */
public interface IDepartmentDao extends IBaseDao<Department> {

	public PageManager searchQuery(Department department, PageManager page);
	public List<Department> queryTree(Department department);
	public Department queryOne(Department department);
	public List<Department> queryAllByDeptId(Department department);
	public Integer updateByDeptIds(Department department);
	public List<Department> getDeptAndUserByDeptId(Long id);
	public List<Department> getDeptByDeptId(Long id);
	public List<Department> getDeptByOrgId(Long id);
	public List<Department> queryManagerDeptTree(Long userId);
	public List<Department> getQueryDeptPidByOrgId(Long id) ;
	public List<Department> getAllOrgNoDeptTree();
	
}
