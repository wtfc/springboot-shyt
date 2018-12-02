package com.jc.system.userPhone.service;

import java.util.Map;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.system.security.domain.User;
import com.jc.system.userPhone.domain.UserPhone;

/**
 * @title GOA2.0使用的个人组别和公共组别应用
 * @description  业务接口类
 * @author 
 * @version  2014-11-21
 */

public interface IUserPhoneService extends IBaseService<UserPhone>{
	public Map<String, Object> valiAndResetUserPhone(UserPhone userPhone,Map<String, Object> resultMap,User user) throws CustomException;


	public void saveUserPhone(UserPhone userPhone) throws CustomException;
}