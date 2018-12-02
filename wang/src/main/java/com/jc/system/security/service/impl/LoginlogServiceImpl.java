package com.jc.system.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;
import com.jc.system.common.util.DateUtils;
import com.jc.system.security.dao.ILoginlogDao;
import com.jc.system.security.domain.Loginlog;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.ILoginlogService;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-05-04
 */
@Service
public class LoginlogServiceImpl extends BaseServiceImpl<Loginlog> implements ILoginlogService{

	private ILoginlogDao loginlogDao;
	
	public LoginlogServiceImpl(){}
	
	@Autowired
	public LoginlogServiceImpl(ILoginlogDao loginlogDao){
		super(loginlogDao);
		this.loginlogDao = loginlogDao;
	}

	/**
	 * @description 登录用户信息注入
	 * @param User userVo 实体类 ip 用户操作机Ip地址 inOrout 用户正在进行的是登入还是登出操作 1登入 2登出
	 * @return 无
	 * @author chz
	 * @version 2014-05-04
	 * @throws CustomException 
	 */
	public void setLoginUserInfo(User user,String ip,int inOrout,int loginDevice) throws CustomException {
		// TODO 自动生成的方法存根
		Loginlog loginlogVo = new Loginlog();
		loginlogVo.setUserId(user.getId());
		loginlogVo.setLoginName(user.getLoginName());
		loginlogVo.setDisplayName(user.getDisplayName());
		loginlogVo.setIp(ip);
		loginlogVo.setLoginDevice(loginDevice);
		if(inOrout == 1){
			loginlogVo.setLoginTime(DateUtils.getSysDate());
			loginlogDao.save(loginlogVo);
		}else if(inOrout == 2){
			loginlogVo.setLogoutTime(DateUtils.getSysDate());
			loginlogDao.save(loginlogVo);
		}
		
		
	}

}