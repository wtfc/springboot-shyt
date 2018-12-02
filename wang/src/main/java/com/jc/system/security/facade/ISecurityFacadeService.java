/**
 * 
 */
package com.jc.system.security.facade;

import java.util.List;

import com.jc.system.security.domain.Role;
import com.jc.system.security.domain.User;



/**
 * @title GOA V2.0
 * @description 用户门面服务类
 * @version  2014年5月14日上午9:07:35
 */
public interface ISecurityFacadeService {
	/**
	  * 根据用户查询该用户所在部门节点下人员
	  * @param user
	  * @return
	  * @version 1.0 2014年4月22日 上午11:06:27
	*/
	List<User> getDeptUsers(User user);
	
	
	/**
	  * 获取用户对应角色
	  * @return 
	  * @version 1.0 2014年5月14日 下午3:59:13
	 */
	List<Role> getUserRoles(User user);
}
