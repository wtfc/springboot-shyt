package com.jc.system.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.system.security.dao.IAdminSideDao;
import com.jc.system.security.domain.AdminSide;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.security.service.IAdminSideService;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-04-16
 */
@Service
public class AdminSideServiceImpl extends BaseServiceImpl<AdminSide> implements IAdminSideService{

	private IAdminSideDao adminSideDao;
	
	public AdminSideServiceImpl(){
		
	}
	
	@Autowired
	public AdminSideServiceImpl(IAdminSideDao adminSideDao){
		super(adminSideDao);
		this.adminSideDao = adminSideDao;
	}
	
	/**
	 * 删除方法
	 * @param AdminSide
	 * @return Integer 删除的记录数
	 * @author
	 * @version 2014-03-18
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer deleteAdminSide(AdminSide adminSide) {
		return adminSideDao.deleteAdminSide(adminSide);
	}

	/**
	 * 查询管理员机构树
	 * @param AdminSide
	 * @return List<AdminSide> 查询结果
	 * @author
	 * @version 2014-03-18
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<AdminSide> queryManagerDeptRree(AdminSide adminSide) {
		return adminSideDao.queryManagerDeptRree(adminSide);
	}

	/**
	 * 根据部门ID查询管理员机构
	 * @param AdminSide
	 * @return List<AdminSide> 查询结果
	 * @author
	 * @version 2014-03-18
	 */
	@Override
	public List<AdminSide> queryAdminSideIdByDeptId(AdminSide adminSide) {
		return adminSideDao.queryAdminSideIdByDeptId(adminSide);
	}
	
	/**
	  *  根据部门ID查出AdminSide关联IDS
	  * @param adminSide
	  * @return
	  * @author 张继伟
	  * @version 1.0 2014年4月22日 上午10:11:28
	*/
	public boolean queryAdminSideIdByDeptIds(String deptIds[]){
		for(String deptId : deptIds){
			AdminSide adminSide = new AdminSide();
			adminSide.setDeptId(Long.parseLong(deptId));
			adminSide = adminSideDao.get(adminSide);
			if(adminSide != null){
				return true;
			}
		}
		return false;
	}

}