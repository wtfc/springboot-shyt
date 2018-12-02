package com.jc.system.security.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.security.dao.IUserIpDao;
import com.jc.system.security.domain.UserIp;

/**
 * @title GOA2.0
 * @description  dao实现类
 * @author 
 * @version  2014-04-30
 */
@Repository
public class UserIpDaoImpl extends BaseDaoImpl<UserIp> implements IUserIpDao{

	public UserIpDaoImpl(){}

}