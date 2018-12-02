package com.jc.system.security.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.foundation.domain.PageManager;
import com.jc.system.security.dao.IDepartmentDao;
import com.jc.system.security.domain.Department;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-18
 * 
 */
@Repository
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements IDepartmentDao {

	public static final String SQL_SEARCH_QUERY_COUNT= "searchQueryCount";	//查询记录总数
	
	public static final String SQL_SEARCH_QUERY = "searchQuery";			//查询
	
	public static final String SQL_QUERY_TREE = "queryTree";				//查询部门树
	
	public static final String SQL_QUERY_GET = "queryOne";					//获取部门信息
	
	public static final String SQL_QUERY_ALL_BY_DEPTID = "queryAllByDeptId";//获取部门节点
	
	public static final String SQL_UPDATE_BY_DEPTIDS = "updateByDeptIds";	//根据部门Id集合删除
	
	public static final String SQL_QUERY_BY_DEPTID = "queryByDeptId";		//根据部门ID获取部门全部信息
	
	public static final String SQL_QUERY_BY_ID = "queryById";		//根据部门ID获取部门第一层的信息
	
	public static final String SQL_QUERY_BY_ORG_ID = "queryByOrgId";		//根据组织ID获取部门第一层的信息
	
	public static final String SQL_QUERY_DEPTPID_BY_ORG_ID = "queryDeptPidByOrgId";		//根据组织ID获取部门第一层的信息
	
	public static final String SQL_QUERY_MANAGER_DEPTTREE = "queryManagerDeptTree";//查询管理员机构树
	
	public static final String SQL_QUERY_ALL_ORG_TREE = "queryAllOrgTree";//查询管理员机构树
	
	
	
	public PageManager searchQuery(Department department, PageManager page){
		PageManager page_ = new PageManager();
		// 查询行数
		Integer rowsCount = template.selectOne(getNameSpace(department) + "."+SQL_SEARCH_QUERY_COUNT, department);
		page_.setTotalCount(rowsCount);

		// 计算页数 page.getRows()获得每页显示条数，系统中固定值
		Integer pageCount = rowsCount / page.getPageRows();
		if (rowsCount % page.getPageRows() > 0) {
			pageCount++;
		}
		// 如果传过来的当前页码大于总页码 则把当前页码设置为最大页码
		if (page.getPage() + 1 > pageCount && pageCount != 0) {
			page.setPage(pageCount);
		}

		// 将页面传过来的当前页传回到前台
		page_.setPage(page.getPage() + 1);

		List<Department> list = template.selectList(
			getNameSpace(department) + "."+SQL_SEARCH_QUERY, department,
			new RowBounds((page.getPage()) * page.getPageRows(), page .getPageRows()));

		page_.setTotalPages(pageCount);
		page_.setData(list);
		page_.setsEcho(page.getsEcho());
		return page_;
	}
	
	public List<Department> queryTree(Department department) {
		return template.selectList(getNameSpace(department) + "."+SQL_QUERY_TREE, department);
	}
	
	public Department queryOne(Department department){
		return template.selectOne(getNameSpace(department) + "."+SQL_QUERY_GET, department);
	}

	public List<Department> queryAllByDeptId(Department department) {
		return template.selectList(getNameSpace(department) + "." + SQL_QUERY_ALL_BY_DEPTID, department);
	}

	public Integer updateByDeptIds(Department department) {
		return template.update(getNameSpace(department) + "." + SQL_UPDATE_BY_DEPTIDS, department);
	}

	public List<Department> getDeptAndUserByDeptId(Long id) {
		return template.selectList(getNameSpace(new Department()) + "." + SQL_QUERY_BY_DEPTID, id);
	}
	
	public List<Department> getDeptByDeptId(Long id) {
		return template.selectList(getNameSpace(new Department()) + "." + SQL_QUERY_BY_ID, id);
	}
	
	public List<Department> getDeptByOrgId(Long id) {
		return template.selectList(getNameSpace(new Department()) + "." + SQL_QUERY_BY_ORG_ID, id);
	}
	
	public List<Department> getQueryDeptPidByOrgId(Long id) {
		return template.selectList(getNameSpace(new Department()) + "." + SQL_QUERY_DEPTPID_BY_ORG_ID, id);
	}
	
	
	public List<Department> queryManagerDeptTree(Long userId) {
		return template.selectList(getNameSpace(new Department()) + "." + SQL_QUERY_MANAGER_DEPTTREE, userId);
	}
	
	public List<Department> getAllOrgNoDeptTree() {
		return template.selectList(getNameSpace(new Department()) + "." + SQL_QUERY_ALL_ORG_TREE);
	}
	
}