package com.jc.system.group.util;

import java.util.ArrayList;
import java.util.List;

import com.jc.system.group.domain.Group;
import com.jc.system.group.domain.GroupUser;


/**
 * @title 组别
 * @description 业务共通类
 * @version 2014-07-25 10:44
 */
public class GroupUtils {

	/**
	 * @description 字符串分割保存到bean中
	 * @param Bean Group
	 * @return listBean
	 * @version 2014-07-25 10:44
	 */
    public static List<GroupUser> getGroupUserList(Group group)
    {
    	//定义组用户List
    	List<GroupUser> lstGroupUser = new ArrayList<GroupUser>();
    	
    	//存在组用户的情况
    	if(group.getGroupMember().length()>0 && group.getGroupMember() != null){
    		//用户名称在页面中是根据<,>分隔开的
        	String membersId[] = group.getGroupMember().split(",");
        	
        	//循环遍历用户数组
        	for(String memberId : membersId){
        		GroupUser groupUser = new GroupUser();
        		groupUser.setGroupId(group.getId());
        		groupUser.setUserId(Long.parseLong(memberId));
        		lstGroupUser.add(groupUser);
        	}
    	}
    	return lstGroupUser;
    }
}

