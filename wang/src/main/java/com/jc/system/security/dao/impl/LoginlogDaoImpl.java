package com.jc.system.security.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.security.dao.ILoginlogDao;
import com.jc.system.security.domain.Loginlog;

/**
 * @title GOA2.0
 * @description  dao实现类
 * @author 
 * @version  2014-05-04
 */
@Repository
public class LoginlogDaoImpl extends BaseDaoImpl<Loginlog> implements ILoginlogDao{

	public LoginlogDaoImpl(){}

}