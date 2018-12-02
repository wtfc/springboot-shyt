package com.jc.system.security.util;

import com.jc.system.CustomException;
import com.jc.system.common.util.CacheUtils;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.security.domain.Department;
import com.jc.system.security.service.IDepartmentService;
import com.jc.system.security.service.ISystemService;
import com.jc.system.security.service.impl.DepartmentServiceImpl;
import com.jc.system.security.service.impl.SystemServiceImpl;

/**
 * 缓存中查询组织工具类
 * @title GOA V2.0
 * @description 
 * @version  2014年10月21日
 */
public class DeptCacheUtil {

	private static final String CACHE_DEPT_INFO = "dept_info_";
	private static IDepartmentService departmentService = SpringContextHolder.getBean(DepartmentServiceImpl.class);
	private static ISystemService systemService = SpringContextHolder.getBean(SystemServiceImpl.class);
	
	/**
	 * 缓存查询单个组织
	 * @param deptId
	 * @return
	 * @throws CustomException
	 * @version 1.0 2014年10月21日 上午8:49:06
	 */
	public static Department getDeptById(Long deptId) throws CustomException {
		Department d = (Department) CacheUtils.get(CACHE_DEPT_INFO + deptId);
		if(d == null){
			Department dept = new Department();
			dept.setId(deptId);
			dept.setDeleteFlag(0);
			d = departmentService.get(dept);
			if(d != null){
				Department department = new Department();
				department.setId(d.getId());
				department = systemService.queryOrgIdAndName(department);
				d.setOrgId(department.getOrgId());
				d.setOrgName(department.getOrgName());
				CacheUtils.put(CACHE_DEPT_INFO + d.getId(), d);
			}
		}
		return d;
	}
}
