package com.jc.system.group.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.system.CustomException;
import com.jc.system.group.dao.IGroupDao;
import com.jc.system.group.domain.Group;
import com.jc.system.group.domain.GroupUser;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.group.service.IGroupService;
import com.jc.system.group.service.IGroupUserService;
import com.jc.system.group.util.GroupUtils;
import com.jc.system.security.util.UserUtils;

/**
 * @title GOA2.0使用的个人组别和公共组别应用
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-07-24
 */
@Service
public class GroupServiceImpl extends BaseServiceImpl<Group> implements IGroupService{

	@Autowired
	private IGroupUserService groupUserService;
	
	private IGroupDao groupDao;
	
	public IGroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public GroupServiceImpl(){}
	
	@Autowired
	public GroupServiceImpl(IGroupDao groupDao){
		super(groupDao);
		this.groupDao = groupDao;
	}
	
	/**
	 * @description 追加组别
	 * @param Group group 实体类
	 * @param BindingResult result 校验结果
	 * @return Integer 是否成功1:成功 0:失败
	 * @author 都业广
	 * @version 2014-07-25
	 */
	public Integer add(Group group){
		Integer flag = -1;
		try {
			flag = this.save(group);
			if(flag > 0){
				flag = groupUserService.saveList(GroupUtils.getGroupUserList(group));
			}
		} catch (CustomException e) {
			flag = -1;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * @description 删除组别
	 * @param Group group 实体类
	 * @param BindingResult result 校验结果
	 * @return Integer 是否成功1:成功 0:失败
	 * @author 都业广
	 * @version 2014-07-25
	 */
	public Integer remove(Group group){
		Integer flag = -1;
		try {
			flag = this.delete(group, false);
			if(flag > 0){
				GroupUser groupUser = new GroupUser();
				groupUser.setPrimaryKeys(group.getPrimaryKeys());
				flag = groupUserService.delete(groupUser, false);
			}
		} catch (CustomException e) {
			flag = -1;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * @description 获得组别和成员
	 * @param Group group 实体类
	 * @return Group 实体类
	 * @author 都业广
	 * @version 2014-07-28
	 */
	public Group getAll(Group group){
		
		try {
			//获得组别
			group = this.get(group);
			
			//设定组别ID
			GroupUser groupUser = new GroupUser();
			groupUser.setGroupId(group.getId());
			
			//取得该组别的所有成员
			List<GroupUser> lstGroupUser = new ArrayList<GroupUser>();
			lstGroupUser = groupUserService.queryAll(groupUser);
			
			//解析所有成员,拼装成字符串
			String userId = "";
			String userName = "";
			String userJson = "";
			for(GroupUser gu : lstGroupUser){
				userId += gu.getUserId() + ",";
				String name = UserUtils.getUser(gu.getUserId()).getDisplayName();
				userName += name + ",";
				userJson += "{\"id\":\""+gu.getUserId()+"\""+", \"text\":\""+name+"\"},";
			}
			
			//保存成员ID
			group.setMembersId(userId.substring(0, userId.length()-1));
			
			//保存成员姓名
			group.setGroupMember(userName.substring(0, userName.length()-1));
			
			//保存成员json串
			group.setUserJson("[" + userJson.substring(0, userJson.length()-1) + "]");
			
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return group;
	}
	
	/**
	 * @description 更新组别
	 * @param Group group 实体类
	 * @param BindingResult result 校验结果
	 * @return Integer 是否成功1:成功 0:失败
	 * @author 都业广
	 * @version 2014-07-28
	 */
	public Integer updateAll(Group group){
		Integer flag = -1;
		try {
			flag = this.update(group);
			if(flag > 0){
				GroupUser groupUser = new GroupUser();
				String ids[] = {String.valueOf(group.getId())};
				groupUser.setPrimaryKeys(ids);
				flag = groupUserService.delete(groupUser, false);
				
				if(flag > 0){
					flag = groupUserService.saveList(GroupUtils.getGroupUserList(group));
				}
			}
		} catch (CustomException e) {
			flag = -1;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * @description 获得所有组别和成员
	 * @param Group group 实体类
	 * @return Group 实体类
	 * @author 都业广
	 * @version 2014-07-29
	 */
	public List<Group> getAllGroupUsers(Group group){
		
		//获得组别
		List<Group> lstGroup= groupDao.queryAllGroupUsers(group);
		
		return lstGroup;
	}
	
	/**
	 * @description 查询该组名称是否存在
	 * @param Group group 实体类
	 * @return Group 实体类
	 * @author 都业广
	 * @version 2014-07-29
	 */
	public Group querySameGroupCount(Group group){
		
		//获得组别
		group= groupDao.querySameGroupCount(group);
		
		return group;
	}

}