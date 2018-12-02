package com.jc.system.security.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.security.dao.ISysOtherDeptsDao;
import com.jc.system.security.domain.SysOtherDepts;

 /**
 * @title GOA2.0
 * @description 用户其他部门任职表 dao实现类
 * @version  2014-04-14
 *
 */
@Repository
public class SysOtherDeptsDaoImpl extends BaseDaoImpl<SysOtherDepts> implements ISysOtherDeptsDao{

	/**
	  * 保存方法
	  * @param List<SysOtherDepts>
	  * @return Integer
	  * @author 高研
	  * @version 1.0 2014年4月22日 上午10:11:28
	*/
	@Override
	public Integer save(List<SysOtherDepts> list) {
		return template.insert(getNameSpace(new SysOtherDepts()) +".insert", list);
	}

	/**
	  * 查询方法
	  * @param SysOtherDepts
	  * @return Integer
	  * @author 高研
	  * @version 1.0 2014年4月22日 上午10:11:28
	*/
	@Override
	public List<SysOtherDepts> query(SysOtherDepts sysOtherDepts) {
		return template.selectList(getNameSpace(new SysOtherDepts()) +".query", sysOtherDepts);
	}

	/**
	  * 删除方法
	  * @param Long userId
	  * @return Integer
	  * @author 高研
	  * @version 1.0 2014年4月22日 上午10:11:28
	*/
	@Override
	public int deleteOtherDept(Long userId) {
		return template.delete(getNameSpace(new SysOtherDepts()) + ".deleteOtherDept" , userId);
	}
	/**
	  * 修改删除状态
	  * @param SysOtherDepts
	  * @return Integer
	  * @author 高研
	  * @version 1.0 2014年4月22日 上午10:11:28
	*/
	@Override
	public Integer updateDeleteFlagByIds(SysOtherDepts sysOtherDepts) {
		return template.update(getNameSpace(new SysOtherDepts()) + ".updateDeleteFlagByIds" , sysOtherDepts);
	}


}