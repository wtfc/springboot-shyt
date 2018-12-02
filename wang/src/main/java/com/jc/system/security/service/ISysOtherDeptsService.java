package com.jc.system.security.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.security.domain.SysOtherDepts;


/**
 * @title GOA2.0
 * @description 用户其他部门任职表 业务接口类
 * @author 
 * @version  2014-04-14
 *
 */
public interface ISysOtherDeptsService extends IBaseService<SysOtherDepts>{
	
	/**
	*  根据主键删除多条记录方法
	* @param SysOtherDepts sysOtherDepts 实体类
	* @return Integer 处理结果
	* @author
	* @version  2014-04-14 
	*/
	public int deleteOtherDept(Long userId);

	/**
	  * 修改删除状态
	  * @param adminSide
	  * @return
	  * @author 高研
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	public Integer updateDeleteFlagByIds(SysOtherDepts sysOtherDepts) ;
}