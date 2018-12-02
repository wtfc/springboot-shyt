package com.jc.system.group.service;

import java.util.List;

import com.jc.foundation.service.IBaseService;
import com.jc.system.group.domain.Group;

/**
 * @title GOA2.0使用的个人组别和公共组别应用
 * @description  业务接口类
 * @version  2014-07-24
 */

public interface IGroupService extends IBaseService<Group>{
	
	//追加组别
	public Integer add(Group group);
	
	//删除组别
	public Integer remove(Group group);
	
	//更新组别
	public Integer updateAll(Group group);
	
	//获得组别和成员
	public Group getAll(Group group);
	
	//根据条件获得所有数据
	public List<Group> getAllGroupUsers(Group group);
	
	//查询该组名称是否存在
	public Group querySameGroupCount(Group group);
	
}