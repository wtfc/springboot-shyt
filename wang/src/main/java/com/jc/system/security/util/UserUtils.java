package com.jc.system.security.util;

import org.apache.log4j.Logger;

import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.security.domain.SysUser;
import com.jc.system.security.domain.User;
import com.jc.android.oa.system.domain.User4M;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.service.impl.UserServiceImpl;

/**
 * @author Administrator
 * 
 */
public class UserUtils {
	
	private transient static final Logger log = Logger.getLogger(UserUtils.class);
	
	private static IUserService userService = SpringContextHolder.getBean(UserServiceImpl.class);
	
	public static void user2SysUser(User user, SysUser sysUser) {
		sysUser.setId(user.getId());
		sysUser.setCode(user.getCode());
		sysUser.setDisplayName(user.getDisplayName());
		sysUser.setLoginName(user.getLoginName());
		sysUser.setPassword(user.getPassword());
		sysUser.setSex(user.getSex());
		sysUser.setCreateDate(user.getCreateDate());
		sysUser.setCreateUser(user.getCreateUser());
		sysUser.setModifyDate(user.getModifyDate());
		sysUser.setModifyUser(user.getModifyUser());
		sysUser.setDeleteFlag(user.getDeleteFlag());
	}
	
	public static void user2User4M(User user, User4M user4m){
		user4m.setId(user.getId());
		user4m.setDeptId(user.getDeptId());
		user4m.setDeptName(user.getDeptName());
		user4m.setDisplayName(user.getDisplayName());
		user4m.setDutyId(user.getDutyId());
		user4m.setEmail(user.getEmail());
		user4m.setLoginName(user.getLoginName());
		user4m.setMobile(user.getMobile());
		user4m.setPhoto(user.getPhoto());
		user4m.setOrgId(user.getOrgId());
		user4m.setOrgName(user.getOrgName());
	}
	
	/**
	  * 根据userId获得user对象
	  * @param userId
	  * @return
	  * @version 1.0 2014年5月13日 上午9:38:47
	*/
	public static User getUser(Long id) {
		User user = new User();
		if(id != null){
			user.setId(id);
			user.setDeleteFlag(null);
			return userService.getUserById(user);
		}
		return null;
	}
}
