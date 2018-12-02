package com.jc.system.security.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.system.security.domain.Loginlog;
import com.jc.system.security.domain.User;

/**
 * @title GOA2.0
 * @description  业务接口类
 * @author 
 * @version  2014-05-04
 */

public interface ILoginlogService extends IBaseService<Loginlog>{
	
	public void setLoginUserInfo(User userVo,String ip,int inOrout,int loginDevice) throws CustomException;
	
}