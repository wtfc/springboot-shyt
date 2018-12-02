package com.jc.system.security.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.security.domain.SysOtherDepts;


/**
 * @title GOA2.0
 * @description 用户其他部门任职表 dao接口类
 * @author 
 * @version  2014-04-14
 *
 */
public interface ISysOtherDeptsDao extends IBaseDao<SysOtherDepts>{

	/**
	  * 保存方法
	  * @param List<SysOtherDepts>
	  * @return Integer
	  * @author 高研
	  * @version 1.0 2014年4月22日 上午10:11:28
	*/
	public Integer save(List<SysOtherDepts> list);
	/**
	  * 查询方法
	  * @param SysOtherDepts
	  * @return Integer
	  * @author 高研
	  * @version 1.0 2014年4月22日 上午10:11:28
	*/
	public List<SysOtherDepts> query(SysOtherDepts sysOtherDepts);
	/**
	  * 删除方法
	  * @param Long userId
	  * @return Integer
	  * @author 高研
	  * @version 1.0 2014年4月22日 上午10:11:28
	*/
	public int deleteOtherDept(Long userId);
	/**
	  * 修改删除状态
	  * @param SysOtherDepts
	  * @return Integer
	  * @author 高研
	  * @version 1.0 2014年4月22日 上午10:11:28
	*/
	public Integer updateDeleteFlagByIds(SysOtherDepts sysOtherDepts);
}
