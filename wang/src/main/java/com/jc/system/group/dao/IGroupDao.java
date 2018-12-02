package com.jc.system.group.dao;

import java.util.List;

import com.jc.system.group.domain.Group;
import com.jc.foundation.dao.IBaseDao;


/**
 * @title GOA2.0使用的个人组别和公共组别应用
 * @author 
 * @version  2014-07-24
 */
 
public interface IGroupDao extends IBaseDao<Group>{

	//根据查询条件，所有的组及用户
	public List<Group> queryAllGroupUsers(Group group);
	
	//根据查询条件，查询该组名称是否存在
	public Group querySameGroupCount(Group group);
}
