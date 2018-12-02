package com.jc.system.security.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.dao.ISystemDao;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.ISystemService;
import com.jc.system.security.service.ILoginlogService;
import com.jc.system.common.util.Utils;

@Service
public class SystemServiceImpl extends BaseServiceImpl<User> implements ISystemService {
	
	@Autowired
	private ILoginlogService loginlogService;
	
	@Autowired
	public SystemServiceImpl(ISystemDao systemDao) {
		super(systemDao);
		 this.systemDao= systemDao;
	}
	public SystemServiceImpl(){
		
	}
	private ISystemDao systemDao;

	public User get(String loginName) {
		return systemDao.get(loginName);
	}
	
	@Override
	@Cacheable(value="departmentCache",key="#department.id")
	public Department queryOrgIdAndName(Department department) {
		return systemDao.queryOrgIdAndName(department);
	}
	
	@Override
	public void loginCallBack(HttpServletRequest request) throws CustomException{
		User user = SystemSecurityUtils.getUser();
		//登录日志
		loginlogService.setLoginUserInfo(user, Utils.getIpAddr(request), 1, 1);
		//用户登录信息
		SystemSecurityUtils.logInMes();
	}
	
	@Override
	public void logoutCallBack(HttpServletRequest request) throws CustomException {
		User user = SystemSecurityUtils.getUser();
		loginlogService.setLoginUserInfo(user, Utils.getIpAddr(request), 2, 1);
	}
	
	@Override
	public void loginCallBack4M(User user, HttpServletRequest request)
			throws CustomException {
		loginlogService.setLoginUserInfo(user, Utils.getIpAddr(request), 1, 2);
		
	}
	
	@Override
	public void logoutCallBack4M(HttpServletRequest request)
			throws CustomException {
		User user = SystemSecurityUtils.getUser();
		loginlogService.setLoginUserInfo(user, Utils.getIpAddr(request), 2, 2);
	}

}