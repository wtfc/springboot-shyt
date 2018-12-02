package com.jc.system.security.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.jc.system.security.domain.AdminSide;
import com.jc.system.security.dao.IAdminSideDao;
import com.jc.foundation.dao.impl.BaseDaoImpl;

/**
 * @title GOA2.0
 * @description  dao实现类
 * @author 
 * @version  2014-04-16
 */
@Repository
public class AdminSideDaoImpl extends BaseDaoImpl<AdminSide> implements IAdminSideDao{

	/**
	  * 删除方法
	  * @param adminSide
	  * @return
	  * @author 高研
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	@Override
	public Integer deleteAdminSide(AdminSide adminSide) {
		return template.delete(getNameSpace(adminSide) + ".deleteAdminSide" , adminSide);
	}

	/**
	  * 查询管理员机构树
	  * @param adminSide
	  * @return
	  * @author 高研
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	@Override
	public List<AdminSide> queryManagerDeptRree(AdminSide adminSide) {
		return template.selectList(getNameSpace(adminSide) + ".queryManagerDeptRree" , adminSide);
	}
	
	/**
	  *  根据部门ID查出AdminSide关联ID
	  * @param adminSide
	  * @return
	  * @author 张继伟
	  * @version 1.0 2014年4月22日 上午10:11:28
	*/
	@Override
	public List<AdminSide> queryAdminSideIdByDeptId(AdminSide adminSide) {
		return template.selectList(getNameSpace(adminSide) + ".queryAdminSideIdByDeptId" , adminSide);
	}


}