package com.jc.system.security.service;

import javax.servlet.http.HttpServletRequest;

import com.jc.system.CustomException;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.User;

public interface ISystemService {

	public User get(String loginName);
	
	public Department queryOrgIdAndName(Department department);
	
	public void loginCallBack(HttpServletRequest request) throws CustomException;
	
	public void logoutCallBack(HttpServletRequest request) throws CustomException;
	
	public void loginCallBack4M(User user, HttpServletRequest request) throws CustomException;
	
	public void logoutCallBack4M(HttpServletRequest request) throws CustomException;
	
}