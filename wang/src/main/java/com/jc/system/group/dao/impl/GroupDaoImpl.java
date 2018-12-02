package com.jc.system.group.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.group.dao.IGroupDao;
import com.jc.system.group.domain.Group;

/**
 * @title GOA2.0使用的个人组别和公共组别应用
 * @description  dao实现类
 * @author 
 * @version  2014-07-24
 */
@Repository
public class GroupDaoImpl extends BaseDaoImpl<Group> implements IGroupDao{

	public GroupDaoImpl(){}
	
	/**
	 * 条件查询SQL ID
	 */
	public static final String SQL_ALLGROUPUSERS = "queryAllGroupUsers";
	
	/**
	 * 条件查询SQL ID
	 */
	public static final String SQL_SAMEGROUPCOUNT = "querySameGroupCount";
	
	/** 
	 * 根据查询条件，所有的组及用户
	 * @version  2014-07-29 13:05:00
	 */
	public List<Group> queryAllGroupUsers(Group group) {
		return template.selectList(getNameSpace(group) + "." + SQL_ALLGROUPUSERS, group);
	}
	
	/** 
	 * 根据查询条件，查询该组名称是否存在
	 * @version  2014-07-29 13:05:00
	 */
	public Group querySameGroupCount(Group group) {
		Group sameGroup = (Group)template.selectOne(getNameSpace(group) + "." + SQL_SAMEGROUPCOUNT, group);
		if(null == sameGroup){
			sameGroup = new Group();
		}
		return sameGroup;
	}

}